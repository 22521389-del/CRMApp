package com.example.crmmobile;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class CalendarViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

    public final TextView dayofMonth;
    private final AdapterCalendar.onItemListener onItemListener;
    public CalendarViewHolder(@NonNull View itemView, AdapterCalendar.onItemListener onItemListener) {
        super(itemView);
        dayofMonth = itemView.findViewById(R.id.day_text);
        this.onItemListener = onItemListener;
        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        onItemListener.onItemClick(getAdapterPosition(), (String) dayofMonth.getText());
    }
}
