package com.example.crmmobile.OpportunityDirectory;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;

import com.example.crmmobile.OpportunityDirectory.Opportunity;
import com.example.crmmobile.R;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.textfield.TextInputEditText;

public class OpportunityActionBottomSheet extends BottomSheetDialogFragment {

    private OpportunityActionViewModel viewModel;
    private com.example.crmmobile.OpportunityDirectory.Opportunity opportunity;

    public static OpportunityActionBottomSheet newInstance(Opportunity opportunity) {
        OpportunityActionBottomSheet sheet = new OpportunityActionBottomSheet();
        Bundle args = new Bundle();
        args.putSerializable("opportunity", opportunity);
        sheet.setArguments(args);
        return sheet;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_opportunity_stage_transition, container, false);
        opportunity = (Opportunity) getArguments().getSerializable("opportunity");
        viewModel = new ViewModelProvider(requireActivity()).get(OpportunityActionViewModel.class);

        AutoCompleteTextView spNextStage = view.findViewById(R.id.sp_next_sales_stage);
        TextInputEditText etNote = view.findViewById(R.id.et_bottom_sheet_description);
        Button btnSave = view.findViewById(R.id.btn_save);

        btnSave.setOnClickListener(v -> {
            String nextStage = spNextStage.getText().toString();
            String note = etNote.getText().toString();
            if (opportunity != null) {
                // Gọi theo id thay vì object
                viewModel.changeStage(opportunity.getId(), nextStage, note);
            }
            dismiss();
        });

        return view;
    }
}
