package com.itcteam.kalkulatorpks.ui.calculate.task.task;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.itcteam.kalkulatorpks.R;
import com.itcteam.kalkulatorpks.util.DatabaseHandler;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;

public class Hitung01_simpan extends AppCompatActivity {

    Button simpan;
    TextInputLayout nama, tanam, matang, date;
    DatabaseHandler databaseHandler;
    JSONObject jsonVal;
    DatePickerDialog.OnDateSetListener dateListener;
    String tbs, tangkosHasil, tangkosHasilp, seratHasil, seratHasilp, cangkangHasil, cangkangHasilp, intiHasil, intiHasilp, cpoHasil, cpoHasilp, dirtHasil, dirtHasilp;

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

        date.getEditText().setOnClickListener(new View.OnClickListener() {
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
                String dayD = String.valueOf(day);
                if (day<10) {
                    dayD = "0"+dayD;
                }
                String monthD = String.valueOf(month);
                if (month<10) {
                    monthD= "0"+monthD;
                }
                Log.d( "onDateSet" , month + "/" + day + "/" + year );
                date.getEditText().setText( new StringBuilder().append( year ).append( "-" )
                        .append( monthD ).append( "-" ).append( dayD ) );
            }
        };

        ActionBar actbar;
        actbar = getSupportActionBar();
        actbar.setHomeButtonEnabled(true);
        actbar.setTitle("Simpan Berkas");
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

    public void SimpanRecord(){
        JSONObject jsonObject = new JSONObject();
        String nama = this.nama.getEditText().getText().toString();
        String tanam = this.tanam.getEditText().getText().toString();
        String date = this.date.getEditText().getText().toString();
        String matang = this.matang.getEditText().getText().toString();
        try {
            jsonObject.put("nama", nama);
            jsonObject.put("tanam", tanam);
            jsonObject.put("matang", matang);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.d("tahun_tanam", tanam);
        Log.d("date_save", date);
        Log.d("nama_kebun", nama);
        Log.d("faksi_matang", matang);

        Long id_record = databaseHandler.SaveRecord(date, 1);
        if (id_record!=-1){
            Integer rec = Math.toIntExact(id_record);
            if (databaseHandler.SaveItem(jsonObject.toString(), rec)){
                if (databaseHandler.SaveRecordValue(jsonVal.toString(), rec))
                    Toast.makeText(this, "Record Berhasil Disimpan !!", Toast.LENGTH_SHORT).show();
                else
                    errMSG("Record Gagal");
            }else errMSG("Item Gagal");
        }else errMSG("ID Record");
    }

    void errMSG(String msg){
        Toast.makeText(this, "Terjadi kesalahan dalam membuat " + msg, Toast.LENGTH_SHORT).show();
    }
}