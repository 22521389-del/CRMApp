package com.example.crmmobile.DataBase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.crmmobile.MainDirectory.Recent;

import java.util.ArrayList;
import java.util.List;

public class RecentRepository {
    private static final String TAG = "RECENT";
    DBCRMHandler dbHelper;

    public RecentRepository(Context context){
        dbHelper = new DBCRMHandler(context);
    }

    public long addRecent(Recent item_recent){
        SQLiteDatabase db = this.dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put("OBJECT_TYPE", item_recent.getObjectType());
        values.put("NAME", item_recent.getName());
        values.put("ACCESS_TIME", item_recent.getTime());
        values.put("OBJECT_ID", item_recent.getObjectID());

        //không câp nhật khi trùng OBJECT và ID
        int ItemChange = db.update("RECENT", values, "OBJECT_ID = ? AND OBJECT_TYPE = ?",
                new String[]{String.valueOf(item_recent.getObjectID()), item_recent.getObjectType()});

        long newId;
        if (ItemChange == 0){
            newId = db.insert("RECENT", null, values);
        }else {
            newId = ItemChange;
        }

        db.close();
        return  newId;
    }

    public List<Recent> getAllRecent(){
        List<Recent> list = new ArrayList<>();
        SQLiteDatabase db = this.dbHelper.getReadableDatabase();
        String sql =
                "SELECT * FROM RECENT ORDER BY ACCESS_TIME DESC LIMIT 10";

        Cursor cursor = db.rawQuery(sql, null );

        if(cursor.moveToFirst()){
            do {
                Recent item = new Recent();
                item.setObjectType(cursor.getString(cursor.getColumnIndexOrThrow("OBJECT_TYPE")));
                item.setName(cursor.getString(cursor.getColumnIndexOrThrow("NAME")));
                item.setTime(cursor.getLong(cursor.getColumnIndexOrThrow("ACCESS_TIME")));
                item.setObjectID(cursor.getInt(cursor.getColumnIndexOrThrow("OBJECT_ID")));

                list.add(item);
            }while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        Log.e(TAG, "COUNT = " + cursor.getCount());

        return list;
    }

    public void insert_and_updateRecent(Recent recent){
        SQLiteDatabase db = this.dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put("OBJECT_TYPE", recent.getObjectType());
        values.put("NAME", recent.getName());
        values.put("ACCESS_TIME", recent.getTime());
        values.put("OBJECT_ID", recent.getObjectID());

        db.insertWithOnConflict(
                "RECENT",
                null,
                values,
                SQLiteDatabase.CONFLICT_REPLACE
        );
        db.close();
    }
}
