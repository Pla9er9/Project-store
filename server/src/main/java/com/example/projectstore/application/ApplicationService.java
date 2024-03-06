package com.example.projectstore.application;

import com.example.projectstore.account.AccountDto;
import com.example.projectstore.application.accessToken.AccessTokenRepository;
import com.example.projectstore.user.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.text.RandomStringGenerator;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import com.example.projectstore.util.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ApplicationService {

    private final ApplicationRepository applicationRepository;
    private final AccessTokenRepository accessTokenRepository;
    private final UserRepository userRepository;
    private final AESUtil aesUtil;

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
        var randomString = generateRandomString();
        var encrypted = aesUtil.encrypt(randomString);
        if (encrypted.equals(randomString)) {
            log.info("Could not encrypt string with aes_utils");
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        var app = new Application(
                null,
                request.getName(),
                0,
                request.isCommercial(),
                LocalDateTime.now(),
                encrypted,
                request.getAllowedRedirectUrls(),
                Set.of(),
                user
        );
        applicationRepository.save(app);
    }

    public ApplicationDto getApplicationInfo(UUID id, String username) {
        var app = applicationRepository.findByIdAndOwner_Username(id, username)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        return entityToDto(app);
    }

    public String getApplicationName(UUID id) {
        var app = applicationRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        return app.getName();
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

        var randomString = generateRandomString();
        var encrypted = aesUtil.encrypt(randomString);
        if (encrypted.equals(randomString)) {
            log.info("Could not encrypt string with aes_utils");
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        app.setSecret(encrypted);
        applicationRepository.save(app);
        return randomString;
    }

    public void deleteApplication(UUID id, String username) {
        accessTokenRepository.deleteAllByApplication_IdAndUserAccount_Username(id, username);
        applicationRepository.deleteByIdAndOwner_Username(id, username);
    }

    public String generateRandomString() {
        RandomStringGenerator pwdGenerator = new RandomStringGenerator.Builder().withinRange(97, 122)
                .build();
        return pwdGenerator.generate(32);
    }

    public AccountDto getAccountData(String accessToken, String secret) {
        var token = accessTokenRepository.findByToken(accessToken)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        var appEncryptedSecret = token.getApplication().getSecret();
        var decrypted = aesUtil.decrypt(appEncryptedSecret);
        if (decrypted.equals(appEncryptedSecret)) {
            log.info("Could not decrypt string with aes_utils");
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if (!decrypted.equals(secret)) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }

        var user = token.getUserAccount();

        return new AccountDto(
                user.getUsername(),
                user.getDescription(),
                user.getEmail(),
                user.getFirstname(),
                user.getLastname(),
                List.of(user.getPersonalLinks().split(";"))
        );
    }
}
