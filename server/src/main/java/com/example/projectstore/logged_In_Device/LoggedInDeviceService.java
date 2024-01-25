package com.example.projectstore.logged_In_Device;

import com.example.projectstore.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LoggedInDeviceService {

    private final LoggedInDeviceRepository deviceRepository;

    public List<LoggedInDeviceDto> getLoggedInDevices(String username) {
        return deviceRepository.findByBlockedFalseAndAccount_username(username)
                .stream().map(d -> new LoggedInDeviceDto(
                        d.getId(),
                        d.getUserAgent(),
                        d.getLoggedInTime(),
                        d.isBlocked()
                )).collect(Collectors.toList());
    }

    public void addNewLoggedInDevice(
            String jwtToken,
            User user,
            String userAgent) {

        var device = LoggedInDevice.builder()
                .userAgent(userAgent)
                .loggedInTime(LocalDateTime.now())
                .jwtToken(jwtToken)
                .account(user)
                .build();

        deviceRepository.save(device);
    }

    public boolean isTokenBlocked(String token) {
        var device = deviceRepository.findByJwtToken(token).orElse(null);
        if (device == null) {
            return false;
        }
        return device.isBlocked();
    }

    public void logoutDevice(UUID deviceId) {
        var device = deviceRepository.findById(deviceId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        device.setBlocked(true);
        deviceRepository.save(device);
    }
}
