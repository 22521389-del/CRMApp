package com.example.crmmobile.LeadDirectory;

import androidx.annotation.NonNull;

public class Nhanvien {
    private int id;
    private String hoten;

    public Nhanvien(int id, String hoten) {
        this.id = id;
        this.hoten = hoten;
    }
    public Nhanvien(String hoten){
        this.hoten = hoten;
    }

    public Nhanvien(){}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getHoten() {
        return hoten;
    }

    @NonNull
    @Override
    public String toString(){
        return hoten;
    }

    public void setHoten(String hoten) {
        this.hoten = hoten;
    }
}
