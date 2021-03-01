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

public class Hitung02_inti_air extends Hitung02_alb {

    public Hitung02_inti_air() {
        String title = "Kadar air pada inti";
        String ket = "Keterangan :\n" +
                "A: Berat contoh sebelum dikeringkan\n" +
                "B: Berat contoh sesudah dikeringkan\n" +
                "C: Berat contoh";
        super.setAll(title, ket, "A (gr)", "B (gr)", "C (gr)", true);
    }

    @Override
    public void setRumusDrawable() {
        super.formula.setBackgroundResource(R.drawable.cpo_air);
    }

    @Override
    public void kalkulasi() {
        ambilnilai();
        float fhasil;

        fhasil = finput01 - finput02;
        fhasil = fhasil / Float.valueOf(finput03);
        fhasil *= 100;

        super.hasil.setText(Float.toString(fhasil) + "%");
    }

}