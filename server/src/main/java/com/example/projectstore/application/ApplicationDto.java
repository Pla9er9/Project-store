package com.example.projectstore.application;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

@Data
@AllArgsConstructor
public class ApplicationDto {
    private UUID Id;
    private String name;
    private long numberOfUses;
    private boolean isCommercial;
    private LocalDateTime created;
    private Set<String> redirectUrls;
}
