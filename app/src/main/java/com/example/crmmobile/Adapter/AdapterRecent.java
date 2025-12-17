package com.example.crmmobile.Adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.crmmobile.MainDirectory.InitClass;
import com.example.crmmobile.MainDirectory.Recent;
import com.example.crmmobile.R;

import java.util.List;

public class AdapterRecent extends RecyclerView.Adapter<AdapterRecent.RecentHolder> {
    private static final String TAG = "RECENT_ADAPTER";
    private List<Recent> recent_list;

    public AdapterRecent(List<Recent> recent_list) {
        this.recent_list = recent_list;

    }

    public void updateData(List<Recent> recent_list){
        this.recent_list.clear();
        if (recent_list != null){
            this.recent_list.addAll(recent_list);
        }
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecentHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recent, parent, false);
        return new RecentHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecentHolder holder, int position) {
        Recent item = recent_list.get(position);

        holder.tv_title.setText(item.getName());
        holder.iv_icon.setImageResource(InitClass.getIconRecent(item.getObjectType()));
        holder.tv_time.setText(InitClass.getTimeAgo(item.getTime()));
        Log.e(TAG, "Name: " + item.getName());
        Log.e(TAG, "Object type" + item.getObjectType());
    }

    @Override
    public int getItemCount() {
        return recent_list.size();
    }

    public class RecentHolder extends RecyclerView.ViewHolder {
        ImageView iv_icon;
        TextView tv_title, tv_time;
        public RecentHolder(@NonNull View itemView) {
            super(itemView);

            iv_icon = itemView.findViewById(R.id.iv_icon);
            tv_title = itemView.findViewById(R.id.tv_title);
            tv_time = itemView.findViewById(R.id.tv_time);
        }
    }
}
