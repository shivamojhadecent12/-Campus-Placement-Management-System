package com.SpringBoot.Service;

import java.util.List;
import com.SpringBoot.Entities.Students;

public interface StudentService {
	Students registerStudent(Students student);
	Students getStudentById(Long id);
	Students findByEmail(String email);
	Students loginStudent(String email, String password);
	List<Students> getAllStudents();
	Students updateStudent(Long id, Students updatedStudent);
	void deleteStudent(Long id);
}
