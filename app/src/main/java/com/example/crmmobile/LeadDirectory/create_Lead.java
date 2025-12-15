package com.example.crmmobile.LeadDirectory;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager2.widget.ViewPager2;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.crmmobile.Adapter.AdapterCreateLead;
import com.example.crmmobile.AppConstant;
import com.example.crmmobile.DataBase.LeadReposity;
import com.example.crmmobile.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class create_Lead extends Fragment {

    ImageButton back_btn;
    private TabLayout tabLayout;
    private ViewPager2 viewPager2;

    private MaterialButton btnSave, btnAbort;
    private ViewModelLead viewModelLead;
    
    public create_Lead() {
        // Required empty public constructor
    }

    public static create_Lead newInstance(String param1, String param2) {
        create_Lead fragment = new create_Lead();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_create_lead, container, false);
        back_btn = view.findViewById(R.id.btn_lead_back);
        tabLayout = view.findViewById(R.id.tablayout);
        viewPager2 = view.findViewById(R.id.vp_tab);
        btnSave = view.findViewById(R.id.save_button);
        btnAbort = view.findViewById(R.id.abort_button);
        AdapterCreateLead adapterCreate = new AdapterCreateLead(this);
        viewPager2.setAdapter(adapterCreate);

        viewModelLead = new ViewModelProvider(requireActivity()).get(ViewModelLead.class);

        new TabLayoutMediator(tabLayout, viewPager2,((tab, i) -> {
            if(i == 0) tab.setText("Thông tin lead");
            else tab.setText("Thông tin khác");
        })).attach();

        btnSave.setOnClickListener(v -> saveCreateLead());

        back_btn.setOnClickListener(v -> {
            ViewPager2 viewPager = requireActivity().findViewById(R.id.viewPager);
            BottomNavigationView navFooter = requireActivity().findViewById(R.id.nav_footer);
            FrameLayout contain = requireActivity().findViewById(R.id.main_container);

            viewPager.setVisibility(View.VISIBLE);
            navFooter.setVisibility(View.VISIBLE);
            contain.setVisibility(View.GONE);

            requireActivity().getSupportFragmentManager().popBackStack();
        });
        return view;
    }

    private void saveCreateLead() {
        String hovatendem = viewModelLead.hovatendem.getValue();
        String first_name = viewModelLead.first_name.getValue();
        String company = viewModelLead.company.getValue();
        String title = viewModelLead.title.getValue();
        String phone_number = viewModelLead.phonenumber.getValue();
        String job = viewModelLead.Job.getValue();
        Integer SentoID = viewModelLead.SendtoID.getValue();
        String Email = viewModelLead.Email.getValue();
        String Sex = viewModelLead.Sex.getValue();
        String contact_day = viewModelLead.contact_day.getValue();
        String birthday = viewModelLead.Birthday.getValue();
        String Address = viewModelLead.Address.getValue();
        String Province = viewModelLead.Province.getValue();
        String Nation = viewModelLead.Nation.getValue();
        String District = viewModelLead.District.getValue();
        String position_company = viewModelLead.position_company.getValue();
        String state = viewModelLead.state.getValue();


        LeadReposity db = new LeadReposity(requireContext());
        if(TextUtils.isEmpty(first_name) || TextUtils.isEmpty(phone_number) || SentoID == null){
            Toast.makeText(getContext(), "Nhập tên và số điện thoại.", Toast.LENGTH_SHORT).show();
            return;
        }
        Lead lead = new Lead();
        lead.setHovaTendem(hovatendem);
        lead.setCongty(company);
        lead.setNganhnghe(job);
        lead.setTen(first_name);
        lead.setTitle(title);
        lead.setEmail(Email);
        lead.setDienThoai(phone_number);
        lead.setGioitinh(Sex);
        lead.setGiaochoID(SentoID);
        lead.setNgayLienHe(contact_day);
        lead.setNgaysinh(birthday);
        lead.setDiachi(Address);
        lead.setTinhTrang(state);
        lead.setTinh(Province);
        lead.setQuocGia(Nation);
        lead.setQuanHuyen(District);
        lead.setChucvu(position_company);

        db.addLead(lead);

        //Thông báo cho fragment refresh dữ liệu
        Bundle result = new Bundle();
        result.putBoolean(AppConstant.REFRESH, true);
        getParentFragmentManager().setFragmentResult(AppConstant.KEY_CREATE_LEAD, result);

        Toast.makeText(getContext(), "Tạo Lead thành công", Toast.LENGTH_SHORT).show();

        ViewPager2 viewPager = requireActivity().findViewById(R.id.viewPager);
        BottomNavigationView navFooter = requireActivity().findViewById(R.id.nav_footer);
        FrameLayout contain = requireActivity().findViewById(R.id.main_container);

        viewPager.setVisibility(View.VISIBLE);
        navFooter.setVisibility(View.VISIBLE);
        contain.setVisibility(View.GONE);

        requireActivity().getSupportFragmentManager().popBackStack();
    }

    private void getfromViewModel() {
    }
}