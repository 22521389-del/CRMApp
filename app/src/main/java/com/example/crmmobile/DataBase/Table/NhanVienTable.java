package com.example.crmmobile.DataBase.Table;

public class NhanVienTable {
    public static final String TABLE_NAME = "NHANVIEN";
    public static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME +" (" +
                    "ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "HOTEN TEXT," +
                    "EMAIL TEXT," +
                    "DIENTHOAI TEXT," +
                    "CHUCVU TEXT," +
                    "BOPHAN TEXT," +
                    "TAIKHOAN TEXT," +
                    "MATKHAU TEXT," +
                    "ROLE TEXT," +
                    "TRANGTHAI TEXT," +
                    "NGAYVAOLAM TEXT," +
                    "NGAYNGHIVIEC TEXT," +
                    "MOTA TEXT" +
                    ");";
}
