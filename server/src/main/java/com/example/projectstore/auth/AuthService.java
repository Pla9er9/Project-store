package com.example.projectstore.auth;

import com.example.projectstore.application.accessToken.AccessToken;
import com.example.projectstore.application.accessToken.AccessTokenRepository;
import com.example.projectstore.application.ApplicationRepository;
import com.example.projectstore.application.ApplicationService;
import com.example.projectstore.config.JwtService;
import com.example.projectstore.file.FileService;
import com.example.projectstore.project.Project;
import com.example.projectstore.project.ProjectRepository;
import com.example.projectstore.user.*;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final ProjectRepository projectRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final ApplicationService applicationService;
    private final ApplicationRepository applicationRepository;
    private final AccessTokenRepository accessTokenRepository;
    private FileService fileService;

    public void setFileService(@Lazy FileService fileService) {
        this.fileService = fileService;
    }


    public AuthResponse signUp(SignUpRequest request) {
        request.setPassword(
                passwordEncoder.encode(request.getPassword()));

        User user = User.builder()
                .username(request.getUsername())
                .email(request.getEmail())
                .password(request.getPassword())
                .firstname(request.getFirstname())
                .lastname(request.getLastname())
                .build();
        userRepository.save(user);
        fileService.createDirectory("/avatars", String.valueOf(user.getId()));
        var jwtToken = jwtService.generateToken(user);

        return AuthResponse.builder()
                .token(jwtToken)
                .userId(user.getId())
                .build();
    }

    public AuthResponse signIn(SignInRequest request) {
        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword())
        );

        var jwtToken = jwtService.generateToken(user);
        return AuthResponse.builder()
                .token(jwtToken)
                .userId(user.getId())
                .build();
    }

    public String Oauth2SignIn(SignInRequest request, String redirectUrl, UUID appId) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword())
        );

        var app = applicationRepository.findById(appId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        if (!app.getAllowedUrls().contains(redirectUrl)) {
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, "Url is not in allowed url list");
        }

        var user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED));

        var randomString = applicationService.generateRandomString();

        var newAccessToken = new AccessToken(
                null,
                randomString,
                LocalDateTime.now(),
                app,
                user
        );

        accessTokenRepository.save(newAccessToken);
        app.setNumberOfUses(app.getNumberOfUses() + 1);
        applicationRepository.save(app);

        return randomString;
    }

    public Project ownerAuthGate(UUID projectId, String name) {
        var user = userRepository.findByUsername(name)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED));

        var p = projectRepository.findById(projectId).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        if (!Objects.equals(p.getOwner().getId(), user.getId())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }
        return p;
    }

    public Project ownerAuthGateByToken(UUID projectId, String token) {
        if (token == null || token.length() < 8) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }
        var name = jwtService.extractUsername(token.substring(7));
        return ownerAuthGate(projectId, name);
    }

    public Project creatorAuthGateByToken(UUID projectId, String token, boolean isAllowedIfPublic) {
        if (!isAllowedIfPublic && (token == null || token.length() < 8)) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }
        String name = null;

        if (!isAllowedIfPublic) {
            name = jwtService.extractUsername(token.substring(7));
        }
        return creatorAuthGate(projectId, name, isAllowedIfPublic);
    }

    public Project creatorAuthGate(UUID projectId, String name) {
        return creatorAuthGate(projectId, name, false);
    }

    public Project creatorAuthGate(UUID projectId, String name, boolean isAllowedIfPublic) {
        var p = projectRepository.findById(projectId).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        if (isAllowedIfPublic && !p.isPrivate()) {
            return p;
        }

        var user = userRepository.findByUsername(name)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED));

        var found = false;

        if (p.getOwner().getId().equals(user.getId())) {
            return p;
        }

        for (int i = 0; i < p.getCreators().size(); i++) {
            if (p.getCreators().get(i).getId().equals(user.getId())) {
                found = true;
                break;
            }
        }
        if (!found) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }
        return p;
    }
}
