package com.example.projectstore.application.accessToken;

import com.example.projectstore.application.Application;
import com.example.projectstore.user.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccessToken {
    @Id
    @GeneratedValue
    private UUID id;
    private String token;
    private LocalDateTime createdTime;
    @ManyToOne
    private Application application;
    @OneToOne
    private User userAccount;
}
