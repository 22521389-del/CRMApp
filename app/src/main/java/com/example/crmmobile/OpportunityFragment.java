package com.example.crmmobile;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ScrollView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

public class OpportunityFragment extends Fragment {

    private View layoutOpportunityForm;
    private View btnAddOpportunity;        // Nút bong bóng "+"
    private View btnAddOpportunityBody;    // Nút add trong body (ImageView)
    private View btnOpportunityBack;       // Nút quay lại trong form
    private RecyclerView rvOpportunityBody;
    private OpportunityAdapter opportunityAdapter;

    BottomNavigationView bottomNav;
    private ScrollView bodyscroll;

    @SuppressLint("WrongViewCast")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_opportunity, container, false);

        // Ánh xạ view
        layoutOpportunityForm = view.findViewById(R.id.layout_opportunity_form);
        btnAddOpportunity = view.findViewById(R.id.btn_add_opportunity);
        btnAddOpportunityBody = view.findViewById(R.id.btn_opportunity_body_add);
        rvOpportunityBody = view.findViewById(R.id.rv_opportunity_body);
        AutoCompleteTextView etCompany = layoutOpportunityForm.findViewById(R.id.et_company);
        AutoCompleteTextView etContact = layoutOpportunityForm.findViewById(R.id.et_contact);
        AutoCompleteTextView spSalesStage = layoutOpportunityForm.findViewById(R.id.sp_sales_stage);
        AutoCompleteTextView spSuccessRate = layoutOpportunityForm.findViewById(R.id.sp_success_rate);
        bottomNav = requireActivity().findViewById(R.id.nav_footer);
        bodyscroll = view.findViewById(R.id.scroll_body);

        // Setup RecyclerView
        rvOpportunityBody.setLayoutManager(new LinearLayoutManager(getContext()));
        opportunityAdapter = new OpportunityAdapter(getSampleOpportunities());
        rvOpportunityBody.setAdapter(opportunityAdapter);

        // Nút Back trong form
        btnOpportunityBack = view.findViewById(R.id.btn_opportunity_back);
        ImageButton btnOpportunityFormBack = layoutOpportunityForm.findViewById(R.id.btn_opportunity_back);

        // ---- Xử lý mở form ----
//        View.OnClickListener openForm = v -> layoutOpportunityForm.setVisibility(View.VISIBLE);

        // Áp dụng listener cho cả 2 nút mở
//        btnAddOpportunity.setOnClickListener(openForm);
//        if (btnAddOpportunityBody != null) {
//            btnAddOpportunityBody.setOnClickListener(openForm);
//        }
        btnAddOpportunity.setOnClickListener(v->{
            layoutOpportunityForm.setVisibility(View.VISIBLE);
            bottomNav.setVisibility(View.GONE);
            bodyscroll.setVisibility(View.GONE);
            btnAddOpportunity.setVisibility(View.GONE);
        });

        // Áp dụng listener cho nút back
        btnOpportunityBack.setOnClickListener(v -> {
            requireActivity().getSupportFragmentManager().popBackStack();
        });
        // ---- Xử lý đóng form ----
        btnOpportunityFormBack.setOnClickListener(v -> {
            layoutOpportunityForm.setVisibility(View.GONE);
            bottomNav.setVisibility(View.VISIBLE);
            bodyscroll.setVisibility(View.VISIBLE);
            btnAddOpportunity.setVisibility(View.VISIBLE);
        });

        // dropdown for item_opportunity_info
        String[] companies = {"Google", "Microsoft", "Apple", "Meta"};
        String[] contacts = {"John Doe", "Jane Smith", "Alice Johnson"};
        String[] stages = {"Prospecting", "Qualification", "Proposal", "Negotiation", "Closed Won", "Closed Lost"};
        String[] rates = {"10%", "25%", "50%", "75%", "90%", "100%"};

        etCompany.setAdapter(new ArrayAdapter<>(requireContext(), android.R.layout.simple_dropdown_item_1line, companies));
        etContact.setAdapter(new ArrayAdapter<>(requireContext(), android.R.layout.simple_dropdown_item_1line, contacts));
        spSalesStage.setAdapter(new ArrayAdapter<>(requireContext(), android.R.layout.simple_dropdown_item_1line, stages));
        spSuccessRate.setAdapter(new ArrayAdapter<>(requireContext(), android.R.layout.simple_dropdown_item_1line, rates));







        return view;
    }

    // Dữ liệu mẫu
    private List<Opportunity> getSampleOpportunities() {
        List<Opportunity> list = new ArrayList<>();
        list.add(new Opportunity(
                "Phần mềm CloudWork",
                "14.875.000 đ",
                "17/07/2024",
                "Thương lượng đàm phán",
                2, 5, "Trao đổi (1)"
        ));
        list.add(new Opportunity(
                "Ứng dụng CRM Mobile",
                "22.300.000 đ",
                "12/08/2024",
                "Đã ký hợp đồng",
                1, 3, "Trao đổi (3)"
        ));
        list.add(new Opportunity(
                "Website Quản lý Dự án",
                "9.700.000 đ",
                "05/09/2024",
                "Đang chờ phản hồi",
                0, 2, "Trao đổi (3)"
        ));
        return list;
    }

}
