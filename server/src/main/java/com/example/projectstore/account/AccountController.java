package com.example.projectstore.account;

import com.example.projectstore.file.FileService;
import com.example.projectstore.invitation.InvitationDto;
import com.example.projectstore.invitation.InvitationService;
import com.example.projectstore.logged_In_Device.LoggedInDeviceDto;
import com.example.projectstore.logged_In_Device.LoggedInDeviceService;
import com.example.projectstore.user.UserRepository;
import com.example.projectstore.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("api/v1/account")
@RequiredArgsConstructor
public class AccountController {

    private final UserService userService;
    private final FileService fileService;
    private final LoggedInDeviceService deviceService;
    private final InvitationService invitationService;

    @GetMapping
    public AccountDto getAccountData(
            Authentication authentication
    ) {
        return userService.getUserAccountData(authentication);
    }

    @PutMapping
    public void editAccount(
            @RequestBody AccountDto request,
            Authentication authentication) {
        userService.editAccount(request, authentication);
    }

    @DeleteMapping
    public void deleteAccount(Authentication authentication) {
        userService.deleteAccount(authentication);
    }

    @PutMapping("change-password")
    public void changePassword(
            Authentication authentication,
            @RequestBody ResetPasswordRequest request) {
        userService.changePassword(authentication, request.getPassword());
    }

    @PostMapping("/avatar")
    public void uploadAvatar(
            @RequestParam("file") MultipartFile file,
            Authentication authentication
    ) {
        fileService.uploadAvatar(file, authentication.getName());
    }

    @DeleteMapping("/avatar")
    public void deleteAvatar(
            Authentication authentication
    ) {
        fileService.deleteAvatar(authentication.getName());
    }

    @GetMapping("/loggedInDevices")
    public List<LoggedInDeviceDto> getLoggedInDevices(Authentication authentication) {
        return deviceService.getLoggedInDevices(authentication.getName());
    }

    @DeleteMapping("/loggedInDevices/{deviceId}")
    public void logoutDevice(
            @PathVariable UUID deviceId,
            Authentication authentication
    ) {
        deviceService.logoutDevice(deviceId);
    }

    @GetMapping("/invitations")
    public List<InvitationDto> getAllUserInvitations(
            Authentication authentication
    ) {
        return invitationService.getAllInvitations(authentication.getName());
    }
}
