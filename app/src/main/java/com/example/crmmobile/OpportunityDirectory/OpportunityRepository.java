package com.example.crmmobile.OpportunityDirectory;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;

import java.util.List;

public class OpportunityRepository {
    private static OpportunityRepository instance;
    private OpportunityDAO dao;

    OpportunityRepository(Context context) {
        dao = new OpportunityDAO(context.getApplicationContext());
    }

    public static synchronized OpportunityRepository getInstance(Context context) {
        if (instance == null) {
            instance = new OpportunityRepository(context);
        }
        return instance;
    }

    public List<Opportunity> getAll() {
        return dao.getAll();
    }
//    public Opportunity getById(int id) {
//        for (Opportunity o : dao.getAll()) if (o.getId() == id) return o;
//        return null;
//    }

    public Opportunity getById(int id) {
        return dao.getById(id);
    }
    public long add(Opportunity opportunity) {
        return dao.add(opportunity);
    }
    public void update(Opportunity opportunity) {
        dao.update(opportunity);
    }
    public void delete(int id) {
        dao.delete(id);
    }

    public interface Callback { void onComplete(boolean success); }

    public void updateStage(Opportunity opportunity, String newStage, String note, Callback callback) {
        // Giả lập call API
        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            opportunity.setStatus(newStage);
            callback.onComplete(true);
        }, 1000);
    }
}



