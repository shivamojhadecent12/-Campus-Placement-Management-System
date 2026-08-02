package com.SpringBoot.Repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.SpringBoot.Entities.Application;
import com.SpringBoot.Entities.ApplicationStatus;

@Repository
public interface ApplicationRepo extends JpaRepository<Application, Long> {

    @Query("SELECT a FROM Application a WHERE a.student.s_id = :studentId")
    List<Application> findByStudentId(@Param("studentId") Long studentId);

    @Query("SELECT a FROM Application a WHERE a.placementDrive.id = :driveId")
    List<Application> findByPlacementDriveId(@Param("driveId") Long driveId);

    @Query("SELECT a FROM Application a WHERE a.student.s_id = :studentId AND a.placementDrive.id = :driveId")
    Optional<Application> findByStudentIdAndDriveId(@Param("studentId") Long studentId, @Param("driveId") Long driveId);

    @Query("SELECT COUNT(a) > 0 FROM Application a WHERE a.student.s_id = :studentId AND a.placementDrive.id = :driveId")
    boolean existsByStudentIdAndDriveId(@Param("studentId") Long studentId, @Param("driveId") Long driveId);

    long countByStatus(ApplicationStatus status);
    
    @Query("SELECT COUNT(DISTINCT a.student.s_id) FROM Application a WHERE a.status = com.SpringBoot.Entities.ApplicationStatus.SELECTED")
    long countDistinctPlacedStudents();
}
