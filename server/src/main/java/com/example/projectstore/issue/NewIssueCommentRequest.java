package com.example.projectstore.issue;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class NewIssueCommentRequest {
    @NotNull
    @Size(min = 1)
    private String comment;
}
