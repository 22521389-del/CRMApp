package com.example.crmmobile;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.util.ArrayList;
import java.util.Calendar;
import java.text.SimpleDateFormat;
import java.util.Locale;

public class DanhSachCaNhanActivity extends AppCompatActivity {

    private RecyclerView rvCaNhan;
    private CaNhanAdapter adapter;
    private ArrayList<CaNhan> caNhanList;
    private FloatingActionButton btnAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_danhsachcanhan);

        rvCaNhan = findViewById(R.id.rvCaNhan);
        btnAdd = findViewById(R.id.btn_add_contact);

        caNhanList = new ArrayList<>();
        adapter = new CaNhanAdapter(caNhanList);

        rvCaNhan.setLayoutManager(new LinearLayoutManager(this));
        rvCaNhan.setAdapter(adapter);

        btnAdd.setOnClickListener(v -> {
            Intent intent = new Intent(DanhSachCaNhanActivity.this, ThongTinLienHeActivity.class);
            startActivityForResult(intent, 100);
        });

        adapter.setOnItemClickListener(new CaNhanAdapter.OnItemClickListener() {
            @Override
            public void onMoreClick(CaNhan cn) {
                BottomActionFragment bottomSheet = new BottomActionFragment();
                bottomSheet.show(getSupportFragmentManager(), "BottomAction");
            }

            @Override
            public void onItemClick(CaNhan cn) {
                Intent intent = new Intent(DanhSachCaNhanActivity.this, TabActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 100 && resultCode == RESULT_OK && data != null) {
            CaNhan cn = new CaNhan();

            // Lấy dữ liệu từ form
            cn.setHoTen(data.getStringExtra("hoTen"));
            cn.setCongTy(data.getStringExtra("congTy"));

            // Ngày hiển thị mặc định là ngày hôm nay
            Calendar calendar = Calendar.getInstance();
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
            cn.setNgaySinh(sdf.format(calendar.getTime()));

            // Số cuộc gọi & meeting mặc định = 2
            cn.setSoCuocGoi(2);
            cn.setSoMeeting(2);

            adapter.addItem(cn);
            rvCaNhan.scrollToPosition(caNhanList.size() - 1); // cuộn xuống item mới
        }
    }
}