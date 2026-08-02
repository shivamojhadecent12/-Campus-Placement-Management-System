package com.SpringBoot.Controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.SpringBoot.Entities.Students;
import com.SpringBoot.Service.StudentService;

@RestController
@RequestMapping("/api/students")
@CrossOrigin(origins = "*")
public class StudentRestController {

    @Autowired
    private StudentService studentService;

    @GetMapping
    public ResponseEntity<List<Students>> getAllStudents() {
        return ResponseEntity.ok(studentService.getAllStudents());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Students> getStudentById(@PathVariable Long id) {
        Students student = studentService.getStudentById(id);
        if (student == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(student);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateStudentProfile(@PathVariable Long id, @RequestBody Students updatedStudent) {
        Students updated = studentService.updateStudent(id, updatedStudent);
        if (updated == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteStudent(@PathVariable Long id) {
        studentService.deleteStudent(id);
        return ResponseEntity.ok().build();
    }
}
