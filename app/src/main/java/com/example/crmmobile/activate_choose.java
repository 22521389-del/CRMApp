package com.example.crmmobile;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

public class activate_choose extends Fragment {

    RecyclerView recyclerView;
    List<listActive> listactive;
    AdapterActive adapterActive;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_activate_choose, container, false);
        recyclerView = view.findViewById(R.id.active_recycler);

        listactive = new ArrayList<>();
        listactive.add(new listActive(R.drawable.pushpin, "Ghim"));
        listactive.add(new listActive(R.drawable.ic_call, "Gọi điện"));
        listactive.add(new listActive(R.drawable.ic_comment_mess, "Chat" ));
        listactive.add(new listActive(R.drawable.ic_sms, "Gửi tin nhắn SMS"));
        listactive.add(new listActive(R.drawable.ic_mail, "Gửi Email"));
        listactive.add(new listActive(R.drawable.ic_calendar, "Thêm hoạt động"));
        listactive.add(new listActive(R.drawable.ic_loop, "Chuyển đổi Lead"));
        listactive.add(new listActive(R.drawable.ic_pencil, "Chỉnh sửa"));
        listactive.add(new listActive(R.drawable.ic_garbage, "Xóa"));

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);

        adapterActive = new AdapterActive(listactive, position -> {
            listActive item = listactive.get(position);

            if(item.getName().equals("Chuyển đổi Lead")){
                getParentFragmentManager().beginTransaction()
                        .replace(R.id.main_container, new ConvertLead())
                        .addToBackStack(null)
                        .commit();
            }
        });

        recyclerView.setAdapter(adapterActive);

        ImageView iv_exit = view.findViewById(R.id.iv_exit);
        iv_exit.setOnClickListener(v -> {
            if(getActivity() != null){
                ViewPager2 viewPager = requireActivity().findViewById(R.id.viewPager);
                BottomNavigationView navFooter = requireActivity().findViewById(R.id.nav_footer);
                FrameLayout contain = requireActivity().findViewById(R.id.main_container);

                viewPager.setVisibility(View.VISIBLE);
                navFooter.setVisibility(View.VISIBLE);
                contain.setVisibility(View.GONE);

                getActivity().getSupportFragmentManager().popBackStack();
            }
        });
        return view;
    }
}