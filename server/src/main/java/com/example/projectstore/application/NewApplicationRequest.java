package com.example.projectstore.application;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@AllArgsConstructor
@Data
public class NewApplicationRequest {
    @Size(min = 1, max = 15)
    private String name;
    private boolean isCommercial;
    @NotEmpty
    @NotNull
    private List<String> allowedRedirectUrls;
}
