package com.itcteam.kalkulatorpks.ui.calculate.task.fragment;

import android.os.Bundle;
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

public class Cal_01_standart extends Fragment {

    Button saveP, hitung;
    String tangkos, serat, cangkang, inti, cpo, tbs;
    EditText tangkosET, seratET, cangkangET, intiET, cpoET, tbsET;
    DatabaseHandler databaseHandler;
    TextView tangkosT, seratT, cangkangT, intiT, cpoT;

    public Cal_01_standart() {
        super(R.layout.fragment_cal_01_standart);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        Toast.makeText(getActivity(), "Standart", Toast.LENGTH_SHORT).show();
        databaseHandler = new DatabaseHandler(getContext());

        hitung = view.findViewById(R.id.cal_01_hitung_std);
        tbsET = view.findViewById(R.id.beratTBS_std);
        tangkosET = view.findViewById(R.id.tangkos_p_std);
        tangkosT = view.findViewById(R.id.tangkos_hsl_std);
        seratET = view.findViewById(R.id.serat_p_std);
        seratT = view.findViewById(R.id.serat_hsl_std);
        cangkangET = view.findViewById(R.id.cangkang_p_std);
        cangkangT = view.findViewById(R.id.cangkang_hsl_std);
        intiET = view.findViewById(R.id.inti_p_std);
        intiT = view.findViewById(R.id.inti_hsl_std);
        cpoET = view.findViewById(R.id.cpo_p_std);
        cpoT = view.findViewById(R.id.cpo_hsl_std);

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
        
        getSavedData();
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

    public void getSavedData(){
        HashMap<String, String> gData = new HashMap<String, String>();
        gData = databaseHandler.getPersen01();
        if (gData.get("error").equals("false")){
            tangkosET.setText(gData.get("tangkos"));;
            seratET.setText(gData.get("serat"));;
            cangkangET.setText(gData.get("cangkang"));;
            intiET.setText(gData.get("inti"));;
            cpoET.setText(gData.get("cpo"));;
        }else{
            Toast.makeText(getActivity(), "Gagal mengambil nilai tersimpan !!", Toast.LENGTH_SHORT).show();
        }
    }
}
