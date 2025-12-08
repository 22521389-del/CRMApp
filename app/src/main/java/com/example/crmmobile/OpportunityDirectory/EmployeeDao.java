package com.example.crmmobile.OpportunityDirectory;

import androidx.room.Dao;
import androidx.room.Query;

import java.util.List;

@Dao
public interface EmployeeDao {

    @Query("SELECT id, name FROM employees")
    List<Employee> getAllEmployees();
}
