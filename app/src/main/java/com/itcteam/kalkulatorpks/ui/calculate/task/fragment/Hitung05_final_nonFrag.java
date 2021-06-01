package com.itcteam.kalkulatorpks.ui.calculate.task.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.itcteam.kalkulatorpks.R;

public class Hitung05_final_nonFrag extends AppCompatActivity {

    public Hitung05_final_nonFrag() {
        super(R.layout.fragment_hitung05_final);
    }

    TextView hasil, rumus;
    Button back, save;
    String hasils;

    @Override
    protected void onCreate(@Nullable  Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_hitung05_final);
        hasil = findViewById(R.id.hitung05_final_hasil);
        rumus = findViewById(R.id.hitung05_final_rumus2);
        final Bundle bundle = getIntent().getExtras();

        if (bundle == null) {
            Log.w("Bunnde", "Empty");
        } else {
            Float fhasil = Float.valueOf(bundle.getString("AV"))*Float.valueOf(bundle.getString("PR"));
            fhasil = fhasil*Float.valueOf(bundle.getString("QU"));
            fhasil *= 100;
            hasils = fhasil + "%";
            rumus.setText("OEE = " + bundle.getString("AV").toString() + "% x " + bundle.getString("PR").toString() + "%" +
                    " x " + bundle.getString("QU").toString() + "%");
            hasil.setText("OEE = " + hasils);
        }

        back = findViewById(R.id.hitung05_final_retry);
        save = findViewById(R.id.hitung05_save);
        back.setVisibility(View.GONE);

        save.setVisibility(View.GONE);
    }
}
