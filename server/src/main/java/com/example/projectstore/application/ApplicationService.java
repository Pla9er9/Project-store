package com.example.projectstore.application;

import com.example.projectstore.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.Random;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

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
                app.getSecret()
        );
    }

    public void newSecret(UUID id, String username) {
        var app = applicationRepository.findByIdAndOwner_Username(id, username)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        app.setSecret(generateSecret());
        applicationRepository.save(app);
    }

    public void deleteApplication(UUID id, String username) {
        applicationRepository.deleteByIdAndOwner_Username(id, username);
    }

    public String generateSecret() {
        Random random = new SecureRandom();
        IntStream specialChars = random.ints(32, 33, 45);
        return specialChars.mapToObj(data -> (char) data)
                .map(Object::toString).collect(Collectors.joining());
    }
}
