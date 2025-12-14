package com.example.crmmobile.OpportunityDirectory;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager2.widget.ViewPager2;

import com.example.crmmobile.OpportunityDirectory.Opportunity;
import com.example.crmmobile.R;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.Arrays;
import java.util.List;


public class OpportunityDetailActivity extends AppCompatActivity {
    private ImageView ivBack, detailEdit;
    private TabLayout tabLayout;
    private ViewPager2 viewPager;
    private OpportunityDetailPagerAdapter pagerAdapter;

    // pipeline
    private OpportunityActionViewModel actionVM;
    private ImageView ivStep1, ivStep2, ivStep3, ivStep4, ivStep5;
    private View vStep1Start, vStep1, vStep2, vStep3, vStep4;
    private int opportunityId = -1;
    private Opportunity opportunity;
    private OpportunityDetailViewModel detailVM;

    boolean isPipelineSetup = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("OD_DEBUG", "onCreate()");
        setContentView(R.layout.activity_opportunity_detail);

        ivBack = findViewById(R.id.iv_opportunity_detail_back);
        tabLayout = findViewById(R.id.tl_opportunity_detail_tabs);
        viewPager = findViewById(R.id.vp_opportunity_detail_content);
        detailEdit = findViewById(R.id.iv_opportunity_detail_edit);

// cẩn thâ truyền đúng intent key "id" từ list fragment để tránh lỗi render
        opportunityId = getIntent().getIntExtra("id", -1);
        Log.d("OD_DEBUG", "opportunityId = " + opportunityId);

        actionVM = new ViewModelProvider(this)
                .get(OpportunityActionViewModel.class);

        detailVM = new ViewModelProvider(this).get(OpportunityDetailViewModel.class);

        detailVM.loadOpportunityById(opportunityId);

        detailVM.getOpportunity().observe(this, o -> {
            Log.d("OD_DEBUG", "observe opportunity = " + o);
            if (o != null) {
                opportunity = o;

                // BIND DATA
                bindData(findViewById(android.R.id.content), opportunity);

                if (!isPipelineSetup) {
                    setupViewPager();
                    setupPipeline(); // init 1 lần
                    isPipelineSetup = true;
                }

                updatePipelineUI(o);
                updatePipelineClickability(o);
            }
        });

        actionVM.getActionSuccess().observe(this, success -> {
            if (Boolean.TRUE.equals(success)) {
                // reload từ DB
                detailVM.loadOpportunityById(opportunityId);
            }
        });

        // Nút back
        ivBack.setOnClickListener(v -> finish());

