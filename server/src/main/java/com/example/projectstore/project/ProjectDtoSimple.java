package com.example.projectstore.project;

import com.example.projectstore.user.UserDtoSimple;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProjectDtoSimple {
    private UUID id;
    private String name;
    private int likes;
    private LocalDateTime created;
    private UserDtoSimple owner;
    private Boolean isPrivate;
    private String mainLanguage;
}
