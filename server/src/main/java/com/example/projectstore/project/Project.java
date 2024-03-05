package com.example.projectstore.project;

import com.example.projectstore.invitation.Invitation;
import com.example.projectstore.issue.Issue;
import com.example.projectstore.user.User;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Entity
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Project {
    @Id
    @GeneratedValue
    private UUID id;
    @NotNull
    private String name;
    private String description;
    private String license;
    private long codeSize;
    @ManyToOne(fetch = FetchType.LAZY)
    private User owner;
    private boolean isPrivate;
    private LocalDateTime created;
    private int numberOfFiles;
    private String websiteLink;
    private String latestReleaseVersion;
    @ManyToMany(mappedBy = "likedProjects")
    private List<User> likes;
    private int likesToday;
    @ManyToMany(cascade = CascadeType.ALL)
    private List<User> creators;
    @OneToMany(cascade = CascadeType.ALL)
    private List<Language> languages;
    private String mainLanguage;
    @ElementCollection(targetClass = String.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "project_tags", joinColumns = @JoinColumn(name = "project_id"))
    @Column(name = "tag", nullable = false)
    private Set<String> tags;
    @OneToMany
    private List<Invitation> invitations;
    @OneToMany(mappedBy = "project")
    private List<Issue> issues;
}
