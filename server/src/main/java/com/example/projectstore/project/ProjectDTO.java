package com.example.projectstore.project;

import com.example.projectstore.user.UserDtoSimple;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProjectDTO {
    private UUID id;
    private String name;
    private String description;
    private String license;
    private int likes;
    private Boolean liked;
    private String codeSize;
    private String mainLanguage;
    private LocalDateTime created;
    private UserDtoSimple owner;
    private List<UserDtoSimple> creators;
    private boolean isPrivate;
    private String latestReleaseVersion;
    List<LanguageDto> languages;
    Set<String> tags;
}
