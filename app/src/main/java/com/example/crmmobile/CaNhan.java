package com.example.crmmobile;
public class CaNhan {
    private String hoTen;
    private String congTy;
    private String ngaySinh;
    private int soCuocGoi = 0;
    private int soMeeting = 0;

    // Các thông tin khác vẫn lưu nhưng không hiển thị ở item
    private String diaChi;
    private String moTa;
    private String quanLy;

    public CaNhan() {}

    // Getters & Setters
    public String getHoTen() { return hoTen; }
    public void setHoTen(String hoTen) { this.hoTen = hoTen; }

    public String getCongTy() { return congTy; }
    public void setCongTy(String congTy) { this.congTy = congTy; }

    public String getNgaySinh() { return ngaySinh; }
    public void setNgaySinh(String ngaySinh) { this.ngaySinh = ngaySinh; }

    public int getSoCuocGoi() { return soCuocGoi; }
    public void setSoCuocGoi(int soCuocGoi) { this.soCuocGoi = soCuocGoi; }

    public int getSoMeeting() { return soMeeting; }
    public void setSoMeeting(int soMeeting) { this.soMeeting = soMeeting; }

    public String getDiaChi() { return diaChi; }
    public void setDiaChi(String diaChi) { this.diaChi = diaChi; }

    public String getMoTa() { return moTa; }
    public void setMoTa(String moTa) { this.moTa = moTa; }

    public String getQuanLy() { return quanLy; }
    public void setQuanLy(String quanLy) { this.quanLy = quanLy; }
}