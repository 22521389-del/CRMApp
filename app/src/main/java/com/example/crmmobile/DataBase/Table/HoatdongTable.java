package com.example.crmmobile.DataBase.Table;

public class HoatdongTable {
    public static final String TABLE_NAME = "HOATDONG";
    public static final String CREATE_TABLE =
            "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " (" +
                    "ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "TENHOATDONG TEXT," +
                    "THOIGIANBATDAU TEXT," +
                    "THOIGIANKETTHUC TEXT," +
                    "TINHTRANG TEXT," +
                    "KHACHHANG TEXT," +
                    "NHANVIEN INTEGER," +
                    "TOCHUC TEXT," +
                    "NGUOILIENHE INTEGER," +
                    "LEAD INTEGER," +
                    "LIENQUANTOI TEXT," +
                    "MOTA TEXT," +
                    "GIAOCHO INTEGER," +
                    "FOREIGN KEY(NHANVIEN) REFERENCES NHANVIEN(ID) ON DELETE SET NULL," +
                    "FOREIGN KEY(NGUOILIENHE) REFERENCES CONTACT(ID) ON DELETE SET NULL," +
                    "FOREIGN KEY(LEAD) REFERENCES LEAD(ID) ON DELETE SET NULL," +
                    "FOREIGN KEY(GIAOCHO) REFERENCES NHANVIEN(ID) ON DELETE SET NULL" +
                    ");";
}
