package com.example.projectstore.issue;

import com.example.projectstore.user.User;
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
public class IssueComment {
    @Id
    @GeneratedValue
    private UUID id;
    @OneToOne
    private User createdBy;
    private String comment;
    private LocalDateTime created;
    @ManyToOne(fetch = FetchType.LAZY)
    private Issue issue;
}
