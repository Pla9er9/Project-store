package com.example.projectstore.issue;

import com.example.projectstore.project.ProjectRepository;
import com.example.projectstore.user.UserDtoSimple;
import com.example.projectstore.user.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class IssueService {

    private final IssueRepository issueRepository;
    private final ProjectRepository projectRepository;
    private final IssueCommentRepository issueCommentRepository;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    public Page<IssueDtoSimple> getAllIssue(UUID projectId, int page) {
        return issueRepository.findAllByProjectId(projectId, PageRequest.of(page, 10)).map(this::issueToIssueDtoSimple);
    }

    public IssueDto getIssue(UUID issueId) {
        var issue = issueRepository.findById(issueId).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        return issueToIssueDto(issue);
    }

    public void createIssue(UUID projectId, NewIssueRequest request, Authentication authentication) {
        var user = userRepository.findByUsername(authentication.getName()).
                orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED));
        var p = projectRepository.findById(projectId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        var issue = Issue
                .builder()
                .created(LocalDateTime.now())
                .createdBy(user)
                .comments(List.of())
                .title(request.getTitle())
                .description(request.getDescription())
                .isOpen(true)
                .project(p)
                .build();

        issueRepository.save(issue);
    }

    public IssueCommentDto addComment(
            NewIssueCommentRequest issueComment,
            UUID issueId,
            Authentication authentication) {
        var i = issueRepository.findById(issueId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        var user = userRepository.findByUsername(authentication.getName())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED));

        var newIssueComment = IssueComment
                .builder()
                .comment(issueComment.getComment())
                .createdBy(user)
                .created(LocalDateTime.now())
                .issue(i)
                .build();

        var issue = issueCommentRepository.save(newIssueComment);
        return new IssueCommentDto(
                new UserDtoSimple(
                        issue.getCreatedBy().getId(),
                        issue.getCreatedBy().getUsername()
                ),
                issue.getComment(),
                issue.getCreated()
        );
    }

    public void closeIssue(UUID issueId, Authentication authentication) {
        var issue = issueRepository.findById(issueId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        if (!issue.getCreatedBy().getUsername().equals(authentication.getName())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }
        issue.setOpen(false);
        issueRepository.save(issue);
    }

    public List<IssueDtoSimple> getOpenedIssuesByUser(String username) {
        return issueRepository.findTop10ByCreatedBy_Username(username).stream()
                .map(this::issueToIssueDtoSimple).collect(Collectors.toList());
    }

    private IssueDtoSimple issueToIssueDtoSimple(Issue issue) {
        return new IssueDtoSimple(
                issue.getId(),
                modelMapper.map(issue.getCreatedBy(), UserDtoSimple.class),
                issue.getTitle(),
                issue.isOpen(),
                issue.getProject().getId(),
                issue.getCreated()
        );
    }

    public IssueDto issueToIssueDto(Issue issue) {
        return new IssueDto(
                issue.getId(),
                new UserDtoSimple(
                        issue.getCreatedBy().getId(),
                        issue.getCreatedBy().getUsername()
                ),
                issue.getTitle(),
                issue.getDescription(),
                issue.isOpen(),
                issue.getCreated(),
                issue.getComments().stream().map(c ->
                        new IssueCommentDto(
                                new UserDtoSimple(
                                        c.getCreatedBy().getId(),
                                        c.getCreatedBy().getUsername()
                                ),
                                c.getComment(),
                                c.getCreated()
                        )
                ).collect(Collectors.toList())
        );
    }
}
