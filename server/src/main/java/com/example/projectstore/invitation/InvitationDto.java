package com.example.projectstore.invitation;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
public class InvitationDto {
    private UUID id;
    private String invitedUsername;
    private String projectName;
    private LocalDateTime created;
}
