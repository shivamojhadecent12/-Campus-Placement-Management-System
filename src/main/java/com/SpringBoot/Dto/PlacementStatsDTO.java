package com.SpringBoot.Dto;

public class PlacementStatsDTO {
    private long totalStudents;
    private long totalCompanies;
    private long totalDrives;
    private long totalApplications;
    private long totalPlacedStudents;
    private double placementPercentage;

    public PlacementStatsDTO() {
    }

    public PlacementStatsDTO(long totalStudents, long totalCompanies, long totalDrives, long totalApplications, long totalPlacedStudents, double placementPercentage) {
        this.totalStudents = totalStudents;
        this.totalCompanies = totalCompanies;
        this.totalDrives = totalDrives;
        this.totalApplications = totalApplications;
        this.totalPlacedStudents = totalPlacedStudents;
        this.placementPercentage = placementPercentage;
    }

    public long getTotalStudents() {
        return totalStudents;
    }

    public void setTotalStudents(long totalStudents) {
        this.totalStudents = totalStudents;
    }

    public long getTotalCompanies() {
        return totalCompanies;
    }

    public void setTotalCompanies(long totalCompanies) {
        this.totalCompanies = totalCompanies;
    }

    public long getTotalDrives() {
        return totalDrives;
    }

    public void setTotalDrives(long totalDrives) {
        this.totalDrives = totalDrives;
    }

    public long getTotalApplications() {
        return totalApplications;
    }

    public void setTotalApplications(long totalApplications) {
        this.totalApplications = totalApplications;
    }

    public long getTotalPlacedStudents() {
        return totalPlacedStudents;
    }

    public void setTotalPlacedStudents(long totalPlacedStudents) {
        this.totalPlacedStudents = totalPlacedStudents;
    }

    public double getPlacementPercentage() {
        return placementPercentage;
    }

    public void setPlacementPercentage(double placementPercentage) {
        this.placementPercentage = placementPercentage;
    }
}
