package com.example.projectstore.issue;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.UUID;

@RestController
@RequestMapping("api/v1/issue/{id}")
@RequiredArgsConstructor
public class IssueController {
    private final IssueService issueService;

    @GetMapping
    public ResponseEntity<IssueDto> getIssue(@PathVariable UUID id) {
        return ResponseEntity.ok(issueService.getIssue(id));
    }

    @PostMapping("comment")
    public IssueCommentDto addComent(
            @PathVariable UUID id,
            @RequestBody NewIssueCommentRequest issueComment,
            Authentication authentication
    ) {
        return issueService.addComment(issueComment, id, authentication);
    }

}
