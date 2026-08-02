package com.SpringBoot.Service;

import java.util.List;
import com.SpringBoot.Dto.PlacementStatsDTO;
import com.SpringBoot.Entities.Application;
import com.SpringBoot.Entities.ApplicationStatus;

public interface ApplicationService {
    Application applyForDrive(Long studentId, Long driveId);
    List<Application> getStudentApplications(Long studentId);
    List<Application> getDriveApplications(Long driveId);
    List<Application> getAllApplications();
    Application updateApplicationStatus(Long applicationId, ApplicationStatus status);
    PlacementStatsDTO getPlacementStatistics();
}
