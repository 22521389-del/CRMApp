package com.example.crmmobile.LeadDirectory;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.crmmobile.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.Calendar;
import java.util.Objects;
import java.util.function.Consumer;

public class lead_information extends Fragment {
    private ViewModelLead viewModelLead;
    private TextInputLayout birthday_layout;
    private TextInputEditText state_detail, edt_birthday, edt_family_name,
            edt_first_name, edt_phone_number, edt_email,
            edt_state;
    private AutoCompleteTextView edtTitle, edt_sex, edt_potential;

    public interface StringUpdater{
        void update(String s);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_lead_information,container,false);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initVariables(view);
        viewModelLead = new ViewModelProvider(requireActivity()).get(ViewModelLead.class);
        //lưu giá trị mỗi khi user nhập
        bindEditTexttoViewModel(edtTitle, s -> viewModelLead.title.setValue(s));
        bindEditTexttoViewModel(edt_family_name, s -> viewModelLead.hovatendem.setValue(s));
        bindEditTexttoViewModel(edt_first_name, s -> viewModelLead.first_name.setValue(s));
        bindEditTexttoViewModel(edt_phone_number, s->viewModelLead.phonenumber.setValue(s));
        bindEditTexttoViewModel(edt_email, s -> viewModelLead.Email.setValue(s));
        bindEditTexttoViewModel(edt_sex, s -> viewModelLead.Sex.setValue(s));
        bindEditTexttoViewModel(edt_birthday, s -> viewModelLead.Birthday.setValue(s));
        bindEditTexttoViewModel(edt_state, s-> viewModelLead.state.setValue(s));
        bindEditTexttoViewModel(state_detail, s -> viewModelLead.state_detail.setValue(s));
        bindEditTexttoViewModel(edt_potential, s -> viewModelLead.potential.setValue(s));
    }

    private void initVariables(View view) {
        edtTitle = view.findViewById(R.id.edt_title);
        String[] title = {"Ông", "Bà", "Anh", "Chị"};
        ArrayAdapter<String> AdapterTitle = new ArrayAdapter<String>(requireContext(), android.R.layout.simple_list_item_1, title);
        edtTitle.setAdapter(AdapterTitle);

        edt_family_name = view.findViewById(R.id.edt_family_name);
        edt_first_name = view.findViewById(R.id.edt_first_name);
        edt_phone_number = view.findViewById(R.id.edt_phone_number);
        edt_email = view.findViewById(R.id.edt_email);
        edt_sex = view.findViewById(R.id.edt_sex);
        String[] gender = {"Nam", "Nữ"};
        ArrayAdapter<String> AdapterGender = new ArrayAdapter<String>(requireContext(), android.R.layout.simple_list_item_1, gender);
        edt_sex.setAdapter(AdapterGender);

        edt_birthday = view.findViewById(R.id.edt_birthday);
        birthday_layout = view.findViewById(R.id.birthday_layout);
        //show calendar
        birthday_layout.setEndIconOnClickListener(v -> {
            showDatePicker(edt_birthday);
        });
        edt_state = view.findViewById(R.id.edt_state);
        state_detail = view.findViewById(R.id.state_detail);
        edt_potential = view.findViewById(R.id.edt_potential);
    }

    private void showDatePicker(TextInputEditText edtBirthday) {
        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(requireContext(),
                (view, selectedYear, selectedMonth, selectedDay)->{
                    String date = selectedDay + "/" + (selectedMonth + 1) + "/" + selectedYear;
                    edtBirthday.setText(date);
                }, year, month, day);

        datePickerDialog.show();
    }


    private void bindEditTexttoViewModel(EditText editText, StringUpdater updater) {
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
