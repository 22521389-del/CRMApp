package com.example.crmmobile;

import android.graphics.PorterDuff;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class AdapterActive  extends RecyclerView.Adapter<AdapterActive.ActiveViewHolder> {
    private List<listActive> activeList;
    private OnItemClickListener listener;


    public AdapterActive(List<listActive> activeList, OnItemClickListener listener){
        this.activeList = activeList;
        this.listener = listener;
    }
    public interface OnItemClickListener{
        void onItemClick(int position);
    }

    public static class ActiveViewHolder extends RecyclerView.ViewHolder{
        ImageView iv_img;
        TextView tv_name;

        public ActiveViewHolder(View itemview){
            super(itemview);
            iv_img = itemview.findViewById(R.id.action_image);
            tv_name = itemview.findViewById(R.id.action_name);
        }
    }
    @NonNull
    @Override
    public ActiveViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_active, parent, false);
        return new ActiveViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ActiveViewHolder holder, int position) {
        listActive lactive = activeList.get(position);
        holder.iv_img.setImageResource(lactive.getImage());
        holder.tv_name.setText(lactive.getName());

        if(lactive.getName().equals("Xóa")){
            holder.iv_img.setColorFilter(null);
        }
        else{
            holder.iv_img.setColorFilter(
                    ContextCompat.getColor(holder.itemView.getContext(), R.color.active_add),
                    PorterDuff.Mode.SRC_IN
            );
        }

        holder.itemView.setOnClickListener(v->{
            if(listener != null){
                listener.onItemClick(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return activeList.size();
    }
}
