package com.example.projectstore.server_status;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/status")
@RequiredArgsConstructor
public class StatusController {

    private final StatusService statusService;

    @GetMapping
    public Status getStatus() {
        return statusService.getStatus();
    }
}
