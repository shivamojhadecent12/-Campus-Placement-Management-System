package com.SpringBoot.Config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.SpringBoot.Entities.Admin;
import com.SpringBoot.Entities.Company;
import com.SpringBoot.Repository.AdminRepo;
import com.SpringBoot.Repository.CompanyRepo;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private AdminRepo adminRepo;

    @Autowired
    private CompanyRepo companyRepo;

    @Override
    public void run(String... args) throws Exception {
        // Seed default Placement Officer / Admin account if no admins exist
        if (adminRepo.count() == 0) {
            Admin admin = new Admin();
            admin.setA_name("Placement Cell Officer");
            admin.setA_email("admin@college.edu");
            admin.setA_password("admin123");
            adminRepo.save(admin);
            System.out.println("Default Admin Account Created: admin@college.edu / admin123");
        }

        // Seed initial sample companies if none exist
        if (companyRepo.count() == 0) {
            Company c1 = new Company(null, "Google India", "Global Technology Leader", "https://google.com", "Bangalore");
            Company c2 = new Company(null, "Microsoft", "Empowering every person and organization", "https://microsoft.com", "Hyderabad");
            Company c3 = new Company(null, "Tata Consultancy Services", "IT services, consulting and business solutions", "https://tcs.com", "Mumbai");
            companyRepo.save(c1);
            companyRepo.save(c2);
            companyRepo.save(c3);
            System.out.println("Sample Companies Seeded Successfully.");
        }
    }
}
