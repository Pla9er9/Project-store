package com.example.projectstore.issue;

import com.example.projectstore.project.Project;
import com.example.projectstore.user.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Issue {
    @Id
    @GeneratedValue
    private UUID id;
    @ManyToOne
    private User createdBy;
    private String title;
    private String description;
    private boolean isOpen;
    private LocalDateTime created;
    @OneToMany(mappedBy = "issue")
    private List<IssueComment> comments;
    @ManyToOne(fetch = FetchType.LAZY)
    private Project project;
}
