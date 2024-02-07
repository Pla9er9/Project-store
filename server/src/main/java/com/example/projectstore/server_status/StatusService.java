package com.example.projectstore.server_status;

import com.example.projectstore.auth.AuthService;
import com.example.projectstore.auth.SignInRequest;
import com.example.projectstore.auth.SignUpRequest;
import com.example.projectstore.chat.ChatMessage;
import com.example.projectstore.chat.ChatMessageRepository;
import com.example.projectstore.file.FileService;
import com.example.projectstore.project.Project;
import com.example.projectstore.project.ProjectRepository;
import com.example.projectstore.project.ProjectService;
import com.example.projectstore.user.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Slf4j
@Service
@AllArgsConstructor
public class StatusService {

    private final UserRepository userRepository;
    private final ProjectRepository projectRepository;
    private final FileService fileService;
    private final ChatMessageRepository chatMessageRepository;
    private final ProjectService projectService;
    private final AuthService authService;

    public Status getStatus() {
        var project = isProject();
        var auth = isAuth();
        var file = isFiles();
        var chat = isChat();
        var search = isSearch();
        var database = auth && chat && search && project;

        return new Status(
                database,
                search,
                auth,
                file,
                project,
                chat
        );
    }

    public boolean isSearch() {
        try {
            userRepository.searchByUserName("a", PageRequest.of(1, 5));
            return true;
        } catch (Exception e) {
            log.info(e.getMessage());
            return false;
        }
    }

    public boolean isAuth() {
        try {
            authService.signIn(new SignInRequest(
                    "--fakeusername",
                    "totally random password"
            ));
            return true;
        } catch (ResponseStatusException e) {
            return true;
        }
        catch (Exception e) {
            log.info(e.getMessage());
            return false;
        }
    }

    public boolean isFiles() {
        try {
            var page = projectService.getMostLikedProjects(1);
            if (page.isEmpty()) {
                return true;
            }
            var p = page.get().findFirst();
            if (p.isEmpty()) {
                return true;
            }
            fileService.getByPathInProject(p.get().getId(), "");
            return true;
        } catch (Exception e) {
            log.info(e.getMessage());
            return  false;
        }
    }

    public boolean isProject() {
        try {
            projectService.getMostLikedProjects(1);
            return true;
        } catch (Exception e) {
            log.info(e.getMessage());
            return  false;
        }
    }

    public boolean isChat() {
        try {
            var message = chatMessageRepository.save(new ChatMessage());
            chatMessageRepository.delete(message);
            return true;
        } catch (Exception e) {
            log.info(e.getMessage());
            return  false;
        }
    }
}
