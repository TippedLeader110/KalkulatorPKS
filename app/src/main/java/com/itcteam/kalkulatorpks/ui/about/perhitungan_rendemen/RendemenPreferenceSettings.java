package com.itcteam.kalkulatorpks.ui.about.perhitungan_rendemen;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.itcteam.kalkulatorpks.R;
import com.itcteam.kalkulatorpks.db.DatabaseHandler;
import com.itcteam.kalkulatorpks.ui.about.ExportCSV;
import com.itcteam.kalkulatorpks.ui.about.material_balance.MBPrefSet;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class RendemenPreferenceSettings extends AppCompatActivity implements PRPrefSet.callBack {

    DatabaseHandler databaseHandler;
    ProgressDialog dialog;
    private Context myContext;
    MBPrefSet settingsFragment;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preference);
        settingsFragment = new MBPrefSet();
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.pref_menu, settingsFragment, null)
                .commit();
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        dialog = new ProgressDialog(this);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public boolean backProc(){
        exportAll();
        return true;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void exportAll() {

        databaseHandler = new DatabaseHandler(this);
        List<HashMap<String, String >> dataRecord = new ArrayList(databaseHandler.getRecord(1));

        String lineCSV =  "Tanggal,Nama Kebun,Faksi Matang,Tahun Tanam,TBS,Tangkos Hasil,tangkos Hasil Persen,Serat Hasil,Serat Hasil Persen,Cangkang Hasil,Cangkang Hasil Persen,Inti Hasil,Inti Hasil Peresn,Cpo Hasil,Cpo Hasil Persen,Dirt Hasil,Dirt Hasil Persen\n";
        List<String > arrayCSV = new ArrayList<>();
        arrayCSV.add(lineCSV);

        for (HashMap<String, String> hash : dataRecord){
            try {
                JSONObject jsonRecord = new JSONObject(databaseHandler.getRecordValue(hash.get("id_record"), 1));
                JSONObject jsonItem = new JSONObject(databaseHandler.getItemValue(hash.get("id_record")));

                String lines = hash.get("date")+ "," +
                        jsonItem.get("nama")+ "," +
                        jsonItem.get("matang")+ "," +
                        jsonItem.get("tanam")+ "," +
                        jsonRecord.getString("tbs")+ "," +
                        jsonRecord.getString("tangkosHasil")+ "," +
                        jsonRecord.getString("tangkosHasilp")+ "," +
                        jsonRecord.getString("seratHasil")+ "," +
                        jsonRecord.getString("seratHasilp")+ "," +
                        jsonRecord.getString("cangkangHasil")+ "," +
                        jsonRecord.getString("cangkangHasilp")+ "," +
                        jsonRecord.getString("intiHasil")+ "," +
                        jsonRecord.getString("intiHasilp")+ "," +
                        jsonRecord.getString("cpoHasil")+ "," +
                        jsonRecord.getString("cpoHasilp")+ "," +
                        jsonRecord.getString("dirtHasil")+ "," +
                        jsonRecord.getString("dirtHasilp");

                arrayCSV.add(lines+"\n");

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        ExportCSV exportCSV = new ExportCSV(arrayCSV, this);

        if (dialog.isShowing())
            dialog.dismiss();

        if (exportCSV.DoExportCSV("MaterialBalanceALL")){
            Toast.makeText(this, "CSV Berhasil disimpan !!!", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(this, "Terjadi kesalahan !!!", Toast.LENGTH_SHORT).show();
        }


    }
}
