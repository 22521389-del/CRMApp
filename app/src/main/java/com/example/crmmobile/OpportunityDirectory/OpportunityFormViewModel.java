package com.example.crmmobile.OpportunityDirectory;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

public class OpportunityFormViewModel extends AndroidViewModel {

    private final OpportunityRepository repository;

    // ===== STATES =====
    private final MutableLiveData<String> error = new MutableLiveData<>();
    private final MutableLiveData<Boolean> success = new MutableLiveData<>();
    private final MutableLiveData<Opportunity> existing = new MutableLiveData<>();

    // mode: create / update
    private String mode = OpportunityFormFragment.MODE_CREATE;

    public OpportunityFormViewModel(@NonNull Application application) {
        super(application);
        repository = OpportunityRepository.getInstance(application);
    }

    public LiveData<String> getError() { return error; }
    public LiveData<Boolean> getSuccess() { return success; }
    public LiveData<Opportunity> getExisting() { return existing; }

    // ========= INIT FORM =========
    public void init(String mode, Opportunity opp) {
        this.mode = mode;

        if (OpportunityFormFragment.MODE_UPDATE.equals(mode) && opp != null) {
            existing.setValue(opp);
        }
    }

    // ========= SAVE LOGIC =========
    public void save(
            String title,
            String companyName,
            String contactName,
            String priceStr,
            String stage,
            String date1,
            String date2,
            String description,
            String managementName
    ) {
        // Validate
        if (title == null || title.isEmpty()) {
            error.setValue("Tên cơ hội không được để trống");
            return;
        }

        // Parse price
        double price = 0;
        try {
            if (!priceStr.isEmpty()) {
                priceStr = priceStr.replaceAll("[^\\d.]", "");
                if (!priceStr.isEmpty()) {
                    price = Double.parseDouble(priceStr);
                }
            }
        } catch (Exception e) {
            error.setValue("Giá trị tiền không hợp lệ");
            return;
        }

        // Convert names → IDs (hiện là mock)
        int companyId = 1;
        int contactId = 1;
        int managementId = 1;

        Opportunity opp;
        if (OpportunityFormFragment.MODE_UPDATE.equals(mode)
                && existing.getValue() != null) {

            Opportunity old = existing.getValue();

            opp = new Opportunity(
                    old.getId(),
                    title, companyId, contactId, price,
                    stage, date1, date2, description,
                    managementId,
                    old.getCallCount(),
                    old.getMessageCount()
            );

            repository.update(opp);
        } else {
            opp = new Opportunity(
                    0,
                    title, companyId, contactId, price,
                    stage, date1, date2, description,
                    managementId,
                    0,
                    0
            );

            repository.add(opp);
        }

        success.setValue(true);
    }
}
