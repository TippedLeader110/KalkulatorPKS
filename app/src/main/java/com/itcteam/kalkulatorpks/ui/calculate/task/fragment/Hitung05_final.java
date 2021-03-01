package com.itcteam.kalkulatorpks.ui.calculate.task.fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.itcteam.kalkulatorpks.R;

public class Hitung05_final extends Fragment {

    public Hitung05_final() {
        super(R.layout.fragment_hitung05_final);
    }

    TextView hasil;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Float fhasil = Float.valueOf(savedInstanceState.getString("hasil"));
        fhasil *= 100;
        String hasil = fhasil + "%";

    }
}
