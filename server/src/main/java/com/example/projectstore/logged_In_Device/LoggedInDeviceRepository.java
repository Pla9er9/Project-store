package com.example.projectstore.logged_In_Device;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface LoggedInDeviceRepository extends JpaRepository<LoggedInDevice, UUID> {
    List<LoggedInDevice> findByBlockedFalseAndAccount_username(String username);

    Optional<LoggedInDevice> findByJwtToken(String token);
}
