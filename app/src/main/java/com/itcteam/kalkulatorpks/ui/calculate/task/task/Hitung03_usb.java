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

public class Hitung03_usb extends Hitung03_kernel {

    public Hitung03_usb() {

        super.dropdownSample = new ArrayList<String>();

        super.dropdownSample.add("Norma Max");

        super.tooltip_title = "Norma Max 2%";
        super.tooltip_text = tooltip_title;
        super.title = "USB";
        super.inp1 = "Jumlah janjangan ada buah";
        super.inp2 = "Jumlah sampel";
        super.backtipe = "usb";
        super.tipe = 1;
    }

}