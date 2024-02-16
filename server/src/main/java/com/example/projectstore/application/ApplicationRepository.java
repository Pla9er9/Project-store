package com.example.projectstore.application;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Repository
public interface ApplicationRepository extends JpaRepository<Application, UUID> {
    @Query("SELECT app FROM Application app WHERE app.owner.username = :username")
    Set<Application> getUserApplication(@Param("username") String username);

    Optional<Application> findByIdAndOwner_Username(UUID Id, String Username);

    void deleteByIdAndOwner_Username(UUID Id, String Username);
}
