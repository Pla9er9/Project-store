package com.example.projectstore.account;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ResetPasswordRequest {
    @NotNull
    @Size(min = 8, max = 100)
    private String password;
}
