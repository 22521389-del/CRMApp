package com.example.crmmobile.MainDirectory;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.crmmobile.DataBase.RecentRepository;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class RecentViewModel extends AndroidViewModel {
    private final MutableLiveData<List<Recent>> recentlist = new MutableLiveData<>();
    private RecentRepository recentRepository;
    private Executor executor = Executors.newSingleThreadExecutor();
    public RecentViewModel(@NonNull Application application) {
        super(application);
        recentRepository = new RecentRepository(application);
        loadRecent();
    }

    public LiveData<List<Recent>> getRecentList(){
        return recentlist;
    }

    public void loadRecent() {
        executor.execute(()->{
            List<Recent> list = recentRepository.getAllRecent();
            recentlist.postValue(list);
        });
    }

    public void upsertRecent(Recent recent){
        executor.execute(()->{
            recentRepository.addRecent(recent);
            loadRecent();
        });
    }
}
