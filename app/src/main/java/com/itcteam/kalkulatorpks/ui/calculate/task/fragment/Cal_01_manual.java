package com.itcteam.kalkulatorpks.ui.calculate.task.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.itcteam.kalkulatorpks.R;
import com.itcteam.kalkulatorpks.db.DatabaseHandler;

import java.util.HashMap;

public class Cal_01_manual extends Fragment {

    Button saveP, hitung;
    String tangkos, serat, cangkang, inti, cpo, tbs;
    EditText tangkosET, seratET, cangkangET, intiET, cpoET, tbsET;
    DatabaseHandler databaseHandler;
    TextView tangkosT, seratT, cangkangT, intiT, cpoT;

    public Cal_01_manual() {
        super(R.layout.fragment_cal_01_manual);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        Toast.makeText(getActivity(), "MANUAL", Toast.LENGTH_SHORT).show();
        databaseHandler = new DatabaseHandler(getContext());

        saveP = view.findViewById(R.id.cal_01_save_manual);
        hitung = view.findViewById(R.id.cal_01_hitung_manual);
        tbsET = view.findViewById(R.id.beratTBS_manual);
        tangkosET = view.findViewById(R.id.tangkos_p_manual);
        tangkosT = view.findViewById(R.id.tangkos_hsl_manual);
        seratET = view.findViewById(R.id.serat_p_manual);
        seratT = view.findViewById(R.id.serat_hsl_manual);
        cangkangET = view.findViewById(R.id.cangkang_p_manual);
        cangkangT = view.findViewById(R.id.cangkang_hsl_manual);
        intiET = view.findViewById(R.id.inti_p_manual);
        intiT = view.findViewById(R.id.inti_hsl_manual);
        cpoET = view.findViewById(R.id.cpo_p_manual);
        cpoT = view.findViewById(R.id.cpo_hsl_manual);

        hitung.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getValue();
                Float tangkosi = Float.valueOf(tangkos)/100;
                tangkosi = tangkosi * Float.valueOf(tbs);
                tangkosT.setText(tangkosi.toString() + "KG");
                Float serati = Float.valueOf(serat)/100;
                serati = serati * Float.valueOf(tbs);
                seratT.setText(serati.toString() + "KG");
                Float cangkangi = Float.valueOf(cangkang)/100;
                cangkangi = cangkangi * Float.valueOf(tbs);
                cangkangT.setText(cangkangi.toString() + "KG");
                Float intii = Float.valueOf(inti)/100;
                intii = intii * Float.valueOf(tbs);
                intiT.setText(intii.toString() + "KG");
                Float cpoi = Float.valueOf(cpo)/100;
                cpoi = cpoi * Float.valueOf(tbs);
                cpoT.setText(cpoi.toString() + "KG");
            }
        });

        saveP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (databaseHandler.savePersen01(getValue())){
                    Toast.makeText(getActivity(), "Berhasil disimpan", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(getActivity(), "Gagal disimpan", Toast.LENGTH_SHORT).show();
                }
            }
        });
        super.onViewCreated(view, savedInstanceState);
    }

    public HashMap<String, String> getValue(){
        HashMap<String, String> nilai = new HashMap<String, String>();
        tbs = tbsET.getText().toString();
        nilai.put("tbs", tbs);
        tangkos = tangkosET.getText().toString();
        nilai.put("tangkos", tangkos);
        serat = seratET.getText().toString();
        nilai.put("serat", serat);
        cangkang = cangkangET.getText().toString();
        nilai.put("cangkang", cangkang);
        inti = intiET.getText().toString();
        nilai.put("inti", inti);
        cpo = cpoET.getText().toString();
        nilai.put("cpo", cpo);

        return nilai;
    }
}
