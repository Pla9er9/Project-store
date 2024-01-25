package com.example.projectstore.invitation;

import com.example.projectstore.project.Project;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Invitation {
    @Id
    @GeneratedValue
    private UUID id;
    private String invitedUsername;
    @ManyToOne(fetch = FetchType.LAZY)
    private Project project;
    private LocalDateTime created;
}
