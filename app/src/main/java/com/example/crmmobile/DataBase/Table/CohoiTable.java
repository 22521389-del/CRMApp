package com.example.crmmobile.DataBase.Table;

public class CohoiTable {
    public static final String TABLE_NAME = "COHOI";
    public static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + " (" +
                    "ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "TENCOHOI TEXT," +
                    "CONGTY INTEGER," +
                    "NGUOILIENHE INTEGER," +
                    "GIATRI REAL," +
                    "BUOCBANHANG TEXT," +
                    "MOTA TEXT," +
                    "GIAOCHO INTEGER," +
                    "NGAYTAO TEXT," +
                    "NGAYCHOT TEXT," +
                    "FOREIGN KEY(CONGTY) REFERENCES COMPANY(ID) ON DELETE SET NULL," +
                    "FOREIGN KEY(NGUOILIENHE) REFERENCES CONTACT(ID) ON DELETE SET NULL," +
                    "FOREIGN KEY(GIAOCHO) REFERENCES NHANVIEN(ID) ON DELETE SET NULL" +
                    ");";
}
