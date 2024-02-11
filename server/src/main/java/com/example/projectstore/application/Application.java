package com.example.projectstore.application;

import com.example.projectstore.user.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Application {
    @Id
    @GeneratedValue
    private UUID id;
    private String name;
    private long numberOfUses;
    private boolean isCommercial;
    private LocalDateTime created;
    private String secret;
    @ElementCollection(targetClass = String.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "allowedUrls", joinColumns = @JoinColumn(name = "application_id"))
    @Column(name = "allowedUrl", nullable = false)
    private List<String> allowedUrls;
    @ManyToOne
    private User owner;
}
