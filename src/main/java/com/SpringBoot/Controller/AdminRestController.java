package com.SpringBoot.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.SpringBoot.Dto.PlacementStatsDTO;
import com.SpringBoot.Service.ApplicationService;

@RestController
@RequestMapping("/api/admin")
@CrossOrigin(origins = "*")
public class AdminRestController {

    @Autowired
    private ApplicationService applicationService;

    @GetMapping("/stats")
    public ResponseEntity<PlacementStatsDTO> getPlacementStatistics() {
        return ResponseEntity.ok(applicationService.getPlacementStatistics());
    }
}
