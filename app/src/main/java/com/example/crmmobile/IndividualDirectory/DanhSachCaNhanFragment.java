package com.example.crmmobile.IndividualDirectory;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.crmmobile.Adapter.AdapterCaNhan;
import com.example.crmmobile.BottomSheet.BottomActionFragment;
import com.example.crmmobile.BottomSheet.BottomHoatDongFragment;
import com.example.crmmobile.DataBase.CaNhanRepository;
import com.example.crmmobile.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class DanhSachCaNhanFragment extends Fragment {

    private ImageView icBack;
    private RecyclerView rvCaNhan;
    private AdapterCaNhan adapter;
    private ArrayList<CaNhan> caNhanList;
    private FloatingActionButton btnAdd;
    private CaNhanRepository db;

    private static final int REQ_ADD = 100;
    private static final int REQ_EDIT = 101;

    public DanhSachCaNhanFragment() {
        // Required empty public constructor
    }

    @Nullable
    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater,
            @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState
    ) {
        View view = inflater.inflate(R.layout.activity_danhsachcanhan, container, false);

        rvCaNhan = view.findViewById(R.id.rvCaNhan);
        btnAdd = view.findViewById(R.id.btn_add_contact);
        icBack = view.findViewById(R.id.ic_back);

        db = new CaNhanRepository(requireContext());

        loadCaNhan();

        adapter = new AdapterCaNhan(requireContext(), caNhanList);
        rvCaNhan.setLayoutManager(new LinearLayoutManager(requireContext()));
        rvCaNhan.setAdapter(adapter);

        btnAdd.setOnClickListener(v -> {
            Intent intent = new Intent(requireContext(), ThongTinLienHeActivity.class);
            startActivityForResult(intent, REQ_ADD);
        });

        icBack.setOnClickListener(v -> requireActivity().onBackPressed());

        adapter.setOnItemClickListener(new AdapterCaNhan.OnItemClickListener() {
            @Override
            public void onMoreClick(CaNhan cn) {
                BottomActionFragment bottomSheet = new BottomActionFragment();
                bottomSheet.setCaNhan(cn, new BottomActionFragment.OnActionListener() {

                    @Override
                    public void onDelete(CaNhan deletedCn) {
                        db.delete(deletedCn.getId());

                        int index = -1;
                        for (int i = 0; i < caNhanList.size(); i++) {
                            if (caNhanList.get(i).getId() == deletedCn.getId()) {
                                index = i;
                                break;
                            }
                        }
                        if (index != -1) {
                            caNhanList.remove(index);
                            adapter.notifyItemRemoved(index);
                        }
                    }

                    @Override
                    public void onEdit(CaNhan editCn) {
                        Intent intent = new Intent(requireContext(), ThongTinLienHeActivity.class);
                        intent.putExtra("mode", "edit");
                        intent.putExtra("id", editCn.getId());
                        intent.putExtra("danhXung", editCn.getDanhXung());
                        intent.putExtra("hoTen", editCn.getHoVaTen());
                        intent.putExtra("ten", editCn.getTen());
                        intent.putExtra("congTy", editCn.getCongTy());
                        intent.putExtra("gioiTinh", editCn.getGioiTinh());
                        intent.putExtra("diDong", editCn.getDiDong());
                        intent.putExtra("email", editCn.getEmail());
                        intent.putExtra("ngaySinh", editCn.getNgaySinh());
                        intent.putExtra("diaChi", editCn.getDiaChi());
                        intent.putExtra("quanHuyen", editCn.getQuanHuyen());
                        intent.putExtra("tinhTP", editCn.getTinhTP());
                        intent.putExtra("quocGia", editCn.getQuocGia());
                        intent.putExtra("moTa", editCn.getMoTa());
                        intent.putExtra("ghiChu", editCn.getGhiChu());
                        intent.putExtra("giaoCho", editCn.getGiaoCho());
                        intent.putExtra("soCuocGoi", editCn.getSoCuocGoi());
                        intent.putExtra("soCuocHop", editCn.getSoCuocHop());

                        startActivityForResult(intent, REQ_EDIT);
                    }

                    @Override
                    public void onAddHoatDong(CaNhan cn) {
                        BottomHoatDongFragment bottom = new BottomHoatDongFragment();
                        bottom.setCaNhan(cn);
                        bottom.show(getParentFragmentManager(), "hoatdong");
                    }
                });

                bottomSheet.show(getParentFragmentManager(), "BottomAction");
            }

            @Override
            public void onItemClick(CaNhan cn) {
                Intent intent = new Intent(requireContext(), TabActivity.class);
                intent.putExtra("CANHAN_OBJECT", cn);
                startActivity(intent);
            }
        });

        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode != requireActivity().RESULT_OK || data == null) return;

        // ADD
        if (requestCode == REQ_ADD) {
            CaNhan cn = new CaNhan();
            cn.setHoVaTen(data.getStringExtra("hoTen"));
            cn.setTen(data.getStringExtra("ten"));
            cn.setCongTy(data.getStringExtra("congTy"));
            cn.setDanhXung(data.getStringExtra("danhXung"));
            cn.setGioiTinh(data.getStringExtra("gioiTinh"));
            cn.setDiDong(data.getStringExtra("diDong"));
            cn.setEmail(data.getStringExtra("email"));

            Calendar calendar = Calendar.getInstance();
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault());
            cn.setNgayTao(sdf.format(calendar.getTime()));

            long id = db.add(cn);
            cn.setId((int) id);

            caNhanList.add(cn);
            adapter.notifyItemInserted(caNhanList.size() - 1);
        }

        // EDIT
        if (requestCode == REQ_EDIT) {
            int id = data.getIntExtra("id", -1);
            if (id == -1) return;

            for (int i = 0; i < caNhanList.size(); i++) {
                if (caNhanList.get(i).getId() == id) {
                    CaNhan cn = caNhanList.get(i);

                    cn.setHoVaTen(data.getStringExtra("hoTen"));

                    Calendar calendar = Calendar.getInstance();
                    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault());
                    cn.setNgaySua(sdf.format(calendar.getTime()));

                    db.update(cn);
                    adapter.notifyItemChanged(i);
                    break;
                }
            }
        }
    }

    private void loadCaNhan() {
        List<CaNhan> listFromDB = db.getAllCaNhan();
        if (listFromDB.isEmpty()) {
            caNhanList = new ArrayList<>();
        } else {
            caNhanList = new ArrayList<>(listFromDB);
        }
    }
}
