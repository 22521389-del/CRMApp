package com.example.crmmobile.Adapter;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.adapter.FragmentViewHolder;

import com.example.crmmobile.AppConstant;
import com.example.crmmobile.LeadDirectory.Lead;
import com.example.crmmobile.LeadDirectory.another_lead_information;
import com.example.crmmobile.LeadDirectory.lead_information;

import java.util.List;

public class AdapterEditLead extends FragmentStateAdapter {
    public static final int TAB_INFORMATION = 0;
    public static final int TAB_ANOTHER_INFORMATION = 1;

    private Lead lead;
    public AdapterEditLead(@NonNull FragmentActivity fragmentActivity, Lead lead) {
        super(fragmentActivity);
        this.lead = lead;
    }

    @Override
    public int getItemCount() {
        return 2;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        Fragment fragment;
        if(position == TAB_INFORMATION){
            fragment = new lead_information();
        }
        else{
            fragment =  new another_lead_information();
        }

        Bundle bundle = new Bundle();
        bundle.putSerializable(AppConstant.KEY_LEAD_DATA, lead);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onBindViewHolder(@NonNull FragmentViewHolder holder, int position, @NonNull List<Object> payloads) {
        super.onBindViewHolder(holder, position, payloads);
    }
}