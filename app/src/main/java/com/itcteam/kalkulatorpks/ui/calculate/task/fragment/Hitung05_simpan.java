package com.itcteam.kalkulatorpks.ui.calculate.task.fragment;

import android.app.DatePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputLayout;
import com.itcteam.kalkulatorpks.R;
import com.itcteam.kalkulatorpks.db.DatabaseHandler;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;

public class Hitung05_simpan extends AppCompatActivity {

    Button simpan;
    TextInputLayout nama, datesimpan;
    DatabaseHandler databaseHandler;
    String quality, perfomance, avail, hasil;
    DatePickerDialog.OnDateSetListener dateListener;
    Integer tipe = 5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hitung05_simpan);

        databaseHandler = new DatabaseHandler(this);
        Bundle extras = getIntent().getExtras();

        Toast.makeText(this, "Perfomance: " + extras.getString("PR") + "\n " +
                "Availability : " + extras.getString("AV") + "\n" +
                "Quality : " + extras.getString("QU"), Toast.LENGTH_SHORT).show();


        simpan = findViewById(R.id.hitung05_saverecord);
        nama = findViewById(R.id.hitung05_namakebun);
        datesimpan = findViewById(R.id.hitung05_simpantanggal);

        quality = extras.getString("QU");
        perfomance = extras.getString("PR");
        avail = extras.getString("AV");
        hasil = extras.getString("hasil");

        datesimpan.getEditText().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog =  new DatePickerDialog(
                        Hitung05_simpan.this,
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
                datesimpan.getEditText().setText( new StringBuilder().append( year ).append( "-" )
                        .append( monthD ).append( "-" ).append( dayD ) );
            }
        };

        ActionBar actbar;
        actbar = getSupportActionBar();
        actbar.setHomeButtonEnabled(true);

        if (getIntent().hasExtra("hide")){
            actbar.setTitle("Informasi Berkas");
            datesimpan.setVisibility(View.GONE);
            nama.setVisibility(View.GONE);
            simpan.setText("Hapus");
            simpan.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(Hitung05_simpan.this, "Hapus ", Toast.LENGTH_SHORT).show();
                }
            });
        }else{
            actbar.setTitle("Simpan Berkas");
            simpan.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    SimpanRecord();
                }
            });
        }


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
        JSONObject jsonObjectval = new JSONObject();
        String nama = this.nama.getEditText().getText().toString();
        String date = this.datesimpan.getEditText().getText().toString();
        try {
            jsonObject.put("nama", nama);
            jsonObjectval.put("perfomance", perfomance);
            jsonObjectval.put("quality", quality);
            jsonObjectval.put("availability", avail);
            jsonObjectval.put("OEE", hasil);
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
                    Toast.makeText(this, "Record Berhasil Disimpan !!", Toast.LENGTH_SHORT).show();
                    finish();
                }
                else
                    errMSG("Record Gagal");
            }else errMSG("Item Gagal");
        }else errMSG("ID Record");
    }

    void errMSG(String msg){
        Toast.makeText(this, "Terjadi kesalahan dalam membuat " + msg, Toast.LENGTH_SHORT).show();
    }
}