package com.example.crmmobile.DataBase.Table;

public class CompanyTable {
    public static final String TABLE_NAME = "COMPANY";
    public static final String CREATE_TABLE =
            "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " (" +
                    "ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "TENCONGTY TEXT," +
                    "NGANHNGHE TEXT," +
                    "DIENTHOAI TEXT," +
                    "EMAIL TEXT," +
                    "DIACHI TEXT," +
                    "TRANGTHAI TEXT," +
                    "NGAYTHANHLAP TEXT," +
                    "GIAOCHO INTEGER," +
                    "FOREIGN KEY(GIAOCHO) REFERENCES NHANVIEN(ID) ON DELETE SET NULL" +
                    ");";
}
