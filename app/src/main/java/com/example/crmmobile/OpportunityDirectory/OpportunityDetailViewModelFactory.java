package com.example.crmmobile.OpportunityDirectory;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class OpportunityDetailViewModelFactory implements ViewModelProvider.Factory {

    private final Application application;
    private final int id;

    public OpportunityDetailViewModelFactory(Application app, int id) {
        this.application = app;
        this.id = id;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new OpportunityDetailViewModel(application, id);
    }
}
