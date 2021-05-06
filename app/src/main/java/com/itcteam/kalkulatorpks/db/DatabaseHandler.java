package com.itcteam.kalkulatorpks.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.itcteam.kalkulatorpks.ui.about.ExportCSV;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DatabaseHandler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "saveapp";
    private static final String TABLE_01 = "calculate01";
    private static final String TABLE_RECORD_VALUE = "save_record";
    private static final String TABLE_RECORD = "record_name";
    private static final String TABLE_ITEM_NAME = "item_name";
    private static final String TABLE_ITEM_VALUE = "record_value";

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
                " id_record INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL , record_type INTEGER, date DATE)";
        Log.w("Bentuk Query", CREATE_RECORD);
        db.execSQL(CREATE_RECORD);

        String CREATE_VALUE = " CREATE TABLE " + TABLE_ITEM_VALUE + "( " +
                "id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL , id_record int, item_value text)";
        Log.w("Bentuk Query", CREATE_VALUE);
        db.execSQL(CREATE_VALUE);

        String CREATE_SAVE = " CREATE TABLE " + TABLE_RECORD_VALUE + "( " +
                "id  INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL , id_record int, record_value text)";
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

    public long SaveRecord(String date, Integer record_type){

        ContentValues contentValues= new ContentValues();
        contentValues.put("date", date);
        contentValues.put("record_type", record_type);
        Long record = Long.valueOf(-1);

        db = this.getWritableDatabase();
        Long ret = db.insert(TABLE_RECORD, null, contentValues);
        db.close();
        if (ret!=-1){
            record = ret;
        }

        return record;
    }

    public boolean SaveRecordValue(String data, int IDRecord){

        ContentValues contentValues= new ContentValues();
        contentValues.put("record_value", data);
        contentValues.put("id_record", IDRecord);

        db = this.getWritableDatabase();
        Long ret = db.insert(TABLE_RECORD_VALUE, null, contentValues);
        db.close();
        if (ret!=-1){
            return true;
        }else {
            return false;
        }
    }

    public boolean SaveItem(String data, int IDRecord){

        ContentValues contentValues= new ContentValues();
        contentValues.put("item_value", data);
        contentValues.put("id_record", IDRecord);

        db = this.getWritableDatabase();
        Long ret = db.insert(TABLE_ITEM_VALUE, null, contentValues);
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

    public List getRecord(int i) {
        List list = new ArrayList();
        db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery( "select * from "+TABLE_RECORD+ " where record_type = " + i, null );

        // Material balance
        while (cursor.moveToNext()){
            HashMap<String,String> berkasObj = new HashMap<>();
            berkasObj.put("id_record",Integer.toString(cursor.getInt(cursor.getColumnIndex("id_record"))));
            berkasObj.put("date",cursor.getString(cursor.getColumnIndex("date")));
            list.add(berkasObj);
        }

        cursor.close();
        db.close();
        return list;
    }

    public String getRecordValue(String id_record, int tipe) {
        db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery( "select * from "+ TABLE_RECORD_VALUE + " where id_record = " + id_record, null );
        HashMap<String, Object> list = new HashMap<>();
        // Material balance
        if(cursor.moveToFirst()){
            String rec = cursor.getString(cursor.getColumnIndex("record_value"));
            if (tipe==1){
                try {
                    JSONObject js = new JSONObject(rec);
                    list.put("tbs", js.getString("tbs"));
                    list.put("tangkosHasil", js.getString("tangkosHasil"));
                    list.put("tangkosHasilp", js.getString("tangkosHasilp"));
                    list.put("seratHasil", js.getString("seratHasil"));
                    list.put("seratHasilp", js.getString("seratHasilp"));
                    list.put("cangkangHasil", js.getString("cangkangHasil"));
                    list.put("cangkangHasilp", js.getString("cangkangHasilp"));
                    list.put("intiHasil", js.getString("intiHasil"));
                    list.put("intiHasilp", js.getString("intiHasilp"));
                    list.put("cpoHasil", js.getString("cpoHasil"));
                    list.put("cpoHasilp", js.getString("cpoHasilp"));
                    list.put("dirtHasil", js.getString("dirtHasil"));
                    list.put("dirtHasilp", js.getString("dirtHasilp"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

        }

        cursor.close();
        db.close();
        return list.toString();
    }

    public String getItemValue(String id_record) {
        db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery( "select * from "+ TABLE_ITEM_VALUE + " where id_record = " + id_record, null );
        String list = "";
        // Material balance
        if(cursor.moveToFirst()){
            String rec = cursor.getString(cursor.getColumnIndex("item_value"));
            list = rec;
        }
        cursor.close();
        db.close();
        Log.w("LIST", list);
        return list;
    }

    public List getRecordFilter(int i, String firstDate, String endDate) {
        List list = new ArrayList();
        db = this.getReadableDatabase();
//        Log.w("DATE", "First Date = " + firstDate + "("+ firstDate.equals("25-4-2021") +") dan End Date = "+ endDate);
//        Cursor cursor = db.rawQuery( "select * from "+TABLE_RECORD+ " where record_type = " + i + " " +
//                "AND date BETWEEN " + firstDate + " AND " + endDate, null );
        Cursor cursor = db.rawQuery( "select * from "+TABLE_RECORD+ " where record_type = " + i + " " +
                "AND date BETWEEN '"+ firstDate +"' AND '"+ endDate + "'", null );
        Log.w("DB QUERY FILTER", "select * from "+TABLE_RECORD+ " where record_type = " + i + " " +
                "AND date BETWEEN '" + firstDate + "' AND '" + endDate + "'");

        // Material balance

        while (cursor.moveToNext()){
            HashMap<String,String> berkasObj = new HashMap<>();
            berkasObj.put("id_record",Integer.toString(cursor.getInt(cursor.getColumnIndex("id_record"))));
            berkasObj.put("date",cursor.getString(cursor.getColumnIndex("date")));
            list.add(berkasObj);
        }

        cursor.close();
        db.close();
        return list;
    }

    public List getAllFilter(String first, String end, int i) {

        List<HashMap<String, String>> list = new ArrayList<>(getRecordFilter(i, first, end));
        String linesC = "";
        if (i==1){
            linesC =  "Tanggal,Nama Kebun,Faksi Matang,Tahun Tanam,TBS,Tangkos Hasil,tangkos Hasil Persen,Serat Hasil,Serat Hasil Persen,Cangkang Hasil,Cangkang Hasil Persen,Inti Hasil,Inti Hasil Peresn,Cpo Hasil,Cpo Hasil Persen,Dirt Hasil,Dirt Hasil Persen\n";
        }else if(i==4){
            linesC =  "Tanggal,Nama Kebun,CPO,Inti,Storage\n";
        }

        List dataLines = new ArrayList<String>();

        for (HashMap<String,String> hash:list){
            String fetchData = getRecordValue(hash.get("id_record"), i);
            String fetchDataItem = getItemValue(hash.get("id_record"));
            if (i==1){
                try {
                    JSONObject jsonBerkas = new JSONObject(fetchData);
                    JSONObject value = new JSONObject(fetchDataItem);
                    String lines = hash.get("date")+ "," +
                            value.getString("nama")+ "," +
                            value.getString("matang")+ "," +
                            value.getString("tanam")+ "," +
                            jsonBerkas.getString("tbs")+ "," +
                            jsonBerkas.getString("tangkosHasil")+ "," +
                            jsonBerkas.getString("tangkosHasilp")+ "," +
                            jsonBerkas.getString("seratHasil")+ "," +
                            jsonBerkas.getString("seratHasilp")+ "," +
                            jsonBerkas.getString("cangkangHasil")+ "," +
                            jsonBerkas.getString("cangkangHasilp")+ "," +
                            jsonBerkas.getString("intiHasil")+ "," +
                            jsonBerkas.getString("intiHasilp")+ "," +
                            jsonBerkas.getString("cpoHasil")+ "," +
                            jsonBerkas.getString("cpoHasilp")+ "," +
                            jsonBerkas.getString("dirtHasil")+ "," +
                            jsonBerkas.getString("dirtHasilp");

                    Log.w("Lines", lines);
                    Log.w("LinesC", linesC);
                    dataLines.add(linesC);
                    dataLines.add(lines);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }else if(i==4){
                try {
                    JSONObject jsonBerkas = new JSONObject(fetchData);
                    JSONObject value = new JSONObject(fetchDataItem);
                    String lines = hash.get("date")+ "," +
                            value.getString("nama")+ "," +
                            jsonBerkas.getString("cpo")+ "," +
                            jsonBerkas.getString("init")+ "," +
                            jsonBerkas.getString("storage");

                    Log.w("Lines", lines);
                    Log.w("LinesC", linesC);
                    dataLines.add(linesC);
                    dataLines.add(lines);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
        return dataLines;
    }

    private List getRecordAll(int i) {
        List list = new ArrayList();
        db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery( "select * from "+TABLE_RECORD, null );

        // Material balance
        if (i==1){
            while (cursor.moveToNext()){
                HashMap<String,String> berkasObj = new HashMap<>();
                berkasObj.put("id_record",Integer.toString(cursor.getInt(cursor.getColumnIndex("id_record"))));
                berkasObj.put("date",cursor.getString(cursor.getColumnIndex("date")));
                list.add(berkasObj);
            }
        }

        cursor.close();
        db.close();
        return list;
    }
}
