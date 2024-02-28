package com.example.projectstore.project;

import com.example.projectstore.file.FileService;
import com.example.projectstore.invitation.InvitationDto;
import com.example.projectstore.invitation.InvitationService;
import com.example.projectstore.invitation.NewInvitationRequest;
import com.example.projectstore.issue.*;
import com.example.projectstore.release.ReleaseService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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

    @GetMapping
    public ResponseEntity<List<ProjectDTO>> getAllUserProjects(
            @RequestParam UUID userId
    ) {
        if (userId == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        return ResponseEntity.ok(projectService.getAllUserProjects(userId));
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
        return projectService.getProject(id, request);
    }

    @PostMapping
    public ResponseEntity<String> createProject(
            @RequestBody CreateProjectRequest request,
            Authentication authentication
    ) {
        return ResponseEntity.ok(projectService.createProject(
                request, authentication));
    }

    @PutMapping("/{id}")
    public void editProject(
            @PathVariable UUID id,
            @RequestBody EditProjectRequest request,
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
            @PathVariable UUID id
    ) {
        return invitationService.getAllInvitations(id);
    }

    @PostMapping("{id}/invitations")
    public InvitationDto sendInvitationToProject(
            @PathVariable UUID id,
            @RequestBody NewInvitationRequest request,
            Authentication authentication
    ) {
        return invitationService.sendInvitation(id, request, authentication);
    }

    @GetMapping(value = "{id}/download", produces = "application/zip")
    public byte[] getProjectCode(
            @PathVariable UUID id
    ) throws IOException {
        return fileService.getZip(id);
    }

    @GetMapping("{id}/files")
    public ResponseEntity<String> getFile(
            @PathVariable UUID id,
            @Nullable @RequestParam(defaultValue = "", required = false) String path
    ) {
        return ResponseEntity.ok(fileService.getByPathInProject(id, path));
    }

    @PostMapping("{id}/files")
    public ResponseEntity<String> uploadFile(
            @PathVariable UUID id,
            @RequestParam(required = false, defaultValue = "") String path,
            @RequestParam("file") MultipartFile file,
            Authentication authentication
    ) {
        fileService.uploadFileToProject(id, path, file, authentication);
        return ResponseEntity.ok("");
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
    public ResponseEntity<List<IssueDtoSimple>> getIssues(@PathVariable UUID id) {
        return ResponseEntity.ok(issueService.getAllIssue(id));
    }

    @PostMapping("{id}/issues")
    public void createIssue(
            @PathVariable UUID id,
            @RequestBody NewIssueRequest newIssueRequest,
            Authentication authentication
    ) {
        issueService.createIssue(id, newIssueRequest, authentication);
    }

    @GetMapping("{id}/issues/{issueId}")
    public ResponseEntity<IssueDto> getIssue(@PathVariable UUID issueId) {
        return ResponseEntity.ok(issueService.getIssue(issueId));
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
            HttpServletResponse response
    ) {

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
