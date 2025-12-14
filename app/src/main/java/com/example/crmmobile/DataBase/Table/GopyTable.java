package com.example.crmmobile.DataBase.Table;

public class GopyTable {
    public static final String TABLE_NAME = "GOPY";
    public static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + " (" +
                    "ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "NGUOILIENHE INTEGER," +
                    "DONHANG INTEGER," +
                    "RATING INTEGER," +
                    "COMMENT TEXT," +
                    "GIAOCHO INTEGER," +
                    "FOREIGN KEY(NGUOILIENHE) REFERENCES CONTACT(ID) ON DELETE SET NULL," +
                    "FOREIGN KEY(DONHANG) REFERENCES DONHANG(ID) ON DELETE SET NULL," +
                    "FOREIGN KEY(GIAOCHO) REFERENCES NHANVIEN(ID) ON DELETE SET NULL" +
                    ");";
}
