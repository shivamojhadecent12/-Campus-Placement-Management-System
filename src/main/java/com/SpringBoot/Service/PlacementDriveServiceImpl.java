package com.SpringBoot.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.SpringBoot.Dto.EligibilityDTO;
import com.SpringBoot.Dto.PlacementDriveRequest;
import com.SpringBoot.Entities.Company;
import com.SpringBoot.Entities.DriveStatus;
import com.SpringBoot.Entities.PlacementDrive;
import com.SpringBoot.Entities.Students;
import com.SpringBoot.Repository.CompanyRepo;
import com.SpringBoot.Repository.PlacementDriveRepo;
import com.SpringBoot.Repository.StudentsRepo;

@Service
public class PlacementDriveServiceImpl implements PlacementDriveService {

    @Autowired
    private PlacementDriveRepo driveRepo;

    @Autowired
    private CompanyRepo companyRepo;

    @Autowired
    private StudentsRepo studentRepo;

    @Override
    public PlacementDrive createDrive(PlacementDriveRequest request) {
        Company company = companyRepo.findById(request.getCompanyId())
                .orElseThrow(() -> new RuntimeException("Company not found with id: " + request.getCompanyId()));

        PlacementDrive drive = new PlacementDrive();
        drive.setCompany(company);
        drive.setJobRole(request.getJobRole());
        drive.setPackageAmount(request.getPackageAmount());
        drive.setMinCgpa(request.getMinCgpa() != null ? request.getMinCgpa() : 0.0);
        drive.setMaxBacklogs(request.getMaxBacklogs() != null ? request.getMaxBacklogs() : 0);
        drive.setAllowedBranches(request.getAllowedBranches());
        drive.setLocation(request.getLocation());
        drive.setDeadline(request.getDeadline());
        drive.setDriveDate(request.getDriveDate());
        drive.setStatus(request.getStatus() != null ? request.getStatus() : DriveStatus.ACTIVE);

        return driveRepo.save(drive);
    }

    @Override
    public PlacementDrive getDriveById(Long id) {
        return driveRepo.findById(id).orElse(null);
    }

    @Override
    public List<PlacementDrive> getAllDrives() {
        return driveRepo.findAll();
    }

    @Override
    public List<PlacementDrive> getDrivesByStatus(DriveStatus status) {
        return driveRepo.findByStatus(status);
    }

    @Override
    public PlacementDrive updateDrive(Long id, PlacementDriveRequest request) {
        PlacementDrive drive = driveRepo.findById(id).orElse(null);
        if (drive == null) return null;

        if (request.getCompanyId() != null) {
            Company company = companyRepo.findById(request.getCompanyId()).orElse(null);
            if (company != null) drive.setCompany(company);
        }
        if (request.getJobRole() != null) drive.setJobRole(request.getJobRole());
        if (request.getPackageAmount() != null) drive.setPackageAmount(request.getPackageAmount());
        if (request.getMinCgpa() != null) drive.setMinCgpa(request.getMinCgpa());
        if (request.getMaxBacklogs() != null) drive.setMaxBacklogs(request.getMaxBacklogs());
        if (request.getAllowedBranches() != null) drive.setAllowedBranches(request.getAllowedBranches());
        if (request.getLocation() != null) drive.setLocation(request.getLocation());
        if (request.getDeadline() != null) drive.setDeadline(request.getDeadline());
        if (request.getDriveDate() != null) drive.setDriveDate(request.getDriveDate());
        if (request.getStatus() != null) drive.setStatus(request.getStatus());

        return driveRepo.save(drive);
    }

    @Override
    public void deleteDrive(Long id) {
        driveRepo.deleteById(id);
    }

    @Override
    public EligibilityDTO checkStudentEligibility(Long studentId, Long driveId) {
        Students student = studentRepo.findById(studentId).orElse(null);
        PlacementDrive drive = driveRepo.findById(driveId).orElse(null);

        EligibilityDTO dto = new EligibilityDTO();
        List<String> reasons = new ArrayList<>();

        if (student == null || drive == null) {
            dto.setEligible(false);
            reasons.add("Invalid student or placement drive ID");
            dto.setReasons(reasons);
            return dto;
        }

        double sCgpa = student.getCgpa() != null ? student.getCgpa() : 0.0;
        double reqCgpa = drive.getMinCgpa() != null ? drive.getMinCgpa() : 0.0;
        int sBacklogs = student.getBacklogs() != null ? student.getBacklogs() : 0;
        int maxBacklogs = drive.getMaxBacklogs() != null ? drive.getMaxBacklogs() : 0;
        String sBranch = student.getBranch() != null ? student.getBranch().trim() : "";
        String allowed = drive.getAllowedBranches() != null ? drive.getAllowedBranches().trim() : "ALL";

        dto.setStudentCgpa(sCgpa);
        dto.setRequiredCgpa(reqCgpa);
        dto.setStudentBacklogs(sBacklogs);
        dto.setMaxAllowedBacklogs(maxBacklogs);
        dto.setStudentBranch(sBranch);
        dto.setAllowedBranches(allowed);

        boolean isEligible = true;

        // CGPA Check
        if (sCgpa < reqCgpa) {
            isEligible = false;
            reasons.add("CGPA (" + sCgpa + ") is below required minimum (" + reqCgpa + ")");
        }

        // Backlogs Check
        if (sBacklogs > maxBacklogs) {
            isEligible = false;
            reasons.add("Active backlogs (" + sBacklogs + ") exceed maximum limit (" + maxBacklogs + ")");
        }

        // Branch Check
        if (!allowed.equalsIgnoreCase("ALL") && !allowed.isEmpty()) {
            List<String> branchList = Arrays.stream(allowed.split(","))
                    .map(String::trim)
                    .map(String::toLowerCase)
                    .toList();
            if (!branchList.contains(sBranch.toLowerCase())) {
                isEligible = false;
                reasons.add("Branch (" + sBranch + ") is not eligible for this drive. Allowed: " + allowed);
            }
        }

        dto.setEligible(isEligible);
        dto.setReasons(reasons);
        return dto;
    }
}
