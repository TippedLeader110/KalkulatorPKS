package com.itcteam.kalkulatorpks.ui.calculate.task;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.itcteam.kalkulatorpks.R;
import com.itcteam.kalkulatorpks.ui.calculate.task.task.Hitung02_alb;
import com.itcteam.kalkulatorpks.ui.calculate.task.task.Hitung02_cpo_air;
import com.itcteam.kalkulatorpks.ui.calculate.task.task.Hitung02_cpo_kotoran;
import com.itcteam.kalkulatorpks.ui.calculate.task.task.Hitung02_dobi;
import com.itcteam.kalkulatorpks.ui.calculate.task.task.Hitung02_inti_air;
import com.itcteam.kalkulatorpks.ui.calculate.task.task.Hitung02_inti_kotoran;

public class Hitung02 extends AppCompatActivity {

    Button alb, dobi, cpo_air, cpo_kotoran, inti_air, inti_kotoran;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hitung02);

        ActionBar actbar;
        actbar = getSupportActionBar();
        actbar.setHomeButtonEnabled(true);
        actbar.setTitle(R.string.cal_02);
        actbar.setDisplayHomeAsUpEnabled(true);

        alb = findViewById(R.id.cal02_alb);
        alb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Hitung02.this, Hitung02_alb.class);
                startActivity(intent);;
            }
        });

        dobi = findViewById(R.id.cal02_dobi);
        dobi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Hitung02.this, Hitung02_dobi.class);
                startActivity(intent);;
            }
        });

        cpo_air = findViewById(R.id.cal02_kadar_air);
        cpo_air.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Hitung02.this, Hitung02_cpo_air.class);
                startActivity(intent);;
            }
        });

        cpo_kotoran = findViewById(R.id.cal02_kadar_kotoran);
        cpo_kotoran.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Hitung02.this, Hitung02_cpo_kotoran.class);
                startActivity(intent);;
            }
        });

        inti_air = findViewById(R.id.hitung02_inti_air);
        inti_air.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Hitung02.this, Hitung02_inti_air.class);
                startActivity(intent);
            }
        });
        
        inti_kotoran = findViewById(R.id.hitung02_inti_kotoran);
        inti_kotoran.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Hitung02.this, Hitung02_inti_kotoran.class);
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