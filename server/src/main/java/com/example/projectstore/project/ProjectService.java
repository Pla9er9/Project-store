package com.example.projectstore.project;

import com.example.projectstore.auth.AuthService;
import com.example.projectstore.config.JwtService;
import com.example.projectstore.file.FileService;
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

    public List<ProjectDTO> getAllUserProjects(UUID userId) {
        return projectRepository.findAllByOwnerIdOrderByCreated(userId).stream().map(
                this::projectEntityToDto).collect(Collectors.toList());
    }

    public String createProject(CreateProjectRequest request, Authentication authentication) {

        var owner = userRepository.findByUsername(authentication.getName())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED));

        log.info(request.getLicense());
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
        project.setPrivate(project.isPrivate());
        project.setTags(request.getTags());

        if (request.getLicense() != null) {
            project.setLicense(request.getLicense());
        }
        projectRepository.save(project);
    }

    public String deleteProject(
            Authentication authentication, UUID projectId) {
        authService.ownerAuthGate(projectId, authentication);
        projectRepository.deleteById(projectId);
        fileService.deleteDirectoryInCdn(String.valueOf(projectId));
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
        var p = projectRepository.searchByTag(tag, PageRequest.of(0, 20));
        return p.stream().map(this::projectEntityToSimpleDto).collect(Collectors.toSet());
    }

    public Page<ProjectDTO> getTrending(Integer page) {
        var pageable = PageRequest.of(page, 6, Sort.by("likesToday"));
        return projectRepository.findAll(pageable).map(this::projectEntityToDto);
    }

    public Page<ProjectDTO> getMostLikedProjects(Integer page) {
        var pageable = PageRequest.of(page, 6, Sort.by("likes"));
        return projectRepository.findAll(pageable).map(this::projectEntityToDto);
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
                                language.getFilesCount())
                ).collect(Collectors.toList()),
                project.getTags());
    }

    public ProjectDtoSimple projectEntityToSimpleDto(Project project) {
        Language mainLanguage = null;
        if (project.getLanguages().size() > 0) {
            mainLanguage = project.getLanguages().get(0);
        }

        return new ProjectDtoSimple(
                project.getId(),
                project.getName(),
                project.getLikes().size(),
                project.getCreated(),
                new UserDtoSimple(
                        project.getOwner().getId(),
                        project.getOwner().getUsername()),
                project.isPrivate(),
                mainLanguage
        );
    }
}
