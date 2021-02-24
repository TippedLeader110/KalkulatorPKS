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

public class Hitung02_alb extends AppCompatActivity {

    Button hitung;
    EditText berat, nkoh, mlkoh;
    TextView hasil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hitung02_alb_air_kotoran);

        ActionBar actbar;
        actbar = getSupportActionBar();
        actbar.setHomeButtonEnabled(true);
        actbar.setTitle(R.string.alb_cpo);
        actbar.setDisplayHomeAsUpEnabled(true);

        berat = findViewById(R.id.hitung02_p_berat_contoh);
        nkoh = findViewById(R.id.hitung02_p_nkoh);
        mlkoh = findViewById(R.id.hitung02_p_mlkoh);

        hitung = findViewById(R.id.hitung02_btn_hitung);

        hasil = findViewById(R.id.hitung02_hasil_r);

        hitung.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                float fberat = Float.valueOf(berat.getText().toString());
                float fnkoh = Float.valueOf(nkoh.getText().toString());
                float fmlkoh = Float.valueOf(mlkoh.getText().toString());
                float fhasil;

                fhasil = fmlkoh * fnkoh;
                fhasil = fhasil * Float.valueOf("25.6");
                fhasil = fhasil/fberat;

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