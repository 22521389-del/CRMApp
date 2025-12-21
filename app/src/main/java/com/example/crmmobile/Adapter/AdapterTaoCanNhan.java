package com.example.crmmobile.Adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.crmmobile.IndividualDirectory.ThongTinKhacFragment;
import com.example.crmmobile.IndividualDirectory.ThongTinNguoiLienHeFragment;

public class AdapterTaoCanNhan extends FragmentStateAdapter {
    public static final int TAB_INFORMATION = 0;
    public static final int TAB_ANOTHER_INFORMATION = 1;

    public AdapterTaoCanNhan(@NonNull Fragment fragment) {
        super(fragment);
    }

    public AdapterTaoCanNhan(@NonNull FragmentActivity fragment) {
        super(fragment);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        if (position == TAB_INFORMATION){
            return new ThongTinNguoiLienHeFragment();
        }else {
            return new ThongTinKhacFragment();
        }
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
