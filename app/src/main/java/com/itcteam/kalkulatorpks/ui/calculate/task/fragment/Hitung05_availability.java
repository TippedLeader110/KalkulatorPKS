package com.itcteam.kalkulatorpks.ui.calculate.task.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.textfield.TextInputLayout;
import com.itcteam.kalkulatorpks.R;
import com.itcteam.kalkulatorpks.db.DatabaseHandler;

import java.util.HashMap;

public class Hitung05_availability extends Fragment {


    passingData kirimBalik;
    TextInputLayout input01, input02, input03;
    Float value;
    Button next;
    boolean third;
    TextView title;
    String titles;
    String inp1, inp2, inp3, btn;

    public Hitung05_availability() {
        super(R.layout.fragment_hitung05_availability);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        setAll();
        title = view.findViewById(R.id.hitung05_title);
        title.setText(titles);
        input01 = view.findViewById(R.id.hitung05_input01);
        input01.setHint(inp1);
        input02 = view.findViewById(R.id.hitung05_input02);
        input02.setHint(inp2);
        input03 = view.findViewById(R.id.hitung05_input03);

        if(!third){
            input03.setVisibility(View.GONE);
        }else{
            input03.setHint(inp3);
        }

        next = view.findViewById(R.id.hitung05_btn_hitung);
        next.setText(btn);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                kalkulasi();
            }
        });

    }

    public void setAll() {
        third = false;
        inp1 = "Loading time";
        inp2 = "Downtime losses";
        titles = "Availability";
        btn = "Next";
    }

    public void kalkulasi(){
        Float k1 = Float.valueOf(input01.getEditText().getText().toString());
        Float k2 = Float.valueOf(input02.getEditText().getText().toString());
//        Float k2 = Float.valueOf(input03.getEditText().getText().toString());
        value = k1/k2;

        kirimBalik.dataKirimBalik(value, 1);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        kirimBalik = (passingData) context;
    }

    public interface passingData{
        public void dataKirimBalik(Float value, Integer type);
    }
}
