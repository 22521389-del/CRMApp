package com.example.crmmobile.DataBase.Table;

public class ContactTable {
    public static final String TABLE_NAME = "COMPANY";
    public static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + " (" +
                    "ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "HOTEN TEXT," +
                    "DANHXUNG TEXT," +
                    "TEN TEXT," +
                    "DIENTHOAI TEXT," +
                    "EMAIL TEXT," +
                    "NGAYSINH TEXT," +
                    "GIOITINH TEXT," +
                    "DIACHI TEXT," +
                    "QUANHUYEN TEXT," +
                    "TINHTP TEXT," +
                    "QUOCGIA TEXT," +
                    "CHUCVU TEXT," +
                    "CONGTY TEXT," +
                    "MOTA TEXT," +
                    "GHICHU TEXT," +
                    "GIAOCHO TEXT," +
                    "NGAYTAO TEXT," +
                    "NGAYSUA TEXT," + // THÊM CÁI NÀY VÀO
                    "CUOCGOI INTEGER," +
                    "CUOCHOP INTEGER," +
                    "FOREIGN KEY(GIAOCHO) REFERENCES NHANVIEN(ID) ON DELETE SET NULL" +
                    ");";
}
