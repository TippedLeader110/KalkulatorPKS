package com.itcteam.kalkulatorpks.ui.calculate.task.fragment;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.itcteam.kalkulatorpks.R;

public class Hitung05_final extends Fragment {

    public Hitung05_final() {
        super(R.layout.fragment_hitung05_final);
    }

    TextView hasil;
    Button back;
    String hasils;
    KirimBalik kirimBalik;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        hasil = view.findViewById(R.id.hitung05_final_hasil);

        if (savedInstanceState==null){
            
        }else{
            Float fhasil = Float.valueOf(savedInstanceState.getString("hasil"));
            fhasil *= 100;
            hasils = fhasil + "%";
            hasil.setText(hasils);
        }

        back = view.findViewById(R.id.hitung05_final_retry);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                kirimBalik.retryKalkulasi();
            }
        });
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        kirimBalik = (KirimBalik) context;
    }

    public interface KirimBalik{
        void retryKalkulasi();
    }
}
