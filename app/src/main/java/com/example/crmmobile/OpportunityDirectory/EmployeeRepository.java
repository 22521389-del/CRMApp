package com.example.crmmobile.OpportunityDirectory;

import java.util.ArrayList;
import java.util.List;

public class EmployeeRepository {

    private EmployeeDao employeeDao;

    public EmployeeRepository(EmployeeDao employeeDao) {
        this.employeeDao = employeeDao;
    }

    public List<Employee> getAllEmployees() {
        // TODO: Khi DB xong → return employeeDao.getAllEmployees();

        List<Employee> fakeList = new ArrayList<>();
        fakeList.add(new Employee(1, "David Manager"));
        fakeList.add(new Employee(2, "Sarah Lead"));
        fakeList.add(new Employee(3, "Tom Supervisor"));
        fakeList.add(new Employee(4, "Emma Coordinator"));

        return fakeList;
    }
}
