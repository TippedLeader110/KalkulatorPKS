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

public class Hitung03_usb extends AppCompatActivity {
    
    TextView tooltip, input01, input02, hasil;
    EditText einput01, einput02;
    Button hitung;
    String tooltip_text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hitung03_sub);
        
        tooltip = findViewById(R.id.hitung03_input);
        tooltip.setText("Norma Max 2%");

        input01 = findViewById(R.id.hitung03_input01);
        input01.setText("Jumlah janjangan ada buah");

        input02 = findViewById(R.id.hitung03_input02);
        input02.setText("Jumlah sampel");
        
        hasil = findViewById(R.id.hitung03_hasil_r);

        einput01 = findViewById(R.id.hitung03_p_input01);
        tooltip_text = "Masukan jumlah";
        einput01.setHint(tooltip_text);

        einput02 = findViewById(R.id.hitung03_p_input02);
        einput02.setHint("Masukan jumlah");

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
        actbar.setTitle("USB");
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