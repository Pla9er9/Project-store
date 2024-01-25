package com.example.projectstore.server_status;

import org.springframework.stereotype.Service;

@Service
public class StatusService {
    public Status getStatus() {
        return new Status(
                this.getApiResponseTime(),
                this.getDatabase(),
                this.getActions(),
                this.getIssues(),
                this.getRelease(),
                this.getProject(),
                this.getUser());
    }

    private String getApiResponseTime() {
        return "100ms";
    }

    private Boolean getDatabase() {
        return true;
    }

    private Boolean getActions() {
        return true;
    }

    private Boolean getIssues() {
        return true;
    }

    private Boolean getRelease() {
        return true;
    }

    private Boolean getProject() {
        return true;
    }

    private Boolean getUser() {
        return true;
    }

}
