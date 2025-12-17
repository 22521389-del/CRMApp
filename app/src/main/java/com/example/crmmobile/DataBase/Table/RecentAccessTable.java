package com.example.crmmobile.DataBase.Table;

public class RecentAccessTable {
    public static final String TABLE_NAME = "RECENT";
    public static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + " (" +
                    "ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "OBJECT_TYPE TEXT," +
                    "NAME TEXT," +
                    "OBJECT_ID INTEGER," +
                    "ACCESS_TIME INTEGER" +
                    ");";
}
