package com.SpringBoot.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.SpringBoot.Entities.Company;

@Repository
public interface CompanyRepo extends JpaRepository<Company, Long> {
    Company findByCompanyName(String companyName);
}
