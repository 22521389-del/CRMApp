package com.example.crmmobile.OpportunityDirectory;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

public class OpportunityDetailViewModel extends AndroidViewModel {

    private OpportunityRepository repo;
    private Opportunity opportunity;

    public OpportunityDetailViewModel(@NonNull Application app, int id) {
        super(app);
        repo = new OpportunityRepository(app);
        opportunity = repo.getById(id);
    }

    public Opportunity getOpportunity() {
        return opportunity;
    }
}
