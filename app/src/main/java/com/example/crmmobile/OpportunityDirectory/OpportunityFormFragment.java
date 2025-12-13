package com.example.crmmobile.OpportunityDirectory;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import java.text.ParseException;
import java.util.Calendar;
import java.util.List;

import com.example.crmmobile.R;

public class OpportunityFormFragment extends Fragment {

    private OpportunityFormViewModel formVM;
    private OpportunityFormHandler handler;

    private String mode;
    private int opId = -1;
    private Opportunity existing;
    private EditText etName, etValue, etDate1, etDate2, etDesc;
    private AutoCompleteTextView etCompany, etContact, etStage, etManager;
    private TextView tvTitle;
    private Button btnSave, btnCancel;
    private ImageButton btnBack;

    private boolean loaded1, loaded2, loaded3;

    public static OpportunityFormFragment newInstance(int id, String m) {
        Bundle b = new Bundle();
        b.putInt("id", id);
        b.putString("mode", m);

        OpportunityFormFragment f = new OpportunityFormFragment();
        f.setArguments(b);
        return f;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle state) {

        View v = inflater.inflate(R.layout.layout_opportunity_form, container, false);

        formVM = new ViewModelProvider(requireActivity()).get(OpportunityFormViewModel.class);
        handler = formVM.getHandler();

        initViews(v);
        readArgs();
        setupActions();
        observeOpportunity();
        observeDropdowns();
        setupStaticDropdowns();

        setupDatePickerIcon(etDate1);
        setupDatePickerIcon(etDate2);

        return v;
    }

    private void observeOpportunity() {
        formVM.getEditingOpportunity().observe(getViewLifecycleOwner(), o -> {
            if (o != null) {
                existing = o;     // <<< SAVE
                tryPopulate();
            }
        });
    }

    private void initViews(View v) {
        tvTitle = v.findViewById(R.id.tv_opportunity_title_create);
        btnBack = v.findViewById(R.id.btn_opportunity_back);

        etName = v.findViewById(R.id.et_opportunity_name);
        etCompany = v.findViewById(R.id.et_company);
        etContact = v.findViewById(R.id.et_contact);
        etValue = v.findViewById(R.id.et_value);
        etStage = v.findViewById(R.id.sp_sales_stage);
        etDate1 = v.findViewById(R.id.et_expected_date);
        etDate2 = v.findViewById(R.id.et_expected_date_2);
        etDesc = v.findViewById(R.id.et_description);
        etManager = v.findViewById(R.id.et_management);

        btnSave = v.findViewById(R.id.btn_save);
        btnCancel = v.findViewById(R.id.btn_cancel);
    }

    private void readArgs() {
        if (getArguments() != null) {
            mode = getArguments().getString("mode");
            opId = getArguments().getInt("id", -1);
        }

        if ("update".equals(mode)) {
            tvTitle.setText("Cập nhật cơ hội");
            btnSave.setText("Cập nhật");
        } else {
            tvTitle.setText("Thêm cơ hội mới");
            btnSave.setText("Lưu");
        }

        // Load object từ id
        if ("update".equals(mode)) {
            formVM.loadOpportunityById(opId);
        }
    }

