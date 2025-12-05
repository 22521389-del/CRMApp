package com.example.crmmobile.OpportunityDirectory;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ScrollView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.crmmobile.Adapter.AdapterOpportunity;
import com.example.crmmobile.BottomSheet.OpportunityBottomSheetHelper;
import com.example.crmmobile.OpportunityDirectory.Opportunity;
import com.example.crmmobile.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

public class OpportunityFragment extends Fragment {

    private View layoutOpportunityForm;
    private View btnAddOpportunity;
    private RecyclerView rvOpportunityBody;
    private OpportunityViewModel viewModel;
    private AdapterOpportunity opportunityAdapter;
    private BottomNavigationView bottomNav;
    private ScrollView bodyscroll;

    @SuppressLint("WrongViewCast")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_opportunity, container, false);

        initViews(view);
        setupRecyclerView();
        setupClickListeners();

        return view;
    }

    private void initViews(View view) {
        layoutOpportunityForm = view.findViewById(R.id.layout_opportunity_form);
        btnAddOpportunity = view.findViewById(R.id.btn_add_opportunity);
        rvOpportunityBody = view.findViewById(R.id.rv_opportunity_body);
        bottomNav = requireActivity().findViewById(R.id.nav_footer);
        bodyscroll = view.findViewById(R.id.scroll_body);

        // Nút back ở header fragment (nếu có)
        ImageButton btnOpportunityBack = view.findViewById(R.id.btn_opportunity_back);
        if (btnOpportunityBack != null) {
            btnOpportunityBack.setOnClickListener(v -> requireActivity().getSupportFragmentManager().popBackStack());
        }
    }

//    private void setupRecyclerView() {
//        rvOpportunityBody.setLayoutManager(new LinearLayoutManager(getContext()));
//        List<Opportunity> list = OpportunityRepository.getInstance(requireContext()).getAll();
//        Log.d("OpportunityFragment", "Opportunities size = " + list.size());
//        for (Opportunity o : list) {
//            Log.d("OpportunityFragment", "Title: " + o.getTitle() + ", Price: " + o.getPrice());
//        }
//        opportunityAdapter = new AdapterOpportunity(
//                list,
//                (item, id, anchor) -> {
//                    // Sử dụng id nếu muốn update/delete trực tiếp
//                    OpportunityBottomSheetHelper.showBottomSheet(requireContext(), item, id, anchor);
//                },
//                (item, id) -> openOpportunityDetail(item)
//        );
//
//        rvOpportunityBody.setAdapter(opportunityAdapter);
//    }

    private void setupRecyclerView() {
        rvOpportunityBody.setLayoutManager(new LinearLayoutManager(getContext()));

        // INIT VIEWMODEL
        viewModel = new ViewModelProvider(requireActivity())
                .get(OpportunityViewModel.class);

        // INIT ADAPTER (không truyền list ở đây)
        opportunityAdapter = new AdapterOpportunity(
                new ArrayList<>(), // list rỗng, chờ LiveData cập nhật
                (item, id, anchor) -> {
                    // Sử dụng id nếu muốn update/delete trực tiếp
                    OpportunityBottomSheetHelper.showBottomSheet(requireContext(), item, id, anchor);
                },
                (item, id) -> openOpportunityDetail(item)
        );

        rvOpportunityBody.setAdapter(opportunityAdapter);

        // OBSERVE DATA TỪ VIEWMODEL
        viewModel.getOpportunities().observe(getViewLifecycleOwner(), list -> {
            Log.d("OpportunityFragment", "LiveData size = " + list.size());
            for (Opportunity o : list) {
                Log.d("OpportunityFragment", "Title: " + o.getTitle() + ", Price: " + o.getPrice());
            }
            opportunityAdapter.setData(list);
        });
    }

    private void setupClickListeners() {
        View.OnClickListener openFormListener = v -> showOpportunityForm();

        if (btnAddOpportunity != null) btnAddOpportunity.setOnClickListener(openFormListener);

        // Nút back trong form (nếu layout_opportunity_form nằm cùng fragment)
        if (layoutOpportunityForm != null) {
            ImageButton btnOpportunityFormBack = layoutOpportunityForm.findViewById(R.id.btn_opportunity_back);
            if (btnOpportunityFormBack != null) {
                btnOpportunityFormBack.setOnClickListener(v -> hideOpportunityForm());
            }
        }
    }

    private void showOpportunityForm() {
        if (layoutOpportunityForm != null) layoutOpportunityForm.setVisibility(View.VISIBLE);
        if (bottomNav != null) bottomNav.setVisibility(View.GONE);
        if (bodyscroll != null) bodyscroll.setVisibility(View.GONE);
        if (btnAddOpportunity != null) btnAddOpportunity.setVisibility(View.GONE);
    }

    private void hideOpportunityForm() {
        if (layoutOpportunityForm != null) layoutOpportunityForm.setVisibility(View.GONE);
        if (bottomNav != null) bottomNav.setVisibility(View.VISIBLE);
        if (bodyscroll != null) bodyscroll.setVisibility(View.VISIBLE);
        if (btnAddOpportunity != null) btnAddOpportunity.setVisibility(View.VISIBLE);
    }

    private void openOpportunityDetail(Opportunity item) {
        Intent intent = new Intent(getContext(), OpportunityDetailActivity.class);
        intent.putExtra("opportunity", item);
        startActivity(intent);
    }

    private void openOpportunityCreateForm() {
        Intent intent = new Intent(getContext(), OpportunityFormActivity.class);
        intent.putExtra("mode", "create");
        startActivity(intent);
    }

    private void openOpportunityUpdateForm(Opportunity item) {
        Intent intent = new Intent(getContext(), OpportunityFormActivity.class);
        intent.putExtra("mode", "update");
        intent.putExtra("opportunityId", item.getId());
        startActivity(intent);
    }



    // Reload data mỗi khi fragment visible lại (cập nhật sau add/update/delete)
    @Override
    public void onResume() {
        super.onResume();
        // Lấy dữ liệu mới từ Repository theo id
        List<Opportunity> list = OpportunityRepository.getInstance(requireContext()).getAll();
        if (opportunityAdapter != null) {
            opportunityAdapter.setData(list);
        }
    }

}
