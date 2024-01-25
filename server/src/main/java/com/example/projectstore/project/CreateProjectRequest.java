package com.example.projectstore.project;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class CreateProjectRequest {
    @NotNull
    @Size(min = 1, max = 20)
    private String name;
    @NotNull
    private String description;
    @NotNull
    private String license;
    private boolean isPrivate;
}
