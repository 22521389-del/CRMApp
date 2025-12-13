package com.example.crmmobile.OpportunityDirectory;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import com.example.crmmobile.OpportunityDirectory.Opportunity;
import com.example.crmmobile.R;


public class OpportunityFormActivity extends AppCompatActivity {
    private int opportunityId = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_opportunity_form);

        // Lấy intent
        String mode = getIntent().getStringExtra("mode");
        opportunityId = getIntent().getIntExtra("opportunity_id", -1);

        // Gọi fragment chỉ với 2 tham số: object + mode



        OpportunityFormFragment fragment =
                OpportunityFormFragment.newInstance(opportunityId, mode);

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.layout_opportunity_form, fragment)
                .commit();
    }
}
