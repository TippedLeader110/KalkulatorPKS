package com.itcteam.kalkulatorpks.ui.calculate.task.task;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputLayout;
import com.itcteam.kalkulatorpks.R;

public class Hitung03_kernel_old extends AppCompatActivity {

    TextView tooltip, hasil;
    public float hasil_p;
    Button hitung;
    FloatingActionButton save;
    String tooltip_text, title, inp1, inp2, tooltip_title;
    Context contex;
    TextInputLayout input01, input02;
    float  a, b;
    int tipe;
    String backtipe;
    InputMethodManager inputManager;

    public Hitung03_kernel_old(){
        this.tooltip_text = "- LTDS I 2% \n" +
                "- LTDS II 2% \n" +
                "- Fiber Cyclone 2% \n" +
                "- Hydrocyclone 4%";
        this.title = "Kernel";
        this.inp1 = "Hasil Losses Kernel (gr)";
        this.inp2 = "Berat sampel (gr)";
        this.tooltip_title = "Norma Max";
        this.backtipe = "kernel";
        this.tipe = 4;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        inputManager = (InputMethodManager)
                getSystemService(Context.INPUT_METHOD_SERVICE);
        contex = this;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hitung03_sub);
        
        tooltip = findViewById(R.id.hitung03_input);

        tooltip.setText(tooltip_title);
        tooltip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(contex, tooltip_text, Toast.LENGTH_LONG).show();
            }
        });

        input01 = findViewById(R.id.hitung03_input01);
        input01.setHint(inp1);

        input02 = findViewById(R.id.hitung03_input02);
        input02.setHint(inp2);

        hasil = findViewById(R.id.hitung03_hasil_r);

        hitung = findViewById(R.id.hitung03_btn_hitung);

        hitung.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Hitung();
            }
        });

        save = this.findViewById(R.id.hitung03_save);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(contex, backtipe + " : " + hasil_p, Toast.LENGTH_SHORT).show();
                Intent intent = new Intent();
                intent.putExtra(backtipe, String.valueOf(hasil_p));
                setResult(tipe, intent);
                finish();
            }
        });

        ActionBar actbar;
        actbar = getSupportActionBar();
        actbar.setHomeButtonEnabled(true);
        actbar.setTitle(title);
        actbar.setDisplayHomeAsUpEnabled(true);
    }

    public void ambilnilai(){
        if (!input01.getEditText().getText().toString().equals("")){
            a = Float.valueOf(input01.getEditText().getText().toString());
        }else{
            a = 0;
            input01.getEditText().setText("0");
        }

        if (!input02.getEditText().getText().toString().equals("")){
            b = Float.valueOf(input02.getEditText().getText().toString());
        }else{
            b = 0;
            input02.getEditText().setText("0");
        }
    }

    public void Hitung() {
        ambilnilai();


        hasil_p = a/b;
        hasil_p = hasil_p*100;

        hasil.setText(Float.toString(hasil_p) + "%");
        inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
                InputMethodManager.HIDE_NOT_ALWAYS);

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