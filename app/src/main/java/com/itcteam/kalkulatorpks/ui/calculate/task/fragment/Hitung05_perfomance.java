package com.itcteam.kalkulatorpks.ui.calculate.task.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.textfield.TextInputLayout;
import com.itcteam.kalkulatorpks.R;

public class Hitung05_perfomance extends Hitung05_availability {

    @Override
    public void setAll() {
        third = true;
        inp1 = "Loading time";
        inp2 = "Downtime losses";
        inp3 = "Operating time";
        titles = "Perfomance rate";
        btn = "Next";
    }

    @Override
    public void kalkulasi(){
        ambilnilai();
        value = k1*k2;
        value = value/k3;

        super.kirimBalik.dataKirimBalik(value, 2);
    }
}
