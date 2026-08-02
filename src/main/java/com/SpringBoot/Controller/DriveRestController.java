package com.SpringBoot.Controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.SpringBoot.Dto.EligibilityDTO;
import com.SpringBoot.Dto.PlacementDriveRequest;
import com.SpringBoot.Entities.DriveStatus;
import com.SpringBoot.Entities.PlacementDrive;
import com.SpringBoot.Service.PlacementDriveService;

@RestController
@RequestMapping("/api/drives")
@CrossOrigin(origins = "*")
public class DriveRestController {

    @Autowired
    private PlacementDriveService driveService;

    @GetMapping
    public ResponseEntity<List<PlacementDrive>> getAllDrives(@RequestParam(required = false) DriveStatus status) {
        if (status != null) {
            return ResponseEntity.ok(driveService.getDrivesByStatus(status));
        }
        return ResponseEntity.ok(driveService.getAllDrives());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PlacementDrive> getDriveById(@PathVariable Long id) {
        PlacementDrive drive = driveService.getDriveById(id);
        if (drive == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(drive);
    }

    @PostMapping
    public ResponseEntity<?> createDrive(@RequestBody PlacementDriveRequest request) {
        try {
            PlacementDrive created = driveService.createDrive(request);
            return ResponseEntity.ok(created);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateDrive(@PathVariable Long id, @RequestBody PlacementDriveRequest request) {
        try {
            PlacementDrive updated = driveService.updateDrive(id, request);
            if (updated == null) return ResponseEntity.notFound().build();
            return ResponseEntity.ok(updated);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteDrive(@PathVariable Long id) {
        driveService.deleteDrive(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{driveId}/eligibility/{studentId}")
    public ResponseEntity<EligibilityDTO> checkEligibility(@PathVariable Long driveId, @PathVariable Long studentId) {
        EligibilityDTO dto = driveService.checkStudentEligibility(studentId, driveId);
        return ResponseEntity.ok(dto);
    }
}
