package com.itcteam.kalkulatorpks.ui.calculate.task.task;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputLayout;
import com.itcteam.kalkulatorpks.R;

public class Hitung02_alb extends AppCompatActivity {

    Button hitung;
    InputMethodManager inputManager;
    TextInputLayout input03, input01, input02;
    TextView hasil, keteranganTV;
    String keterangan, acttitle, input01s, input02s, input03s;
    Context context;
    ImageView formula;
    Boolean thirdInput = true;
    float finput01, finput02, finput03;

    public Hitung02_alb(){
        acttitle = "ALB pada CPO";
        keterangan = "";
        input01s = "Berat Contoh (gr)";
        input02s = "N KOH";
        input03s = "ml KOH (ml)";
        setAll(acttitle, keterangan, input01s, input02s, input03s, true);
    }

    public void setAll(String title, String ket, String i01, String i02, String i03, Boolean thinp){
        this.acttitle = title;
        this.keterangan = ket;
        this.input01s = i01;
        this.input02s = i02;
        this.input03s = i03;
        this.thirdInput = thinp;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        inputManager = (InputMethodManager)
                getSystemService(Context.INPUT_METHOD_SERVICE);
        setContentView(R.layout.activity_hitung02_sub);

        context = this;
        ActionBar actbar;
        actbar = getSupportActionBar();
        actbar.setHomeButtonEnabled(true);
        actbar.setTitle(acttitle);
        actbar.setDisplayHomeAsUpEnabled(true);


        formula = findViewById(R.id.hitung02_rumus);

        setRumusDrawable();

        input01 = findViewById(R.id.hitung02_input01);
        input01.setHint(input01s);
        input02 = findViewById(R.id.hitung02_input02);
        input02.setHint(input02s);
        input03 = findViewById(R.id.hitung02_input03);
        input03.setHint(input03s);

        if (!thirdInput){
            input03.setVisibility(View.GONE);
        }

        hitung = findViewById(R.id.hitung02_btn_hitung);

        hasil = findViewById(R.id.hitung02_hasil_r);

        keteranganTV = findViewById(R.id.hitung02_keterangan);
        keteranganTV.setText(this.keterangan);

        hitung.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                kalkulasi();
            }
        });
    }

    public void setRumusDrawable() {
        formula.setBackgroundResource(R.drawable.alb);
    }

    public void ambilnilai(){
        if (!input01.getEditText().getText().toString().equals("")){
            finput01 = Float.valueOf(input01.getEditText().getText().toString());
        }else{
            finput01 = 0;
            input01.getEditText().setText("0");
        }

        if (!input02.getEditText().getText().toString().equals("")){
            finput02 = Float.valueOf(input02.getEditText().getText().toString());
        }else{
            finput02 = 0;
            input02.getEditText().setText("0");
        }

        if(thirdInput){
            if (!input03.getEditText().getText().toString().equals("")){
                finput03 = Float.valueOf(input03.getEditText().getText().toString());
            }else{
                input03.getEditText().setText("0");
                finput03 = 0;
            }
        }
    }

    public void kalkulasi(){
        ambilnilai();

        float fhasil;

        fhasil = finput03 * finput02;
        fhasil = fhasil * Float.valueOf("25.6");
        fhasil = fhasil/finput01;
        fhasil *= 100;

        hasil.setText(Float.toString(fhasil) + "%");
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