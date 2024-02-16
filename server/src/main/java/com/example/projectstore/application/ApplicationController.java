package com.example.projectstore.application;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Set;
import java.util.UUID;

@RestController
@RequestMapping("api/v1/dev/application")
@RequiredArgsConstructor
public class ApplicationController {

    private final ApplicationService applicationService;

    @GetMapping
    public Set<ApplicationDtoSimple> getApplications(Authentication authentication) {
        return applicationService.getApplications(authentication.getName());
    }

    @PostMapping
    public void createNewApplication(@RequestBody NewApplicationRequest request, Authentication authentication) {
        applicationService.createNewApplication(request, authentication.getName());
    }

    @GetMapping("/{id}")
    public ApplicationDto getApplicationInfo(@PathVariable UUID id, Authentication authentication) {
        return applicationService.getApplicationInfo(id, authentication.getName());
    }

    @PutMapping("/{id}/secret") String newSecret(@PathVariable UUID id, Authentication authentication) {
        return applicationService.newSecret(id, authentication.getName());
    }

    @DeleteMapping("/{id}")
    public void deleteApplication(@PathVariable UUID id, Authentication authentication) {
        applicationService.deleteApplication(id, authentication.getName());
    }
}
