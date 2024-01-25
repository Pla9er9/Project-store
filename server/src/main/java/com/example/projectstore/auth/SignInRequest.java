package com.example.projectstore.auth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SignInRequest {
    @NotNull
    @Size(min = 1, max = 30)
    private String username;
    @NotNull
    @Size(min = 8, max = 100)
    private String password;
}
