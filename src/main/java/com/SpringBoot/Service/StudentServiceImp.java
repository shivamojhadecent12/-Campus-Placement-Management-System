package com.SpringBoot.Service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.SpringBoot.Entities.Students;
import com.SpringBoot.Repository.StudentsRepo;

@Service
public class StudentServiceImp implements StudentService {

	@Autowired
	private StudentsRepo studentRepo;

	@Override
	public Students registerStudent(Students student) {
		if (student.getBranch() == null || student.getBranch().isEmpty()) {
			student.setBranch(student.getDepartment());
		}
		return studentRepo.save(student);
	}

	@Override
	public Students getStudentById(Long id) {
		return studentRepo.findById(id).orElse(null);
	}

	@Override
	public Students findByEmail(String email) {
		return studentRepo.findBySEmail(email);
	}

	@Override
	public Students loginStudent(String email, String password) {
		Students student = studentRepo.findByEmailAndPassword(email, password);
		if (student == null) {
			student = studentRepo.findBySEmail(email);
			if (student != null && !student.getS_password().equals(password)) {
				student = null;
			}
		}
		return student;
	}

	@Override
	public List<Students> getAllStudents() {
		return studentRepo.findAll();
	}

	@Override
	public Students updateStudent(Long id, Students updatedStudent) {
		Students existing = studentRepo.findById(id).orElse(null);
		if (existing == null) {
			return null;
		}
		if (updatedStudent.getS_name() != null) existing.setS_name(updatedStudent.getS_name());
		if (updatedStudent.getGender() != null) existing.setGender(updatedStudent.getGender());
		if (updatedStudent.getDob() != null) existing.setDob(updatedStudent.getDob());
		if (updatedStudent.getBatch() != null) existing.setBatch(updatedStudent.getBatch());
		if (updatedStudent.getDepartment() != null) existing.setDepartment(updatedStudent.getDepartment());
		if (updatedStudent.getPhone() != null) existing.setPhone(updatedStudent.getPhone());
		if (updatedStudent.getCity() != null) existing.setCity(updatedStudent.getCity());
		if (updatedStudent.getCgpa() != null) existing.setCgpa(updatedStudent.getCgpa());
		if (updatedStudent.getBranch() != null) existing.setBranch(updatedStudent.getBranch());
		if (updatedStudent.getBacklogs() != null) existing.setBacklogs(updatedStudent.getBacklogs());
		if (updatedStudent.getGraduationYear() != null) existing.setGraduationYear(updatedStudent.getGraduationYear());
		if (updatedStudent.getSkills() != null) existing.setSkills(updatedStudent.getSkills());

		return studentRepo.save(existing);
	}

	@Override
	public void deleteStudent(Long id) {
		studentRepo.deleteById(id);
	}
}
