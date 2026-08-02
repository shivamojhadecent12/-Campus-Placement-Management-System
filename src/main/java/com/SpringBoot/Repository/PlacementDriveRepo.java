package com.SpringBoot.Repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.SpringBoot.Entities.DriveStatus;
import com.SpringBoot.Entities.PlacementDrive;

@Repository
public interface PlacementDriveRepo extends JpaRepository<PlacementDrive, Long> {
    List<PlacementDrive> findByStatus(DriveStatus status);
    List<PlacementDrive> findByCompanyId(Long companyId);
}
