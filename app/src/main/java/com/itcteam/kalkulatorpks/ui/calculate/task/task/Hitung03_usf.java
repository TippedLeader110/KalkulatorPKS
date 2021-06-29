package com.itcteam.kalkulatorpks.ui.calculate.task.task;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.itcteam.kalkulatorpks.R;

import java.util.ArrayList;

public class Hitung03_usf extends Hitung03_kernel {

    public Hitung03_usf() {

        super.dropdownSample = new ArrayList<String>();

        super.dropdownSample.add("USF");

        super.tooltip_title = "Norma Max 0.70%";
        super.tooltip_text = tooltip_title;
        super.title = "USF";
        super.inp1 = "Berat buah yang dilepaskan (gr)";
        super.inp2 = "Berat sampel janjangan (gr)";
        super.backtipe = "usf";
        super.tipe = 2;
    }

    public void setRumus() {
        super.rumus.setBackgroundResource(R.drawable.l_usf);
    }

}