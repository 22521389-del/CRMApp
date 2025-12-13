package com.example.crmmobile.LeadDirectory;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.graphics.shapes.Utils;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager2.widget.ViewPager2;

import com.example.crmmobile.Adapter.AdapterEditLead;
import com.example.crmmobile.AppConstant;
import com.example.crmmobile.DataBase.LeadReposity;
import com.example.crmmobile.R;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class EditLeadActivity extends AppCompatActivity {
    private ImageButton btn_back;
    private TabLayout tabLayout;
    private ViewPager2 vp_tab;
    private ViewModelLead viewModelLead;
    private Lead lead;
    private MaterialButton btn_abort;
    private LeadReposity db;
    private MaterialButton btn_save;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_lead);

        initVariables();

        lead = (Lead) getIntent().getSerializableExtra(AppConstant.LEAD_OBJECT);
        viewModelLead = new ViewModelProvider(this).get(ViewModelLead.class);

        if(lead != null){
            setValues();
        }

        AdapterEditLead adapterEdit = new AdapterEditLead(this, lead);
        vp_tab.setAdapter(adapterEdit);

        new TabLayoutMediator(tabLayout, vp_tab, ((tab, i) -> {
            if(i == 0) tab.setText("Thông tin Lead");
            else tab.setText("Thông tin khác");
        })).attach();

        btn_back.setOnClickListener(v -> {
            finish();
        });

        btn_save.setOnClickListener(v -> {
            String first_name = viewModelLead.first_name.getValue();
            String phone_number = viewModelLead.phonenumber.getValue();
            String Send_to = viewModelLead.Sendto.getValue();

            if (TextUtils.isEmpty(first_name)){
                Toast.makeText(this, "Vui lòng nhập tên", Toast.LENGTH_SHORT).show();
                return;
            }
            if (TextUtils.isEmpty(phone_number)) {
                Toast.makeText(this, "Vui lòng nhập số điện thoại", Toast.LENGTH_SHORT).show();
                return;
            }
            if (TextUtils.isEmpty(Send_to)){
                Toast.makeText(this, "Vui lòng nhập thông tin giao cho", Toast.LENGTH_SHORT).show();
                return;
            }

            updateLead();
        });
    }

    private void updateLead() {
        Intent intent = new Intent();
        db = new LeadReposity(this);

        lead.setHovaTendem(viewModelLead.hovatendem.getValue());
        lead.setTen(viewModelLead.first_name.getValue());
        lead.setTitle(viewModelLead.title.getValue());
        lead.setDienThoai(viewModelLead.phonenumber.getValue());
        lead.setEmail(viewModelLead.Email.getValue());
        lead.setGioitinh(viewModelLead.Sex.getValue());
        lead.setNgaysinh(viewModelLead.Birthday.getValue());
        lead.setTinhTrang(viewModelLead.state.getValue());
        lead.setDiachi(viewModelLead.Address.getValue());
        lead.setTinh(viewModelLead.Province.getValue());
        lead.setQuanHuyen(viewModelLead.District.getValue());
        lead.setQuocGia(viewModelLead.Nation.getValue());
        lead.setCongty(viewModelLead.company.getValue());
        lead.setGiaocho(viewModelLead.Sendto.getValue());
        lead.setNganhnghe(viewModelLead.Job.getValue());
        lead.setMaThue(viewModelLead.Tax.getValue());
        lead.setNgayLienHe(viewModelLead.contact_day.getValue());
//        lead.setDoanhThu(viewModelLead.);

        db.updateLead(lead);

        intent.putExtra(AppConstant.LEAD_OBJECT, lead);
        setResult(RESULT_OK, intent);// resend lead to update
        finish();
    }

    private void initVariables() {
        btn_back = findViewById(R.id.btn_back);
        tabLayout = findViewById(R.id.tablayout);
        vp_tab = findViewById(R.id.vp_tab);
        btn_abort = findViewById(R.id.abort_button);
        btn_save = findViewById(R.id.save_button);
    }

    private void setValues() {
        viewModelLead.hovatendem.setValue(lead.getHovaTendem());
        viewModelLead.first_name.setValue(lead.getTen());
        viewModelLead.company.setValue(lead.getCongty());
        viewModelLead.Sex.setValue(lead.getGioitinh());
        viewModelLead.Birthday.setValue(lead.getNgaysinh());
        viewModelLead.phonenumber.setValue(lead.getDienThoai());
        viewModelLead.Email.setValue(lead.getEmail());
        viewModelLead.state.setValue(lead.getTinhTrang());
        viewModelLead.Address.setValue(lead.getDiachi());
        viewModelLead.Province.setValue(lead.getTinh());
        viewModelLead.District.setValue(lead.getQuanHuyen());
        viewModelLead.Nation.setValue(lead.getQuocGia());
        viewModelLead.Job.setValue(lead.getNganhnghe());
        viewModelLead.number_of_employees.setValue(lead.getSoNV());
        viewModelLead.Revenue.setValue(lead.getDoanhThu());
        viewModelLead.Sendto.setValue(lead.getGiaocho());
        viewModelLead.Tax.setValue(lead.getMaThue());
        viewModelLead.contact_day.setValue(lead.getNgayLienHe());

    }
}
