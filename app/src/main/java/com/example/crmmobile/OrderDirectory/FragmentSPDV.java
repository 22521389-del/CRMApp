package com.example.crmmobile.OrderDirectory;

import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.crmmobile.R;

import java.text.NumberFormat;

public class FragmentSPDV extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_spdv, container, false);

        // ---Vùng tính thuế ---
        View rowRegion = view.findViewById(R.id.rowRegion);
        ((TextView) rowRegion.findViewById(R.id.tvLabel)).setText("Vùng tính thuế");
        ((TextView) rowRegion.findViewById(R.id.tvValue)).setText("Trong nước");

        // --- Loại tiền ---
        View rowCurrency = view.findViewById(R.id.rowCurrency);
        ((TextView) rowCurrency.findViewById(R.id.tvLabel)).setText("Loại tiền");
        ((TextView) rowCurrency.findViewById(R.id.tvValue)).setText("Vietnam, Dong (đ)");

        // --- Cách tính thuế ---
        View rowTaxType = view.findViewById(R.id.rowTaxType);
        ((TextView) rowTaxType.findViewById(R.id.tvLabel)).setText("Cách tính thuế");
        ((TextView) rowTaxType.findViewById(R.id.tvValue)).setText("Theo từng mặt hàng");

        // --- Cam AI HA800 ---
        View camAI = view.findViewById(R.id.itemCamAI);
        ((TextView) camAI.findViewById(R.id.tvTenSanPham)).setText("Cam AI HA800");
        ((TextView) camAI.findViewById(R.id.tvSoLuong)).setText("1 x 4.200.000 đ");
        ((TextView) camAI.findViewById(R.id.tvThanhTien)).setText("4.200.000 đ");

        // --- CloudLead ---
        View cloudLead = view.findViewById(R.id.itemCloudLead);
        ((TextView) cloudLead.findViewById(R.id.tvTenSanPham)).setText("CloudLead");
        ((TextView) cloudLead.findViewById(R.id.tvSoLuong)).setText("1 x 1.820.000 đ");
        ((TextView) cloudLead.findViewById(R.id.tvThanhTien)).setText("1.820.000 đ");

        // ---Phần tổng ---
        long tamTinh = 4_200_000L + 1_820_000L;
        long thue = Math.round(tamTinh * 0.10); // VAT 10%
        long tongCong = tamTinh + thue;

        ((TextView) view.findViewById(R.id.tvTamTinh)).setText(formatCurrency(tamTinh));
        ((TextView) view.findViewById(R.id.tvTruocThue)).setText(formatCurrency(tamTinh));
        ((TextView) view.findViewById(R.id.tvThue)).setText(formatCurrency(thue));
        ((TextView) view.findViewById(R.id.tvTongThue)).setText(formatCurrency(thue));
        ((TextView) view.findViewById(R.id.tvTongCong)).setText(formatCurrency(tongCong));
        ((TextView) view.findViewById(R.id.tvGiamGiaChung)).setText("0 đ");
        ((TextView) view.findViewById(R.id.tvTongGiam)).setText("0 đ");

        // --- Tooltip khi nhấn icon ---
        ImageView iconGiamGia = view.findViewById(R.id.iconTooltipGiamGia);
        ImageView iconThue = view.findViewById(R.id.iconTooltipThue);

        iconGiamGia.setOnClickListener(v ->
                showTooltip(v, "Số tiền giảm giá cuối cùng = 0 đ"));

        iconThue.setOnClickListener(v ->
                showTooltip(v, "VAT: 10% của 6.020.000 đ = 602.000 đ\nTổng số tiền thuế = 602.000 đ"));

        return view;
    }

    // Định dạng tiền: 602000 -> "602.000 đ"
    private String formatCurrency(long value) {
        NumberFormat nf = NumberFormat.getInstance();
        return nf.format(value) + " đ";
    }

    private void showTooltip(View anchor, String message) {
        // Inflate layout tooltip
        View popupView = LayoutInflater.from(anchor.getContext())
                .inflate(R.layout.tooltip_layout, null);

        TextView tvTooltip = popupView.findViewById(R.id.tvTooltip);
        tvTooltip.setText(message);

        // Tạo popup window
        final PopupWindow popupWindow = new PopupWindow(
                popupView,
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT,
                true
        );

        // Cho phép tắt khi nhấn ngoài
        popupWindow.setOutsideTouchable(true);
        popupWindow.setElevation(10f);

        // Tính vị trí hiển thị: bên phải icon
        int[] location = new int[2];
        anchor.getLocationOnScreen(location);

        // Hiển thị popup bên phải icon
        popupWindow.showAtLocation(anchor,
                Gravity.NO_GRAVITY,
                location[0] + anchor.getWidth() + 15, // dịch sang phải
                location[1] - 20); // căn chỉnh lên một chút

        // Tự ẩn sau 3 giây (tùy chỉnh)
        popupView.postDelayed(popupWindow::dismiss, 3000);
    }

}
