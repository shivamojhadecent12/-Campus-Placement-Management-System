package com.SpringBoot.Controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.SpringBoot.Dto.ApplicationStatusUpdateRequest;
import com.SpringBoot.Entities.Application;
import com.SpringBoot.Service.ApplicationService;

@RestController
@RequestMapping("/api/applications")
@CrossOrigin(origins = "*")
public class ApplicationRestController {

    @Autowired
    private ApplicationService applicationService;

    @PostMapping("/apply")
    public ResponseEntity<?> applyForDrive(@RequestParam Long studentId, @RequestParam Long driveId) {
        try {
            Application app = applicationService.applyForDrive(studentId, driveId);
            return ResponseEntity.ok(app);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/student/{studentId}")
    public ResponseEntity<List<Application>> getStudentApplications(@PathVariable Long studentId) {
        return ResponseEntity.ok(applicationService.getStudentApplications(studentId));
    }

    @GetMapping("/drive/{driveId}")
    public ResponseEntity<List<Application>> getDriveApplications(@PathVariable Long driveId) {
        return ResponseEntity.ok(applicationService.getDriveApplications(driveId));
    }

    @GetMapping
    public ResponseEntity<List<Application>> getAllApplications() {
        return ResponseEntity.ok(applicationService.getAllApplications());
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<?> updateApplicationStatus(
            @PathVariable Long id,
            @RequestBody ApplicationStatusUpdateRequest request) {
        try {
            Application updated = applicationService.updateApplicationStatus(id, request.getStatus());
            return ResponseEntity.ok(updated);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
