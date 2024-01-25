package com.example.projectstore.server_status;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Status {
    private String apiResponseTime;
    private Boolean database;
    private Boolean actions;
    private Boolean issues;
    private Boolean release;
    private Boolean project;
    private Boolean user;
}
