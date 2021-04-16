package com.itcteam.kalkulatorpks.ui.calculate.task.task;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.itcteam.kalkulatorpks.R;
import com.itcteam.kalkulatorpks.db.DatabaseHandler;

public class Hitung01_simpan extends AppCompatActivity {

    Button simpan;
    TextInputLayout nama, tanam, matang, date;
    DatabaseHandler databaseHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hitung01_simpan);

        databaseHandler = new DatabaseHandler(this);

        simpan = findViewById(R.id.hitung01_saverecord);
        nama = findViewById(R.id.hitung01_namakebun);
        tanam = findViewById(R.id.hitung01_tanam);
        date = findViewById(R.id.hitung01_simpantanggal);
        matang = findViewById(R.id.hitung01_matang);

        simpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SimpanRecord();
            }
        });

    }

    public void SimpanRecord(){
        String nama = this.nama.getEditText().getText().toString();
        Log.d("nama_kebun", nama);
        String tanam = this.tanam.getEditText().getText().toString();
        Log.d("tahun_tanam", tanam);
        String date = this.date.getEditText().getText().toString();
        Log.d("date_save", date);
        String matang = this.matang.getEditText().getText().toString();
        Log.d("faksi_matang", matang);


        Long id_record = databaseHandler.SaveRecord(date);
        if (id_record!=-1){
            Integer rec = Math.toIntExact(id_record);
            if (databaseHandler.SaveItem(nama, rec, 0)){
                if (databaseHandler.SaveItem(tanam, rec, 2)){
                    if (databaseHandler.SaveItem(matang, rec, 3)){
                        Toast.makeText(this, "Record Berhasil Disimpan !!", Toast.LENGTH_SHORT).show();
                    }else errMSG("Faksi Matang");
                }else errMSG("Tahun Tanam");
            }else errMSG("Nama Kebun");
        }else errMSG("Record");
    }

    void errMSG(String msg){
        Toast.makeText(this, "Terjadi kesalahan dalam membuat " + msg, Toast.LENGTH_SHORT).show();
    }
}