package com.example.projectstore.project;

import com.example.projectstore.auth.AuthService;
import com.example.projectstore.file.FileService;
import com.example.projectstore.invitation.InvitationDto;
import com.example.projectstore.invitation.InvitationService;
import com.example.projectstore.invitation.NewInvitationRequest;
import com.example.projectstore.issue.*;
import com.example.projectstore.release.ReleaseService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/project")
@RequiredArgsConstructor
@Slf4j
public class ProjectController {

    private final ProjectService projectService;
    private final FileService fileService;
    private final IssueService issueService;
    private final ReleaseService releaseService;
    private final InvitationService invitationService;
    private final AuthService authService;

    @GetMapping
    public ResponseEntity<List<ProjectDtoSimple>> getAllUserProjects(
            @RequestParam UUID userId,
            HttpServletRequest request
    ) {
        return ResponseEntity.ok(projectService.getAllUserProjects(userId, request.getHeader("Authorization")));
    }

    @GetMapping("/trending")
    public Page<ProjectDtoSimple> getRecommendedProjects(
            @RequestParam(defaultValue = "0", required = false) Integer page,
            @RequestParam(defaultValue = "*", required = false) String language
    ) {
        return projectService.getTrending(page, language);
    }

    @GetMapping("/mostLiked")
    public Page<ProjectDtoSimple> getMostLikedProjects(
            @RequestParam(defaultValue = "0", required = false) Integer page,
            @RequestParam(defaultValue = "*", required = false) String language
    ) {
        return projectService.getMostLikedProjects(page, language);
    }

    @GetMapping("/{id}")
    public ProjectDTO getProject(
            @PathVariable UUID id,
            HttpServletRequest request
    ) {
        return projectService.getProject(id, request.getHeader("Authorization"));
    }

    @PostMapping
    public ResponseEntity<String> createProject(
            @Valid @RequestBody CreateProjectRequest request,
            Authentication authentication
    ) {
        return ResponseEntity.ok(projectService.createProject(
                request, authentication));
    }

    @PutMapping("/{id}")
    public void editProject(
            @PathVariable UUID id,
            @Valid @RequestBody EditProjectRequest request,
            Authentication authentication
    ) {
        projectService.editProject(authentication, request, id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProject(
            @PathVariable UUID id,
            Authentication authentication
    ) {
        return ResponseEntity.ok(projectService.deleteProject(
                authentication, id));
    }

    @PostMapping("/{id}/like")
    public void likeProject(
            @PathVariable UUID id,
            Authentication authentication
    ) {
        projectService.likeProject(id, authentication);
    }

    @PutMapping("/{id}/unlike")
    public void unlikeProject(
            @PathVariable UUID id,
            Authentication authentication
    ) {
        projectService.unlikeProject(id, authentication);
    }

    @GetMapping("{id}/invitations")
    public List<InvitationDto> getAllProjectInvitations(
            @PathVariable UUID id,
            HttpServletRequest request
    ) {
        authService.ownerAuthGateByToken(id, request.getHeader("Authorization"));
        return invitationService.getAllInvitations(id);
    }

    @PostMapping("{id}/invitations")
    public InvitationDto sendInvitationToProject(
            @PathVariable UUID id,
            @Valid @RequestBody NewInvitationRequest request,
            Authentication authentication
    ) {
        return invitationService.sendInvitation(id, request, authentication);
    }

    @DeleteMapping("{id}/invitation/{invitationId}")
    public void sendInvitationToProject(
            @PathVariable UUID id,
            @PathVariable UUID invitationId,
            Authentication authentication
    ) {
        invitationService.deleteInvitation(invitationId, authentication);
    }

    @DeleteMapping("{id}/creators/{username}")
    public void removeCreator(
            @PathVariable UUID id,
            @PathVariable String username,
            Authentication authentication
    ) {
        projectService.removeCreator(id, username, authentication);
    }

    @GetMapping(value = "{id}/download", produces = "application/zip")
    public byte[] getProjectCode(
            @PathVariable UUID id,
            HttpServletRequest request
    ) throws IOException {
        authService.creatorAuthGateByToken(id, request.getHeader("Authorization"), true);
        return fileService.getZip(id);
    }

    @GetMapping("{id}/files")
    public ResponseEntity<String> getFile(
            @PathVariable UUID id,
            @Nullable @RequestParam(defaultValue = "", required = false) String path,
            HttpServletRequest request
    ) {
        authService.creatorAuthGateByToken(id, request.getHeader("Authorization"), true);
        return ResponseEntity.ok(fileService.getByPathInProject(id, path));
    }

    @PostMapping("{id}/files")
    public void uploadFile(
            @PathVariable UUID id,
            @RequestParam(required = false, defaultValue = "") String path,
            @RequestParam("file") MultipartFile file,
            Authentication authentication
    ) {
        fileService.uploadFileToProject(id, path, file, authentication);
    }

    @PutMapping("{id}/files")
    public void editFile(
            @PathVariable UUID id,
            @RequestParam String path,
            @RequestParam("file") MultipartFile file,
            Authentication authentication
    ) {
        fileService.editFile(id, path, file, authentication);
    }

    @DeleteMapping("{id}/files")
    public void deleteFile(
            @PathVariable UUID id,
            @RequestParam String path,
            Authentication authentication
    ) {
        fileService.deleteFileInProject(id, path, authentication);
    }

    @GetMapping("{id}/issues")
    public ResponseEntity<Page<IssueDtoSimple>> getIssues(
            @PathVariable UUID id,
            @RequestParam(required = false, defaultValue = "0") int page,
            HttpServletRequest request) {
        authService.creatorAuthGateByToken(id, request.getHeader("Authorization"), true);
        return ResponseEntity.ok(issueService.getAllIssue(id, page));
    }

    @PostMapping("{id}/issues")
    public void createIssue(
            @PathVariable UUID id,
            @Valid @RequestBody NewIssueRequest newIssueRequest,
            Authentication authentication
    ) {
        issueService.createIssue(id, newIssueRequest, authentication);
    }

    @PostMapping("{id}/issues/{issueId}/close")
    public void closeIssue(
            @PathVariable UUID id,
            @PathVariable UUID issueId,
            Authentication authentication) {
        issueService.closeIssue(issueId, authentication);
    }

    @GetMapping("{id}/release")
    public @ResponseBody byte[] getRelease(
            @PathVariable UUID id,
            HttpServletRequest request,
            HttpServletResponse response
    ) {
        authService.creatorAuthGateByToken(id, request.getHeader("Authorization"), true);
        var release = releaseService.getLatestRelease(id);
        response.setContentType("application/force-download");
        response.setContentLength(release.getBytes().length);
        response.setHeader("Content-Transfer-Encoding", "binary");
        response.setHeader("Content-Disposition", "attachment; filename=\"" + release.getFilename() + "\"");
        return release.getBytes();
    }

    @PostMapping("{id}/release/{version}")
    public void uploadRelease(
            @PathVariable UUID id,
            @RequestParam("file") MultipartFile file,
            @PathVariable String version,
            Authentication authentication
    ) {
        releaseService.uploadRelease(id, file, version, authentication);
    }

    @DeleteMapping("{id}/release")
    public void deleteRelease(
            @PathVariable UUID id,
            Authentication authentication
    ) {
        releaseService.deleteRelease(id, authentication);
    }
}
