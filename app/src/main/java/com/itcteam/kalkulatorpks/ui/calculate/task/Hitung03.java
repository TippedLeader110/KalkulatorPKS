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
import com.itcteam.kalkulatorpks.ui.calculate.task.task.Hitung03_kernel;
import com.itcteam.kalkulatorpks.ui.calculate.task.task.Hitung03_minyak;
import com.itcteam.kalkulatorpks.ui.calculate.task.task.Hitung03_usb;
import com.itcteam.kalkulatorpks.ui.calculate.task.task.Hitung03_usf;

public class Hitung03 extends AppCompatActivity {

    Button usb, usf, minyak, kernel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hitung03);

        ActionBar actbar;
        actbar = getSupportActionBar();
        actbar.setHomeButtonEnabled(true);
        actbar.setTitle(R.string.cal_03);
        actbar.setDisplayHomeAsUpEnabled(true);

        usb = findViewById(R.id.hitung03_usb);
        usb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Hitung03.this, Hitung03_usb.class);
                startActivity(intent);
            }
        });

        usf = findViewById(R.id.hitung03_usf);
        usf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Hitung03.this, Hitung03_usf.class);
                startActivity(intent);
            }
        });

        minyak = findViewById(R.id.hitung03_minyak);
        minyak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Hitung03.this, Hitung03_minyak.class);
                startActivity(intent);
            }
        });

        kernel = findViewById(R.id.hitung03_kernel);
        kernel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Hitung03.this, Hitung03_kernel.class);
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