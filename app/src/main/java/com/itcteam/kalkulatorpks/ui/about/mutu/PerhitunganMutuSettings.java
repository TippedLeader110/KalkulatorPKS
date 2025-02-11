package com.itcteam.kalkulatorpks.ui.about.mutu;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.itcteam.kalkulatorpks.R;
import com.itcteam.kalkulatorpks.ui.about.ExportCSV;
import com.itcteam.kalkulatorpks.ui.about.perhitungan_rendemen.PRPrefSet;
import com.itcteam.kalkulatorpks.util.DatabaseHandler;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class PerhitunganMutuSettings extends AppCompatActivity implements MTPrefSet.callBack {

    DatabaseHandler databaseHandler;
    ProgressDialog dialog;
    private Context myContext;
    MTPrefSet settingsFragment;
    int tipe = 2;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preference);
        Bundle extras = getIntent().getExtras();
        this.tipe = Integer.valueOf(extras.getString("tipe"));
        Log.w("Tipe Settings", String.valueOf(tipe));

        settingsFragment = new MTPrefSet(tipe);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.pref_menu, settingsFragment, null)
                .commit();
        ActionBar actionBar = getSupportActionBar();

        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle("Export Perhitungan Mutu");
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
        List<HashMap<String, String >> dataRecord = new ArrayList(databaseHandler.getRecord(tipe));
        String lineCSV;
        if (tipe==2){
            lineCSV =  "Tanggal,Nama Mesin/Alat,ALB,CPO Air,CPO Kotoran,DOBI\n";
        }else{
            lineCSV =  "Tanggal,Nama Mesin/Alat,Inti Air,Inti Kotoran\n";
        }
        List<String > arrayCSV = new ArrayList<>();
        arrayCSV.add(lineCSV);

        for (HashMap<String, String> hash : dataRecord){
            try {
                JSONObject jsonRecord = new JSONObject(databaseHandler.getRecordValue(hash.get("id_record"), tipe));
                JSONObject jsonItem = new JSONObject(databaseHandler.getItemValue(hash.get("id_record")));

                String lines;

                if (tipe==2){
                    lines = hash.get("date")+ "," +
                            jsonItem.get("nama")+ "," +
                            jsonRecord.getString("cpo_alb")+ "," +
                            jsonRecord.getString("cpo_air")+ "," +
                            jsonRecord.getString("cpo_kotoran")+ "," +
                            jsonRecord.getString("cpo_dobi");
                }else{
                    lines = hash.get("date")+ "," +
                            jsonItem.get("nama")+ "," +
                            jsonRecord.getString("inti_air")+ "," +
                            jsonRecord.getString("inti_kotoran");
                }

                arrayCSV.add(lines+"\n");

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        ExportCSV exportCSV = new ExportCSV(arrayCSV, this);

        if (dialog.isShowing())
            dialog.dismiss();
        String tt;
        if (tipe==2){
            tt = "MutuCPOALL";
        }else{
            tt = "MutuIntiALl";
        }

        if (exportCSV.DoExportCSV(tt)){
            Toast.makeText(this, "CSV Berhasil disimpan !!!", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(this, "Terjadi kesalahan !!!", Toast.LENGTH_SHORT).show();
        }


    }
}
