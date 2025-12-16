package com.example.crmmobile.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.widget.ImageView;

import com.example.crmmobile.DataBase.HoatDongRepository;
import com.example.crmmobile.HoatDongDirectory.HoatDong;
import com.example.crmmobile.IndividualDirectory.CaNhan;
import com.example.crmmobile.R;

import java.util.List;

public class AdapterCaNhan extends RecyclerView.Adapter<AdapterCaNhan.ViewHolder> {
    private Context context;
    private List<CaNhan> caNhanList;
    private OnItemClickListener listener;

    private HoatDongRepository hoatDongRepository;


    public interface OnItemClickListener {
        void onMoreClick(CaNhan cn);
        void onItemClick(CaNhan cn);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public AdapterCaNhan(Context context, List<CaNhan> caNhanList) {
        this.context = context;
        this.caNhanList = caNhanList;
        this.hoatDongRepository = new HoatDongRepository(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_canhan, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        CaNhan cn = caNhanList.get(position);
        holder.tvDanhXung.setText(cn.getDanhXung());
        holder.tvHoTen.setText(cn.getHoVaTen());
        holder.tvTen.setText(cn.getTen());
        holder.tvCongTy.setText(cn.getCongTy());
        holder.tvNgay.setText(cn.getNgayTao());
        // Đếm số cuộc gọi và cuộc họp từ database
        int callCount = 0;
        int meetingCount = 0;

        if (cn.getId() > 0 && hoatDongRepository != null) {
            List<HoatDong> hoatDongList = hoatDongRepository.getHoatDongByNguoiLienHe(cn.getId());
            for (HoatDong hd : hoatDongList) {
                String type = hd.getType();
                if ("call".equalsIgnoreCase(type)) {
                    callCount++;
                } else if ("meeting".equalsIgnoreCase(type)) {
                    meetingCount++;
                }
            }
        }

        holder.tvCuocGoi.setText(String.valueOf(callCount));
        holder.tvMeeting.setText(String.valueOf(meetingCount));


        // --- Click vào icon "More" ---
        holder.icMore.setOnClickListener(v -> {
            if (listener != null) listener.onMoreClick(cn);
        });

        // --- Click vào toàn item ---
        holder.itemView.setOnClickListener(v -> {
            if (listener != null) listener.onItemClick(cn);
        });

    }

    @Override
    public int getItemCount() {
        return caNhanList.size();
    }

    public void addItem(CaNhan cn) {
        caNhanList.add(cn);
        notifyItemInserted(caNhanList.size() - 1);
    }

    public void setData(List<CaNhan> newList) {
        this.caNhanList = newList != null ? newList : new java.util.ArrayList<>();
        notifyDataSetChanged();
    }


    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvDanhXung, tvHoTen, tvTen, tvCongTy, tvNgay, tvCuocGoi, tvMeeting;
        ImageView icMore;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvDanhXung = itemView.findViewById(R.id.tvDanhXung);
            tvHoTen = itemView.findViewById(R.id.tvHoTen);
            tvTen = itemView.findViewById(R.id.tvTen);
            tvCongTy = itemView.findViewById(R.id.tvCongTy);
            tvNgay = itemView.findViewById(R.id.tvNgay);
            tvCuocGoi = itemView.findViewById(R.id.fill_cuocgoi);
            tvMeeting = itemView.findViewById(R.id.fill_meeting);
            icMore = itemView.findViewById(R.id.ic_more);
        }
    }


}
