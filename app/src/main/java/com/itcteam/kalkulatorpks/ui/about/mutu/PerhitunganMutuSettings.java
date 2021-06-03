package com.itcteam.kalkulatorpks.ui.about.mutu;

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
    static int tipe = 2;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preference);
        settingsFragment = new MTPrefSet();
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.pref_menu, settingsFragment, null)
                .commit();
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle("Export Rendemen");
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

        String lineCSV =  "Tanggal,Nama Kebun,CPO,Inti,Storage\n";
        List<String > arrayCSV = new ArrayList<>();
        arrayCSV.add(lineCSV);

        for (HashMap<String, String> hash : dataRecord){
            try {
                JSONObject jsonRecord = new JSONObject(databaseHandler.getRecordValue(hash.get("id_record"), tipe));
                JSONObject jsonItem = new JSONObject(databaseHandler.getItemValue(hash.get("id_record")));

                String lines = hash.get("date")+ "," +
                        jsonItem.get("nama")+ "," +
                        jsonRecord.getString("cpo")+ "," +
                        jsonRecord.getString("inti")+ "," +
                        jsonRecord.getString("storage");

                arrayCSV.add(lines+"\n");

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        ExportCSV exportCSV = new ExportCSV(arrayCSV, this);

        if (dialog.isShowing())
            dialog.dismiss();

        if (exportCSV.DoExportCSV("RendemenALL")){
            Toast.makeText(this, "CSV Berhasil disimpan !!!", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(this, "Terjadi kesalahan !!!", Toast.LENGTH_SHORT).show();
        }


    }
}
