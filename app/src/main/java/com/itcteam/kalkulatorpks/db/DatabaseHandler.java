package com.itcteam.kalkulatorpks.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.HashMap;

public class DatabaseHandler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "saveapp";
    private static final String TABLE_01 = "calculate01";
    private static final String TABLE_SAVE = "save_record";
    private static final String TABLE_RECORD = "record_name";
    private static final String TABLE_ITEM_NAME = "item_name";

    SQLiteDatabase db;

    public DatabaseHandler(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        db = this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_01 = " CREATE TABLE " + TABLE_01 + "( " +
                " tangkos text, serat text, cangkang text, inti text, cpo text, dirt text )";
        Log.w("Bentuk Query", CREATE_01);
        db.execSQL(CREATE_01);

        String CREATE_RECORD = " CREATE TABLE " + TABLE_RECORD + "( " +
                " id_record INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL , date text)";
        Log.w("Bentuk Query", CREATE_RECORD);
        db.execSQL(CREATE_RECORD);

        String CREATE_ITEM = " CREATE TABLE " + TABLE_ITEM_NAME + "( " +
                " id_item int, item_name text)";
        Log.w("Bentuk Query", CREATE_ITEM);
        db.execSQL(CREATE_ITEM);

        String CREATE_SAVE = " CREATE TABLE " + TABLE_SAVE + "( " +
                "id  INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL , id_record int, id_item int, item_value text)";
        Log.w("Bentuk Query", CREATE_SAVE);
        db.execSQL(CREATE_SAVE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_01);
        onCreate(db);
    }

    public boolean checkRow(){
        db = this.getReadableDatabase();
        Cursor mCursor = db.rawQuery("SELECT * FROM " + TABLE_01, null);
        if (mCursor.moveToFirst())
        {
            Log.w("checkRow","Ada row");
            mCursor.close();
            db.close();
            return true;
        }
        else
        {
            Log.w("checkRow","Tidak Ada row");
            mCursor.close();
            db.close();
            return false;
        }
    }

    public HashMap<String, String> getPersen01(){
        HashMap<String, String> data = new HashMap<String, String>();
        if (checkRow()){
            db = getReadableDatabase();
            Cursor res = db.rawQuery( "select * from "+TABLE_01, null );
            res.moveToFirst();
            data.put("error", "false");
            data.put("tangkos", res.getString(res.getColumnIndex("tangkos")));
            data.put("serat", res.getString(res.getColumnIndex("serat")));
            data.put("cangkang", res.getString(res.getColumnIndex("cangkang")));
            data.put("inti", res.getString(res.getColumnIndex("inti")));
            data.put("cpo", res.getString(res.getColumnIndex("cpo")));
            data.put("dirt", res.getString(res.getColumnIndex("dirt")));
            res.close();
            db.close();
            return data;
        }else{
            data.put("error", "false");
            return data;
        }

    }

    public long SaveRecord(String date){

        ContentValues contentValues= new ContentValues();
        contentValues.put("date", date);
        Long record = Long.valueOf(-1);

        db = this.getWritableDatabase();
        Long ret = db.insert(TABLE_RECORD, null, contentValues);
        db.close();
        if (ret!=-1){
            record = ret;
        }

        return record;
    }

    public boolean SaveItem(String data, int IDRecord, int IDItem){

        ContentValues contentValues= new ContentValues();
        contentValues.put("item_value", data);
        contentValues.put("id_item", IDItem);
        contentValues.put("id_record", IDRecord);

        db = this.getWritableDatabase();
        Long ret = db.insert(TABLE_SAVE, null, contentValues);
        db.close();
        if (ret!=-1){
            return true;
        }else {
            return false;
        }
    }

    public boolean savePersen01(HashMap<String, String> data){
        boolean done = false;
        ContentValues contentValues = new ContentValues();
        contentValues.put("tangkos", data.get("tangkos"));
        contentValues.put("serat", data.get("serat"));
        contentValues.put("cangkang", data.get("cangkang"));
        contentValues.put("inti", data.get("inti"));
        contentValues.put("cpo", data.get("cpo"));
        contentValues.put("dirt", data.get("dirt"));


        if (!checkRow()){
            db = this.getWritableDatabase();
            Long ret = db.insert(TABLE_01, null, contentValues);
            db.close();
            if (ret!=-1){
                done =  true;
            }else{
                done = false;
            }
        }else{
            db = this.getWritableDatabase();
            Integer ret = db.update(TABLE_01, contentValues, null, null);
            db.close();
            if (ret>0)
                done =  true;
            else{
                done =  false;
            }
        }
        return done;
    }

}
