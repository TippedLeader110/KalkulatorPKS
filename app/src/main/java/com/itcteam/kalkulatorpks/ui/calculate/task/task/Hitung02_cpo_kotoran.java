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

public class Hitung02_cpo_kotoran extends Hitung02_alb {

    public Hitung02_cpo_kotoran() {
        String title = "Kadar kotoran pada CPO";
        String ket = "Keterangan :\n" +
                "A: Berat contoh (gr)\n" +
                "B: Berat kertas saring sesudah pengeringan\n" +
                "C: Berat kertas saring + kotoran";
        super.setAll(title, ket, "A (gr)", "B (gr)", "C (gr)", true);
    }

    @Override
    public void setRumusDrawable() {
        super.formula.setBackgroundResource(R.drawable.cpo_kotoran);
    }

    @Override
    public void kalkulasi() {
        float finput01 = Float.valueOf(super.input01.getEditText().getText().toString());
        float finput02 = Float.valueOf(super.input02.getEditText().getText().toString());
        float finput03 = Float.valueOf(super.input03.getEditText().getText().toString());
        float fhasil;

        fhasil = finput03 - finput02;
        fhasil = fhasil / Float.valueOf(finput01);
        fhasil *= 100;

        super.hasil.setText(Float.toString(fhasil) + "%");
    }

}