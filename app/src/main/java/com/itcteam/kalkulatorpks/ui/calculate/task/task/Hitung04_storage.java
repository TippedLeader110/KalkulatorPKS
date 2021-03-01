package com.itcteam.kalkulatorpks.ui.calculate.task.task;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputLayout;
import com.itcteam.kalkulatorpks.R;

import java.util.ArrayList;

public class Hitung04_storage extends AppCompatActivity {

    ArrayList dropdownVal, dropdownText;
    Button hitung;
    AutoCompleteTextView autoCompleteTextView;
    TextInputLayout inp1, inp2;
    Integer pos ;
    InputMethodManager inputManager;
    TextView hasil;


    public void setDropdown(){
        dropdownText = new ArrayList();
        dropdownVal = new ArrayList();
        dropdownText.add("48" + (char) 0x00B0 + "C (0,8919)");
        dropdownText.add("49" + (char) 0x00B0 + "C (0,8912)");
        dropdownText.add("50" + (char) 0x00B0 + "C (0,8906)");
        dropdownText.add("51" + (char) 0x00B0 + "C (0,8900)");
        dropdownText.add("52" + (char) 0x00B0 + "C (0,8893)");
        dropdownText.add("53" + (char) 0x00B0 + "C (0,8887)");
        dropdownText.add("54" + (char) 0x00B0 + "C (0,8881)");
        dropdownText.add("55" + (char) 0x00B0 + "C (0,8874)");
        dropdownText.add("56" + (char) 0x00B0 + "C (0,8868)");
        dropdownText.add("57" + (char) 0x00B0 + "C (0,8862)");
        dropdownText.add("58" + (char) 0x00B0 + "C (0,8855)");


        dropdownVal.add((float) 0.8919);
        dropdownVal.add((float) 0.8912);
        dropdownVal.add((float) 0.8906);
        dropdownVal.add((float) 0.8900);
        dropdownVal.add((float) 0.8893);
        dropdownVal.add((float) 0.8887);
        dropdownVal.add((float) 0.8881);
        dropdownVal.add((float) 0.8874);
        dropdownVal.add((float) 0.8868);
        dropdownVal.add((float) 0.8862);
        dropdownVal.add((float) 0.8855);
        pos = 0;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        inputManager = (InputMethodManager)
                getSystemService(Context.INPUT_METHOD_SERVICE);
        setDropdown();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hitung04_storage);

        hasil = findViewById(R.id.hitung04_hasil_r);

        inp1 = findViewById(R.id.hitung04_input01);
        inp1.setHint("Luas alas storage (m²)");
        inp2 = findViewById(R.id.hitung04_input02);
        inp2.setHint("Tebal CPO (m)");

        autoCompleteTextView = findViewById(R.id.hitung04_autocomplete);
        final ArrayAdapter arrayAdapter = new ArrayAdapter(Hitung04_storage.this, R.layout.hitung04_list_item, dropdownText);
        autoCompleteTextView.setText(arrayAdapter.getItem(0).toString(),false);
        autoCompleteTextView.setAdapter(arrayAdapter);
//        Log.e("test", dropdownVal.get(5).toString());

        hitung = findViewById(R.id.hitung04_btn_hitung);

        autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
//                String selectedValue = arrayAdapter.getItem(position).toString();
                Log.e("selected", dropdownVal.get(position).toString());
                pos = position;
            }
        });

        hitung.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                float a, b;
                if(inp1.getEditText().getText().toString() != ""){
                    a = Float.valueOf(inp1.getEditText().getText().toString());
                }else{
                    a = 0;
                    inp1.getEditText().setText("0");
                }

                if(inp2.getEditText().getText().toString() != ""){
                    b = Float.valueOf(inp2.getEditText().getText().toString());
                }else{
                    b = 0;
                    inp2.getEditText().setText("0");
                }
                float hasil_p;

                hasil_p = a*b;
                hasil_p = hasil_p*Float.valueOf(dropdownVal.get(pos).toString());

                hasil.setText(Float.toString(hasil_p) + " Ton");
                inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
                        InputMethodManager.HIDE_NOT_ALWAYS);
            }
        });

        ActionBar actbar;
        actbar = getSupportActionBar();
        actbar.setHomeButtonEnabled(true);
        actbar.setTitle("Volume CPO/inti di storage");
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