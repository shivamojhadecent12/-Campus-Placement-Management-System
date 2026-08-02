package com.SpringBoot.Service;

import java.util.List;
import com.SpringBoot.Entities.Company;

public interface CompanyService {
    Company createCompany(Company company);
    Company getCompanyById(Long id);
    List<Company> getAllCompanies();
    Company updateCompany(Long id, Company company);
    void deleteCompany(Long id);
}
