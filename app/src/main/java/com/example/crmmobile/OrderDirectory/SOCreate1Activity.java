package com.example.crmmobile.OrderDirectory;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.crmmobile.R;
import com.example.crmmobile.OrderDirectory.DonHang;
import com.example.crmmobile.DataBase.DonHangRepository;
import com.example.crmmobile.OrderDirectory.OrderFragment;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.Calendar;
import java.util.Locale;

public class SOCreate1Activity extends AppCompatActivity {

    private TextInputEditText edtOrderDate;
    private View generalContainer;
    private View otherTabContainer;
    private DonHangRepository donHangRepository;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_socreate1);
        donHangRepository = new DonHangRepository(this);

        // ----- Toolbar + nút back -----
        MaterialToolbar tb = findViewById(R.id.toolbar);
        setSupportActionBar(tb);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        tb.setNavigationOnClickListener(v -> goBack());

        // ----- Containers -----
        generalContainer  = findViewById(R.id.generalContainer);   // layout "Thông tin chung"
        otherTabContainer = findViewById(R.id.otherTabContainer);  // FrameLayout cho các tab còn lại

        // ----- TabLayout -----
        TabLayout tabs = findViewById(R.id.tabLayout);
        if (tabs != null) {
            tabs.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
                @Override
                public void onTabSelected(TabLayout.Tab tab) {
                    if (tab == null) return;
                    switch (tab.getPosition()) {
                        case 0: // Thông tin chung
                            showGeneral();
                            break;
                        case 1: // Sản phẩm
                            showOther(new SOProductsFragment());
                            break;
                        case 2: // Thanh toán
                            showOther(new ThanhToanFragment());
                            break;
                        default:
                            showGeneral();
                            break;
                    }
                }

                @Override public void onTabUnselected(TabLayout.Tab tab) {}
                @Override public void onTabReselected(TabLayout.Tab tab) {}
            });

            if (savedInstanceState == null) {
                // mở mặc định tab 0
                tabs.selectTab(tabs.getTabAt(0));
                showGeneral();
            }
        } else {
            showGeneral();
        }

        // ========== Logic "Thông tin chung" ==========
        AutoCompleteTextView actCompany  = findViewById(R.id.actCompany);
        AutoCompleteTextView actContact  = findViewById(R.id.actContact);
        AutoCompleteTextView actStatus   = findViewById(R.id.actStatus);

        if (actCompany != null) {
            actCompany.setAdapter(new ArrayAdapter<>(
                    this, android.R.layout.simple_list_item_1,
                    new String[]{"Công ty A", "Công ty B", "Công ty C"}));
        }

        if (actContact != null) {
            actContact.setAdapter(new ArrayAdapter<>(
                    this, android.R.layout.simple_list_item_1,
                    new String[]{"Nguyễn Văn An", "Trần B", "Lê C"}));
        }

        if (actStatus != null) {
            actStatus.setAdapter(new ArrayAdapter<>(
                    this, android.R.layout.simple_list_item_1,
                    new String[]{"Mới", "Đang xử lý", "Hoàn tất"}));
        }

        TextInputLayout tilCompany = findViewById(R.id.tilCompany);
        TextInputLayout tilContact = findViewById(R.id.tilContact);
        if (tilCompany != null) tilCompany.setEndIconOnClickListener(v -> { /* TODO */ });
        if (tilContact != null) tilContact.setEndIconOnClickListener(v -> { /* TODO */ });

        TextInputLayout tilOrderDate = findViewById(R.id.tilOrderDate);
        edtOrderDate = findViewById(R.id.edtOrderDate);
        if (tilOrderDate != null) tilOrderDate.setEndIconOnClickListener(v -> openDatePicker());
        if (edtOrderDate != null) edtOrderDate.setOnClickListener(v -> openDatePicker());

        View btnCancel = findViewById(R.id.btnCancel);
        if (btnCancel != null) btnCancel.setOnClickListener(v -> goBack());

        View btnSave = findViewById(R.id.btnSave);
        if (btnSave != null) {
            btnSave.setOnClickListener(v -> saveOrder());
        }
    }

    // ===== Back / Up =====
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            goBack();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void goBack() {
        if (isTaskRoot()) {
            Intent i = new Intent(this, OrderFragment.class);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(i);
            finish();
        } else {
            finish();
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        goBack();
        return true;
    }

    // ===== Hiển thị container =====
    private void showGeneral() {
        if (generalContainer != null) generalContainer.setVisibility(View.VISIBLE);
        if (otherTabContainer != null) otherTabContainer.setVisibility(View.GONE);
    }

    private void showOther(Fragment fragment) {
        if (generalContainer != null) generalContainer.setVisibility(View.GONE);
        if (otherTabContainer != null) {
            otherTabContainer.setVisibility(View.VISIBLE);
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.otherTabContainer, fragment);
            ft.commit();
        }
    }
    // ===== Lưu đơn hàng vào DB =====
    private void saveOrder() {
        // Lấy view trong tab "Thông tin chung"
        TextInputEditText edtTitle    = findViewById(R.id.edtTitle);
        AutoCompleteTextView actCompany = findViewById(R.id.actCompany);
        AutoCompleteTextView actContact = findViewById(R.id.actContact);
        AutoCompleteTextView actStatus  = findViewById(R.id.actStatus);

        // Đã có edtOrderDate là biến member, gán ở onCreate:
        // edtOrderDate = findViewById(R.id.edtOrderDate);

        // Lấy text (nếu view null thì cho chuỗi rỗng để tránh crash)
        String title      = edtTitle     != null ? edtTitle.getText().toString().trim() : "";
        String companyStr = actCompany   != null ? actCompany.getText().toString().trim() : "";
        String contactStr = actContact   != null ? actContact.getText().toString().trim() : "";
        String statusStr  = actStatus    != null ? actStatus.getText().toString().trim() : "";
        String dateStr    = edtOrderDate != null ? edtOrderDate.getText().toString().trim() : "";

        // ==== Validate cơ bản ====
        if (title.isEmpty()) {
            Toast.makeText(this, "Vui lòng nhập tiêu đề đơn hàng", Toast.LENGTH_SHORT).show();
            return;
        }

        if (dateStr.isEmpty()) {
            Toast.makeText(this, "Vui lòng chọn ngày đặt hàng", Toast.LENGTH_SHORT).show();
            return;
        }

        // Nếu chưa chọn tình trạng thì mặc định là "Mới"
        if (statusStr.isEmpty()) {
            statusStr = "Mới";
        }

        // ==== Tạo đối tượng DonHang để lưu DB ====
        DonHang dh = new DonHang();

        // Map tối thiểu: tiêu đề + ngày + tình trạng
        dh.setTenDonHang(title);
        dh.setNgayDatHang(dateStr);
        dh.setTinhTrang(statusStr);

        // Các field khác hiện chưa liên kết bảng (Công ty, Liên hệ, Sản phẩm, Thanh toán)
        // nên tạm thời cho giá trị mặc định. Khi nhóm làm xong module khác thì map lại.
        dh.setCongTyId(0);        // sau này map từ bảng Company
        dh.setNguoiLienHeId(0);   // sau này map từ Contact
        dh.setCoHoiId(0);         // sau này map từ Opportunity
        dh.setBaoGiaId(0);        // sau này map từ Quote

        dh.setNgayNhanHang("");   // chưa có UI
        dh.setSanPham("");        // sau này serialize danh sách ProductLine
        dh.setSoLuong(0);
        dh.setDonGia(0);
        dh.setTongTien(0);

        // Tạm dùng mô tả để lưu tên công ty/người liên hệ hiển thị cho dễ debug
        dh.setMoTa(companyStr + " - " + contactStr);

        dh.setGiaoChoId(0);       // sau này map từ user đăng nhập

        // ==== Gọi repository để insert ====
        long newId = donHangRepository.insert(dh);
        if (newId > 0) {
            Toast.makeText(this, "Lưu đơn hàng thành công", Toast.LENGTH_SHORT).show();
            // Quay lại MainActivity, onResume() sẽ reload list
            finish();
        } else {
            Toast.makeText(this, "Lưu đơn hàng thất bại", Toast.LENGTH_SHORT).show();
        }
    }

    // ===== Date picker =====
    private void openDatePicker() {
        Calendar c = Calendar.getInstance();
        new DatePickerDialog(this, (view, y, m, d) -> {
            String s = String.format(Locale.getDefault(), "%02d/%02d/%04d",
                    d, m + 1, y);
            if (edtOrderDate != null) edtOrderDate.setText(s);
        }, c.get(Calendar.YEAR), c.get(Calendar.MONTH),
                c.get(Calendar.DAY_OF_MONTH)).show();
    }

}





