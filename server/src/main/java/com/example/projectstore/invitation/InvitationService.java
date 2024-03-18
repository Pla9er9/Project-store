package com.example.projectstore.invitation;

import com.example.projectstore.auth.AuthService;
import com.example.projectstore.config.JwtService;
import com.example.projectstore.project.ProjectRepository;
import com.example.projectstore.user.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class InvitationService {

    private final InvitationRepository invitationRepository;
    private final UserRepository userRepository;
    private final ProjectRepository projectRepository;
    private final AuthService authService;
    private final JwtService jwtService;

    public List<InvitationDto> getAllInvitations(UUID projectId) {
        return invitationRepository.findAllByProject_Id(projectId)
                .stream().map(this::mapInvitationEntityToDto).collect(Collectors.toList());
    }

    public List<InvitationDto> getAllInvitations(String username) {
        return invitationRepository.findAllByInvitedUsername(username)
                .stream().map(this::mapInvitationEntityToDto).collect(Collectors.toList());
    }

    public InvitationDto mapInvitationEntityToDto(Invitation invitation) {
        return new InvitationDto(
                invitation.getId(),
                invitation.getInvitedUsername(),
                invitation.getProject().getName(),
                invitation.getCreated());
    }

    public InvitationDto sendInvitation(
            UUID projectId,
            NewInvitationRequest request,
            Authentication authentication
    ) {


        userRepository.findByUsername(request.getUsername()
        ).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        var existInvitation = invitationRepository.findByInvitedUsernameAndProject_Id(request.getUsername(), projectId);
        if (existInvitation.isPresent()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT);
        }

        var project = projectRepository.findById(projectId
        ).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        if (!project.getOwner().getUsername().equals(authentication.getName())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }

        Invitation invitation = Invitation
                .builder()
                .invitedUsername(request.getUsername())
                .project(project)
                .created(LocalDateTime.now())
                .build();

        invitation = invitationRepository.save(invitation);

        return new InvitationDto(
                invitation.getId(),
                invitation.getInvitedUsername(),
                invitation.getProject().getName(),
                invitation.getCreated()
        );
    }

    public void acceptInvitation(UUID id, Authentication authentication) {
        var user = userRepository.findByUsername(authentication.getName())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED));

        var invitation = invitationRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        if (!invitation.getInvitedUsername().equals(authentication.getName())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }

        var project = invitation.getProject();

        var creators = project.getCreators();
        creators.add(user);
        project.setCreators(creators);
        projectRepository.save(project);
        invitationRepository.deleteById(invitation.getId());
    }

    public void deleteInvitation(UUID id, Authentication authentication) {
        var invitation = invitationRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        if (!invitation.getInvitedUsername().equals(authentication.getName()) &&
                !invitation.getProject().getOwner().getUsername().equals(authentication.getName())
        ) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }
        invitationRepository.deleteById(id);
    }
}
