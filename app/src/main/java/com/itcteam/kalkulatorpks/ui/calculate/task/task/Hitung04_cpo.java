package com.itcteam.kalkulatorpks.ui.calculate.task.task;

import android.content.Context;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputLayout;
import com.itcteam.kalkulatorpks.R;

public class Hitung04_cpo extends AppCompatActivity {

    TextView hasil;
    Button hitung;
    String title, inp1, inp2;
    Context contex;
    TextInputLayout input01, input02;
    InputMethodManager inputManager;

    public Hitung04_cpo(){
        this.title = "Rendemen CPO";
        this.inp1 = "Berat TBS yang di olah (kg)";
        this.inp2 = "Berat CPO yang dihasilkan (kg)";
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        contex = this;
        inputManager = (InputMethodManager)
                getSystemService(Context.INPUT_METHOD_SERVICE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hitung04_inti_cpo);

        input01 = findViewById(R.id.hitung04_input01);
        input01.setHint(inp1);

        input02 = findViewById(R.id.hitung04_input02);
        input02.setHint(inp2);

        hasil = findViewById(R.id.hitung04_hasil_r);

        hitung = findViewById(R.id.hitung04_btn_hitung);

        hitung.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Hitung();
            }
        });

        ActionBar actbar;
        actbar = getSupportActionBar();
        actbar.setHomeButtonEnabled(true);
        actbar.setTitle(title);
        actbar.setDisplayHomeAsUpEnabled(true);
    }

    public void Hitung() {

        float a = Float.valueOf(input01.getEditText().getText().toString());
        float b = Float.valueOf(input02.getEditText().getText().toString());
        float hasil_p;

        hasil_p = b/a;
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