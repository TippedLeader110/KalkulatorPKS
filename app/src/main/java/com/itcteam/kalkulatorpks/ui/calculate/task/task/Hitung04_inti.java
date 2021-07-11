package com.itcteam.kalkulatorpks.ui.calculate.task.task;

import com.itcteam.kalkulatorpks.R;

public class Hitung04_inti extends Hitung04_cpo {

    public Hitung04_inti() {
        super.title = "Rendemen Inti";
        super.inp1 = "Berat TBS yang diolah (kg)";
        super.inp2 = "Berat inti yang dihasilkan (kg)";
        super.tipe = "inti";
        super.resultCode = 2;
    }

    public void setRumus() {
        rumus.setBackgroundResource(R.drawable.r_inti);
    }

}