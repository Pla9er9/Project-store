package com.example.projectstore.auth;

import com.example.projectstore.logged_In_Device.LoggedInDeviceService;
import com.example.projectstore.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

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

// TODO 2FA

//    @PostMapping("/signup/confirm")
//    public ResponseEntity<AuthResponse> SignUp() {
//        return ResponseEntity.ok(new AuthResponse());
//    }
//
//    @PostMapping("/signin/confirm")
//    public ResponseEntity<AuthResponse> SignIn() {
//        return ResponseEntity.ok(new AuthResponse());
//    }

}
