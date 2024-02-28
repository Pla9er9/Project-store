package com.example.projectstore.release;

import com.example.projectstore.auth.AuthService;
import com.example.projectstore.project.ProjectRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Service
@Slf4j
public class ReleaseService {
    private final String cdnPath;
    private final AuthService authService;
    private final ProjectRepository projectRepository;

    public ReleaseService(
            AuthService authService,
            ProjectRepository projectRepository) throws IOException {
        this.cdnPath = new java.io.File(".").getCanonicalPath() + "\\cdn";
        this.authService = authService;
        this.projectRepository = projectRepository;
    }

    public Release getLatestRelease(UUID projectId) {
        Path p = Paths.get(this.cdnPath + "\\projects\\" + projectId + "\\release\\");
        try {
            Path latestReleasePath = null;

            var arr = (List<Path>) Files.walk(p).toList();
            for (Path f : arr
            ) {
                if (f.toString().endsWith(".zip") || f.toString().endsWith(".exe")) {
                    latestReleasePath = f;
                    break;
                }
            }

            if (latestReleasePath == null) {
                throw new IOException();
            }
            return new Release(
                    latestReleasePath.getFileName().toString(),
                    Files.readAllBytes(latestReleasePath)
            );
        } catch (IOException e) {
            log.error(e.getMessage());
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public void uploadRelease(UUID projectId, MultipartFile file, String version, Authentication authentication) {
        var project = authService.creatorAuthGate(projectId, authentication);
        deleteRelease(projectId, authentication);
        var filename = file.getOriginalFilename();
        assert filename != null;
        if (!filename.endsWith(".zip") && !filename.endsWith(".exe")) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        Path p = Path.of(this.cdnPath + "\\projects\\" + projectId + "\\release\\" + filename);
        try {
            if (file.isEmpty()) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
            }

            var out = new FileOutputStream(p.toFile());
            out.write(file.getBytes());
            out.close();

            project.setLatestReleaseVersion(version);
            projectRepository.save(project);
        } catch (IOException e) {
            log.error(e.getMessage());
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public void deleteRelease(UUID projectId, Authentication authentication) {
        var project = authService.creatorAuthGate(projectId, authentication);

        Path p = Paths.get(this.cdnPath + "\\projects\\" + projectId + "\\release\\");
        try {
            var files = Files.walk(p).toList();
            for (Path e : files
            ) {
                var str = e.toString();
                if (str.endsWith(".zip") || str.endsWith(".exe")) {
                    Files.delete(e);
                    project.setLatestReleaseVersion(null);
                    projectRepository.save(project);
                }
            }
        } catch (IOException e) {
            log.error(e.getMessage());
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
