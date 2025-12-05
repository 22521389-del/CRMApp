package com.example.crmmobile.DataBase;

import android.app.Application;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.crmmobile.DataBase.DatabaseSeeder;

public class CRMApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        initializeDatabase();
    }

    private void initializeDatabase() {
        DBCRMHandler DBCRMHandler = new DBCRMHandler(this);
        SQLiteDatabase db = DBCRMHandler.getWritableDatabase();

        // Kiểm tra nếu database trống thì seed data
        if (isDatabaseEmpty(db)) {
            DatabaseSeeder.seedAllData(db);
        }
        db.close();
    }

    private boolean isDatabaseEmpty(SQLiteDatabase db) {
        Cursor cursor = db.rawQuery("SELECT COUNT(*) FROM NHANVIEN", null);
        cursor.moveToFirst();
        int count = cursor.getInt(0);
        cursor.close();
        return count == 0;
    }
}