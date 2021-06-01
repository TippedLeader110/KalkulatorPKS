package com.itcteam.kalkulatorpks.ui.calculate.task.fragment;

import android.content.Context;
import android.content.Intent;
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

    TextView hasil, rumus;
    Button back, save;
    String hasils;
    Float fhasil;
    KirimBalik kirimBalik;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        hasil = view.findViewById(R.id.hitung05_final_hasil);
        rumus = view.findViewById(R.id.hitung05_final_rumus2);
        final Bundle bundle = this.getArguments();

        if (bundle==null){
            Log.w("Bunnde", "Empty");
        }else{
            fhasil = Float.valueOf(bundle.getString("HASIL"));
            fhasil *= 100;
            hasils = fhasil + "%";
            rumus.setText("OEE = "+ bundle.getString("AV").toString() + "% x " + bundle.getString("PR").toString()+ "%" +
                    " x " + bundle.getString("QU").toString() + "%");
            hasil.setText("OEE = " + hasils);
    }

        back = view.findViewById(R.id.hitung05_final_retry);
        save = view.findViewById(R.id.hitung05_save);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                kirimBalik.retryKalkulasi();
            }
        });

        if (bundle.getString("hide")==null){
            save.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getActivity(), Hitung05_simpan.class);
                    intent.putExtras(bundle);
                    intent.putExtra("hasil", String.valueOf(fhasil));
                    startActivity(intent);
                }
            });
        }

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
