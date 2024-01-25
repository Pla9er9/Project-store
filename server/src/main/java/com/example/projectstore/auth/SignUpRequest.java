package com.example.projectstore.auth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SignUpRequest {
    @NotNull
    @Size(min = 1, max = 30)
    private String username;
    @NotNull
    @Email
    private String email;
    @NotNull
    @Size(min = 8, max = 100)
    private String password;
    @Size(min = 1, max = 30)
    @NotNull
    private String firstname;
    @Size(min = 1, max = 30)
    @NotNull
    private String lastname;
}