package com.example.projectstore.invitation;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface InvitationRepository extends JpaRepository<Invitation, UUID> {
    List<Invitation> findAllByProject_Id(@Param("id") UUID projectId);

    Optional<Invitation> findByInvitedUsernameAndProject_Id(
            @Param("InvitedUsername") String username,
            @Param("id") UUID projectId);

    List<Invitation> findAllByInvitedUsername(
            @Param("InvitedUsername") String username);

    @Transactional
    void deleteAllByProject_Id(UUID projectId);
}
