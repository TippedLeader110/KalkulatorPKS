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

public class Hitung02_inti_kotoran extends AppCompatActivity {

    Button hitung;
    EditText berat, nkoh, mlkoh;
    TextView hasil, tberat, tnkoh, tmlkoh, ket;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hitung02_alb_air_kotoran);

        String keterangan = "";

        ActionBar actbar;
        actbar = getSupportActionBar();
        actbar.setHomeButtonEnabled(true);
        actbar.setTitle("Kadar Kotoran pada Inti");
        actbar.setDisplayHomeAsUpEnabled(true);

        berat = findViewById(R.id.hitung02_p_berat_contoh);
        tberat = findViewById(R.id.hitung02_berat_contoh);
        nkoh = findViewById(R.id.hitung02_p_nkoh);
        tnkoh = findViewById(R.id.hitung02_nkoh);
        mlkoh = findViewById(R.id.hitung02_p_mlkoh);
        tmlkoh = findViewById(R.id.hitung02_mlkoh);
        ket = findViewById(R.id.hitung02_keterangan);

        ket.setText(keterangan);
        tberat.setText("Berat Kotoran");
        tnkoh.setText("Berat contoh");

        berat.setHint("%");
        nkoh.setHint("%");

        tmlkoh.setVisibility(View.GONE);
        mlkoh.setVisibility(View.GONE);

        hitung = findViewById(R.id.hitung02_btn_hitung);

        hasil = findViewById(R.id.hitung02_hasil_r);

        hitung.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                float a = Float.valueOf(berat.getText().toString());
                float b = Float.valueOf(nkoh.getText().toString());
                float fhasil;

                fhasil = a/b;

                hasil.setText(Float.toString(fhasil) + "%");

            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){
            case android.R.id.home:
                finish();
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}