package com.example.projectstore.project;

import com.example.projectstore.auth.AuthService;
import com.example.projectstore.config.JwtService;
import com.example.projectstore.file.FileService;
import com.example.projectstore.invitation.InvitationRepository;
import com.example.projectstore.issue.IssueCommentRepository;
import com.example.projectstore.issue.IssueRepository;
import com.example.projectstore.user.User;
import com.example.projectstore.user.UserDtoSimple;
import com.example.projectstore.user.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProjectService {

    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;
    private final FileService fileService;
    private final AuthService authService;
    private final JwtService jwtService;
    private final InvitationRepository invitationRepository;
    private final IssueRepository issueRepository;
    private final IssueCommentRepository issueCommentRepository;

    public ProjectDTO getProject(UUID id, HttpServletRequest request) {
        var project = projectRepository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Project not found"));

        boolean isLiked = false;
        var authHeader = request.getHeader("Authorization");
        if (authHeader != null && authHeader.length() > 8) {
            var authUser = userRepository.findByUsername(jwtService.extractUsername(authHeader.substring(7)));
            if (authUser.isPresent()) {
                if (project.isPrivate()) {
                    var auth = authUser.get().getId().equals(project.getOwner().getId());
                    for (User u : project.getCreators()) {
                        if (authUser.get().getId().equals(u.getId()) || auth) {
                            auth = true;
                            break;
                        }
                    }
                    if (!auth) {
                        throw new ResponseStatusException(HttpStatus.FORBIDDEN);
                    }
                }
                for (Project p : authUser.get().getLikedProjects()
                ) {
                    if (p.getId().equals(project.getId())) {
                        isLiked = true;
                        break;
                    }
                }
            }
        } else if (project.isPrivate()) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }
        var projectDto_ = projectEntityToDto(project);
        projectDto_.setLiked(isLiked);
        return projectDto_;
    }

    public List<ProjectDtoSimple> getAllUserProjects(UUID userId) {
        return projectRepository.findAllByOwnerIdOrderByCreated(userId).stream().map(
                this::projectEntityToSimpleDto).collect(Collectors.toList());
    }

    public String createProject(CreateProjectRequest request, Authentication authentication) {

        var owner = userRepository.findByUsername(authentication.getName())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED));

        Project project = new Project();
        project.setName(request.getName());
        project.setDescription(request.getDescription());
        project.setOwner(owner);
        project.setLikes(List.of());
        project.setLicense(request.getLicense());
        project.setPrivate(request.isPrivate());
        project.setCreated(LocalDateTime.now());

        Project p = projectRepository.save(project);
        fileService.createDirectory("projects", String.valueOf(p.getId()));
        fileService.createDirectory("projects\\" + p.getId(), "release");
        fileService.createDirectory("projects\\" + p.getId(), "code");
        return "{\n\"id\": \"" + project.getId() + "\"\n}";
    }

    public void editProject(
            Authentication authentication,
            EditProjectRequest request,
            UUID projectId) {

        var project = authService.ownerAuthGate(projectId, authentication);
        project.setName(request.getName());
        project.setDescription(request.getDescription());
        project.setPrivate(request.isPrivate());
        project.setTags(request.getTags());

        if (request.getLicense() != null) {
            project.setLicense(request.getLicense());
        }
        projectRepository.save(project);
    }

    public String deleteProject(
            Authentication authentication, UUID projectId) {
        authService.ownerAuthGate(projectId, authentication);
        invitationRepository.deleteAllByProject_Id(projectId);
        issueCommentRepository.deleteAllByIssue_Project_Id(projectId);
        issueRepository.deleteAllByProject_Id(projectId);
        projectRepository.deleteById(projectId);
        fileService.deleteDirectoryInCdn("projects\\" + projectId);
        return "Deleted";
    }

    public void likeProject(UUID projectId, Authentication authentication) {
        var user = userRepository.findByUsername(authentication.getName())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED));

        var found = false;
        for (Project project : user.getLikedProjects()
        ) {
            if (project.getId().equals(projectId)) {
                found = true;
                break;
            }
        }
        if (found) {
            return;
        }

        var p = projectRepository.findById(projectId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        var likes = new ArrayList<>(List.copyOf(p.getLikes()));
        likes.add(user);

        p.setLikes(likes);
        p.setLikesToday(p.getLikesToday() + 1);
        projectRepository.save(p);

        var liked = user.getLikedProjects();
        liked.add(p);
        user.setLikedProjects(liked);
        userRepository.save(user);
    }

    public void unlikeProject(UUID projectId, Authentication authentication) {
        var user = userRepository.findByUsername(authentication.getName())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED));

        for (Project project : user.getLikedProjects()
        ) {
            if (project.getId().equals(projectId)) {
                var likes = new ArrayList<>(List.copyOf(project.getLikes()));
                likes.remove(user);

                project.setLikes(likes);
                project.setLikesToday(project.getLikesToday() - 1);
                projectRepository.save(project);

                var likedProjects = new ArrayList<>(List.copyOf(user.getLikedProjects()));
                likedProjects.remove(project);

                user.setLikedProjects(likedProjects);
                userRepository.save(user);
                return;
            }
        }
    }

    public Set<ProjectDtoSimple> searchByName(String name) {
        var p = projectRepository.searchByName(name.toLowerCase(), PageRequest.of(0, 20));
        return p.stream().map(this::projectEntityToSimpleDto).collect(Collectors.toSet());
    }

    public Set<ProjectDtoSimple> searchByTag(String tag) {
        var p = projectRepository.findByTagsAndIsPrivateFalse(tag, PageRequest.of(0, 20));
        return p.stream().map(this::projectEntityToSimpleDto).collect(Collectors.toSet());
    }

    public Page<ProjectDtoSimple> getTrending(Integer page, String language) {
        var pageable = PageRequest.of(page, 6, Sort.by("likesToday"));
        if (!language.equals("*")) {
            return projectRepository.findByTagsAndIsPrivateFalse(language, pageable).map(this::projectEntityToSimpleDto);
        } else {
            return projectRepository.findByIsPrivateFalse(pageable).map(this::projectEntityToSimpleDto);
        }
    }

    public Page<ProjectDtoSimple> getMostLikedProjects(Integer page) {
        return getMostLikedProjects(page, "*");
    }

    public Page<ProjectDtoSimple> getMostLikedProjects(Integer page, String language) {
        var pageable = PageRequest.of(page, 6, Sort.by("likes"));
        if (!language.equals("*")) {
            return projectRepository.findByMainLanguageAndIsPrivateFalse(language, pageable).map(this::projectEntityToSimpleDto);
        } else {
            return projectRepository.findByIsPrivateFalse(pageable).map(this::projectEntityToSimpleDto);
        }
    }

    public List<ProjectDtoSimple> getLastProjects(String username) {
        return projectRepository.findTop3ByOwner_username(username).stream()
                .map(this::projectEntityToSimpleDto).collect(Collectors.toList());
    }

    public ProjectDTO projectEntityToDto(Project project) {
        return new ProjectDTO(
                project.getId(),
                project.getName(),
                project.getDescription(),
                project.getLicense(),
                project.getLikes().size(),
                null,
                FileUtils.byteCountToDisplaySize(project.getCodeSize()),
                project.getMainLanguage(),
                project.getCreated(),
                new UserDtoSimple(
                        project.getOwner().getId(),
                        project.getOwner().getUsername()),
                project.getCreators().stream().map((u) ->
                        new UserDtoSimple(
                                u.getId(),
                                u.getUsername())
                ).collect(Collectors.toList()),
                project.isPrivate(),
                project.getLatestReleaseVersion(),
                project.getLanguages().stream().map((language) ->
                        new LanguageDto(
                                language.getName(),
                                language.getBytes())
                ).collect(Collectors.toList()),
                project.getTags());
    }

    public ProjectDtoSimple projectEntityToSimpleDto(Project project) {
        return new ProjectDtoSimple(
                project.getId(),
                project.getName(),
                project.getLikes().size(),
                project.getCreated(),
                new UserDtoSimple(
                        project.getOwner().getId(),
                        project.getOwner().getUsername()),
                project.isPrivate(),
                project.getMainLanguage()
        );
    }

    public void removeCreator(UUID id, String username, Authentication authentication) {
        var project = projectRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        var authUser = userRepository.findByUsername(authentication.getName())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED));

        if (!project.getOwner().getUsername().equals(authUser.getUsername())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }

        var updated = new ArrayList<User>();

        for (User u : project.getCreators()
        ) {
            if (!u.getUsername().equals(username)) {
                updated.add(u);
            }
        }

        if (updated.size() == project.getCreators().size()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        project.setCreators(updated);
        projectRepository.save(project);
    }
}
