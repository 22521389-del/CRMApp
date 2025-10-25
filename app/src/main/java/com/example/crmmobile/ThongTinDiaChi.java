package com.example.crmmobile;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import androidx.appcompat.app.AppCompatActivity;

public class ThongTinDiaChi extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.thongtinkhac); // liên kết với file XML của bạn

        // Gắn view
        ImageView iconArrowDiaChi = findViewById(R.id.icon_arrow_diachi);
        LinearLayout contentThongTin = findViewById(R.id.contentThongTin);

        // Xử lý click
        iconArrowDiaChi.setOnClickListener(v -> {
            if (contentThongTin.getVisibility() == View.VISIBLE) {
                contentThongTin.setVisibility(View.GONE);
                iconArrowDiaChi.setImageResource(R.drawable.ic_arrow_up); // đổi hướng mũi tên
            } else {
                contentThongTin.setVisibility(View.VISIBLE);
                iconArrowDiaChi.setImageResource(R.drawable.ic_arrow_down);
            }
        });
    }
}
