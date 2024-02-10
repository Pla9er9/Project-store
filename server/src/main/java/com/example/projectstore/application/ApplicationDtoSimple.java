package com.example.projectstore.application;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
public class ApplicationDtoSimple {
    private UUID id;
    private String name;
    private LocalDateTime created;
}
