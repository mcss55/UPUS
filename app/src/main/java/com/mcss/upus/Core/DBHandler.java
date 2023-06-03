package com.mcss.upus.Core;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DBHandler extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "uppus_db";

    public DBHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create the tables
        db.execSQL("CREATE TABLE AZ (resource TEXT PRIMARY KEY, value TEXT);");
        db.execSQL("CREATE TABLE EN (resource TEXT PRIMARY KEY, value TEXT);");
        db.execSQL("CREATE TABLE RU (resource TEXT PRIMARY KEY, value TEXT);");
    }

    public void addDataToAZTable(String key, String value) {
        Log.d("TABLE", "addDataToAZTable: ");
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("resource", key);
        values.put("value", value);
        db.insert("AZ", null, values);
        db.close();
    }

    public HashMap<String, String> readAllData(String lang){
        SQLiteDatabase db = this.getReadableDatabase();
        HashMap<String,String> data = new HashMap<>();
        Cursor cursor = db.rawQuery("SELECT * FROM "+lang, null);
        if (cursor.moveToFirst()) {
            do {
                data.put(cursor.getString(0),cursor.getString(1));
            }while (cursor.moveToNext());
        }
        cursor.close();
        return data;

    }

    /*public HashMap<String, List<String>> readAllData(){

        SQLiteDatabase db = this.getReadableDatabase();
        HashMap<String,List<String>> data = new HashMap<>();

        Cursor cursor = db.rawQuery("SELECT * FROM AZ", null);
        if (cursor.moveToFirst()) {
            List<String> values = new ArrayList<>();
            do {
                values.add(cursor.getString(1));
            }while (cursor.moveToNext());
            data.put(cursor.getString(0),values);
        }
        cursor.close();

        Cursor cursor2 = db.rawQuery("SELECT * FROM EN", null);
        if (cursor2.moveToFirst()) {
            List<String> values = new ArrayList<>();
            do {
                values.add(cursor2.getString(1));
            }while (cursor2.moveToNext());
            data.put(cursor2.getString(0),values);
        }
        cursor2.close();

        Cursor cursor3 = db.rawQuery("SELECT * FROM RU", null);
        if (cursor3.moveToFirst()) {
            List<String> values = new ArrayList<>();
            do {
                values.add(cursor3.getString(1));
            }while (cursor3.moveToNext());
            data.put(cursor3.getString(0),values);
        }
        cursor3.close();

        return data;
    }*/

    public void addDataToENTable(String key, String value) {
        Log.d("TABLE", "addDataToENTable: ");
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("resource", key);
        values.put("value", value);
        db.insert("EN", null, values);
        db.close();
    }

    public void addDataToRUTable(String key, String value) {
        Log.d("TABLE", "addDataToRUTable: ");
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("resource", key);
        values.put("value", value);
        db.insert("RU", null, values);
        db.close();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop the tables if they exist and create them again
        db.execSQL("DROP TABLE IF EXISTS AZ;");
        db.execSQL("DROP TABLE IF EXISTS EN;");
        db.execSQL("DROP TABLE IF EXISTS RU;");
        onCreate(db);
    }
}
