package com.example.crmmobile.LeadDirectory;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.crmmobile.R;
import com.google.android.material.textfield.TextInputEditText;

import java.util.Objects;

public class another_lead_information extends Fragment {
    private TextInputEditText company_name, edt_tax, number_of_employees, edt_district
            , edt_address, edt_province, edt_nation;
    private ViewModelLead viewModelLead;
    private AutoCompleteTextView tv_sendto, job_name, edt_revenue;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_another_information,container,false);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initVariables(view);
        viewModelLead = new ViewModelProvider(requireActivity()).get(ViewModelLead.class);

        bindEditTexttoViewModel(tv_sendto, s -> viewModelLead.Sendto.setValue(s));
        bindEditTexttoViewModel(company_name, s -> viewModelLead.company.setValue(s));
        bindEditTexttoViewModel(edt_tax, s -> viewModelLead.Tax.setValue(s));
        bindEditTexttoViewModel(number_of_employees, s -> viewModelLead.number_of_employees.setValue(s));
        bindEditTexttoViewModel(edt_district, s -> viewModelLead.District.setValue(s));
        bindEditTexttoViewModel(edt_address, s -> viewModelLead.Address.setValue(s));
        bindEditTexttoViewModel(edt_province, s -> viewModelLead.Province.setValue(s));
        bindEditTexttoViewModel(edt_nation, s -> viewModelLead.Nation.setValue(s));
    }

    private void initVariables(View view) {
        company_name = view.findViewById(R.id.company_name);
        tv_sendto = view.findViewById(R.id.tv_sendto);
        edt_tax = view.findViewById(R.id.edt_tax);
        number_of_employees = view.findViewById(R.id.number_of_employees);
        job_name = view.findViewById(R.id.job_name);
        edt_revenue = view.findViewById(R.id.edt_revenue);
        edt_district = view.findViewById(R.id.edt_district);
        edt_address = view.findViewById(R.id.edt_address);
        edt_province = view.findViewById(R.id.edt_province);
        edt_nation = view.findViewById(R.id.edt_nation);
    }

    private void bindEditTexttoViewModel(EditText editText, lead_information.StringUpdater updater) {
        editText.addTextChangedListener(new TextWatcher() {
                @Override
                public void afterTextChanged(Editable s) {

                }

                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    updater.update(s.toString());
                }
            }
        );
    }
}
