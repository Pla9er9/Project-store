package com.example.projectstore.application;

import com.example.projectstore.user.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.time.LocalDateTime;
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
    @ManyToOne
    private User owner;
}
