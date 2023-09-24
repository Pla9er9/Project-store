package com.example.projectstore.auth;

import com.example.projectstore.config.JwtService;
import com.example.projectstore.user.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthResponse signUp(SignUpRequest request) {
        request.setPassword(
                passwordEncoder.encode(request.getPassword()));

        User user = User.builder()
            .username(request.getUsername())
            .email(request.getEmail())
            .password(request.getPassword())
            .avatar(request.getAvatar())
            .build();

        userRepository.save(user);

        var jwtToken = jwtService.generateToken(user);

        return AuthResponse.builder()
            .token(jwtToken)
            .build();
    }

    public AuthResponse signIn(SignInRequest request) {
        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                request.getUsername(),
                request.getPassword())
        );

        User user = userRepository.findByUsername(request.getUsername())
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        var jwtToken = jwtService.generateToken(user);
        return AuthResponse.builder()
            .token(jwtToken)
            .build();
    }
    public Optional LogOut() {
        return null;
    }
}
