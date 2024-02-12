package com.example.projectstore.application;

import com.example.projectstore.user.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.text.RandomStringGenerator;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ApplicationService {

    private final ApplicationRepository applicationRepository;
    private final UserRepository userRepository;

    public Set<ApplicationDtoSimple> getApplications(String username) {
        var user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED));
        return applicationRepository.getUserApplication(user.getUsername())
                .stream().map(this::entityToDtoSimple).collect(Collectors.toSet());
    }

    public void createNewApplication(NewApplicationRequest request, String username) {
        var user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED));
        if (user.getApplications().size() == 3) {
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE);
        }
        var app = new Application(
                null,
                request.getName(),
                0,
                request.isCommercial(),
                LocalDateTime.now(),
                generateSecret(),
                request.getAllowedRedirectUrls(),
                user
        );
        applicationRepository.save(app);
    }

    public ApplicationDto getApplicationInfo(UUID id, String username) {
        var app = applicationRepository.findByIdAndOwner_Username(id, username)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        return entityToDto(app);
    }

    public ApplicationDtoSimple entityToDtoSimple(Application app) {
        return new ApplicationDtoSimple(
                app.getId(),
                app.getName(),
                app.getCreated()
        );
    }

    public ApplicationDto entityToDto(Application app) {
        return new ApplicationDto(
                app.getId(),
                app.getName(),
                app.getNumberOfUses(),
                app.isCommercial(),
                app.getCreated(),
                Set.copyOf(app.getAllowedUrls())
        );
    }

    public String newSecret(UUID id, String username) {
        var app = applicationRepository.findByIdAndOwner_Username(id, username)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        var secret = generateSecret();
        app.setSecret(secret);
        applicationRepository.save(app);
        return secret;
    }

    public void deleteApplication(UUID id, String username) {
        applicationRepository.deleteByIdAndOwner_Username(id, username);
    }

    public String generateSecret() {
        RandomStringGenerator pwdGenerator = new RandomStringGenerator.Builder().withinRange(97, 122)
                .build();
        return pwdGenerator.generate(32);
    }
}
