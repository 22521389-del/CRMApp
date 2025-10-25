package com.example.crmmobile;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.transition.AutoTransition;
import androidx.transition.TransitionManager;
import android.app.DatePickerDialog;
import java.util.Calendar;
import com.google.android.material.textfield.TextInputEditText;

public class HienThiNguoiLienHeActivity extends AppCompatActivity {

    private ImageView iconArrow;
    private LinearLayout layoutThongTinChiTiet;
    private LinearLayout headerThongTin;
    private boolean isExpanded = true; // ban đầu đang mở

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.thongtinnguoilienhe);
        // Gắn lịch cho ô Ngày sinh
        TextInputEditText edtNgaySinh = findViewById(R.id.edtngaysinh);
        edtNgaySinh.setOnClickListener(v -> showDatePickerDialog());


        // Gắn ID
        iconArrow = findViewById(R.id.icon_arrow_down);
        layoutThongTinChiTiet = findViewById(R.id.layoutThongTinChiTiet);
        headerThongTin = findViewById(R.id.headerThongTin);

        // Gắn sự kiện ẩn/hiện thông tin
        headerThongTin.setOnClickListener(v -> toggleThongTin());

        // Gắn sự kiện bấm "Thông tin khác"
        TextView thongTinKhac = findViewById(R.id.thongtinkhac);
        thongTinKhac.setOnClickListener(v -> {
            Intent intent = new Intent(HienThiNguoiLienHeActivity.this, ThongTinKhacActivity.class);
            startActivity(intent);
        });

    }

    private void toggleThongTin() {
        TransitionManager.beginDelayedTransition(layoutThongTinChiTiet, new AutoTransition());
        if (isExpanded) {
            layoutThongTinChiTiet.setVisibility(View.GONE);
            iconArrow.setImageResource(R.drawable.ic_arrow_up);
        } else {
            layoutThongTinChiTiet.setVisibility(View.VISIBLE);
            iconArrow.setImageResource(R.drawable.ic_arrow_down);
        }
        isExpanded = !isExpanded;
    }

    private void showDatePickerDialog() {
        final Calendar calendar = Calendar.getInstance();

        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(
                HienThiNguoiLienHeActivity.this,
                (view, selectedYear, selectedMonth, selectedDay) -> {
                    String selectedDate = String.format("%02d/%02d/%04d",
                            selectedDay, selectedMonth + 1, selectedYear);
                    TextInputEditText edtNgaySinh = findViewById(R.id.edtngaysinh);
                    edtNgaySinh.setText(selectedDate);
                },
                year, month, day
        );

        datePickerDialog.show();
    }

}
