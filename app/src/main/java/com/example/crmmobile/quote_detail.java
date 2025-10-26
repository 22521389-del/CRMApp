package com.example.crmmobile;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

public class quote_detail extends Fragment {
    ImageView iv_back;

    public quote_detail() {
        // Required empty public constructor
    }

    public static quote_detail newInstance() {
        quote_detail fragment = new quote_detail();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);
        iv_back = view.findViewById(R.id.iv_back);

        //back to quote layout
        iv_back.setOnClickListener(v -> {
            requireActivity().finish();
        });
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_quote_detail, container, false);
        return view;
    }
}