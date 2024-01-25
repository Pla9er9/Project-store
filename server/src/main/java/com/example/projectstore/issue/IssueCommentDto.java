package com.example.projectstore.issue;

import com.example.projectstore.user.UserDtoSimple;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class IssueCommentDto {
    private UserDtoSimple createdBy;
    private String comment;
    private LocalDateTime created;
}
