package com.example.projectstore.file;

import com.example.projectstore.auth.AuthService;
import com.example.projectstore.project.Language;
import com.example.projectstore.project.ProjectRepository;
import com.example.projectstore.user.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import javax.annotation.PostConstruct;
import java.io.*;
import java.io.File;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class FileService {

    private final ProjectRepository projectRepository;
    private final AuthService authService;
    private final UserRepository userRepository;
    private String cdnPath;

    @PostConstruct
    public void init() {
        authService.setFileService(this);
    }
    {
        try {
            cdnPath = new java.io.File(".").getCanonicalPath() + "\\cdn";
            if (!Files.exists(Path.of(cdnPath))) {
                Files.createDirectories(Path.of(cdnPath));
            }
        } catch (IOException e) {
            log.error(Arrays.toString(e.getStackTrace()));
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public String getByPathInProject(UUID projectId, String path) {
        var fullPath = securityFilter(projectId, path);
        File file = new File(fullPath);
        Path path_ = Paths.get(fullPath);
        if (!file.exists()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        if (file.isFile()) {
            return readFile(path_);
        } else {
            return readFolder(file).toString();
        }
    }

    public byte[] getZip(UUID projectId) throws IOException {
        File file = new File(this.cdnPath + "/projects/" + projectId + "/project.zip");

        if (!file.exists()) {
            URL url = new URL("http://127.0.0.1:1717/zip-project/" + projectId);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
        }
        return Files.readAllBytes(file.toPath());
    }

    private FolderResponse readFolder(File file) {
        List<String> dirs = new ArrayList<>();
        List<String> files = new ArrayList<>();

        for (File f : Objects.requireNonNull(file.listFiles())) {
            if (f.isDirectory()) {
                dirs.add(f.getName());
            } else {
                if (!f.getName().isEmpty()) {
                    files.add(f.getName());
                }
            }
        }
        return new FolderResponse(dirs, files);
    }

    private String readFile(Path path) {
        try {
            return Files.readString(path);
        } catch (IOException e) {
            log.error(Arrays.toString(e.getStackTrace()));
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private byte[] readBytesFromFile(Path path) {
        try {
            return Files.readAllBytes(path);
        } catch (IOException e) {
            log.error(e.getMessage());
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public void saveNewChatImage(UUID id, String fileExtension, byte[] bytes) {
        var fullFilename = id + "." + fileExtension;
        File newFile = new File(this.cdnPath + "\\chatImages\\" + fullFilename);
        try (FileOutputStream outputStream = new FileOutputStream(newFile)) {
            outputStream.write(bytes);
        } catch (Exception e) {
            log.info(e.getMessage());
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public void uploadFileToProject(
            UUID projectId,
            String path,
            MultipartFile file,
            Authentication authentication,
            boolean cutFirstFolder
    ) {
        authService.creatorAuthGate(projectId, authentication);
        securityFilterNonExistingFile(projectId, path);

        try {
            String filename;
            if (!cutFirstFolder) {
                filename = Objects.requireNonNull(file.getOriginalFilename());
            } else {
                var originalName = Objects.requireNonNull(file.getOriginalFilename());
                var separatorIndex = originalName.indexOf("/", 1);
                filename = originalName.substring(separatorIndex + 1);
            }

            var destinationFile = Paths.get(this.cdnPath + path);

            var correctPath = String.valueOf(destinationFile).replace("cdn", ("cdn\\projects\\" + projectId + "\\code\\"));
            try (InputStream inputStream = file.getInputStream()) {
                Path dir = Paths.get(correctPath);
                Files.createDirectories(Paths.get(getFolderPath(String.valueOf(dir))));

                Files.copy(inputStream, dir, StandardCopyOption.REPLACE_EXISTING);

                var _project = projectRepository.findById(projectId)
                        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

                var extension = FilenameUtils.getExtension(file.getOriginalFilename());

                var updated = false;
                var langs = _project.getLanguages();
                for (Language lang : langs
                ) {
                    if (lang.getName().equals(extension)) {
                        updated = true;
                        lang.setFilesCount(lang.getFilesCount() + 1);
                        break;
                    }
                }
                if (!updated) {
                    langs.add(Language.
                            builder()
                            .name(extension)
                            .filesCount(1)
                            .build());
                }

                _project.setLanguages(langs);
                projectRepository.save(_project);
                projectRepository.updateNumberOfFiles(_project.getId(), 1);
                projectRepository.updateCodeSize(_project.getId(), file.getSize());

                URL url = new URL("http://127.0.0.1:1717/zip-project/" + _project.getId());
                HttpURLConnection con = (HttpURLConnection) url.openConnection();
                con.setRequestMethod("GET");
            }
        } catch (IOException e) {
            log.error(Arrays.toString(e.getStackTrace()));
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public static String getFolderPath(String filePath) {
        String[] pathComponents = filePath.split("\\\\");
        pathComponents = Arrays.copyOf(pathComponents, pathComponents.length - 1);
        return String.join(File.separator, pathComponents);
    }

    public byte[] getUserAvatar(String username) {
        var user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        Path p = Paths.get(this.cdnPath + "\\avatars\\" + user.getId() + "\\");
        try {
            Path avatarPath = null;
            var arr = (List<Path>) Files.walk(p).toList();
            for (Path f : arr
            ) {
                if (f.toString().endsWith("avatar.png") || f.toString().endsWith("avatar.jpg")) {
                    avatarPath = f;
                    break;
                }
            }


            if (avatarPath == null) {
                throw new IOException();
            }
            return Files.readAllBytes(avatarPath);
        } catch (NullPointerException | IOException e) {
            return getDefaultAvatar();
        }
    }

    public byte[] getDefaultAvatar() {
        var path = Paths.get(this.cdnPath + "\\defaultAvatar.png\\");
        try {
            return Files.readAllBytes(path);
        } catch (IOException e) {
            log.error(Arrays.toString(e.getStackTrace()));
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public void uploadAvatar(MultipartFile file, String username) {
        var filename = file.getOriginalFilename();
        assert filename != null;
        if (!filename.endsWith(".jpg") && !filename.endsWith(".png")) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        var u = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        var len = filename.length();
        var avatarFileName = "avatar" + filename.substring(len - 4, len);
        Path p = Path.of(this.cdnPath + "\\avatars\\" + u.getId() + "\\" + avatarFileName);
        try {
            if (file.isEmpty()) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
            }
            var out = new FileOutputStream(p.toFile());
            out.write(file.getBytes());
            out.close();

        } catch (IOException e) {
            log.error(Arrays.toString(e.getStackTrace()));
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public void deleteAvatar(String username) {
        var u = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        var p = Paths.get(this.cdnPath + "\\avatars\\" + u.getId());
        try {
            Path avatarPath = null;
            var arr = (List<Path>) Files.walk(p).toList();
            for (Path f : arr
            ) {
                if (f.toString().endsWith("avatar.png") || f.toString().endsWith("avatar.jpg")) {
                    avatarPath = f;
                    break;
                }
            }
            if (avatarPath == null) {
                throw new IOException();
            }
            Files.deleteIfExists(avatarPath);
        } catch (IOException e) {
            log.error(Arrays.toString(e.getStackTrace()));
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    public void createDirectory(String directory, String str) {
        Path p = Paths.get(this.cdnPath + "\\" + directory + "\\" + str);
        try {
            Files.createDirectory(p);
        } catch (IOException e) {
            log.error(Arrays.toString(e.getStackTrace()));
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public void editFile(
            UUID projectId,
            String path,
            MultipartFile file,
            Authentication authentication) {

        authService.creatorAuthGate(projectId, authentication);
        var fullPath = securityFilter(projectId, path);
        Path p = Paths.get(fullPath);

        try {
            if (file.isEmpty()) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
            }
            try (InputStream inputStream = file.getInputStream()) {
                Files.copy(inputStream, p, StandardCopyOption.REPLACE_EXISTING);
            }
        } catch (IOException e) {
            log.error(e.getMessage());
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public void deleteFileInProject(
            UUID projectId,
            String path,
            Authentication authentication) {

        authService.creatorAuthGate(projectId, authentication);

        var fullPath = securityFilter(projectId, path);
        File file = new File(fullPath);
        if (!file.exists()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        if (!file.delete()) {
            log.error("Could not delete the file");
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        var project = projectRepository.findById(projectId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        var extension = FilenameUtils.getExtension(file.getName());

        var langs = project.getLanguages();
        for (Language lang : langs
        ) {
            if (lang.getName().equals(extension)) {
                lang.setFilesCount(lang.getFilesCount() - 1);
                break;
            }
        }

        project.setLanguages(langs);
        projectRepository.save(project);
        projectRepository.updateNumberOfFiles(project.getId(), -1);
    }

    public void deleteDirectoryInCdn(String dir) {
        File directory = new File(this.cdnPath + "\\" + dir);
        if (!directory.exists()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        if (!directory.delete()) {
            log.error("Could not delete the directory");
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private String securityFilter(UUID projectId, String path) {
        var fullPath = this.cdnPath + "\\projects\\" + projectId + "\\code\\" + path;

        if (path.contains("..")) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        File f = new File(fullPath);
        if (!f.getPath().startsWith(this.cdnPath) || !f.exists()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return fullPath;
    }

    private void securityFilterNonExistingFile(UUID projectId, String path) {
        var fullPath = this.cdnPath + "\\" + projectId + "\\code\\" + path;

        if (path.contains("..")) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        File f = new File(fullPath);
        if (!f.getPath().startsWith(this.cdnPath)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        if (f.exists()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT);
        }
    }

    public byte[] getChatImage(String filename) {
        var path = Path.of(this.cdnPath + "\\chatImages\\" + filename);
        var exist = Files.exists(path);
        if (!exist) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return readBytesFromFile(path);
    }
}
