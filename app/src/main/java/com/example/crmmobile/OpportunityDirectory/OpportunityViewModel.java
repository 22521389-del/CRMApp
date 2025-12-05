package com.example.crmmobile.OpportunityDirectory;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.crmmobile.OpportunityDirectory.Opportunity;

import java.util.List;

public class OpportunityViewModel extends AndroidViewModel {
    private OpportunityRepository repository;
    private MutableLiveData<List<Opportunity>> opportunities = new MutableLiveData<>();

    public OpportunityViewModel(@NonNull Application application) {
        super(application);
        repository = OpportunityRepository.getInstance(application);
        loadOpportunities();
    }

    public LiveData<List<Opportunity>> getOpportunities() { return opportunities; }

    public void loadOpportunities() {
        // postValue để thread-safe
        opportunities.postValue(repository.getAll());
    }

    public void add(Opportunity opp) {
        repository.add(opp);
        loadOpportunities();
    }

    public void update(Opportunity opp) {
        repository.update(opp);
        loadOpportunities();
    }

    public void delete(int id) {
        repository.delete(id);
        loadOpportunities();
    }
}
