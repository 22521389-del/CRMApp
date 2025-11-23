package com.example.crmmobile;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class DanhSachCaNhan extends Fragment {
    private ImageView icBack;
    private RecyclerView rvCaNhan;
    private CaNhanAdapter adapter;
    private ArrayList<CaNhan> caNhanList;
    private FloatingActionButton btnAdd;

    public DanhSachCaNhan() {
        // Required empty public constructor
    }

    public static DanhSachCaNhan newInstance(String param1, String param2) {
        DanhSachCaNhan fragment = new DanhSachCaNhan();
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
        View view = inflater.inflate(R.layout.fragment_danh_sach_ca_nhan, container, false);

        rvCaNhan = view.findViewById(R.id.rvCaNhan);
        btnAdd = view.findViewById(R.id.btn_add_contact);
        icBack = view.findViewById(R.id.ic_back);

        // ====== Tạo danh sách ======
        caNhanList = new ArrayList<>();


        // 3 item cố định
        caNhanList.add(new CaNhan("Nguyễn Văn A", "Công ty X", "01/01/2025", 2, 2));
        caNhanList.add(new CaNhan("Trần Thị B", "Công ty Y", "02/01/2025", 2, 2));
        caNhanList.add(new CaNhan("Lê Văn C", "Công ty Z", "03/01/2025", 2, 2));

        adapter = new CaNhanAdapter(caNhanList);
        rvCaNhan.setLayoutManager(new LinearLayoutManager(getContext()));
        rvCaNhan.setAdapter(adapter);

        // ====== Nút thêm ======
        btnAdd.setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), ThongTinLienHeActivity.class);
            startActivityForResult(intent, 100);
        });

        icBack.setOnClickListener(v -> {
            requireActivity().getSupportFragmentManager().popBackStack();
        });

        adapter.setOnItemClickListener(new CaNhanAdapter.OnItemClickListener() {
            @Override
            public void onMoreClick(CaNhan cn) {
                BottomActionFragment bottomSheet = new BottomActionFragment();
                bottomSheet.show(getParentFragmentManager(), "BottomAction");
            }

            @Override
            public void onItemClick(CaNhan cn) {
                Intent intent = new Intent(getContext(), TabActivity.class);
                startActivity(intent);
            }
        });

        return view;
    }

//    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//
//        if (requestCode == 100 && resultCode == RESULT_OK && data != null) {
//            CaNhan cn = new CaNhan();
//
//            // Lấy dữ liệu từ form
//            cn.setHoTen(data.getStringExtra("hoTen"));
//            cn.setCongTy(data.getStringExtra("congTy"));
//
//            // Ngày hiển thị mặc định là ngày hôm nay
//            Calendar calendar = Calendar.getInstance();
//            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
//            cn.setNgaySinh(sdf.format(calendar.getTime()));
//
//            // Số cuộc gọi & meeting mặc định = 2
//            cn.setSoCuocGoi(2);
//            cn.setSoMeeting(2);
//
//            // Thêm item mới vào cuối danh sách
//            adapter.addItem(cn);
//            rvCaNhan.scrollToPosition(caNhanList.size() - 1); // cuộn xuống item mới
//        }
//    }
}