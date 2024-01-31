package com.example.projectstore.search;

import com.example.projectstore.project.ProjectDtoSimple;
import com.example.projectstore.project.ProjectService;
import com.example.projectstore.user.UserDtoSearch;
import com.example.projectstore.user.UserDtoSimple;
import com.example.projectstore.user.UserService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Set;

@RequestMapping("/api/v1/search")
@RestController
@AllArgsConstructor
public class SearchController {

    private final ProjectService projectService;
    private final UserService userService;

    @GetMapping("/all/{query}")
    public List searchAll(
            @PathVariable String query
    ) {
        return List.of();
    }

    @GetMapping("/users/{query}")
    public Set<UserDtoSearch> searchUser(
            @PathVariable String query
    ) {
        return userService.searchByUsername(query.toLowerCase());
    }

    @GetMapping("/projects/{query}")
    public Set<ProjectDtoSimple> searchProjects(
            @PathVariable String query
    ) {
        return projectService.searchByName(query);
    }

    @GetMapping("/tags/{query}")
    public Set<ProjectDtoSimple> searchProjectsByTag(
            @PathVariable String query
    ) {
        return projectService.searchByTag(query);
    }
}
