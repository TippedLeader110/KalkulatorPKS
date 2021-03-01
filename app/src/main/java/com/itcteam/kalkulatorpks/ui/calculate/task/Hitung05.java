package com.itcteam.kalkulatorpks.ui.calculate.task;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import com.itcteam.kalkulatorpks.R;
import com.itcteam.kalkulatorpks.ui.calculate.task.fragment.Hitung01_manual;
import com.itcteam.kalkulatorpks.ui.calculate.task.fragment.Hitung01_standart;
import com.itcteam.kalkulatorpks.ui.calculate.task.fragment.Hitung05_availability;
import com.itcteam.kalkulatorpks.ui.calculate.task.fragment.Hitung05_final;
import com.itcteam.kalkulatorpks.ui.calculate.task.fragment.Hitung05_perfomance;
import com.itcteam.kalkulatorpks.ui.calculate.task.fragment.Hitung05_quality;

public class Hitung05 extends AppCompatActivity implements  Hitung05_availability.passingData, Hitung05_final.KirimBalik{

    Float availability, perfomance, quality;
    ActionBar actbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hitung05);

        if(savedInstanceState == null){
            getSupportFragmentManager().beginTransaction().
                    setReorderingAllowed(true).
                    add(R.id.hitung05_fragment, Hitung05_availability.class, null).
                    commit();
        }

        actbar = getSupportActionBar();
        actbar.setHomeButtonEnabled(true);
        actbar.setTitle(R.string.cal_05);
        actbar.setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public void dataKirimBalik(Float val, Integer type) {
        float value;
        Log.w("Value", Float.toString(val));
        Log.w("Type", Integer.toString(type));
        if (Float.isNaN(val)){
            value = 0;
        }else{
            value = val;
        }

        if (type==1){
            availability = value;
            fragment_m(2);
        }else if(type==2){
            perfomance = value;
            fragment_m(3);
        }else{
            quality = value;
            fragment_m(4);
        }
    }

    void fragment_m(int ch){
        Bundle bundle = new Bundle();
        if (ch==1){
            getSupportFragmentManager().beginTransaction().
                    replace(R.id.hitung05_fragment, Hitung05_availability.class, null).
                    setReorderingAllowed(true).
                    commit();
        }else if(ch==2){
            getSupportFragmentManager().beginTransaction().
                    replace(R.id.hitung05_fragment, Hitung05_perfomance.class, null).
                    setReorderingAllowed(true).
                    commit();
        }else if(ch==3){
            getSupportFragmentManager().beginTransaction().
                    replace(R.id.hitung05_fragment, Hitung05_quality.class, null).
                    setReorderingAllowed(true).
                    commit();
        }else{

            Float hasil = availability*perfomance*quality;
            String shasil;
            if (Float.isNaN(hasil)){
                shasil = "0";
            }else{
                shasil = Float.toString(hasil);
            }

            Log.e("Hasil", shasil);
            bundle.putString("HASIL", shasil);
            bundle.putString("AV", Float.toString(availability*100));
            bundle.putString("PR", Float.toString(perfomance*100));
            bundle.putString("QU", Float.toString(quality*100));

            Log.w("Bundder PRE", bundle.get("HASIL").toString());

            Hitung05_final fragment = new Hitung05_final();
            fragment.setArguments(bundle);
            getSupportFragmentManager().beginTransaction().
                    replace(R.id.hitung05_fragment,fragment).
                    setReorderingAllowed(true).
                    commit();
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

    @Override
    public void retryKalkulasi() {
        fragment_m(1);
    }
}