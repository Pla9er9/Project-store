package com.example.projectstore.server_status;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Status {
    private boolean database;
    private boolean search;
    private boolean auth;
    private boolean files;
    private boolean project;
    private boolean chat;
}
