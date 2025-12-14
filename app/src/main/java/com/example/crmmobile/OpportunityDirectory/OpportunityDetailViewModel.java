package com.example.crmmobile.OpportunityDirectory;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

public class OpportunityDetailViewModel extends AndroidViewModel {

    private OpportunityRepository oppRepo;
    private MutableLiveData<Opportunity> editing = new MutableLiveData<>();


    public OpportunityDetailViewModel(@NonNull Application app) {
        super(app);
        oppRepo = OpportunityRepository.getInstance(app);
    }

    public void loadOpportunityById(int id) {
        Opportunity o = oppRepo.getById(id);
        editing.postValue(o); // luôn emit
    }


    public LiveData<Opportunity> getOpportunity() {
        return editing;
    }
}
