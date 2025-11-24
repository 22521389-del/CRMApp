package com.example.crmmobile;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class Calendar extends Fragment implements AdapterCalendar.onItemListener{
    private LocalDate selecteddate;
    private RecyclerView rv_calendar;
    private TextView tv_monthyear;
    public Calendar() {
        // Required empty public constructor
    }

    public static Calendar newInstance(String param1, String param2) {
        Calendar fragment = new Calendar();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_calendar, container, false);
        rv_calendar = view.findViewById(R.id.rv_calendar);
        tv_monthyear = view.findViewById(R.id.tv_monthyear);

        selecteddate = LocalDate.now();
        setMonthView();
        return view;
    }

    private void setMonthView() {
        tv_monthyear.setText(monthYearFromDate(selecteddate));
        ArrayList<String> dayinMonth = daysInMonthArry(selecteddate);

        AdapterCalendar adapterCalendar = new AdapterCalendar(dayinMonth, this);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getContext(), 7);
        rv_calendar.setLayoutManager(layoutManager);
        rv_calendar.setAdapter(adapterCalendar);
    }

    private ArrayList<String> daysInMonthArry(LocalDate date) {
        ArrayList<String> daysInMonthArray = new ArrayList<>();
        YearMonth yearMonth = YearMonth.from(date);

        int DaysInMonth = yearMonth.lengthOfMonth();
        LocalDate firstofMonth = date.withDayOfMonth(1);
        int dayofWeek = firstofMonth.getDayOfWeek().getValue() - 2;


        for(int i = 0;i < 42; i++){
            if(i <= dayofWeek || i > DaysInMonth + dayofWeek){
                daysInMonthArray.add("");
            }
            else{
                daysInMonthArray.add(String.valueOf(i - dayofWeek));
            }
        }
        return daysInMonthArray;
    }

    private String monthYearFromDate(LocalDate selecteddate) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM yyyy");
        return selecteddate.format(formatter);
    }

    @Override
    public void onItemClick(int position, String dayText) {

    }
}