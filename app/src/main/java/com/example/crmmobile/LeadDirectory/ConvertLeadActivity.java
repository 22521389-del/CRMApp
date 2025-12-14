package com.example.crmmobile.LeadDirectory;

import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.crmmobile.AppConstant;
import com.example.crmmobile.R;
import com.google.android.material.button.MaterialButton;

public class ConvertLeadActivity extends AppCompatActivity {
    private EditText et_chance, et_sale_step, et_opportrate, et_predicted, et_opportvalue,
            et_companyname, et_job, et_source, et_firstname, et_phonenumber, et_ship,
            ed_first_lastName, ed_email;
    private MaterialButton abort_button, save_button;
    private CheckBox cb_new_personal, cb_chance, cb_organization;
    private ImageView iv_back;
    private Lead lead;
    private ViewModelLead viewModelLead;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_convert_lead);
        initVariables();

        viewModelLead = new ViewModelProvider(this).get(ViewModelLead.class);
        lead = (Lead)getIntent().getSerializableExtra(AppConstant.LEAD_OBJECT);

        setRequiredLabel(et_chance, R.string.chance);
        setRequiredLabel(et_sale_step, R.string.sales_step);
        setRequiredLabel(et_opportrate, R.string.success_rate);
        setRequiredLabel(et_predicted, R.string.predicted_date);
        setRequiredLabel(et_opportvalue, R.string.opportunaty_value);
        setRequiredLabel(et_companyname, R.string.name_company);
        setRequiredLabel(et_job, R.string.job_infor);
        setRequiredLabel(et_source, R.string.source);
        setRequiredLabel(et_firstname, R.string.first_name);
        setRequiredLabel(et_phonenumber, R.string.SDT);
        setRequiredLabel(et_ship, R.string.shipto);

        iv_back.setOnClickListener(v -> {
            finish();
        });
        setCheckInit();

        cb_new_personal.setOnCheckedChangeListener(setNewPersonChecked);
        cb_chance.setOnCheckedChangeListener(setchanceChecked);
        cb_organization.setOnCheckedChangeListener(setOrganizationChecked);

        if (lead != null){
            Log.d("LEAD_DEBUG", "Ten = " + lead.getTen());
            et_firstname.setText(lead.getTen());
        }

        save_button.setOnClickListener(v -> {

        });
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

        if (!isChecked){
            et_companyname.setText("");
            et_job.setText("");
            et_source.setText("");
        }
    };

    CompoundButton.OnCheckedChangeListener setNewPersonChecked = (buttonView, isChecked) -> {
        ed_first_lastName.setEnabled(isChecked);
        et_firstname.setEnabled(isChecked);
        ed_email.setEnabled(isChecked);
        et_phonenumber.setEnabled(isChecked);
        et_ship.setEnabled(isChecked);

        if (!isChecked){
            ed_first_lastName.setText("");
            et_firstname.setText("");
            ed_email.setText("");
            et_phonenumber.setText("");
            et_ship.setText("");
        }
    };

    CompoundButton.OnCheckedChangeListener setchanceChecked = (buttonView, isChecked)->{
        et_chance.setEnabled(isChecked);
        et_sale_step.setEnabled(isChecked);
        et_opportrate.setEnabled(isChecked);
        et_predicted.setEnabled(isChecked);
        et_opportvalue.setEnabled(isChecked);

        if (!isChecked){
            et_chance.setText("");
            et_sale_step.setText("");
            et_opportrate.setText("");
            et_predicted.setText("");
            et_opportvalue.setText("");
        }
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
