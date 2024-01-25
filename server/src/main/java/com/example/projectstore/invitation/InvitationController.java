package com.example.projectstore.invitation;

import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/invitation")
public class InvitationController {

    private final InvitationService invitationService;

    @GetMapping("/{invitationId}/accept")
    public void acceptInvitation(
            @PathVariable UUID invitationId,
            Authentication authentication
    ) {
        invitationService.acceptInvitation(invitationId, authentication);
    }

    @DeleteMapping("/{invitationId}")
    public void deleteInvitation(
            @PathVariable UUID invitationId,
            Authentication authentication
    ) {
        invitationService.deleteInvitation(invitationId, authentication);
    }
}
