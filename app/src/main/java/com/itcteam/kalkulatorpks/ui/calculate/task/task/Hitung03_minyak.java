package com.itcteam.kalkulatorpks.ui.calculate.task.task;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.itcteam.kalkulatorpks.R;
import com.itcteam.kalkulatorpks.ui.calculate.task.Hitung03;

import java.util.ArrayList;

public class Hitung03_minyak extends Hitung03_kernel {

    public Hitung03_minyak() {

        super.dropdownSample = new ArrayList<String>();

        super.dropdownSample.add("Tangkos");
        super.dropdownSample.add("Ampas Press");
        super.dropdownSample.add("Solid Decanter");
        super.dropdownSample.add("Drap Akhir");
        super.dropdownSample.add("Biji");

        super.title = "Minyak";
        super.inp1 = "Hasil minyak ekstraksi (gr)";
        super.inp2 = "Berat sampel (gr)";
        super.tooltip_text = "- Tangkos 1,5% - 1,8% \n" +
                "- Ampas Press 4,50% \n" +
                "- Solid Decanter 2,50% \n" +
                "- Drab Akhir 0,60% \n" +
                "- Biji 0,80%";
        super.tooltip_title = "Norma Max";
        super.backtipe = "minyak";
        super.tipe = 3;
    }

}