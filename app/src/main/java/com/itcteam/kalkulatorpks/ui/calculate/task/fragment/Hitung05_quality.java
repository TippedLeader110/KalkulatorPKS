package com.itcteam.kalkulatorpks.ui.calculate.task.fragment;

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
        Float k1 = Float.valueOf(input01.getEditText().getText().toString());
        Float k2 = Float.valueOf(input02.getEditText().getText().toString());
        Float k3 = Float.valueOf(input03.getEditText().getText().toString());
        value = k1-k2;
        value = value/k3;

        super.kirimBalik.dataKirimBalik(value, 3);
    }
}
