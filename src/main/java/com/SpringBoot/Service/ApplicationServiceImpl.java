package com.SpringBoot.Service;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.SpringBoot.Dto.EligibilityDTO;
import com.SpringBoot.Dto.PlacementStatsDTO;
import com.SpringBoot.Entities.Application;
import com.SpringBoot.Entities.ApplicationStatus;
import com.SpringBoot.Entities.PlacementDrive;
import com.SpringBoot.Entities.Students;
import com.SpringBoot.Repository.ApplicationRepo;
import com.SpringBoot.Repository.CompanyRepo;
import com.SpringBoot.Repository.PlacementDriveRepo;
import com.SpringBoot.Repository.StudentsRepo;

@Service
public class ApplicationServiceImpl implements ApplicationService {

    @Autowired
    private ApplicationRepo applicationRepo;

    @Autowired
    private StudentsRepo studentRepo;

    @Autowired
    private PlacementDriveRepo driveRepo;

    @Autowired
    private CompanyRepo companyRepo;

    @Autowired
    private PlacementDriveService driveService;

    @Override
    public Application applyForDrive(Long studentId, Long driveId) {
        if (applicationRepo.existsByStudentIdAndDriveId(studentId, driveId)) {
            throw new RuntimeException("Student has already applied to this placement drive.");
        }

        EligibilityDTO eligibility = driveService.checkStudentEligibility(studentId, driveId);
        if (!eligibility.isEligible()) {
            String reasons = String.join(", ", eligibility.getReasons());
            throw new RuntimeException("Student is not eligible for this drive: " + reasons);
        }

        Students student = studentRepo.findById(studentId)
                .orElseThrow(() -> new RuntimeException("Student not found"));
        PlacementDrive drive = driveRepo.findById(driveId)
                .orElseThrow(() -> new RuntimeException("Placement drive not found"));

        Application application = new Application();
        application.setStudent(student);
        application.setPlacementDrive(drive);
        application.setAppliedDate(LocalDateTime.now());
        application.setStatus(ApplicationStatus.APPLIED);

        return applicationRepo.save(application);
    }

    @Override
    public List<Application> getStudentApplications(Long studentId) {
        return applicationRepo.findByStudentId(studentId);
    }

    @Override
    public List<Application> getDriveApplications(Long driveId) {
        return applicationRepo.findByPlacementDriveId(driveId);
    }

    @Override
    public List<Application> getAllApplications() {
        return applicationRepo.findAll();
    }

    @Override
    public Application updateApplicationStatus(Long applicationId, ApplicationStatus status) {
        Application app = applicationRepo.findById(applicationId)
                .orElseThrow(() -> new RuntimeException("Application not found"));
        app.setStatus(status);
        return applicationRepo.save(app);
    }

    @Override
    public PlacementStatsDTO getPlacementStatistics() {
        long totalStudents = studentRepo.count();
        long totalCompanies = companyRepo.count();
        long totalDrives = driveRepo.count();
        long totalApplications = applicationRepo.count();
        long totalPlacedStudents = applicationRepo.countDistinctPlacedStudents();

        double placementPct = 0.0;
        if (totalStudents > 0) {
            placementPct = Math.round(((double) totalPlacedStudents / totalStudents * 100.0) * 10.0) / 10.0;
        }

        return new PlacementStatsDTO(
                totalStudents,
                totalCompanies,
                totalDrives,
                totalApplications,
                totalPlacedStudents,
                placementPct
        );
    }
}