    private void setupActions() {
        btnBack.setOnClickListener(v -> requireActivity().onBackPressed());
        btnCancel.setOnClickListener(v -> requireActivity().finish());

        btnSave.setOnClickListener(v -> {
            try {
                saveForm();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    private void observeDropdowns() {

        formVM.getCompanies().observe(getViewLifecycleOwner(), list -> {
            setAdapter(etCompany, list);
            loaded1 = true;
            if (mode.equals("update")) tryPopulate();
        });

        formVM.getContacts().observe(getViewLifecycleOwner(), list -> {
            setAdapter(etContact, list);
            loaded2 = true;
            if (mode.equals("update")) tryPopulate();
        });

        formVM.getEmployees().observe(getViewLifecycleOwner(), list -> {
            setAdapter(etManager, list);
            loaded3 = true;
            if (mode.equals("update")) tryPopulate();
        });
    }

    private <T> void setAdapter(AutoCompleteTextView ac, List<T> list) {
        ArrayAdapter<T> ad = new ArrayAdapter<>(requireContext(),
                android.R.layout.simple_dropdown_item_1line, list);
        ac.setAdapter(ad);
        ac.setThreshold(0);
        ac.setOnClickListener(v -> ac.showDropDown());

        ac.setOnItemClickListener((p, v, pos, id) -> {
            if (list.get(pos) instanceof Company)
                formVM.setSelectedCompanyId(((Company) list.get(pos)).getId());

            if (list.get(pos) instanceof Contact)
                formVM.setSelectedContactId(((Contact) list.get(pos)).getId());

            if (list.get(pos) instanceof Employee)
                formVM.setSelectedManagementId(((Employee) list.get(pos)).getId());
        });
    }

    private void setupStaticDropdowns() {
        String[] stages = {
                "Nhận diện người ra quyết định",
                "Phân tích nhận thức",
                "Đề xuất/ Báo giá",
                "Thương lượng đàm phán"
        };
        ArrayAdapter<String> ad = new ArrayAdapter<>(requireContext(),
                android.R.layout.simple_dropdown_item_1line, stages);
        etStage.setAdapter(ad);
    }

    @SuppressLint("ClickableViewAccessibility")
    private void setupDatePickerIcon(EditText edt) {
        edt.setOnTouchListener((v, event) -> {
            if (event.getAction() == android.view.MotionEvent.ACTION_UP) {
                int drawableRight = 2;
                if (event.getRawX() >= (edt.getRight() - edt.getCompoundDrawables()[drawableRight].getBounds().width())) {
                    openDatePicker(edt);
                    return true;
                }
            }
            return false;
        });
    }

    private void openDatePicker(EditText targetEdt) {
        final Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog dialog = new DatePickerDialog(
                requireContext(),
                (view, y, m, d) -> {
                    String formatted = handler.formatSelectedDate(d, m, y);
                    targetEdt.setText(formatted);
                },
                year, month, day
        );
        dialog.show();
    }


    private void tryPopulate() {
        if (!loaded1 || !loaded2 || !loaded3) return;
        populateForm();
    }

    private void populateForm() {
        etName.setText(existing.getTitle());
        etValue.setText(String.valueOf(existing.getPrice()));
        etDate1.setText(existing.getDate());
        etDate2.setText(existing.getExpectedDate2());
        etDesc.setText(existing.getDescription());
        etStage.setText(existing.getStatus(), false);

        etCompany.setText(
                handler.findCompanyName(existing.getCompany(), formVM.getCompanies().getValue()),
                false
        );

        etContact.setText(
                handler.findContactName(existing.getContact(), formVM.getContacts().getValue()),
                false
        );

        etManager.setText(
                handler.findEmployeeName(existing.getManagement(), formVM.getEmployees().getValue()),
                false
        );
    }

    private void saveForm() throws ParseException {

        if (!handler.validateTitle(etName.getText().toString())) {
            etName.setError("Không được để trống");
            return;
        }

        Opportunity o = formVM.createOpportunity(
                mode,
                existing,
                etName.getText().toString(),
                etValue.getText().toString(),
                etStage.getText().toString(),
                etDate1.getText().toString(),
                etDate2.getText().toString(),
                etDesc.getText().toString()
        );

        OpportunityViewModel vm = new ViewModelProvider(requireActivity()).get(OpportunityViewModel.class);

        if (mode.equals("update")) {
            vm.update(o);
            Toast.makeText(getContext(), "Cập nhật thành công", Toast.LENGTH_SHORT).show();
        } else {
            vm.add(o);
            Toast.makeText(getContext(), "Thêm mới thành công", Toast.LENGTH_SHORT).show();
        }

        requireActivity().getSupportFragmentManager().popBackStack();
    }
}
