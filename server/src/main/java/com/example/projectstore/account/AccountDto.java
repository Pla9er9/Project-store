package com.example.projectstore.account;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Data
@AllArgsConstructor
public class AccountDto {
    @NotNull
    @Size(min = 1, max = 30)
    private String username;
    @NotNull
    private String description;
    @NotNull
    @Email
    private String email;
    @Size(min = 1, max = 30)
    @NotNull
    private String firstname;
    @Size(min = 1, max = 30)
    @NotNull
    private String lastname;
    @NotNull
    private List<String> personalLinks;
}
