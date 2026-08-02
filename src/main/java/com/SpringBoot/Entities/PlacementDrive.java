package com.SpringBoot.Entities;

import java.time.LocalDate;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

@Entity
@Table(name = "placement_drives")
public class PlacementDrive {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "drive_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "company_id", nullable = false)
    private Company company;

    @Column(name = "job_role", nullable = false)
    private String jobRole;

    @Column(name = "package_amount")
    private String packageAmount; // e.g. "12 LPA"

    @Column(name = "min_cgpa")
    private Double minCgpa;

    @Column(name = "max_backlogs")
    private Integer maxBacklogs;

    @Column(name = "allowed_branches")
    private String allowedBranches; // e.g. "CSE, ECE, IT" or "ALL"

    @Column(name = "location")
    private String location;

    @Column(name = "deadline")
    private LocalDate deadline;

    @Column(name = "drive_date")
    private LocalDate driveDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private DriveStatus status = DriveStatus.ACTIVE;

    @OneToMany(mappedBy = "placementDrive", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<Application> applications;

    public PlacementDrive() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public String getJobRole() {
        return jobRole;
    }

    public void setJobRole(String jobRole) {
        this.jobRole = jobRole;
    }

    public String getPackageAmount() {
        return packageAmount;
    }

    public void setPackageAmount(String packageAmount) {
        this.packageAmount = packageAmount;
    }

    public Double getMinCgpa() {
        return minCgpa;
    }

    public void setMinCgpa(Double minCgpa) {
        this.minCgpa = minCgpa;
    }

    public Integer getMaxBacklogs() {
        return maxBacklogs;
    }

    public void setMaxBacklogs(Integer maxBacklogs) {
        this.maxBacklogs = maxBacklogs;
    }

    public String getAllowedBranches() {
        return allowedBranches;
    }

    public void setAllowedBranches(String allowedBranches) {
        this.allowedBranches = allowedBranches;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public LocalDate getDeadline() {
        return deadline;
    }

    public void setDeadline(LocalDate deadline) {
        this.deadline = deadline;
    }

    public LocalDate getDriveDate() {
        return driveDate;
    }

    public void setDriveDate(LocalDate driveDate) {
        this.driveDate = driveDate;
    }

    public DriveStatus getStatus() {
        return status;
    }

    public void setStatus(DriveStatus status) {
        this.status = status;
    }

    public List<Application> getApplications() {
        return applications;
    }

    public void setApplications(List<Application> applications) {
        this.applications = applications;
    }
}
