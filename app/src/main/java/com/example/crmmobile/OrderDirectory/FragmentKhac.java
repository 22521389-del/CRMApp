package com.example.crmmobile.OrderDirectory;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.crmmobile.R;

public class FragmentKhac extends Fragment {

    private LinearLayout layoutContractContent, layoutRelatedContent;
    private LinearLayout layoutInvoiceContent, layoutInvoicePeriodicContent;
    private ImageView iconContract, iconRelated, iconInvoice, iconInvoicePeriodic;
    private View view;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        // Inflate layout fragment
        view = inflater.inflate(R.layout.fragment_khac, container, false);

        // === Ánh xạ các View ===
        layoutContractContent = view.findViewById(R.id.layout_contract_content);
        layoutRelatedContent = view.findViewById(R.id.layout_related_content);
        layoutInvoiceContent = view.findViewById(R.id.layout_invoice_content);
        layoutInvoicePeriodicContent = view.findViewById(R.id.layout_invoice_periodic_content);

        iconContract = view.findViewById(R.id.icon_contract);
        iconRelated = view.findViewById(R.id.icon_related);
        iconInvoice = view.findViewById(R.id.icon_invoice);
        iconInvoicePeriodic = view.findViewById(R.id.icon_invoice_periodic);

        // === Gán sự kiện click cho từng phần ===
        view.findViewById(R.id.section_contract)
                .setOnClickListener(v -> toggleSection(layoutContractContent, iconContract));

        view.findViewById(R.id.section_related)
                .setOnClickListener(v -> toggleSection(layoutRelatedContent, iconRelated));

        view.findViewById(R.id.section_invoice)
                .setOnClickListener(v -> toggleSection(layoutInvoiceContent, iconInvoice));

        view.findViewById(R.id.section_invoice_periodic)
                .setOnClickListener(v -> toggleSection(layoutInvoicePeriodicContent, iconInvoicePeriodic));

        return view;
    }

    // === Hàm thu gọn/mở rộng kèm đổi icon ===
    private void toggleSection(LinearLayout layout, ImageView icon) {
        if (layout.getVisibility() == View.GONE) {
            layout.setVisibility(View.VISIBLE);
            icon.setImageResource(R.drawable.ic_arrow_up); // mũi tên lên
        } else {
            layout.setVisibility(View.GONE);
            icon.setImageResource(R.drawable.ic_arrow_down); // mũi tên xuống
        }
    }
}

