package com.itcteam.kalkulatorpks.ui.calculate.task.task;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputLayout;
import com.itcteam.kalkulatorpks.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Hitung03_kernel extends AppCompatActivity {
    
    TextView hasilR, hasilL, hasilRTBS, hasilLTBS, title_sample;
    public float hasil_p, hasil_tbs;
    Button hitungSampel, hitungTBS;
    AutoCompleteTextView autoCompleteTextView;
    FloatingActionButton save;
    String tooltip_text, title, inp1, inp2, tooltip_title, jsonVal;
    Context contex;
    ImageView rumus, rumus2;
    List dropdownSample, dropdownSampleVal, sVal01, sVal02, tVal01, tVal02;
    TextInputLayout input01, input02, input03, input04;
    float  a, b, c, d;
    int tipe, pos;
    String title_sample_txt;
    String backtipe;
    InputMethodManager inputManager;

    public Hitung03_kernel(){
        dropdownSample = new ArrayList<String>();

        dropdownSample.add("LTDS I");
        dropdownSample.add("LTDS II");
        dropdownSample.add("Fiber Cyclone");
        dropdownSample.add("Hydrocyclone");

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

    public void createNewVal(){
        dropdownSampleVal = new ArrayList<String>();
        sVal01 = new ArrayList<Float>();
        sVal02 = new ArrayList<Float>();
        tVal01 = new ArrayList<Float>();
        tVal02 = new ArrayList<Float>();

        for (int i = 0; i < dropdownSample.size(); i++){
            sVal01.add(0);
            sVal02.add(0);
            tVal01.add(0);
            tVal02.add(0);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        createNewVal();
        inputManager = (InputMethodManager)
                getSystemService(Context.INPUT_METHOD_SERVICE);
        contex = this;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hitung03_new_template);
        pos = 0;
        title_sample = findViewById(R.id.hitung_sampel);

        input01 = findViewById(R.id.hitung03_input01);
        input01.setHint(inp1);

        input02 = findViewById(R.id.hitung03_input02);
        input02.setHint(inp2);

        input03 = findViewById(R.id.hitung03_input_tbs1);
        input03.setHint("On Sample");

        input04 = findViewById(R.id.hitung03_input_tbs2);
        input04.setHint("Material Balance %");

        hasilR = findViewById(R.id.hitung03_hasil_r);
        hasilL = findViewById(R.id.hitung03_hasil_l);
        hasilLTBS = findViewById(R.id.hitung03_hasil_l_tbs);
        hasilRTBS = findViewById(R.id.hitung03_hasil_r_tbs);

        hitungSampel = findViewById(R.id.hitung03_sampel);
        hitungTBS = findViewById(R.id.hitung03_sampel_tbs);

        rumus = findViewById(R.id.hitung03_rumus);
        rumus2 = findViewById(R.id.hitung03_rumus_2);

        save = this.findViewById(R.id.hitung03_save_fab);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buatJson();
//                Toast.makeText(contex, backtipe + " : " + jsonVal, Toast.LENGTH_SHORT).show(); // Debug data
                Log.w("BACKTIPE", jsonVal);
                Intent intent = new Intent();
                intent.putExtra(backtipe, jsonVal);
                setResult(tipe, intent);
                finish();
            }
        });

        hasilL.setText("Hasil sampel dari "+ dropdownSample.get(pos) +" = ");
        hasilLTBS.setText("Hasil On TBS dari "+ dropdownSample.get(pos) +" = ");
        hasilRTBS.setText(".....%");
        hasilR.setText("....%");

        autoCompleteTextView = findViewById(R.id.hitung03_autocomplete);
        final ArrayAdapter arrayAdapter = new ArrayAdapter(contex, R.layout.hitung04_list_item, dropdownSample);
        autoCompleteTextView.setText(arrayAdapter.getItem(0).toString(),false);
        autoCompleteTextView.setAdapter(arrayAdapter);
        title_sample.setText("Sampel " + dropdownSample.get(pos).toString());
