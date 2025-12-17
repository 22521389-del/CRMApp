package com.example.crmmobile.OpportunityDirectory;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import java.util.List;

import com.example.crmmobile.OpportunityDirectory.Opportunity;

public class OpportunityDetailPagerAdapter extends FragmentStateAdapter {

    private final int tabCount;
    private final int opportunityId;

    public OpportunityDetailPagerAdapter(
            @NonNull FragmentActivity fragmentActivity,
            int tabCount,
            int opportunityId
    ) {
        super(fragmentActivity);
        this.tabCount = tabCount;
        this.opportunityId = opportunityId;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                return OpportunityDetailTabOverviewFragment
                        .newInstance(opportunityId);

            case 1:
                return OpportunityDetailTabInfoFragment
                        .newInstance(opportunityId);

            default:
                return new EmptyFragment();
        }
    }

    @Override
    public int getItemCount() {
        return tabCount;
    }
}
