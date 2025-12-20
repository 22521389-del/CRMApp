package com.example.crmmobile.LeadDirectory;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.Html;
import android.util.Log;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.crmmobile.AppConstant;
import com.example.crmmobile.DataBase.CaNhanRepository;
import com.example.crmmobile.DataBase.LeadRepository;
import com.example.crmmobile.DataBase.NhanVienRepository;
import com.example.crmmobile.IndividualDirectory.CaNhan;
import com.example.crmmobile.MainDirectory.InitClass;
import com.example.crmmobile.R;
import com.google.android.material.button.MaterialButton;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class ConvertLeadActivity extends AppCompatActivity {
    private static final String TAG = "CONVERT_LEAD";
    private EditText et_chance, et_sale_step, et_opportrate, et_predicted, et_opportvalue,
            et_companyname, et_job, et_source, et_firstname, et_phonenumber, et_ship,
            ed_first_lastName, ed_email;
    private MaterialButton abort_button, save_button;
    private CheckBox cb_new_personal, cb_chance, cb_organization;
    private ImageView iv_back;
    private Lead lead;
    private int leadId = -1;
    private CaNhanRepository caNhanRepository;
    private ViewModelLead viewModelLead;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_convert_lead);
        initVariables();

        viewModelLead = new ViewModelProvider(this).get(ViewModelLead.class);
        leadId = getIntent().getIntExtra("id", -1);
        caNhanRepository = new CaNhanRepository(this);

        setRequiredLabel(et_chance, R.string.chance);
        setRequiredLabel(et_sale_step, R.string.sales_step);
        setRequiredLabel(et_opportrate, R.string.success_rate);
        setRequiredLabel(et_predicted, R.string.predicted_date);
        setRequiredLabel(et_opportvalue, R.string.opportunaty_value);
        setRequiredLabel(et_companyname, R.string.name_company);
        setRequiredLabel(et_job, R.string.job_infor);
        setRequiredLabel(et_source, R.string.source);
        setRequiredLabel(et_phonenumber, R.string.SDT);
        setRequiredLabel(et_ship, R.string.shipto);

        iv_back.setOnClickListener(v -> {
            finish();
        });
        abort_button.setOnClickListener(v -> {
            finish();
        });
        setCheckInit();

        cb_new_personal.setOnCheckedChangeListener(setNewPersonChecked);
        cb_chance.setOnCheckedChangeListener(setchanceChecked);
        cb_organization.setOnCheckedChangeListener(setOrganizationChecked);

        if (leadId != -1){
            LeadRepository leadRepository = new LeadRepository(this);
            lead = leadRepository.getLeadByID(leadId);
        }
        if (lead != null){
            bindLeadtoViewModel();
            bindViewModeltoUI();
        }

        save_button.setOnClickListener(v -> {
            ConvertFromLeadtoContact();

        });
    }

    private void bindViewModeltoUI() {
        et_firstname.setText(viewModelLead.first_name.getValue());
        ed_first_lastName.setText(viewModelLead.hovatendem.getValue());
        ed_email.setText(viewModelLead.Email.getValue());
        et_phonenumber.setText(viewModelLead.phonenumber.getValue());
        et_companyname.setText(viewModelLead.company.getValue());
        et_job.setText(viewModelLead.Job.getValue());
        int Send_toID = lead.getGiaochoID();
        Log.e(TAG, "ID send: " + Send_toID);

        Executor executor = Executors.newSingleThreadExecutor();
        Handler mainHandler = new Handler(Looper.getMainLooper());

        executor.execute(()->{
            NhanVienRepository nhanVienRepository = new NhanVienRepository(this);
            String tenNV = nhanVienRepository.getNameByID(lead.getGiaochoID());
            mainHandler.post(()->{
                viewModelLead.SendtoID.setValue(Send_toID);
                viewModelLead.SendtoName.setValue(tenNV);
                et_ship.setText(tenNV);
                Log.e(TAG, "Tên người phụ trách:" + tenNV);
            });
        });
    }

    private void bindLeadtoViewModel() {
        viewModelLead.title.setValue(lead.getTitle());
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
        viewModelLead.Tax.setValue(lead.getMaThue());
        viewModelLead.description.setValue(lead.getMota());
        viewModelLead.SendtoID.setValue(lead.getGiaochoID());
        viewModelLead.CreatedByID.setValue(lead.getNguoitaoID());
    }

    private void ConvertFromLeadtoContact() {
        if (!cb_new_personal.isChecked()){
            Toast.makeText(this, "Vui lòng chọn contact để chuyển đổi", Toast.LENGTH_SHORT).show();
            return;
        }

        CaNhan cn = new CaNhan();
        cn.setDanhXung(lead.getTitle());
        cn.setHoVaTen(ed_first_lastName.getText().toString());
        cn.setTen(et_firstname.getText().toString());
        cn.setCongTy(lead.getCongty());
        cn.setGioiTinh(lead.getGioitinh());
        cn.setEmail(ed_email.getText().toString());
        cn.setDiDong(et_phonenumber.getText().toString());
        cn.setNgaySinh(lead.getNgaysinh());
        cn.setNgayTao(lead.getNgayLienHe());
        cn.setGiaoChoID(lead.getGiaochoID());
        cn.setDiaChi(lead.getDiachi());
        cn.setQuanHuyen(lead.getQuanHuyen());
        cn.setTinhTP(lead.getTinh());
        cn.setQuocGia(lead.getQuocGia());
        cn.setMoTa(lead.getMota());
        cn.setGhiChu(lead.getGhichu());

        long contactID = caNhanRepository.add(cn);

        if (contactID > 0){
            LeadRepository leadReposity = new LeadRepository(this);
            leadReposity.updateStatus(lead.getID(), "Đã chuyển đổi");
            Toast.makeText(this, "Chuyển đổi thành công", Toast.LENGTH_SHORT).show();
            setResult(RESULT_OK);
            finish();
        }
        else {
            Toast.makeText(this, "Chuyển đổi thất bại", Toast.LENGTH_SHORT).show();
        }
    }

    private void setCheckInit() {
        //personal
        cb_new_personal.setChecked(false);
        ed_first_lastName.setEnabled(false);
        et_firstname.setEnabled(false);
        ed_email.setEnabled(false);
        et_phonenumber.setEnabled(false);
        et_ship.setEnabled(false);

        //chance
        cb_chance.setChecked(false);
        et_chance.setEnabled(false);
        et_sale_step.setEnabled(false);
        et_opportrate.setEnabled(false);
        et_predicted.setEnabled(false);
        et_opportvalue.setEnabled(false);

        //organization
        cb_organization.setChecked(false);
        et_companyname.setEnabled(false);
        et_job.setEnabled(false);
        et_source.setEnabled(false);
    }

    CompoundButton.OnCheckedChangeListener setOrganizationChecked = (buttonView, isChecked) ->{
        et_companyname.setEnabled(isChecked);
        et_job.setEnabled(isChecked);
        et_source.setEnabled(isChecked);
    };

    CompoundButton.OnCheckedChangeListener setNewPersonChecked = (buttonView, isChecked) -> {
        ed_first_lastName.setEnabled(isChecked);
        et_firstname.setEnabled(isChecked);
        ed_email.setEnabled(isChecked);
        et_phonenumber.setEnabled(isChecked);
        et_ship.setEnabled(isChecked);
    };

    CompoundButton.OnCheckedChangeListener setchanceChecked = (buttonView, isChecked)->{
        et_chance.setEnabled(isChecked);
        et_sale_step.setEnabled(isChecked);
        et_opportrate.setEnabled(isChecked);
        et_predicted.setEnabled(isChecked);
        et_opportvalue.setEnabled(isChecked);
    };

    private void initVariables() {
        iv_back = findViewById(R.id.iv_back);
        et_chance = findViewById(R.id.ed_chance);
        et_sale_step = findViewById(R.id.et_sale_step);
        et_opportrate = findViewById(R.id.ed_opportrate);
        et_predicted = findViewById(R.id.ed_predicted);
        et_opportvalue = findViewById(R.id.et_opportvalue);
        et_companyname = findViewById(R.id.ed_company);
        et_job = findViewById(R.id.ed_job);
        et_source = findViewById(R.id.ed_source);
        et_firstname = findViewById(R.id.ed_firstname);
        et_phonenumber = findViewById(R.id.ed_sdt);
        et_ship = findViewById(R.id.ed_ship);
        abort_button = findViewById(R.id.abort_button);
        save_button = findViewById(R.id.save_button);
        cb_new_personal = findViewById(R.id.cb_new_personal);
        cb_chance = findViewById(R.id.cb_chance);
        cb_organization = findViewById(R.id.cb_organization);
        ed_first_lastName = findViewById(R.id.ed_first_lastName);
        ed_email = findViewById(R.id.ed_email);
    }

    private void setRequiredLabel(EditText et, int stringid){
        String hint = getString(stringid) + " <font color='#FF0000'> * </font>";
        et.setHint(Html.fromHtml(hint));
    }
}
