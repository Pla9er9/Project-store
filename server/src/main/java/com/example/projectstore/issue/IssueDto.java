package com.example.projectstore.issue;

import com.example.projectstore.user.UserDtoSimple;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
public class IssueDto {
    private UUID id;
    private UserDtoSimple createdBy;
    private String title;
    private String description;
    private boolean isOpen;
    private LocalDateTime created;
    List<IssueCommentDto> comments;
}
