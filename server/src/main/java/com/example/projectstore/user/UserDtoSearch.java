package com.example.projectstore.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDtoSearch {
    private UUID userId;
    private String username;
    private String firstname;
    private String lastname;
    private int followers;
    private int projectCount;
}
