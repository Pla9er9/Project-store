package com.example.projectstore.issue;

import com.example.projectstore.user.UserDtoSimple;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
public class IssueDtoSimple {
    private UUID id;
    private UserDtoSimple createdBy;
    private String title;
    private Boolean isOpen;
    private LocalDateTime created;

}
