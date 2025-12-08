package com.example.crmmobile.OpportunityDirectory;

import androidx.room.Dao;
import androidx.room.Query;

import java.util.List;

@Dao
public interface CompanyDao {

    @Query("SELECT id, name FROM companies")
    List<Company> getAllCompanies();

}
