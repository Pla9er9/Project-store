package com.example.projectstore.user;

import com.example.projectstore.application.Application;
import com.example.projectstore.project.Project;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@NamedEntityGraph(
        name = "user-entity-graph",
        attributeNodes = {
                @NamedAttributeNode("followers"),
                @NamedAttributeNode("following"),
                @NamedAttributeNode("personalLinks"),
                @NamedAttributeNode("projects"),
                @NamedAttributeNode("likedProjects")
        }
)
@Entity
@Table(name = "\"Users\"")
@Data
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class User implements UserDetails {
    @Id
    @GeneratedValue
    private UUID id;
    @NotNull
    private String username;
    private String description;
    @NotNull
    private String email;
    @NotNull
    private String password;
    @NotNull
    private String firstname;
    @NotNull
    private String lastname;
    @ManyToMany
    private List<User> followers = List.of();
    @ManyToMany
    private List<User> following = List.of();
    private String personalLinks;
    @ManyToMany(mappedBy = "owner")
    private List<Project> projects = List.of();
    @ManyToMany
    private List<Project> likedProjects = List.of();
    @OneToMany
    private Set<Application> applications = Set.of();

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("user"));
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