//        Log.e("test", dropdownVal.get(5).toString());

        autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
//                String selectedValue = arrayAdapter.getItem(position).toString();
                Log.e("selected", dropdownSample.get(position).toString());
                pos = position;
                title_sample.setText("Sampel " + dropdownSample.get(position).toString());
                input01.getEditText().setText(String.valueOf(sVal01.get(pos)));
                input02.getEditText().setText(String.valueOf(sVal02.get(pos)));
                input03.getEditText().setText(String.valueOf(tVal01.get(pos)));
                input04.getEditText().setText(String.valueOf(tVal02.get(pos)));
                calculateSampel();
                calculateTBS();
            }
        });
    
        hitungSampel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calculateSampel();
            }
        });

        hitungTBS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calculateTBS();
            }
        });

        setRumus();
        rumus2.setBackgroundResource(R.drawable.l_tbs_m);

        ActionBar actbar;
        actbar = getSupportActionBar();
        actbar.setHomeButtonEnabled(true);
        actbar.setTitle(title);
        actbar.setDisplayHomeAsUpEnabled(true);
    }

    public void setRumus() {
        rumus.setBackgroundResource(R.drawable.l_kernel);
    }

    private void buatJson() {

        try {
            JSONObject jsonObject = new JSONObject();
            for (int i = 0; i < dropdownSample.size(); i++){
                Float hasil = Float.valueOf(sVal01.get(i).toString())/Float.valueOf(sVal02.get(i).toString());
                hasil = hasil*100;
                if(Float.isNaN(hasil)) hasil = Float.valueOf(0);
                jsonObject.put(dropdownSample.get(i).toString(),
                        new JSONObject()
                                .put(inp1, sVal01.get(i))
                                .put(inp2, sVal02.get(i))
                                .put("Hasil Sampel", hasil)
                                .put("On Sample", tVal01.get(i))
                                .put("Material Balance", tVal02.get(i))
                                .put("Hasil ON TBS", (Float.valueOf(tVal01.get(i).toString())*Float.valueOf(tVal02.get(i).toString()))/100)
                );
            }

            jsonVal = jsonObject.toString();

        }catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void calculateTBS(){
        if (!input03.getEditText().getText().toString().equals("")){
            c = Float.valueOf(input03.getEditText().getText().toString());
        }else{
            c = 0;
            input03.getEditText().setText("0");
        }
        tVal01.set(pos, c);

        if (!input04.getEditText().getText().toString().equals("")){
            d = Float.valueOf(input04.getEditText().getText().toString());
        }else{
            d = 0;
            input04.getEditText().setText("0");
        }
        tVal02.set(pos, d);

        hasil_tbs = (Float)tVal01.get(pos) * (Float)tVal02.get(pos);
        hasil_tbs /= 100;
        hasilRTBS.setText(Float.toString(hasil_tbs) + "%");
        hasilLTBS.setText("Hasil On TBS dari "+ dropdownSample.get(pos) +" = ");
        inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
                InputMethodManager.HIDE_NOT_ALWAYS);
    }

    public void calculateSampel(){
        if (!input01.getEditText().getText().toString().equals("")){
            a = Float.valueOf(input01.getEditText().getText().toString());
        }else{
            a = 0;
            input01.getEditText().setText("0");
        }
        sVal01.set(pos, a);

        if (!input02.getEditText().getText().toString().equals("")){
            b = Float.valueOf(input02.getEditText().getText().toString());
        }else{
            b = 0;
            input02.getEditText().setText("0");
        }
        sVal02.set(pos, b);

        hasil_p = (Float)sVal01.get(pos) / (Float)sVal02.get(pos);
        hasil_p = hasil_p*100;

        hasilR.setText(Float.toString(hasil_p) + "%");
        hasilL.setText("Hasil sampel dari "+ dropdownSample.get(pos) +" = ");
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