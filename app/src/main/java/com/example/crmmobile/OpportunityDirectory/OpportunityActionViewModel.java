package com.example.crmmobile.OpportunityDirectory;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.crmmobile.OpportunityDirectory.Opportunity;

public class OpportunityActionViewModel extends AndroidViewModel {

    private final MutableLiveData<Boolean> actionSuccess = new MutableLiveData<>();
    private final OpportunityRepository repository;

    public OpportunityActionViewModel(@NonNull Application application) {
        super(application);
        repository = OpportunityRepository.getInstance(application.getApplicationContext());
    }

    public LiveData<Boolean> getActionSuccess() {
        return actionSuccess;
    }

    public void changeStage(int opportunityId, String newStage, String note) {
        // Lấy object theo id
        Opportunity opp = repository.getById(opportunityId);
        if (opp != null) {
            repository.updateStage(opp, newStage, note, actionSuccess::postValue);
        } else {
            actionSuccess.postValue(false); // không tìm thấy
        }
    }
}
