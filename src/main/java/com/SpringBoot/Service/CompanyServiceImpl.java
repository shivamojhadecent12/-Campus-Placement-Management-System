package com.SpringBoot.Service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.SpringBoot.Entities.Company;
import com.SpringBoot.Repository.CompanyRepo;

@Service
public class CompanyServiceImpl implements CompanyService {

    @Autowired
    private CompanyRepo companyRepo;

    @Override
    public Company createCompany(Company company) {
        return companyRepo.save(company);
    }

    @Override
    public Company getCompanyById(Long id) {
        return companyRepo.findById(id).orElse(null);
    }

    @Override
    public List<Company> getAllCompanies() {
        return companyRepo.findAll();
    }

    @Override
    public Company updateCompany(Long id, Company companyDetails) {
        Company company = companyRepo.findById(id).orElse(null);
        if (company == null) {
            return null;
        }
        company.setCompanyName(companyDetails.getCompanyName());
        company.setDescription(companyDetails.getDescription());
        company.setWebsite(companyDetails.getWebsite());
        company.setLocation(companyDetails.getLocation());
        return companyRepo.save(company);
    }

    @Override
    public void deleteCompany(Long id) {
        companyRepo.deleteById(id);
    }
}
