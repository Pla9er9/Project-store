package com.example.projectstore.issue;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class NewIssueRequest {
    @NotNull
    @Size(min = 1, max = 30)
    private String title;
    @NotNull
    @Size(max = 3000)
    private String description;
}
