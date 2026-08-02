package com.SpringBoot.Dto;

import java.util.List;

public class EligibilityDTO {
    private boolean eligible;
    private List<String> reasons;
    private Double studentCgpa;
    private Double requiredCgpa;
    private Integer studentBacklogs;
    private Integer maxAllowedBacklogs;
    private String studentBranch;
    private String allowedBranches;

    public EligibilityDTO() {
    }

    public boolean isEligible() {
        return eligible;
    }

    public void setEligible(boolean eligible) {
        this.eligible = eligible;
    }

    public List<String> getReasons() {
        return reasons;
    }

    public void setReasons(List<String> reasons) {
        this.reasons = reasons;
    }

    public Double getStudentCgpa() {
        return studentCgpa;
    }

    public void setStudentCgpa(Double studentCgpa) {
        this.studentCgpa = studentCgpa;
    }

    public Double getRequiredCgpa() {
        return requiredCgpa;
    }

    public void setRequiredCgpa(Double requiredCgpa) {
        this.requiredCgpa = requiredCgpa;
    }

    public Integer getStudentBacklogs() {
        return studentBacklogs;
    }

    public void setStudentBacklogs(Integer studentBacklogs) {
        this.studentBacklogs = studentBacklogs;
    }

    public Integer getMaxAllowedBacklogs() {
        return maxAllowedBacklogs;
    }

    public void setMaxAllowedBacklogs(Integer maxAllowedBacklogs) {
        this.maxAllowedBacklogs = maxAllowedBacklogs;
    }

    public String getStudentBranch() {
        return studentBranch;
    }

    public void setStudentBranch(String studentBranch) {
        this.studentBranch = studentBranch;
    }

    public String getAllowedBranches() {
        return allowedBranches;
    }

    public void setAllowedBranches(String allowedBranches) {
        this.allowedBranches = allowedBranches;
    }
}
