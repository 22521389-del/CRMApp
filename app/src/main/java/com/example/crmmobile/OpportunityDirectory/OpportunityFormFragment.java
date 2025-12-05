package com.example.crmmobile.OpportunityDirectory;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;

import com.example.crmmobile.OpportunityDirectory.Opportunity;
import com.example.crmmobile.R;

public class OpportunityFormFragment extends Fragment {

    public static final String MODE_CREATE = "create";
    public static final String MODE_UPDATE = "update";

    private String mode;
    private Opportunity existingOpportunity;

    private EditText etOpportunityName, etValue, etExpectedDate, etExpectedDate2, etDescription, etManagement;
    private AutoCompleteTextView etCompany, etContact, spSalesStage;
    private TextView tvHeaderTitle;
    private Button btnSave, btnCancel;
    private ImageButton btnBack;


    public static OpportunityFormFragment newInstance(Opportunity opportunity, String mode) {
        OpportunityFormFragment fragment = new OpportunityFormFragment();
        Bundle args = new Bundle();
        args.putSerializable("opportunity", opportunity);
        args.putString("mode", mode);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_opportunity_form, container, false);

        initViews(view);
        setupDropdowns();
        handleArguments();
        setupActions();

        return view;
    }

    private void initViews(View view) {
        // Header
        tvHeaderTitle = view.findViewById(R.id.tv_opportunity_title_create);
        btnBack = view.findViewById(R.id.btn_opportunity_back);

        // Body
        etOpportunityName = view.findViewById(R.id.et_opportunity_name);
        etCompany = view.findViewById(R.id.et_company);
        etContact = view.findViewById(R.id.et_contact);
        etValue = view.findViewById(R.id.et_value);
        spSalesStage = view.findViewById(R.id.sp_sales_stage);
        etExpectedDate = view.findViewById(R.id.et_expected_date);
        etExpectedDate2 = view.findViewById(R.id.et_expected_date_2);
        etDescription = view.findViewById(R.id.et_description);
        etManagement = view.findViewById(R.id.et_management);

        // Footer
        btnSave = view.findViewById(R.id.btn_save);
        btnCancel = view.findViewById(R.id.btn_cancel);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // 🔹 Thông tin cơ hội
        setupToggle(view.findViewById(R.id.layout_section_header),
                view.findViewById(R.id.iv_arrow_detail_toggle),
                view.findViewById(R.id.layout_body));

        // 🔹 Thông tin quản lý
        setupToggle(view.findViewById(R.id.layout_management_header),
                view.findViewById(R.id.iv_arrow_management_toggle),
                view.findViewById(R.id.layout_management_content));


    }
    private void setupToggle(View header, ImageView toggleIcon, LinearLayout contentLayout) {
        View.OnClickListener listener = v -> {
            boolean isVisible = contentLayout.getVisibility() == View.VISIBLE;
            contentLayout.setVisibility(isVisible ? View.GONE : View.VISIBLE);
            toggleIcon.setImageResource(isVisible ? R.drawable.ic_arrow_down : R.drawable.ic_arrow_up);
        };

        header.setOnClickListener(listener);
        toggleIcon.setOnClickListener(listener);
    }


    private void setupDropdowns() {
        String[] companies = {"Google", "Microsoft", "Apple", "Meta"};
        String[] contacts = {"John Doe", "Jane Smith", "Alice Johnson"};
        String[] stages = {"Prospecting", "Qualification", "Proposal", "Negotiation", "Closed Won", "Closed Lost"};
        String[] rates = {"10%", "25%", "50%", "75%", "90%", "100%"};

        etCompany.setAdapter(new ArrayAdapter<>(requireContext(), android.R.layout.simple_dropdown_item_1line, companies));
        etContact.setAdapter(new ArrayAdapter<>(requireContext(), android.R.layout.simple_dropdown_item_1line, contacts));
        spSalesStage.setAdapter(new ArrayAdapter<>(requireContext(), android.R.layout.simple_dropdown_item_1line, stages));
    }

    private void handleArguments() {
        if (getArguments() != null) {
            mode = getArguments().getString("mode", MODE_CREATE);
            existingOpportunity = (Opportunity) getArguments().getSerializable("opportunity");

            if (MODE_UPDATE.equals(mode) && existingOpportunity != null) {
                populateForm(existingOpportunity);
                tvHeaderTitle.setText("Cập nhật cơ hội");
                btnSave.setText("Cập nhật");
            } else {
                tvHeaderTitle.setText("Thêm cơ hội mới");
                btnSave.setText("Lưu");
            }
        }
    }

    private void setupActions() {
        btnBack.setOnClickListener(v -> requireActivity().onBackPressed());
        btnCancel.setOnClickListener(v -> requireActivity().finish());
        btnSave.setOnClickListener(v -> {
            try {
                saveOpportunity();
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
        });
    }

    private void populateForm(Opportunity opportunity) {
        etOpportunityName.setText(opportunity.getTitle());
        etCompany.setText(opportunity.getCompany());
        etContact.setText(opportunity.getContact());
        // Format giá trị tiền tệ
        if (opportunity.getPrice() > 0) {
            NumberFormat format = NumberFormat.getInstance(Locale.getDefault());
            etValue.setText(format.format(opportunity.getPrice()));
        }        spSalesStage.setText(opportunity.getStatus());
        etExpectedDate.setText(opportunity.getDate());
        etExpectedDate2.setText(opportunity.getExpectedDate2());
        etDescription.setText(opportunity.getDescription());
        etManagement.setText(opportunity.getManagement());
    }

    private void saveOpportunity() throws ParseException {
        OpportunityViewModel viewModel = new ViewModelProvider(this).get(OpportunityViewModel.class);
        Opportunity opportunity = createOpportunityFromForm();

        if (opportunity.getTitle().isEmpty()) {
            etOpportunityName.setError("Tên cơ hội không được để trống");
            return;
        }

        if (MODE_UPDATE.equals(mode) && existingOpportunity != null) {
            viewModel.update(opportunity);
            Toast.makeText(getContext(), "Cập nhật thành công", Toast.LENGTH_SHORT).show();
        } else {
            viewModel.add(opportunity);
            Toast.makeText(getContext(), "Thêm cơ hội thành công", Toast.LENGTH_SHORT).show();
        }

        requireActivity().finish();
    }


    private Opportunity createOpportunityFromForm() throws ParseException {
        // Parse giá trị tiền tệ
        double priceValue = 0;
        String priceStr = etValue.getText().toString().trim();
        if (!TextUtils.isEmpty(priceStr)) {
            priceStr = priceStr.replaceAll("[^\\d.]", "");
            if (!TextUtils.isEmpty(priceStr)) {
                priceValue = Double.parseDouble(priceStr);
            }
        }

        // Lấy id nếu là update
        int id = (MODE_UPDATE.equals(mode) && existingOpportunity != null) ?
                existingOpportunity.getId() : 0;

        // Lấy ID từ tên (cần hàm helper)
        int companyId = getCompanyIdFromName(etCompany.getText().toString().trim());
        int contactId = getContactIdFromName(etContact.getText().toString().trim());
        int managementId = getManagementIdFromName(etManagement.getText().toString().trim());

        return new Opportunity(
                id,
                etOpportunityName.getText().toString().trim(),
                companyId,  // Thay vì String, dùng int ID
                contactId,   // Thay vì String, dùng int ID
                priceValue,
                spSalesStage.getText().toString().trim(),
                etExpectedDate.getText().toString().trim(),
                etExpectedDate2.getText().toString().trim(),
                etDescription.getText().toString().trim(),
                managementId, // Thay vì String, dùng int ID
                0,  // callCount
                0   // messageCount
        );
    }

    // Helper methods để lấy ID từ tên
    private int getCompanyIdFromName(String companyName) {
        // TODO: Query database để lấy ID từ tên công ty
        // Tạm thời return 1 hoặc 0
        if (TextUtils.isEmpty(companyName)) return 0;
        return 1; // Hoặc logic mapping của bạn
    }

    private int getContactIdFromName(String contactName) {
        // TODO: Query database để lấy ID từ tên liên hệ
        if (TextUtils.isEmpty(contactName)) return 0;
        return 1;
    }

    private int getManagementIdFromName(String managementName) {
        // TODO: Query database để lấy ID nhân viên từ tên
        if (TextUtils.isEmpty(managementName)) return 0;
        return 1;
    }


}
