package com.example.crmmobile.LeadDirectory;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.example.crmmobile.Adapter.AdapterLead;
import com.example.crmmobile.AppConstant;
import com.example.crmmobile.BottomSheet.BottomSheetActionLead;
import com.example.crmmobile.DataBase.LeadReposity;
import com.example.crmmobile.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class leadFragment extends Fragment {
    RecyclerView recyclerLead;
    AdapterLead adapter;
    List<Lead> leadDB;
    ArrayList<Lead> leadList;
    FloatingActionButton lead_create_button;
    BottomNavigationView navFooter;
    ViewPager2 viewPager;
    FrameLayout contain;
    private LeadReposity db;

    public ActivityResultLauncher<Intent> editLeadLauncher =
            registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
                if (result.getResultCode() == EditLeadActivity.RESULT_OK){
                    reloadList();
                }
            });

    public ActivityResultLauncher<Intent> convertLeadLauncher =
            registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result ->{
                if (result.getResultCode() == ConvertLeadActivity.RESULT_OK){
                    reloadList();
                }
            });

    private void reloadList() {
        leadList.clear();
        leadList.addAll(db.getAllLead());
        adapter.notifyDataSetChanged();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_lead, container, false);

        recyclerLead = view.findViewById(R.id.LeadRecycler);
        lead_create_button = view.findViewById(R.id.btn_add_lead);

        recyclerLead.setLayoutManager(new LinearLayoutManager(getContext()));

        db = new LeadReposity(getContext());

        loadLead();

        navFooter = requireActivity().findViewById(R.id.nav_footer);
        contain = requireActivity().findViewById(R.id.main_container);
        viewPager = requireActivity().findViewById(R.id.viewPager);

        lead_create_button.setOnClickListener(v -> {
            Fragment createFragment = new create_Lead();

            viewPager.setVisibility(View.GONE);
            navFooter.setVisibility(View.GONE);
            contain.setVisibility(View.VISIBLE);

            requireActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .hide(this)
                    .replace(R.id.main_container, createFragment)
                    .addToBackStack(null)
                    .commit();
        });
        adapter = new AdapterLead(requireContext(), leadList, new AdapterLead.onItemClickListener() {
            @Override
            public void onDotsClick(Lead item, int position) {
                BottomSheetActionLead.ShowBottomSheetLead(requireContext(), item, new BottomSheetActionLead.OnActionListenerLead() {
                    @Override
                    public void onEdit(Lead lead) {
                        Intent intent = new Intent(getContext(), EditLeadActivity.class);
                        intent.putExtra(AppConstant.LEAD_OBJECT, lead);
                        editLeadLauncher.launch(intent);
                    }

                    @Override
                    public void onDelete(Lead lead) {
                        db.DeleteLead(lead.getID());//delete from database
                        leadList.remove(position); // delete from list

                        //update recyclerview
                        adapter.notifyItemRemoved(position);
                        adapter.notifyItemRangeChanged(position, leadList.size());
                    }

                    @Override
                    public void onConvertLead(Lead lead) {
                        Intent intent = new Intent(getContext(), ConvertLeadActivity.class);
                        intent.putExtra(AppConstant.LEAD_OBJECT, lead);
                        convertLeadLauncher.launch(intent);
                    }

                });
            }

            @Override
            public void onMenuClick(Lead lead) {
                Intent intent = new Intent(getContext(), DetailLeadActivity.class);
                intent.putExtra(AppConstant.LEAD_OBJECT, lead);
                startActivity(intent);
            }
        });
        recyclerLead.setAdapter(adapter);
        return view;
    }

    private void loadLead() {
        leadDB = db.getAllLead();

        if(leadDB.isEmpty()){
            leadList = new ArrayList<>();
            leadList.add(new Lead("Ông", "Nguyễn Văn", " A", "5/12/2025", "Công ty X", "phong@gmail.com", "Mới"));

            for ( Lead lead: leadList){
                long id = db.addLead(lead);
                lead.setID((int) id);
            }
        }
        else{
            leadList = new ArrayList<>(leadDB);
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        getParentFragmentManager().setFragmentResultListener(AppConstant.KEY_CREATE_LEAD,
                this, (requestKey, bundle) -> {
            boolean refresh = bundle.getBoolean(AppConstant.REFRESH, false);
            if(refresh){
                // Load lại database
                leadList.clear();
                leadList.addAll(db.getAllLead());
                adapter.notifyDataSetChanged();
            }
        });
    }
}
