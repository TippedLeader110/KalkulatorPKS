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

public class Hitung03_usf extends AppCompatActivity {
    
    TextView tooltip, input01, input02, hasil;
    EditText einput01, einput02;
    Button hitung;
    String tooltip_text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hitung03_sub);
        
        tooltip = findViewById(R.id.hitung03_input);
        tooltip_text = "Norma Max 0,70%";
        tooltip.setText(tooltip_text);

        input01 = findViewById(R.id.hitung03_input01);
        input01.setText("Berat buah yang dilepaskan");

        input02 = findViewById(R.id.hitung03_input02);
        input02.setText("Berat sampel janjangan");

        hasil = findViewById(R.id.hitung03_hasil_r);

        einput01 = findViewById(R.id.hitung03_p_input01);
        einput01.setHint("gr");

        einput02 = findViewById(R.id.hitung03_p_input02);
        einput02.setHint("gr");

        hitung = findViewById(R.id.hitung03_btn_hitung);

        hitung.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                float a = Float.valueOf(einput01.getText().toString());
                float b = Float.valueOf(einput02.getText().toString());
                float hasil_p;

                hasil_p = a/b;

                hasil.setText(Float.toString(hasil_p) + "%");
            }
        });

        ActionBar actbar;
        actbar = getSupportActionBar();
        actbar.setHomeButtonEnabled(true);
        actbar.setTitle("USF");
        actbar.setDisplayHomeAsUpEnabled(true);
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