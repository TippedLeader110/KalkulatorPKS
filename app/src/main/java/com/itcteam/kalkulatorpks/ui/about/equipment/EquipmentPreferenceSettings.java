package com.itcteam.kalkulatorpks.ui.about.equipment;

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
import com.itcteam.kalkulatorpks.util.DatabaseHandler;
import com.itcteam.kalkulatorpks.ui.about.ExportCSV;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class EquipmentPreferenceSettings extends AppCompatActivity implements EQPrefSet.callBack {

    DatabaseHandler databaseHandler;
    ProgressDialog dialog;
    private Context myContext;
    EQPrefSet settingsFragment;
    static int tipe = 5;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preference);
        settingsFragment = new EQPrefSet();
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.pref_menu, settingsFragment, null)
                .commit();
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle("Export OEE");
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

        String lineCSV =  "Tanggal,Nama Mesin/Alat,Perfomance,Availability,Quality,OEE(%)\n";
        List<String > arrayCSV = new ArrayList<>();
        arrayCSV.add(lineCSV);

        for (HashMap<String, String> hash : dataRecord){
            try {
                JSONObject jsonRecord = new JSONObject(databaseHandler.getRecordValue(hash.get("id_record"), tipe));
                JSONObject jsonItem = new JSONObject(databaseHandler.getItemValue(hash.get("id_record")));
                Float perfomance = Float.parseFloat(jsonRecord.getString("perfomance"));
                Float availability = Float.parseFloat(jsonRecord.getString("availability"));
                Float quality = Float.parseFloat(jsonRecord.getString("quality"));
                Float hasil = perfomance*availability*quality;
                hasil *= 100;

                String hasilJumlah = Float.toString(hasil);

                String lines = hash.get("date")+ "," +
                        jsonItem.get("nama")+ "," +
                        jsonRecord.getString("perfomance")+ "," +
                        jsonRecord.getString("availability")+ "," +
                        jsonRecord.getString("quality")+ "," +
                        jsonRecord.getString("oee");

                arrayCSV.add(lines+"\n");

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        ExportCSV exportCSV = new ExportCSV(arrayCSV, this);

        if (dialog.isShowing())
            dialog.dismiss();

        if (exportCSV.DoExportCSV("OEEALL")){
            Toast.makeText(this, "CSV Berhasil disimpan !!!", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(this, "Terjadi kesalahan !!!", Toast.LENGTH_SHORT).show();
        }


    }
}
