package com.example.crmmobile.MainDirectory;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.crmmobile.Adapter.AdapterModule;
import com.example.crmmobile.R;

import java.util.Arrays;
import java.util.List;


public class main_screen extends Fragment {

    private RecyclerView rl_module;
    private AdapterModule adapter;
    private List<item_module> itemModules;

    private onModuleItemSelectedListener itemModuleSelectedListener;

    public interface onModuleItemSelectedListener{
        void onModuleSelectedListener(String moduleNames);
    }

    @Override
    public void onAttach(@NonNull Context context){
        super.onAttach(context);
        if(context instanceof onModuleItemSelectedListener){
            itemModuleSelectedListener = (onModuleItemSelectedListener) context;
        }
    }

    public main_screen() {
    }
    public static main_screen newInstance(String param1, String param2) {
        main_screen fragment = new main_screen();
        Bundle args = new Bundle();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_main_screen, container, false);

        rl_module = view.findViewById(R.id.rl_module);
        //shrink icon see more
        TextView tvSeemore = view.findViewById(R.id.tv_seemore);

        itemModules = Arrays.asList(
                    new item_module("Tổ chức", R.drawable.ic_company),
                    new item_module("Cá nhân", R.drawable.ic_individual),
                    new item_module("Báo giá", R.drawable.ic_quote),
                    new item_module("Hóa đơn", R.drawable.ic_bill),
                    new item_module("Hợp đồng", R.drawable.ic_contract),
                    new item_module("Báo cáo", R.drawable.ic_chart),
                    new item_module("Cơ hội", R.drawable.ic_target),
                    new item_module("CSKH", R.drawable.customer_care)
        );

        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 4);
        layoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                return 1;
            }
        });
        //assign layoutmanager
        rl_module.setLayoutManager(layoutManager);

        //Init adapter
        adapter = new AdapterModule(itemModules, position->{
            String selected = itemModules.get(position).getName().trim();
            if(itemModuleSelectedListener != null){
                itemModuleSelectedListener.onModuleSelectedListener(selected);
            }
        });

        //Assign adapter
        rl_module.setAdapter(adapter);

        return view;
    }
}