package com.example.projectstore.application;

import com.example.projectstore.user.User;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
public class ApplicationDto {
    private UUID Id;
    private String name;
    private long numberOfUses;
    private boolean isCommercial;
    private LocalDateTime created;
    private String secret;
}
