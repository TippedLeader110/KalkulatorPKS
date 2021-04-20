package com.itcteam.kalkulatorpks.ui.calculate.task.task;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.itcteam.kalkulatorpks.R;
import com.itcteam.kalkulatorpks.db.DatabaseHandler;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;

public class Hitung01_simpan extends AppCompatActivity {

    Button simpan;
    TextInputLayout nama, tanam, matang, date;
    DatabaseHandler databaseHandler;
    JSONObject jsonVal;
    DatePickerDialog.OnDateSetListener dateListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hitung01_simpan);

        databaseHandler = new DatabaseHandler(this);
        Bundle extras = getIntent().getExtras();

        Log.d("RETJsonVal", extras.getString("jsonVal"));
        try {
            jsonVal = new JSONObject(extras.getString("jsonVal"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

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

        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog =  new DatePickerDialog(
                        Hitung01_simpan.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        dateListener,
                        year,month,day
                );
                dialog.getWindow().setBackgroundDrawable( new ColorDrawable( Color.TRANSPARENT ) );
                dialog.show();
            }
        });

        dateListener  = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker , int year , int month , int day) {
                month = month + 1;

                Log.d( "onDateSet" , month + "/" + day + "/" + year );
                date.getEditText().setText( new StringBuilder().append( day ).append( "-" )
                        .append( month ).append( "-" ).append( year ) );
            }
        };
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