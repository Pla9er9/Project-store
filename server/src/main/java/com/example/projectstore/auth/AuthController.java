package com.example.projectstore.auth;

import com.example.projectstore.logged_In_Device.LoggedInDeviceService;
import com.example.projectstore.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.util.UUID;

@RestController()
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final LoggedInDeviceService deviceService;
    private final UserRepository userRepository;

    @PostMapping("/signup")
    public AuthResponse SignUp(
            @Valid @RequestBody SignUpRequest signUpRequest,
            HttpServletRequest request
    ) {
        if (signUpRequest.getUsername().equals("--fakeusername")) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Username not allowed");
        }
        var res = authService.signUp(signUpRequest);
        deviceService.addNewLoggedInDevice(
                res.getToken(),
                userRepository.findById(res.getUserId()).orElseThrow(),
                request.getHeader("User-Agent"));
        return res;
    }

    @PostMapping("/signin")
    public AuthResponse SignIn(
            @RequestBody SignInRequest signInRequest,
            HttpServletResponse response,
            HttpServletRequest request
    ) {
        var res = authService.signIn(signInRequest);
        deviceService.addNewLoggedInDevice(
                res.getToken(),
                userRepository.findById(res.getUserId()).orElseThrow(),
                request.getHeader("User-Agent"));

        return res;
    }

    @PostMapping("/oauth2/signin")
    public void Oauth2SignIn(
            @RequestBody SignInRequest signInRequest,
            @RequestParam UUID appId,
            @RequestParam String redirectUrl,
            HttpServletResponse response
    ) throws IOException {
        var res = authService.Oauth2SignIn(signInRequest, redirectUrl, appId);
        response.sendRedirect(redirectUrl + "?token=" + res);
    }
}
