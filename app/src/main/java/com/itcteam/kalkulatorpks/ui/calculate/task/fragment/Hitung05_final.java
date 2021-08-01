package com.itcteam.kalkulatorpks.ui.calculate.task.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.itcteam.kalkulatorpks.R;
import com.itcteam.kalkulatorpks.util.DatabaseHandler;

import org.json.JSONException;
import org.json.JSONObject;

public class Hitung05_final extends Fragment {

    public Hitung05_final() {
        super(R.layout.fragment_hitung05_final);
    }

    TextView hasil, rumus;
    Button back, save;
    String hasils;
    Float fhasil;
    Bundle bundle;
    public static int tipe = 5;
    DatabaseHandler databaseHandler;
    KirimBalik kirimBalik;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        databaseHandler = new DatabaseHandler(getContext());

        hasil = view.findViewById(R.id.hitung05_final_hasil);
        rumus = view.findViewById(R.id.hitung05_final_rumus2);
        bundle = this.getArguments();

        if (bundle==null){
            Log.w("Bundle", "Empty");
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
                    SimpanRecord();
                    kirimBalik.retryKalkulasi();
                }
            });
        }

    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        kirimBalik = (KirimBalik) context;
    }

    public void SimpanRecord(){
        JSONObject jsonObject = new JSONObject();
        JSONObject jsonObjectval = new JSONObject();
        String nama = bundle.getString("nama");
        String date = bundle.getString("date");
        try {
            jsonObject.put("nama", nama);
            jsonObjectval.put("perfomance", bundle.getString("PR"));
            jsonObjectval.put("quality", bundle.getString("QU"));
            jsonObjectval.put("availability", bundle.getString("AV"));
            jsonObjectval.put("oee", fhasil);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.d("date_save", date);
        Log.d("nama_kebun", nama);

        Long id_record = databaseHandler.SaveRecord(date, tipe);
        if (id_record!=-1){
            Integer rec = Math.toIntExact(id_record);
            if (databaseHandler.SaveItem(jsonObject.toString(), rec)){
                if (databaseHandler.SaveRecordValue(jsonObjectval.toString(), rec)) {
                    Toast.makeText(getContext(), "Record Berhasil Disimpan !!", Toast.LENGTH_SHORT).show();

                }
                else
                    errMSG("Record Gagal");
            }else errMSG("Item Gagal");
        }else errMSG("ID Record");
    }

    public interface KirimBalik{
        void retryKalkulasi();
    }

    void errMSG(String msg){
        Toast.makeText(getContext(), "Terjadi kesalahan dalam membuat " + msg, Toast.LENGTH_SHORT).show();
    }
}
