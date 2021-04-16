package com.itcteam.kalkulatorpks.ui.calculate.task.task;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.itcteam.kalkulatorpks.R;
import com.itcteam.kalkulatorpks.db.DatabaseHandler;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Hitung01_hasil extends AppCompatActivity {

    ActionBar actbar;
    TextView tangkos, serat, cangkang, inti, cpo, tbs, dirt;
    TextView ptangkos, pserat, pcangkang, pinti, pcpo, pdirt;
    Button simpan;
    JSONObject jsonVal;
    DatabaseHandler databaseHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hitung01_hasil);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        databaseHandler = new DatabaseHandler(this);
        jsonVal = new JSONObject();
        actbar = getSupportActionBar();
        actbar.setHomeButtonEnabled(true);
        actbar.setTitle(R.string.cal_02);
        actbar.setDisplayHomeAsUpEnabled(true);

        Bundle extras = getIntent().getExtras();

        simpan = this.findViewById(R.id.hitung01_save_hasil);

        tbs = findViewById(R.id.beratTBS_manual);
        tbs.setText(extras.getString("tbs")+" KG");



        tangkos = findViewById(R.id.tangkos_hsl_manual);
        ptangkos = findViewById(R.id.tangkos_p_manual);
        tangkos.setText(extras.getString("tangkosHasil")+" KG");
        ptangkos.setText(extras.getString("tangkosHasilp")+"% x " + extras.getString("tbs") + " KG = ");

        serat = findViewById(R.id.serat_hsl_manual);
        pserat = findViewById(R.id.serat_p_manual);
        serat.setText(extras.getString("seratHasil")+" KG");
        pserat.setText(extras.getString("seratHasilp")+"% x " + extras.getString("tbs") + " KG = ");

        cangkang = findViewById(R.id.cangkang_hsl_manual);
        pcangkang = findViewById(R.id.cangkang_p_manual);
        cangkang.setText(extras.getString("cangkangHasil")+" KG");
        pcangkang.setText(extras.getString("cangkangHasilp")+"% x " + extras.getString("tbs") + " KG = ");

        inti = findViewById(R.id.inti_hsl_manual);
        pinti = findViewById(R.id.inti_p_manual);
        inti.setText(extras.getString("intiHasil")+" KG");
        pinti.setText(extras.getString("intiHasilp")+"% x " + extras.getString("tbs") + " KG = ");

        cpo = findViewById(R.id.cpo_hsl_manual);
        pcpo = findViewById(R.id.cpo_p_manual);
        cpo.setText(extras.getString("cpoHasil")+" KG");
        pcpo.setText(extras.getString("cpoHasilp")+"% x " + extras.getString("tbs") + " KG = ");

        dirt = findViewById(R.id.dirt_hasil);
        pdirt = findViewById(R.id.dirt_p);
        dirt.setText(extras.getString("dirtHasil")+" KG");
        pdirt.setText(extras.getString("dirtHasilp")+"% x " + extras.getString("tbs") + " KG = ");


        try {
            jsonVal.put("tbs", extras.getString("tbs"));
            jsonVal.put("tangkosHasil", extras.getString("tangkosHasil"));
            jsonVal.put("tangkosHasilp", extras.getString("tangkosHasilp"));
            jsonVal.put("seratHasil", extras.getString("seratHasil"));
            jsonVal.put("seratHasilp", extras.getString("seratHasilp"));
            jsonVal.put("cangkangHasil", extras.getString("cangkangHasil"));
            jsonVal.put("cangkangHasilp", extras.getString("cangkangHasilp"));
            jsonVal.put("intiHasil", extras.getString("intiHasil"));
            jsonVal.put("intiHasilp", extras.getString("intiHasilp"));
            jsonVal.put("cpoHasil", extras.getString("cpoHasil"));
            jsonVal.put("cpoHasilp", extras.getString("cpoHasilp"));
            jsonVal.put("dirtHasil", extras.getString("dirtHasil"));
            jsonVal.put("dirtHasilp", extras.getString("dirtHasilp"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        simpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(Hitung01_hasil.this, jsonVal.toString(), Toast.LENGTH_SHORT).show();
                Log.d("JsonVal", jsonVal.toString());

                Intent intent = new Intent(Hitung01_hasil.this, Hitung01_simpan.class);
                intent.putExtra("jsonVal", jsonVal.toString());
                startActivity(intent);

            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){
            case android.R.id.home:
                finish();
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}