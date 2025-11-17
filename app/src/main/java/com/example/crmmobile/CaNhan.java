package com.example.crmmobile;

public class CaNhan {
    private String ten;
    private String congTy;
    private String ngay;
    private int cuocGoi;
    private int meeting;

    public CaNhan(String ten, String congTy, String ngay, int cuocGoi, int meeting) {
        this.ten = ten;
        this.congTy = congTy;
        this.ngay = ngay;
        this.cuocGoi = cuocGoi;
        this.meeting = meeting;
    }

    public String getTen() { return ten; }
    public String getCongTy() { return congTy; }
    public String getNgay() { return ngay; }
    public int getCuocGoi() { return cuocGoi; }
    public int getMeeting() { return meeting; }
}
