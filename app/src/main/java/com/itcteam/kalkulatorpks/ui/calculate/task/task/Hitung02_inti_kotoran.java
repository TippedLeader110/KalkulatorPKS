package com.itcteam.kalkulatorpks.ui.calculate.task.task;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.itcteam.kalkulatorpks.R;

public class Hitung02_inti_kotoran extends Hitung02_alb {

    public Hitung02_inti_kotoran() {
        String title = "Kadar kotoran pada inti";
        String ket = "";
        super.setAll(title, ket, "Berat Kotoran (%)", "Berat Contoh (%)", "C (gr)", false);
    }

    @Override
    public void setRumusDrawable() {
        super.formula.setBackgroundResource(R.drawable.inti_kotoran);
    }

    @Override
    public void kalkulasi() {
        ambilnilai();
        float fhasil;

        fhasil = finput01 / finput02;
        fhasil *= 100;

        super.hasil.setText(Float.toString(fhasil) + "%");
    }

}