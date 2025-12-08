package com.example.crmmobile.OpportunityDirectory;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.text.ParseException;
import java.util.List;

public class OpportunityFormViewModel extends AndroidViewModel {

    private CompanyRepository companyRepo;
    private ContactRepository contactRepo;
    private EmployeeRepository employeeRepo;

    private OpportunityFormHandler handler = new OpportunityFormHandler();

    private final MutableLiveData<List<Company>> companies = new MutableLiveData<>();
    private final MutableLiveData<List<Contact>> contacts = new MutableLiveData<>();
    private final MutableLiveData<List<Employee>> employees = new MutableLiveData<>();

    private int selectedCompanyId = 0;
    private int selectedContactId = 0;
    private int selectedManagementId = 0;

    public OpportunityFormViewModel(@NonNull Application app) {
        super(app);

        companyRepo  = new CompanyRepository(null);
        contactRepo  = new ContactRepository(null);
        employeeRepo = new EmployeeRepository(null);

        loadDropdownData();
    }

    private void loadDropdownData() {
        companies.setValue(companyRepo.getAllCompanies());
        contacts.setValue(contactRepo.getAllContacts());
        employees.setValue(employeeRepo.getAllEmployees());
    }

    public LiveData<List<Company>> getCompanies() { return companies; }
    public LiveData<List<Contact>> getContacts() { return contacts; }
    public LiveData<List<Employee>> getEmployees() { return employees; }

    public void setSelectedCompanyId(int id) { selectedCompanyId = id; }
    public void setSelectedContactId(int id) { selectedContactId = id; }
    public void setSelectedManagementId(int id) { selectedManagementId = id; }

    public int getSelectedCompanyId() { return selectedCompanyId; }
    public int getSelectedContactId() { return selectedContactId; }
    public int getSelectedManagementId() { return selectedManagementId; }

    // Business: tạo Opportunity từ form
    public Opportunity createOpportunity(
            String mode,
            Opportunity existing,
            String title,
            String priceStr,
            String stage,
            String date1,
            String date2,
            String desc
    ) throws ParseException {
        return handler.createFromForm(
                mode,
                existing,
                title,
                priceStr,
                stage,
                date1,
                date2,
                desc,
                selectedCompanyId,
                selectedContactId,
                selectedManagementId
        );
    }

    public OpportunityFormHandler getHandler() {
        return handler;
    }
}
