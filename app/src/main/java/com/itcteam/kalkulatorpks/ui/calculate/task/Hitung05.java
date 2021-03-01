package com.itcteam.kalkulatorpks.ui.calculate.task;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.itcteam.kalkulatorpks.R;
import com.itcteam.kalkulatorpks.ui.calculate.task.fragment.Hitung01_manual;
import com.itcteam.kalkulatorpks.ui.calculate.task.fragment.Hitung01_standart;
import com.itcteam.kalkulatorpks.ui.calculate.task.fragment.Hitung05_availability;
import com.itcteam.kalkulatorpks.ui.calculate.task.fragment.Hitung05_final;
import com.itcteam.kalkulatorpks.ui.calculate.task.fragment.Hitung05_perfomance;
import com.itcteam.kalkulatorpks.ui.calculate.task.fragment.Hitung05_quality;

public class Hitung05 extends AppCompatActivity implements  Hitung05_availability.passingData{

    Float availability, perfomance, quality;

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
    }

    @Override
    public void dataKirimBalik(Float value, Integer type) {
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

            bundle.putString("hasil",Float.toString(hasil));
            getSupportFragmentManager().beginTransaction().
                    replace(R.id.hitung05_fragment, Hitung05_final.class, bundle).
                    setReorderingAllowed(true).
                    commit();
        }
    }
}