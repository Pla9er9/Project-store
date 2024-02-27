package com.example.projectstore.account;

import com.example.projectstore.application.ApplicationService;
import com.example.projectstore.chat.ChatMessageService;
import com.example.projectstore.file.FileService;
import com.example.projectstore.invitation.InvitationDto;
import com.example.projectstore.invitation.InvitationService;
import com.example.projectstore.issue.IssueDtoSimple;
import com.example.projectstore.issue.IssueService;
import com.example.projectstore.logged_In_Device.LoggedInDeviceDto;
import com.example.projectstore.logged_In_Device.LoggedInDeviceService;
import com.example.projectstore.project.ProjectDtoSimple;
import com.example.projectstore.project.ProjectService;
import com.example.projectstore.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Set;
import java.util.UUID;

@RestController
@RequestMapping("api/v1")
@RequiredArgsConstructor
public class AccountController {

    private final UserService userService;
    private final FileService fileService;
    private final LoggedInDeviceService deviceService;
    private final InvitationService invitationService;
    private final ApplicationService applicationService;
    private final ChatMessageService chatMessageService;
    private final IssueService issueService;
    private final ProjectService projectService;

    @GetMapping("account")
    public AccountDto getAccountData(
            Authentication authentication
    ) {
        return userService.getUserAccountData(authentication);
    }

    @PutMapping("account")
    public void editAccount(
            @RequestBody AccountDto request,
            Authentication authentication) {
        userService.editAccount(request, authentication);
    }

    @DeleteMapping("account")
    public void deleteAccount(Authentication authentication) {
        userService.deleteAccount(authentication);
    }

    @GetMapping("oauth2/account")
    public AccountDto getAccountByOauth2Token(@RequestParam String accessToken, @RequestParam String secret) {
        return applicationService.getAccountData(accessToken, secret);
    }

    @PutMapping("account/change-password")
    public void changePassword(
            Authentication authentication,
            @RequestBody ResetPasswordRequest request) {
        userService.changePassword(authentication, request.getPassword());
    }

    @PostMapping("account/avatar")
    public void uploadAvatar(
            @RequestParam("file") MultipartFile file,
            Authentication authentication
    ) {
        fileService.uploadAvatar(file, authentication.getName());
    }

    @DeleteMapping("account/avatar")
    public void deleteAvatar(
            Authentication authentication
    ) {
        fileService.deleteAvatar(authentication.getName());
    }

    @GetMapping("account/loggedInDevices")
    public List<LoggedInDeviceDto> getLoggedInDevices(Authentication authentication) {
        return deviceService.getLoggedInDevices(authentication.getName());
    }

    @GetMapping("account/chats")
    public Set<String> getChats(Authentication authentication) {
        return chatMessageService.getUserChats(authentication.getName());
    }

    @GetMapping("account/issues")
    public List<IssueDtoSimple> getOpenedIssues(Authentication authentication) {
        return issueService.getOpenedIssuesByUser(authentication.getName());
    }

    @GetMapping("account/projects")
    public List<ProjectDtoSimple> getLastProjects(Authentication authentication) {
        return projectService.getLastProjects(authentication.getName());
    }

    @GetMapping("account/notifications")
    public void getNotifications() {
        
    }

    @DeleteMapping("account/loggedInDevices/{deviceId}")
    public void logoutDevice(
            @PathVariable UUID deviceId,
            Authentication authentication
    ) {
        deviceService.logoutDevice(deviceId);
    }

    @GetMapping("account/invitations")
    public List<InvitationDto> getAllUserInvitations(
            Authentication authentication
    ) {
        return invitationService.getAllInvitations(authentication.getName());
    }
}
