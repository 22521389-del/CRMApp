package com.example.crmmobile.LeadDirectory;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager2.widget.ViewPager2;

import com.example.crmmobile.Adapter.AdapterTab;
import com.example.crmmobile.AppConstant;
import com.example.crmmobile.R;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class DetailLeadActivity extends AppCompatActivity {
    private ViewModelLead viewModelLead;
    private TextView tv_user, tv_phone, tv_email, tv_company;
    private ImageView iv_back;
    private TabLayout tabLayout;
    private ViewPager2 viewPager2;
    private Lead lead;

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

        setValues();

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
    }

    private void initVariables() {
        tv_user = findViewById(R.id.tv_user);
        iv_back = findViewById(R.id.iv_back);
        tv_phone = findViewById(R.id.tv_phone);
        tv_email = findViewById(R.id.tv_email);
        tv_company = findViewById(R.id.tv_company);
        tabLayout = findViewById(R.id.tabLayout);
        viewPager2 = findViewById(R.id.vp_tab);
    }
}