        // Nút chỉnh sửa (cây bút)
        detailEdit.setOnClickListener(v -> {
            Intent intent = new Intent(this, OpportunityFormActivity.class);
            intent.putExtra("mode", "update");
            intent.putExtra("id", opportunityId);
            startActivity(intent);
        });

    }

    private void setupViewPager() {

        // Danh sách tab
        List<String> tabTitles = Arrays.asList("Tổng quan", "Chi tiết");

        // Gắn adapter
        pagerAdapter = new OpportunityDetailPagerAdapter(this, tabTitles.size(), opportunityId);
        viewPager.setAdapter(pagerAdapter);

        // Gắn TabLayout với ViewPager
        new TabLayoutMediator(tabLayout, viewPager,
                (tab, position) -> tab.setText(tabTitles.get(position))
        ).attach();

        // Mặc định mở tab đầu tiên
        viewPager.setCurrentItem(0);

    }

    private void setupPipeline() {
        // Tìm view trong activity
        ivStep1 = findViewById(R.id.iv_pineline_step1_icon);
        ivStep2 = findViewById(R.id.iv_pineline_step2_icon);
        ivStep3 = findViewById(R.id.iv_pineline_step3_icon);
        ivStep4 = findViewById(R.id.iv_pineline_step4_icon);
        ivStep5 = findViewById(R.id.iv_pineline_step5_icon);

        vStep1Start = findViewById(R.id.v_pineline_step1_start);
        vStep1 = findViewById(R.id.v_pineline_step1_connector);
        vStep2 = findViewById(R.id.v_pineline_step2_connector);
        vStep3 = findViewById(R.id.v_pineline_step3_connector);
        vStep4 = findViewById(R.id.v_pineline_step4_connector);

        // set mặc định cho start line
        vStep1Start.setBackgroundResource(R.color.blue);

        // Khởi tạo ViewModel (sử dụng this vì trong Activity)
        actionVM = new ViewModelProvider(this).get(OpportunityActionViewModel.class);

        // Click vào bước → mở BottomSheet
        View.OnClickListener stepClickListener = v -> {
            OpportunityActionBottomSheet.newInstance(opportunityId)
                    .show(getSupportFragmentManager(), "ActionSheet");
        };

        ivStep1.setOnClickListener(stepClickListener);
        ivStep2.setOnClickListener(stepClickListener);
        ivStep3.setOnClickListener(stepClickListener);
        ivStep4.setOnClickListener(stepClickListener);
        ivStep5.setOnClickListener(stepClickListener);

        // observe CHỈ 1 LẦN
        actionVM.getActionSuccess().observe(this, success -> {
            if (Boolean.TRUE.equals(success)) {
                detailVM.loadOpportunityById(opportunityId); // reload chuẩn
            }
        });
    }

    private void updatePipelineUI(Opportunity opportunity) {
        if (opportunity == null) return;
        // Đổi icon và màu theo stage hiện tại
        String status = opportunity.getStatus();

        resetPipelineColors(); // đưa về mặc định

        switch (status) {
            case "Nhận diện người ra quyết định":
                ivStep1.setImageResource(R.drawable.ic_step_current);
                break;
            case "Phân tích nhận thức":
                ivStep1.setImageResource(R.drawable.ic_step_done);
                vStep1.setBackgroundResource(R.color.blue);
                ivStep2.setImageResource(R.drawable.ic_step_current);
                break;
            case "Đề xuất/ Báo giá":
                ivStep1.setImageResource(R.drawable.ic_step_done);
                ivStep2.setImageResource(R.drawable.ic_step_done);
                vStep1.setBackgroundResource(R.color.blue);
                vStep2.setBackgroundResource(R.color.blue);
                ivStep3.setImageResource(R.drawable.ic_step_current);
                break;
            case "Thương lượng đàm phán":
                ivStep1.setImageResource(R.drawable.ic_step_done);
                ivStep2.setImageResource(R.drawable.ic_step_done);
                ivStep3.setImageResource(R.drawable.ic_step_done);
                vStep1.setBackgroundResource(R.color.blue);
                vStep2.setBackgroundResource(R.color.blue);
                vStep3.setBackgroundResource(R.color.blue);
                ivStep4.setImageResource(R.drawable.ic_step_current);
                break;
            case "Thành công":
                ivStep1.setImageResource(R.drawable.ic_step_done);
                ivStep2.setImageResource(R.drawable.ic_step_done);
                ivStep3.setImageResource(R.drawable.ic_step_done);
                ivStep4.setImageResource(R.drawable.ic_step_done);
                vStep1.setBackgroundResource(R.color.blue);
                vStep2.setBackgroundResource(R.color.blue);
                vStep3.setBackgroundResource(R.color.blue);
                vStep4.setBackgroundResource(R.color.blue);
                ivStep5.setImageResource(R.drawable.ic_step_current);
                break;
        }
    }

    private void resetPipelineColors() {
        ivStep1.setImageResource(R.drawable.ic_step_todo);
        ivStep2.setImageResource(R.drawable.ic_step_todo);
        ivStep3.setImageResource(R.drawable.ic_step_todo);
        ivStep4.setImageResource(R.drawable.ic_step_todo);
        ivStep5.setImageResource(R.drawable.ic_step_todo);

        vStep1.setBackgroundResource(R.color.gray);
        vStep2.setBackgroundResource(R.color.gray);
        vStep3.setBackgroundResource(R.color.gray);
        vStep4.setBackgroundResource(R.color.gray);
    }

    private void updatePipelineClickability(Opportunity opportunity) {
        if (opportunity == null) return;

        // Lấy status hiện tại
        String status = opportunity.getStatus();

        // Mặc định disable tất cả step
        ivStep1.setClickable(false);
        ivStep2.setClickable(false);
        ivStep3.setClickable(false);
        ivStep4.setClickable(false);
        ivStep5.setClickable(false);

        // Enable step hiện tại
        switch (status) {
            case "Nhận diện người ra quyết định":
                ivStep1.setClickable(true);
                break;
            case "Phân tích nhận thức":
                ivStep2.setClickable(true);
                break;
            case "Đề xuất/ Báo giá":
                ivStep3.setClickable(true);
                break;
            case "Thương lượng đàm phán":
                ivStep4.setClickable(true);
                break;
            case "Thành công":
            case "Thất bại":
                ivStep5.setClickable(true);
                break;
        }

        // Gắn click listener cho step hiện tại
        View.OnClickListener stepClickListener = v -> {
            OpportunityActionBottomSheet.newInstance(opportunityId)
                    .show(getSupportFragmentManager(), "ActionSheet");
        };

        if (ivStep1.isClickable()) ivStep1.setOnClickListener(stepClickListener);
        if (ivStep2.isClickable()) ivStep2.setOnClickListener(stepClickListener);
        if (ivStep3.isClickable()) ivStep3.setOnClickListener(stepClickListener);
        if (ivStep4.isClickable()) ivStep4.setOnClickListener(stepClickListener);
        if (ivStep5.isClickable()) ivStep5.setOnClickListener(stepClickListener);
    }

    private void bindData(View view, Opportunity o) {
        if (o == null) return;

        TextView tvCompany = view.findViewById(R.id.tv_company_name);
        TextView tvPrice = view.findViewById(R.id.tv_value);
        TextView tvStatus = view.findViewById(R.id.tv_opportunity_status);
        TextView tvCreator = view.findViewById(R.id.tv_opportunity_creator_name);
        TextView tvAssignee = view.findViewById(R.id.tv_opportunity_assignee_name);

        tvPrice.setText(formatCurrency(o.getPrice()));
        tvStatus.setText(o.getStatus());

        // Tạm thời set id (sau này map sang tên)
        tvCompany.setText("Company ID: " + o.getCompany());
        tvCreator.setText("Creator ID: " + o.getManagement());
        tvAssignee.setText("Assignee ID: " + o.getManagement());
    }

    private String formatCurrency(double amount) {
        return String.format("%,.0f đ", amount);
    }


    @Override
    protected void onResume() {
        super.onResume();
        if (opportunityId != -1) {
            detailVM.loadOpportunityById(opportunityId);
        }
    }


}