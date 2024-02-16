package com.example.projectstore.user;

import com.example.projectstore.account.AccountDto;
import com.example.projectstore.config.JwtService;
import com.example.projectstore.project.Language;
import com.example.projectstore.project.ProjectDtoSimple;
import com.example.projectstore.project.ProjectRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {

    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final ProjectRepository projectRepository;
    private final PasswordEncoder passwordEncoder;

    public UserDTO getUser(String username, HttpServletRequest request) {
        var user = userRepository.findByUsername(username).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        boolean isFollowed = false;
        var authHeader = request.getHeader("Authorization");
        if (authHeader != null && authHeader.length() > 8) {
            var authUser = userRepository.findByUsername(jwtService.extractUsername(authHeader.substring(7)));
            if (authUser.isPresent()) {
                for (User u : authUser.get().getFollowing()
                ) {
                    if (u.getUsername().equals(username)) {
                        isFollowed = true;
                        break;
                    }
                }
            }
        }

        return new UserDTO(
                user.getId(),
                user.getUsername(),
                user.getDescription(),
                user.getFirstname(),
                user.getLastname(),
                isFollowed,
                user.getFollowers().size(),
                user.getFollowing().size(),
                stringLinksToList(user.getPersonalLinks()),
                user.getProjects().stream().map(p -> {
                            var userDtoSimple = userEntityToDtoSimple(p.getOwner());
                            Language mainLanguage = null;
                            if (p.getLanguages().size() != 0) {
                                mainLanguage = p.getLanguages().get(0);
                            }

                            return new ProjectDtoSimple(
                                    p.getId(),
                                    p.getName(),
                                    p.getLikes().size(),
                                    p.getCreated(),
                                    userDtoSimple,
                                    p.isPrivate(),
                                    mainLanguage);
                        }
                ).collect(Collectors.toList()));
    }

    public List<String> stringLinksToList(String links) {
        var linkList = new ArrayList<String>();
        var builder = new StringBuilder();
        if (links == null) {
            return new ArrayList<>();
        }
        for (String c : links.split("")
        ) {
            if (c.equals(";")) {
                linkList.add(builder.toString());
                builder.delete(0, builder.length());
            } else {
                builder.append(c);
            }
        }
        linkList.add(builder.toString());
        return linkList;
    }


    public AccountDto getUserAccountData(Authentication authentication) {
        var user = userRepository.findByUsername(authentication.getName())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED));

        List<String> links = null;
        if (user.getPersonalLinks() != null) {
            links = List.of(user.getPersonalLinks().split(";"));
        }
        return new AccountDto(
                user.getUsername(),
                user.getDescription(),
                user.getEmail(),
                user.getFirstname(),
                user.getLastname(),
                links
        );
    }

    public void editAccount(
            AccountDto request,
            Authentication authentication) {
        var user = userRepository.findByUsername(authentication.getName())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED));

        user.setUsername(request.getUsername());
        user.setDescription(request.getDescription());
        user.setEmail(request.getEmail());
        user.setFirstname(request.getFirstname());
        user.setLastname(request.getLastname());
        user.setPersonalLinks(String.join(";", request.getPersonalLinks()));
        userRepository.save(user);
    }

    public void deleteAccount(Authentication authentication) {
        projectRepository.deleteAllByOwnerUsername(authentication.getName());
        userRepository.deleteByUsername(authentication.getName());
    }

    public void changePassword(Authentication authentication, String newPassword) {
        var user = userRepository.findByUsername(authentication.getName())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED));

        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);
    }

    public void followUser(String username, Authentication authentication) {
        var user = userRepository.findByUsername(authentication.getName())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED));

        if (username.equals(user.getUsername())) {
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE);
        }

        for (User u : user.getFollowing()
        ) {
            if (u.getUsername().equals(username)) {
                return;
            }
        }

        var user2 = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        var followers = new ArrayList<>(List.copyOf(user.getFollowers()));
        followers.add(user2);
        user2.setFollowers(followers);
        userRepository.save(user2);

        var following = new ArrayList<>(List.copyOf(user.getFollowing()));
        following.add(user2);
        user.setFollowing(following);
        userRepository.save(user);
    }

    public void unfollowUser(String username, Authentication authentication) {
        var user = userRepository.findByUsername(authentication.getName())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED));
        if (user.getUsername().equals(username)) {
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE);
        }
        var found = false;
        for (User u : user.getFollowing()
        ) {
            if (username.equals(u.getUsername())) {
                found = true;
                break;
            }
        }

        if (!found) {
            return;
        }

        var user2 = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        var followers = new ArrayList<>(List.copyOf(user.getFollowers()));
        followers.remove(user2);

        user2.setFollowers(followers);
        userRepository.save(user2);

        var following = new ArrayList<>(List.copyOf(user.getFollowing()));
        following.remove(user2);
        user.setFollowing(following);
        userRepository.save(user);
    }

    public Set<UserDtoSearch> searchByUsername(String query) {
        var u = userRepository.searchByUserName(query, PageRequest.of(0, 20));
        return u.stream().map(this::userEntityToDtoSearch).collect(Collectors.toSet());
    }

    public UserDtoSimple userEntityToDtoSimple(User user) {
        return new UserDtoSimple(
                user.getId(),
                user.getUsername()
        );
    }

    public UserDtoSearch userEntityToDtoSearch(User user) {
        return new UserDtoSearch(
                user.getId(),
                user.getUsername(),
                user.getFirstname(),
                user.getLastname(),
                user.getFollowers().size(),
                user.getProjects().size()
        );
    }
}
