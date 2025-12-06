package com.example.crmmobile.OpportunityDirectory;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

public class OpportunityFormViewModel extends AndroidViewModel {

    private CompanyRepository companyRepo;
    private ContactRepository contactRepo;
    private EmployeeRepository employeeRepo;

    private final MutableLiveData<List<Company>> companies = new MutableLiveData<>();
    private final MutableLiveData<List<Contact>> contacts = new MutableLiveData<>();
    private final MutableLiveData<List<Employee>> employees = new MutableLiveData<>();

    // Lưu ID user chọn
    private int selectedCompanyId = 0;
    private int selectedContactId = 0;
    private int selectedManagementId = 0;

    public OpportunityFormViewModel(@NonNull Application application) {
        super(application);

        // Tạo repo
        companyRepo = new CompanyRepository(null);  // null vì bạn dùng fake data
        contactRepo = new ContactRepository(null);
        employeeRepo = new EmployeeRepository(null);

        loadDropdownData();
    }

    // ============================================================
    //  LIVE DATA
    // ============================================================

    public LiveData<List<Company>> getCompanies() {
        return companies;
    }

    public LiveData<List<Contact>> getContacts() {
        return contacts;
    }

    public LiveData<List<Employee>> getEmployees() {
        return employees;
    }

    // ============================================================
    //  Load dữ liệu dropdown
    // ============================================================

    private void loadDropdownData() {
        Log.d("FormViewModel", "Loading dropdown data...");

        companies.setValue(companyRepo.getAllCompanies());
        contacts.setValue(contactRepo.getAllContacts());
        employees.setValue(employeeRepo.getAllEmployees());
    }

    // ============================================================
    //  SETTER ID USER CHỌN DROPDOWN
    // ============================================================

    public void setSelectedCompanyId(int id) {
        selectedCompanyId = id;
    }

    public void setSelectedContactId(int id) {
        selectedContactId = id;
    }

    public void setSelectedManagementId(int id) {
        selectedManagementId = id;
    }

    // ============================================================
    //  GETTER
    // ============================================================

    public int getSelectedCompanyId() {
        return selectedCompanyId;
    }

    public int getSelectedContactId() {
        return selectedContactId;
    }

    public int getSelectedManagementId() {
        return selectedManagementId;
    }
}
