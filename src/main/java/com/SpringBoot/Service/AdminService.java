package com.SpringBoot.Service;

import com.SpringBoot.Entities.Admin;

public interface AdminService {
	Admin findById(long a_id);
	Admin findByEmail(String email);
	Admin loginAdmin(String email, String password);
}
