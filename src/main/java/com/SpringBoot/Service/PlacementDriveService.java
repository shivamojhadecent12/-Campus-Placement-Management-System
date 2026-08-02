package com.SpringBoot.Service;

import java.util.List;
import com.SpringBoot.Dto.EligibilityDTO;
import com.SpringBoot.Dto.PlacementDriveRequest;
import com.SpringBoot.Entities.DriveStatus;
import com.SpringBoot.Entities.PlacementDrive;

public interface PlacementDriveService {
    PlacementDrive createDrive(PlacementDriveRequest request);
    PlacementDrive getDriveById(Long id);
    List<PlacementDrive> getAllDrives();
    List<PlacementDrive> getDrivesByStatus(DriveStatus status);
    PlacementDrive updateDrive(Long id, PlacementDriveRequest request);
    void deleteDrive(Long id);
    EligibilityDTO checkStudentEligibility(Long studentId, Long driveId);
}
