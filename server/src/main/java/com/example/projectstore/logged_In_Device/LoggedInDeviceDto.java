package com.example.projectstore.logged_In_Device;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@AllArgsConstructor
@Data
public class LoggedInDeviceDto {
    private UUID id;
    private String userAgent;
    private LocalDateTime loggedInTime;
    private boolean blocked;
}
