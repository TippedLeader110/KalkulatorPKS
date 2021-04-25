package com.itcteam.kalkulatorpks.ui.calculate.task;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.itcteam.kalkulatorpks.R;
import com.itcteam.kalkulatorpks.db.DatabaseHandler;
import com.itcteam.kalkulatorpks.ui.calculate.task.task.Hitung01_hasil;

import java.util.HashMap;

public class Hitung01 extends AppCompatActivity {

    Button save_persen, reset_persen, hitung;
    TextInputLayout tangkos, serat, cangkang, inti, cpo, tbs, dirt;
    DatabaseHandler databaseHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hitung01_combined_new);
        databaseHandler = new DatabaseHandler(this);

        tbs = this.findViewById(R.id.hitung01_input01);
        tangkos = this.findViewById(R.id.hitung01_input02);
        serat = this.findViewById(R.id.hitung01_input03);
        cangkang = this.findViewById(R.id.hitung01_input04);
        inti = this.findViewById(R.id.hitung01_input05);
        cpo = this.findViewById(R.id.hitung01_input06);
        dirt = this.findViewById(R.id.hitung01_input07);

        ActionBar actbar;
        actbar = getSupportActionBar();
        actbar.setHomeButtonEnabled(true);
        actbar.setTitle(R.string.cal_01);
        actbar.setDisplayHomeAsUpEnabled(true);
        getSavedPersenan();

        save_persen = this.findViewById(R.id.hitung01_btn_save_persen);

        save_persen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (databaseHandler.savePersen01(getValue())){
                    Toast.makeText(Hitung01.this, "Berhasil disimpan", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(Hitung01.this, "Gagal disimpan", Toast.LENGTH_SHORT).show();
                }
            }
        });

        reset_persen = this.findViewById(R.id.hitung01_btn_reset);

        reset_persen.setText("Reset Persen");

        reset_persen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tangkos.getEditText().setText("");
                serat.getEditText().setText("");
                cangkang.getEditText().setText("");
                inti.getEditText().setText("");
                cpo.getEditText().setText("");
                dirt.getEditText().setText("");
            }
        });

        hitung = this.findViewById(R.id.hitung01_btn_hitung);

        hitung.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hitungPersen();
            }
        });

    }

    public void hitungPersen(){
        Intent intent = new Intent(Hitung01.this, Hitung01_hasil.class);

        intent.putExtra("tbs", tbs.getEditText().getText().toString());

        Float tangkosHasil =  Float.valueOf(tangkos.getEditText().getText().toString())/100;
        tangkosHasil = tangkosHasil*Float.valueOf(tbs.getEditText().getText().toString());
        Log.w("TEST", String.valueOf(tangkosHasil));
        intent.putExtra("tangkosHasil", Float.toString(tangkosHasil));
        intent.putExtra("tangkosHasilp", tangkos.getEditText().getText().toString());
        Float seratHasil =  Float.valueOf(serat.getEditText().getText().toString())/100;
        seratHasil = seratHasil*Float.valueOf(tbs.getEditText().getText().toString());
        intent.putExtra("seratHasil", Float.toString(seratHasil));
        intent.putExtra("seratHasilp", serat.getEditText().getText().toString());
        Float cangkangHasil =  Float.valueOf(cangkang.getEditText().getText().toString())/100;
        cangkangHasil = cangkangHasil*Float.valueOf(tbs.getEditText().getText().toString());
        intent.putExtra("cangkangHasil", Float.toString(cangkangHasil));
        intent.putExtra("cangkangHasilp", cangkang.getEditText().getText().toString());
        Float intiHasil =  Float.valueOf(inti.getEditText().getText().toString())/100;
        intiHasil = intiHasil*Float.valueOf(tbs.getEditText().getText().toString());
        intent.putExtra("intiHasil", Float.toString(intiHasil));
        intent.putExtra("intiHasilp", inti.getEditText().getText().toString());
        Float cpoHasil =  Float.valueOf(cpo.getEditText().getText().toString())/100;
        cpoHasil = cpoHasil*Float.valueOf(tbs.getEditText().getText().toString());
        intent.putExtra("cpoHasil", Float.toString(cpoHasil));
        intent.putExtra("cpoHasilp", cpo.getEditText().getText().toString());
        Float dirtHasil =  Float.valueOf(dirt.getEditText().getText().toString())/100;
        dirtHasil = dirtHasil*Float.valueOf(tbs.getEditText().getText().toString());
        intent.putExtra("dirtHasil", Float.toString(dirtHasil));
        intent.putExtra("dirtHasilp", dirt.getEditText().getText().toString());
        intent.putExtra("hide", "no");
        startActivity(intent);
    }

    private void getSavedPersenan() {
        HashMap<String, String> gData = new HashMap<String, String>();
        gData = databaseHandler.getPersen01();
        if (gData.get("error").equals("false")){
            tangkos.getEditText().setText(gData.get("tangkos"));;
            serat.getEditText().setText(gData.get("serat"));;
            cangkang.getEditText().setText(gData.get("cangkang"));;
            inti.getEditText().setText(gData.get("inti"));;
            cpo.getEditText().setText(gData.get("cpo"));;
            dirt.getEditText().setText(gData.get("dirt"));;
        }else{
            Toast.makeText(this, "Gagal mengambil nilai tersimpan !!", Toast.LENGTH_SHORT).show();
        }
    }

    public HashMap<String, String> getValue(){
        HashMap<String, String> nilai = new HashMap<String, String>();
        if(tbs.getEditText().getText().toString().equals("")){
            tbs.getEditText().setText("0");
        }
        nilai.put("tbs", tbs.getEditText().getText().toString());

        if(tangkos.getEditText().getText().toString().equals("")){
            tangkos.getEditText().setText("0");
        }
        nilai.put("tangkos", tangkos.getEditText().getText().toString());

        if(serat.getEditText().getText().toString().equals("")){
           serat.getEditText().setText("0");
        }
        nilai.put("serat", serat.getEditText().getText().toString());

        if(cangkang.getEditText().getText().toString().equals("")){
            cangkang.getEditText().setText("0");
        }
        nilai.put("cangkang", cangkang.getEditText().getText().toString());

        if(inti.getEditText().getText().toString().equals("")){
            inti.getEditText().setText("0");
        }
        nilai.put("inti", inti.getEditText().getText().toString());

        if(cpo.getEditText().getText().toString().equals("")){
            cpo.getEditText().setText("0");
        }
        nilai.put("cpo", cpo.getEditText().getText().toString());

        if(dirt.getEditText().getText().toString().equals("")){
            dirt.getEditText().setText("0");
        }
        nilai.put("dirt", dirt.getEditText().getText().toString());

        return nilai;
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