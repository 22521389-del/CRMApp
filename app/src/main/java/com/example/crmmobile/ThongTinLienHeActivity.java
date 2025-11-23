package com.example.crmmobile;


import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.tabs.TabLayout;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class ThongTinLienHeActivity extends AppCompatActivity {

    private TextView infoTab, thongTinKhacTab;
    private TabLayout tabLayout;
    private ImageView icBack;
    private Button btnLuu, btnHuy;

    private ThongTinNguoiLienHeFragment infoFragment;
    private ThongTinKhacFragment khacFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thong_tin);

        tabLayout = findViewById(R.id.tabLayout);
        icBack = findViewById(R.id.ic_back);
        btnLuu = findViewById(R.id.btnLuu);
        btnHuy = findViewById(R.id.btnHuy);

        // Khởi tạo fragment
        infoFragment = new ThongTinNguoiLienHeFragment();
        khacFragment = new ThongTinKhacFragment();

        loadFragment(infoFragment);

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                Fragment selectedFragment;

                if(tab.getPosition() == 0){
                    selectedFragment = infoFragment;
                }else{
                    selectedFragment = khacFragment;
                }

                loadFragment(selectedFragment);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

//        // --- Mặc định ngày = hôm nay ---
//        Calendar calendar = Calendar.getInstance();
//        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
//        String today = sdf.format(calendar.getTime());
//        infoFragment.setNgaySinhDefault(today);

        // --- Sự kiện lưu dữ liệu ---
        btnLuu.setOnClickListener(v -> saveData());
        icBack.setOnClickListener(v -> finish());
        btnHuy.setOnClickListener(v -> finish());
    }

    private void saveData() {
        // Lấy dữ liệu từ fragment
        String hoTen = infoFragment.getHoVaTenDem();
        String congTy = infoFragment.getCongTy();
        String ngay = infoFragment.getNgaySinh();

        Intent result = new Intent();
        result.putExtra("hoTen", hoTen);
        result.putExtra("congTy", congTy);
        result.putExtra("ngay", ngay);

        setResult(RESULT_OK, result);
        finish();
    }

    private void loadFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragmentContainer, fragment);
        transaction.commit();
    }

//    private void setActiveTab(TextView active, TextView inactive) {
//        // Reset tất cả tab
//        infoTab.setTextColor(getResources().getColor(R.color.grey));
//        infoTab.setBackgroundResource(android.R.color.transparent);
//
//        thongTinKhacTab.setTextColor(getResources().getColor(R.color.grey));
//        thongTinKhacTab.setBackgroundResource(android.R.color.transparent);
//
//        // Tab đang active
//        active.setTextColor(getResources().getColor(R.color.blue));
//        active.setBackgroundResource(R.drawable.edittext_line);
//    }
}