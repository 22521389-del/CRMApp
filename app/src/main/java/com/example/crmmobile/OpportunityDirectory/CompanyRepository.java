package com.example.crmmobile.OpportunityDirectory;

import java.util.ArrayList;
import java.util.List;

public class CompanyRepository {
    private CompanyDao companyDao;

    public CompanyRepository(CompanyDao companyDao) {
        this.companyDao = companyDao;
    }

    // Fake data tạm thời
    public static List<Company> getAllCompanies() {
        // TODO: Khi DB xong → đổi lại thành: return companyDao.getAllCompanies();
        List<Company> fakeList = new ArrayList<>();
        fakeList.add(new Company(1, "Google"));
        fakeList.add(new Company(2, "Microsoft"));
        fakeList.add(new Company(3, "Apple"));
        fakeList.add(new Company(4, "Meta"));
        return fakeList;
    }
}
