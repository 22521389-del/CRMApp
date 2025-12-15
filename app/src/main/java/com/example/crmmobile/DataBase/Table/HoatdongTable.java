package com.example.crmmobile.DataBase.Table;

public class HoatdongTable {
    public static final String TABLE_NAME = "HOATDONG";
    public static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + " (" +
                    "ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "TENHOATDONG TEXT," +
                    "THOIGIANBATDAU TEXT," +
                    "THOIGIANKETTHUC TEXT," +
                    "NGAYBATDAU TEXT," +
                    "TINHTRANG TEXT," +
                    "NHANVIEN INTEGER," +
                    "TOCHUC INTEGER," +
                    "NGUOILIENHE INTEGER," +
                    "COHOI INT," +
                    "MOTA TEXT," +
                    "GIAOCHO INTEGER," +
                    "FOREIGN KEY(NHANVIEN) REFERENCES NHANVIEN(ID) ON DELETE SET NULL," +
                    "FOREIGN KEY(TOCHUC) REFERENCES CONGTY(ID) ON DELETE SET NULL," +
                    "FOREIGN KEY(NGUOILIENHE) REFERENCES CONTACT(ID) ON DELETE SET NULL," +
                    "FOREIGN KEY(COHOI) REFERENCES LEADCOHOI(ID) ON DELETE SET NULL," +
                    "FOREIGN KEY(GIAOCHO) REFERENCES NHANVIEN(ID) ON DELETE SET NULL" +
                    ");";
}