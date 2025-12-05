package com.example.crmmobile.OpportunityDirectory;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

public class OpportunityFormViewModel extends AndroidViewModel {

    private CompanyRepository companyRepo;
    private ContactRepository contactRepo;
    private EmployeeRepository employeeRepo;

    private MutableLiveData<List<Company>> companies = new MutableLiveData<>();
    private MutableLiveData<List<Contact>> contacts = new MutableLiveData<>();
    private MutableLiveData<List<Employee>> employees = new MutableLiveData<>();

    public OpportunityFormViewModel(@NonNull Application application) {
        super(application);

        companyRepo = new CompanyRepository(null);   // null vì fake
        contactRepo = new ContactRepository(null);
        employeeRepo = new EmployeeRepository(null);

        loadDropdownData();
    }

    public LiveData<List<Company>> getCompanies() { return companies; }
    public LiveData<List<Contact>> getContacts() { return contacts; }
    public LiveData<List<Employee>> getEmployees() { return employees; }

    private void loadDropdownData() {
        companies.setValue(companyRepo.getAllCompanies());
        contacts.setValue(contactRepo.getAllContacts());
        employees.setValue(employeeRepo.getAllEmployees());
    }
}

