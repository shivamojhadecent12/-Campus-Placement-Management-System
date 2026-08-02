package com.SpringBoot.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.SpringBoot.Dto.AuthResponse;
import com.SpringBoot.Dto.LoginRequest;
import com.SpringBoot.Entities.Admin;
import com.SpringBoot.Entities.Students;
import com.SpringBoot.Service.AdminService;
import com.SpringBoot.Service.StudentService;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
public class AuthRestController {

    @Autowired
    private StudentService studentService;

    @Autowired
    private AdminService adminService;

    @PostMapping("/student/login")
    public ResponseEntity<AuthResponse> studentLogin(@RequestBody LoginRequest request) {
        Students student = studentService.loginStudent(request.getEmail(), request.getPassword());
        if (student != null) {
            return ResponseEntity.ok(new AuthResponse(
                    true,
                    "Student login successful",
                    "STUDENT",
                    student.getS_id(),
                    student.getS_name(),
                    student.getS_email()
            ));
        } else {
            return ResponseEntity.status(401).body(new AuthResponse(
                    false,
                    "Invalid student email or password",
                    null,
                    null,
                    null,
                    null
            ));
        }
    }

    @PostMapping("/student/register")
    public ResponseEntity<?> studentRegister(@RequestBody Students student) {
        try {
            if (studentService.findByEmail(student.getS_email()) != null) {
                return ResponseEntity.badRequest().body(new AuthResponse(
                        false, "Email already registered", null, null, null, null));
            }
            Students saved = studentService.registerStudent(student);
            return ResponseEntity.ok(new AuthResponse(
                    true,
                    "Registration successful",
                    "STUDENT",
                    saved.getS_id(),
                    saved.getS_name(),
                    saved.getS_email()
            ));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new AuthResponse(
                    false, "Registration failed: " + e.getMessage(), null, null, null, null));
        }
    }

    @PostMapping("/admin/login")
    public ResponseEntity<AuthResponse> adminLogin(@RequestBody LoginRequest request) {
        Admin admin = adminService.loginAdmin(request.getEmail(), request.getPassword());
        if (admin != null) {
            return ResponseEntity.ok(new AuthResponse(
                    true,
                    "Admin login successful",
                    "ADMIN",
                    admin.getA_id(),
                    admin.getA_name(),
                    admin.getA_email()
            ));
        } else {
            return ResponseEntity.status(401).body(new AuthResponse(
                    false,
                    "Invalid admin email or password",
                    null,
                    null,
                    null,
                    null
            ));
        }
    }
}
