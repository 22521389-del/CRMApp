package com.example.crmmobile.OpportunityDirectory;

import android.app.Application;
import android.util.Log;

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
        Log.d("DETAIL_VM", "loadOpportunityById(" + id + ")");
        Opportunity o = oppRepo.getById(id);
        Opportunity copy = new Opportunity(
                o.getId(),
                o.getTitle(),
                o.getCompany(),
                o.getContact(),
                o.getPrice(),
                o.getStatus(),
                o.getDate(),
                o.getExpectedDate2(),
                o.getDescription(),
                o.getManagement(),
                o.getCallCount(),
                o.getMessageCount()
        );

        Log.d("DETAIL_VM",
                "emit status=" + copy.getStatus() +
                        " hash=" + System.identityHashCode(copy)
        );

        editing.setValue(copy);
    }


    public LiveData<Opportunity> getOpportunity() {
        return editing;
    }
}
