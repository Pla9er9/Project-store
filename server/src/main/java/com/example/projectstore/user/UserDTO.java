package com.example.projectstore.user;

import com.example.projectstore.project.ProjectDtoSimple;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDTO {
    private UUID userId;
    private String username;
    private String description;
    private String firstname;
    private String lastname;
    private boolean isFollowed;
    private int followers;
    private int following;
    private List<String> personalLinks;
    private List<ProjectDtoSimple> projects;
}
