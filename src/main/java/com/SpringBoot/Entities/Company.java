package com.SpringBoot.Entities;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

@Entity
@Table(name = "companies")
public class Company {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "company_id")
    private Long id;

    @Column(name = "company_name", nullable = false, unique = true)
    private String companyName;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Column(name = "website")
    private String website;

    @Column(name = "location")
    private String location;

    @OneToMany(mappedBy = "company", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<PlacementDrive> drives;

    public Company() {
    }

    public Company(Long id, String companyName, String description, String website, String location) {
        this.id = id;
        this.companyName = companyName;
        this.description = description;
        this.website = website;
        this.location = location;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public List<PlacementDrive> getDrives() {
        return drives;
    }

    public void setDrives(List<PlacementDrive> drives) {
        this.drives = drives;
    }
}
