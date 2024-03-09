package com.example.projectstore.user;

import com.example.projectstore.file.FileService;
import lombok.RequiredArgsConstructor;
import org.hibernate.cfg.NotYetImplementedException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("api/v1/user/{username}")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final FileService fileService;

    @GetMapping
    public UserDTO getUser(
            @PathVariable String username,
            HttpServletRequest request
    ) {
        return userService.getUser(username, request);
    }

    @GetMapping("/avatar")
    public ResponseEntity<byte[]> getUserAvatar(
            @PathVariable String username
    ) {
        return ResponseEntity.ok(fileService.getUserAvatar(username));
    }

    @GetMapping("/invitations")
    public ResponseEntity<String> getInvitations() {
        throw new NotYetImplementedException();
    }

    @PostMapping("/follow")
    public void followUser(
            @PathVariable String username,
            Authentication authentication
    ) {
        userService.followUser(username, authentication);
    }

    @PutMapping("/unfollow")
    public void unfollowUser(
            @PathVariable String username,
            Authentication authentication
    ) {
        userService.unfollowUser(username, authentication);
    }

    @PostMapping("/block")
    public void blockUser(
            @PathVariable String username,
            Authentication authentication
    ) {
        userService.blockUser(username, authentication.getName());
    }

    @PutMapping("/unblock")
    public void unblockUser(
            @PathVariable String username,
            Authentication authentication
    ) {
        userService.unblockUser(username, authentication.getName());
    }
}
