package com.SpringBoot.Dto;

import com.SpringBoot.Entities.ApplicationStatus;

public class ApplicationStatusUpdateRequest {
    private ApplicationStatus status;

    public ApplicationStatusUpdateRequest() {
    }

    public ApplicationStatus getStatus() {
        return status;
    }

    public void setStatus(ApplicationStatus status) {
        this.status = status;
    }
}
