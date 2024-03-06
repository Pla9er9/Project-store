package com.example.projectstore.application.accessToken;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface AccessTokenRepository extends JpaRepository<AccessToken, UUID> {
    Optional<AccessToken> findByToken(String token);

    @Transactional
    void deleteAllByApplication_IdAndUserAccount_Username(UUID applicationId, String username);
}
