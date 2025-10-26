package com.example.crmmobile;

import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.tabs.TabLayout;

public class TaoCongTyActivity extends AppCompatActivity {
    ImageButton btBack;

    private TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_taocongty);

        tabLayout = findViewById(R.id.tabLayout);
        btBack = findViewById(R.id.btnBack);

        //quay trở lại main
        btBack.setOnClickListener(v -> {
            finish();
        });

        // Hiển thị fragment đầu tiên mặc định
        replaceFragment(new ThongTinCongTyFragment());


        // Lắng nghe khi người dùng chuyển tab
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                Fragment selectedFragment;

                if (tab.getPosition() == 0) {
                    selectedFragment = new ThongTinCongTyFragment();
                } else {
                    selectedFragment = new ThongTinKhacFragment();
                }

                replaceFragment(selectedFragment);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                // Không cần xử lý gì
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                // Không cần xử lý gì
            }
        });
    }

    // Hàm thay fragment trong FrameLayout
    private void replaceFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frameLayoutThongTin, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}
