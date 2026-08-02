package com.SpringBoot.Entities;

import java.time.LocalDateTime;
import jakarta.persistence.*;

@Entity
@Table(name = "applications", uniqueConstraints = { @UniqueConstraint(columnNames = { "student_id", "drive_id" }) })
public class Application {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "student_id", nullable = false)
    private Students student;

    @ManyToOne
    @JoinColumn(name = "drive_id", nullable = false)
    private PlacementDrive placementDrive;

    @Column(name = "applied_date", nullable = false)
    private LocalDateTime appliedDate = LocalDateTime.now();

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private ApplicationStatus status = ApplicationStatus.APPLIED;

    public Application() {
    }

    public Application(Students student, PlacementDrive placementDrive) {
        this.student = student;
        this.placementDrive = placementDrive;
        this.appliedDate = LocalDateTime.now();
        this.status = ApplicationStatus.APPLIED;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Students getStudent() {
        return student;
    }

    public void setStudent(Students student) {
        this.student = student;
    }

    public PlacementDrive getPlacementDrive() {
        return placementDrive;
    }

    public void setPlacementDrive(PlacementDrive placementDrive) {
        this.placementDrive = placementDrive;
    }

    public LocalDateTime getAppliedDate() {
        return appliedDate;
    }

    public void setAppliedDate(LocalDateTime appliedDate) {
        this.appliedDate = appliedDate;
    }

    public ApplicationStatus getStatus() {
        return status;
    }

    public void setStatus(ApplicationStatus status) {
        this.status = status;
    }
}
