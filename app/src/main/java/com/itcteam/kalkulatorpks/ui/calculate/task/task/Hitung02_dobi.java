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

public class Hitung02_dobi extends Hitung02_alb {

    public Hitung02_dobi() {
        String title = "Dobi pada CPO";
        String ket = "Keterangan :\n" +
                "Ab: Absorbens contoh pada\n" +
                "       gelombang 446nm\n" +
                "As: Absorbens contoh pada\n" +
                "       gelombang 269nm";
        super.setAll(title, ket, "Ab (nm)", "As(nm)", "", false);
    }

    @Override
    public void setRumusDrawable() {
        super.formula.setBackgroundResource(R.drawable.dobi);
    }

    @Override
    public void kalkulasi() {
        float finput01 = Float.valueOf(super.input01.getEditText().getText().toString());
        float finput02 = Float.valueOf(super.input02.getEditText().getText().toString());
//        float finput03 = Float.valueOf(super.input03.getEditText().getText().toString());
        float fhasil;

        fhasil = finput01 / finput02;
        fhasil *= 100;

        super.hasil.setText(Float.toString(fhasil) + "%");
    }

}