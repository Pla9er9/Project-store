package com.example.projectstore.issue;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class NewIssueRequest {
    @NotNull
    @Size(min = 1, max = 60)
    private String title;
    @NotNull
    private String description;
}
