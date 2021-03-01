package com.itcteam.kalkulatorpks.ui.calculate.task.fragment;

import android.util.Log;

public class Hitung05_quality extends Hitung05_availability {

    @Override
    public void setAll() {
        third = true;
        inp1 = "Proccess amount";
        inp2 = "Defect amount";
        inp3 = "Processes amount";
        titles = "Quality time";
        btn = "Next";
    }

    @Override
    public void kalkulasi(){
        ambilnilai();
        value = k1-k2;
        value = value/k3;

        super.kirimBalik.dataKirimBalik(value, 3);
    }
}
