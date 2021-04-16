package com.itcteam.kalkulatorpks.ui.calculate.task.task;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import com.itcteam.kalkulatorpks.R;

public class Hitung01_hasil extends AppCompatActivity {

    ActionBar actbar;
    TextView tangkos, serat, cangkang, inti, cpo, tbs, dirt;
    TextView ptangkos, pserat, pcangkang, pinti, pcpo, pdirt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hitung01_hasil);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        actbar = getSupportActionBar();
        actbar.setHomeButtonEnabled(true);
        actbar.setTitle(R.string.cal_02);
        actbar.setDisplayHomeAsUpEnabled(true);

        Bundle extras = getIntent().getExtras();

        tbs = findViewById(R.id.beratTBS_manual);
        tbs.setText(extras.getString("tbsHasil"));

        tangkos = findViewById(R.id.tangkos_hsl_manual);
        ptangkos = findViewById(R.id.tangkos_p_manual);
        tangkos.setText(extras.getString("tangkosHasil"));
        ptangkos.setText(extras.getString("tangkosHasilp"));

        serat = findViewById(R.id.serat_hsl_manual);
        pserat = findViewById(R.id.serat_p_manual);
        serat.setText(extras.getString("seratHasil"));
        pserat.setText(extras.getString("seratHasilp"));

        cangkang = findViewById(R.id.cangkang_hsl_manual);
        pcangkang = findViewById(R.id.cangkang_p_manual);
        cangkang.setText(extras.getString("cangkangHasil"));
        pcangkang.setText(extras.getString("cangkangHasilp"));

        inti = findViewById(R.id.inti_hsl_manual);
        pinti = findViewById(R.id.inti_p_manual);
        inti.setText(extras.getString("intiHasil"));
        pinti.setText(extras.getString("intiHasilp"));

        cpo = findViewById(R.id.cpo_hsl_manual);
        pcpo = findViewById(R.id.cpo_p_manual);
        cpo.setText(extras.getString("cpoHasil"));
        pcpo.setText(extras.getString("cpoHasilp"));

        dirt = findViewById(R.id.dirt_hasil);
        pdirt = findViewById(R.id.dirt_p);
        dirt.setText(extras.getString("dirtHasil"));
        pdirt.setText(extras.getString("dirtHasilp"));

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