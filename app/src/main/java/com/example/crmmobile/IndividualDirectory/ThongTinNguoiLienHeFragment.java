package com.example.crmmobile.IndividualDirectory;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.crmmobile.R;

public class ThongTinNguoiLienHeFragment extends Fragment {

    private AutoCompleteTextView actDanhXung, actGioiTinh, actCongTy;
    private EditText edtHoVaTenDem, edtTen, edtDiDong, edtEmail, edtNgaySinh;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_thong_tin_nguoi_lien_he, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // =============================
        // 1. ÁNH XẠ VIEW
        // =============================
        TextView tvHeader = view.findViewById(R.id.thongtinnguoilienhe);
        LinearLayout layoutDetail = view.findViewById(R.id.layoutThongTinNguoiLienHeChiTiet);

        actDanhXung = view.findViewById(R.id.actdanhxung);
        edtHoVaTenDem = view.findViewById(R.id.edthovatendem);
        edtTen = view.findViewById(R.id.edtten);

        actCongTy = view.findViewById(R.id.actCongTy);
        actGioiTinh = view.findViewById(R.id.actgioitinh);

        edtDiDong = view.findViewById(R.id.edtdidong);
        edtEmail = view.findViewById(R.id.edtemail);
        edtNgaySinh = view.findViewById(R.id.edtngaysinh);

        // =============================
        // 2. DROPDOWN CÔNG TY
        // =============================
        ArrayAdapter<String> adapterCongTy = new ArrayAdapter<>(
                requireContext(),
                android.R.layout.simple_list_item_1,
                new String[]{"Cty TNHH ABC", "Cty TNHH Hỷ Lâm Môn"}
        );
        actCongTy.setAdapter(adapterCongTy);


        // =============================
        // 2. DROPDOWN DANH XƯNG
        // =============================
        ArrayAdapter<String> adapterDanhXung = new ArrayAdapter<>(
                requireContext(),
                android.R.layout.simple_list_item_1,
                new String[]{"Anh", "Chị"}
        );
        actDanhXung.setAdapter(adapterDanhXung);

        // =============================
        // 3. DROPDOWN GIỚI TÍNH
        // =============================
        ArrayAdapter<String> adapterGioiTinh = new ArrayAdapter<>(
                requireContext(),
                android.R.layout.simple_list_item_1,
                new String[]{"Nam", "Nữ"}
        );
        actGioiTinh.setAdapter(adapterGioiTinh);

        // =============================
        // 4. Toggle mở/đóng form
        // =============================
        tvHeader.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_arrow_down, 0);

        tvHeader.setOnClickListener(v -> {
            if (layoutDetail.getVisibility() == View.VISIBLE) {
                layoutDetail.setVisibility(View.GONE);
                tvHeader.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_arrow_up, 0);
            } else {
                layoutDetail.setVisibility(View.VISIBLE);
                tvHeader.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_arrow_down, 0);
            }
        });
    }

    // =============================
    // 5. GETTER LẤY DỮ LIỆU
    // =============================
    public String getDanhXung() { return actDanhXung.getText().toString(); }
    public String getHoVaTenDem() { return edtHoVaTenDem.getText().toString(); }
    public String getTen() { return edtTen.getText().toString(); }
    public String getCongTy() { return actCongTy.getText().toString(); }
    public String getGioiTinh() { return actGioiTinh.getText().toString(); }
    public String getDiDong() { return edtDiDong.getText().toString(); }
    public String getEmail() { return edtEmail.getText().toString(); }
    public String getNgaySinh() { return edtNgaySinh.getText().toString(); }
}