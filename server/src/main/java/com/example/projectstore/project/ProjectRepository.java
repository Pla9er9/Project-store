package com.example.projectstore.project;

import org.jetbrains.annotations.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.persistence.LockModeType;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Repository
public interface ProjectRepository extends JpaRepository<Project, UUID> {

    List<Project> findAllByOwnerIdAndIsPrivateFalseOrderByCreated(@Param("ownerId") UUID ownerId);

    @Query("SELECT p FROM Project p LEFT JOIN p.creators c WHERE p.owner.id = :ownerId AND (p.owner.username = :username OR p.isPrivate = FALSE OR c.username = :username) ORDER BY p.created")
    Set<Project> findAllByOwnerId(@Param("ownerId") UUID ownerId, @Param("username") String username);

    @Modifying
    @Transactional
    @Query("UPDATE Project p set p.numberOfFiles = p.numberOfFiles + :nf WHERE p.id = :id")
    void updateNumberOfFiles(@Param("id") UUID id, @Param("nf") int numberOfFiles);

    @Modifying
    @Transactional
    @Query("UPDATE Project p set p.codeSize = p.codeSize + :cs WHERE p.id = :id")
    void updateCodeSize(@Param("id") UUID id, @Param("cs") long codesize);

    @Query("SELECT p FROM Project p WHERE lower(p.name) LIKE %:name% AND p.isPrivate = FALSE")
    Page<Project> searchByName(@Param("name") String name, Pageable pageable);

    Page<Project> findByTagsAndIsPrivateFalse(String tag, Pageable pageable);

    @Override
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @NotNull
    Project save(@NotNull Project project);

    Page<Project> findByMainLanguageAndIsPrivateFalse(@Param("lang") String language, Pageable pageable);

    Page<Project> findByIsPrivateFalse(Pageable pageable);

    List<Project> findTop3ByOwner_username(String username);

    Optional<Project> findByName(String name);
}
