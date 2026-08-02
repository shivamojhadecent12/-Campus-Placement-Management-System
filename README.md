# 🎓 Campus Placement Management System - Spring Boot & REST APIs

Modern full-stack Campus Placement Management System built with Java 17, Spring boot 3.x, Spring data jpa, Hibernate ORM, MySQL & H2 in-memory database, REST APIs, HTML5, CSS3, JavaScript Fetch API, Bootstrap 5.

> Note: This project uses HTML/CSS/JS and does not use any UI frameworks such as JSP, Thymeleaf, React or Angular
---
## 📌 Description

#### 🔑 Features

### 🎓 Student Module
- Student registration / login
- Manage profile info including CGPA, Branch / Department, Active Backlogs, Year of Graduation, Technical skills
- View all placement drives with automated eligibility check (`Eligible to apply` or `Not eligible`) status
- Eligibility to apply check for each drive based on student's `CGPA`, `Backlogs`, and `Branch`
- Application status tracker with `APPLIED`, `SHORTLISTED`, `INTERVIEW`, `SELECTED`, `REJECTED` statuses.

### 🏢 Company Module

- Company management
- View / Edit / Delete companies info (Name, Location, Website, Description, etc)
### 💼 Placement Drive Module
- Publishing placement drives with `Job Role`, `Package`, `Minimum CGPA`, `Maximum Backlogs`, `Allowed Branches`, `Location`, `Deadline`, `Drive Date`, and `Status`.

### 🔐 Placement Officer Module
- View dashboard with statistics such as `Total Registered Students`, `Total Companies`, `Total Placement Drives`, `Placed Students`, `Placement %`.
- View all students info with eligibility criteria
- Shortlist candidates for a particular placement drive and update their status (SHORTLISTED / INTERVIEW / SELECTED / REJECTED).
---
## 🧱 Tech Stack
| Layer    | Technologies       |
|:-----------:|:------------------------:|
| Back-end  | Java 17, Spring Boot 3.5.3  |
| ORM     | Hibernate ORM, Spring Data JPA   |
| DBMS    | MySQL, H2 In Memory (for demo purposes)    |
| Web Services| REST API           |
| Front-end  | HTML5, CSS3, JS Fetch API, Bootstrap 5 |
| Build Tool | Apache Maven         |
---
## 🗂️ Project Structure
```
com.SpringBoot
├── Application.java
├── Config
│  └── DataInitializer.java
├── Controller
│  ├── AuthRestController.java
│  ├── StudentRestController.java
│  ├── CompanyRestController.java
│  ├── DriveRestController.java
│  ├── ApplicationRestController.java
│  └── AdminRestController.java
├── Dto
│  ├── AuthResponse.java
│  ├── LoginRequest.java
│  ├── PlacementDriveRequest.java
│  ├── EligibilityDTO.java
│  ├── ApplicationStatusUpdateRequest.java
│  └── PlacementStatsDTO.java
├── Entities
│  ├── Students.java
│  ├── Admin.java
│  ├── Company.java
│  ├── PlacementDrive.java
│  ├── Application.java
│  ├── ApplicationStatus.java
│  └── DriveStatus.java
├── Repository
│  ├── StudentsRepo.java
│  ├── AdminRepo.java
│  ├── CompanyRepo.java
│  ├── PlacementDriveRepo.java
│  └── ApplicationRepo.java
└── Service
├── StudentService.java
├── StudentServiceImp.java
├── AdminService.java
├── AdminServiceImp.java
├── CompanyService.java
├── CompanyServiceImpl.java
├── PlacementDriveService.java
├── PlacementDriveServiceImpl.java
├── ApplicationService.java
└── ApplicationServiceImpl.java
```
---
## 🚀 Run the App
### Option 1: Demo Mode (No DB Configuration Needed)
```bash
mvn spring-boot:run -Dspring-boot.run.profiles=h2
```
### Option 2: Run with MySQL DB
Make sure that MySQL server is running on `localhost:3306`
```bash
mvn spring-boot:run
```
---

## 🔑 Default Login Account
### Placement Officer (Admin):
- Email: `admin@college.edu`
- Password: `admin123`
### Student:
- Create a student account using the `Register` link
---

## 📜 License
MIT License