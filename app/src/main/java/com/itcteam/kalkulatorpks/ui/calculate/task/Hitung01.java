package com.itcteam.kalkulatorpks.ui.calculate.task;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.itcteam.kalkulatorpks.R;
import com.itcteam.kalkulatorpks.ui.calculate.task.fragment.Cal_01_manual;
import com.itcteam.kalkulatorpks.ui.calculate.task.fragment.Cal_01_standart;

public class Hitung01 extends AppCompatActivity {
    Button standart, manual;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hitung01);

        standart = findViewById(R.id.cal_01_std_btn);
        manual = findViewById(R.id.cal_01_manual_btn);

        standart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragment_m(1);
            }
        });

        manual.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragment_m(2);
            }
        });

        ActionBar actbar;
        actbar = getSupportActionBar();
        actbar.setHomeButtonEnabled(true);
        actbar.setTitle(R.string.cal_01);
        actbar.setDisplayHomeAsUpEnabled(true);

        if(savedInstanceState == null){
            iconDisabled(2);
            getSupportFragmentManager().beginTransaction().
                    setReorderingAllowed(true).
                    add(R.id.fragment_cal01, Cal_01_standart.class, null).
                    commit();
        }

    }

    void fragment_m(int ch){
        if (ch==1){
            iconDisabled(2);
            getSupportFragmentManager().beginTransaction().
                    replace(R.id.fragment_cal01, Cal_01_standart.class, null).
                    setReorderingAllowed(true).
                    commit();
        }else{
            iconDisabled(1);
            getSupportFragmentManager().beginTransaction().
                    replace(R.id.fragment_cal01, Cal_01_manual.class, null).
                    setReorderingAllowed(true).
                    commit();
        }
    }

    public void iconDisabled(int i){
        standart.setEnabled(true);
        manual.setEnabled(true);
        if (i==1){
            manual.setEnabled(false);
        }else{
            standart.setEnabled(false);
        }
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