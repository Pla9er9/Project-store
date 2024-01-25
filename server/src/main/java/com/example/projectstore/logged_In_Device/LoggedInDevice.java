package com.example.projectstore.logged_In_Device;

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
public class LoggedInDevice {
    @Id
    @GeneratedValue
    private UUID id;
    private String userAgent;
    private LocalDateTime loggedInTime;
    private String jwtToken;
    private boolean blocked = false;
    @ManyToOne(fetch = FetchType.LAZY)
    private User account;
}
