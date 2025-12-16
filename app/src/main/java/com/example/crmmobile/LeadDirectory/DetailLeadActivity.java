package com.example.crmmobile.LeadDirectory;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager2.widget.ViewPager2;

import com.example.crmmobile.Adapter.AdapterTab;
import com.example.crmmobile.AppConstant;
import com.example.crmmobile.DataBase.NhanVienRepository;
import com.example.crmmobile.MainDirectory.InitClass;
import com.example.crmmobile.R;
import com.google.android.material.chip.Chip;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class DetailLeadActivity extends AppCompatActivity {
    private static final String TAG = "DETAIL_ACTIVITY";
    private ViewModelLead viewModelLead;
    private TextView tv_user, tv_phone, tv_email, tv_company,tv_created_by, tv_person_in_charge;
    private ImageView iv_back, iv_created_by, iv_person_in_charge;
    private TabLayout tabLayout;
    private ViewPager2 viewPager2;
    private Lead lead;
    private Chip chip_status;
    private Map<String, Integer> stateColor;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_lead);
        initVariables();
        viewModelLead = new ViewModelProvider(this).get(ViewModelLead.class);
        lead = (Lead) getIntent().getSerializableExtra(AppConstant.LEAD_OBJECT);

        String full_name = lead.getTitle() + " " + lead.getHovaTendem() +" " + lead.getTen();
        tv_user.setText(full_name);
        tv_phone.setText(lead.getDienThoai());
        tv_email.setText(lead.getEmail());
        tv_company.setText(lead.getCongty());
        Log.e(TAG, "Send to: " + lead.getGiaocho());
        tv_created_by.setText(lead.getNguoitao());
        setStateChip();

        setValues();
        MapIDtoNameText();

        iv_back.setOnClickListener(v -> {
            finish();
        });

        //adapter
        AdapterTab adapterTab = new AdapterTab(this);
        viewPager2.setAdapter(adapterTab);
        new TabLayoutMediator(tabLayout, viewPager2, (tab, position)->{
            switch (position){
                case 0:
                    tab.setText("Tổng quan");
                    break;
                case 1:
                    tab.setText("Chi tiết");
                    break;
                default:
                    break;
            }
        }).attach();

    }

    private void MapIDtoNameText() {
        Executor executor = Executors.newSingleThreadExecutor();
        Handler mainHandler = new Handler(Looper.getMainLooper());

        executor.execute(()->{
            NhanVienRepository nhanVienRepository = new NhanVienRepository(this);
            String tenNV = nhanVienRepository.getNameByID(lead.getGiaochoID());
            String TenNVTao = nhanVienRepository.getNameByID(lead.getNguoitaoID());
            mainHandler.post(()->{
                if (!TextUtils.isEmpty(tenNV)){
                    tv_person_in_charge.setText(tenNV);
                    tv_created_by.setText(TenNVTao);
                }else {
                    tv_person_in_charge.setText("");
                    tv_created_by.setText("");
                }

                Log.e(TAG, "Tên người phụ trác:" + tenNV);
                Log.e(TAG, "TENNV phụ trách: " + tv_person_in_charge.getText().toString());
                Log.e(TAG, "Tên người tạo:" + TenNVTao);
                Log.e(TAG, "TENNV tạo: " + tv_created_by.getText().toString());
            });
        });
    }

    private void setStateChip() {
        stateColor = new HashMap<>();
        stateColor.put("Mới", Color.parseColor("#BBE2EC"));
        stateColor.put("Chưa liên hệ được", Color.parseColor("#FADA7A"));
        stateColor.put("Liên hệ sau", Color.parseColor("#C5BAFF"));
        stateColor.put("Ngừng chăm sóc", Color.parseColor("#BD2E2D"));
        stateColor.put("Đã chuyển đổi", Color.parseColor("#38A4F9"));
        stateColor.put("Đã liên hệ", Color.parseColor("#78C1F3"));
        stateColor.put("", Color.LTGRAY);

        String status = lead.getTinhTrang();

        if (status == null || status.isEmpty()){
            chip_status.setVisibility(View.GONE);
        }else{
            //set chip color
            chip_status.setVisibility(View.VISIBLE);
            Integer color = stateColor.getOrDefault(status, Color.LTGRAY);
            chip_status.setChipBackgroundColor(ColorStateList.valueOf(color));
            chip_status.setText(status);
        }
    }

    private void setValues() {
        viewModelLead.hovatendem.setValue(lead.getHovaTendem());
        viewModelLead.first_name.setValue(lead.getTen());
        viewModelLead.Sex.setValue(lead.getGioitinh());
        viewModelLead.Birthday.setValue(lead.getNgaysinh());
        viewModelLead.phonenumber.setValue(lead.getDienThoai());
        viewModelLead.Email.setValue(lead.getEmail());
        viewModelLead.state.setValue(lead.getTinhTrang());
        viewModelLead.Address.setValue(lead.getDiachi());
        viewModelLead.Province.setValue(lead.getTinh());
        viewModelLead.company.setValue(lead.getCongty());
        viewModelLead.District.setValue(lead.getQuanHuyen());
        viewModelLead.Nation.setValue(lead.getQuocGia());
        viewModelLead.Job.setValue(lead.getNganhnghe());
        viewModelLead.number_of_employees.setValue(lead.getSoNV());
        viewModelLead.Revenue.setValue(lead.getDoanhThu());
        viewModelLead.state.setValue(lead.getTinhTrang());
        viewModelLead.state_detail.setValue(lead.getMota());
        viewModelLead.Tax.setValue(lead.getMaThue());
        viewModelLead.description.setValue(lead.getMota());
        viewModelLead.SendtoID.setValue(lead.getGiaochoID());
        viewModelLead.CreatedByID.setValue(lead.getNguoitaoID());
        int level = InitClass.getIconNhanVien(lead.getGiaochoID());
        iv_person_in_charge.setImageLevel(level);
        int create_by_level = InitClass.getIconNhanVien(lead.getNguoitaoID());
        iv_created_by.setImageLevel(create_by_level);
        Log.e(TAG, "Giao cho ID: "+ lead.getGiaochoID());
        Log.e(TAG, "Người tạo ID: " + lead.getNguoitaoID());
    }

    private void initVariables() {
        tv_user = findViewById(R.id.tv_user);
        iv_back = findViewById(R.id.iv_back);
        tv_phone = findViewById(R.id.tv_phone);
        tv_email = findViewById(R.id.tv_email);
        tv_company = findViewById(R.id.tv_company);
        tabLayout = findViewById(R.id.tabLayout);
        viewPager2 = findViewById(R.id.vp_tab);
        tv_created_by = findViewById(R.id.tv_created_by);
        tv_person_in_charge = findViewById(R.id.tv_person_in_charge);
        iv_created_by = findViewById(R.id.iv_created_by);
        iv_person_in_charge = findViewById(R.id.iv_person_in_charge);
        chip_status = findViewById(R.id.chip_status);
    }
}
