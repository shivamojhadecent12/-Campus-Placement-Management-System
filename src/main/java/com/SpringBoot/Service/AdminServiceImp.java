package com.SpringBoot.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.SpringBoot.Entities.Admin;
import com.SpringBoot.Repository.AdminRepo;

@Service
public class AdminServiceImp implements AdminService {

	@Autowired
	private AdminRepo adminRepo;

	@Override
	public Admin findById(long a_id) {
		return adminRepo.findById(a_id).orElse(null);
	}

	@Override
	public Admin findByEmail(String email) {
		return adminRepo.findByAEmail(email);
	}

	@Override
	public Admin loginAdmin(String email, String password) {
		Admin admin = adminRepo.findByEmailAndPassword(email, password);
		if (admin == null) {
			admin = adminRepo.findByAEmail(email);
			if (admin != null && !admin.getA_password().equals(password)) {
				admin = null;
			}
		}
		return admin;
	}
}
