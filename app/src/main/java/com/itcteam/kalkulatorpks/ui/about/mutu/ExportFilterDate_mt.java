package com.itcteam.kalkulatorpks.ui.about.mutu;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputLayout;
import com.itcteam.kalkulatorpks.R;
import com.itcteam.kalkulatorpks.ui.about.ExportCSV;
import com.itcteam.kalkulatorpks.util.DatabaseHandler;

import java.util.Calendar;

public class ExportFilterDate_mt extends AppCompatActivity {

    Button cari;
    String first, end;
    TextView title;
    DatePickerDialog.OnDateSetListener dateListenerFirst, dateListenerEnd;
    TimePickerDialog.OnTimeSetListener timeSetListenerFirst, timeSetListenerEnd;
    TextInputLayout firstDate, endDate;
    int tipe = 2;


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){
            case android.R.id.home:
                finish();
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.modal_filter_tanggal_berkas);

        Bundle ex = getIntent().getExtras();

        tipe = Integer.valueOf(ex.getString("tipe"));

        title = this.findViewById(R.id.filter_title);
        title.setText("Export CSV dengan rentang waktu berikut");
        cari = this.findViewById(R.id.modal_filter_cari);
        cari.setText("Export CSV");
        firstDate = this.findViewById(R.id.modal_filter_berkas_simpantanggal_awal);
        endDate = this.findViewById(R.id.modal_filter_berkas_simpantanggal_akhir);

        ActionBar actbar;
        actbar = getSupportActionBar();
        actbar.setHomeButtonEnabled(true);
        actbar.setTitle("Export Berkas");
        actbar.setDisplayHomeAsUpEnabled(true);

        cari.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                first = firstDate.getEditText().getText().toString();
                end = endDate.getEditText().getText().toString();
                DatabaseHandler databaseHandler = new DatabaseHandler(ExportFilterDate_mt.this);
                ExportCSV exportCSV = new ExportCSV(databaseHandler.getAllFilter(first, end, tipe), ExportFilterDate_mt.this);
                String tt;
                if (tipe==2){
                    tt = "CPO";
                }else{
                    tt = "Inti";
                }

                if (exportCSV.DoExportCSV("Filter"+ first +"-sd-"+end+"-Mutu-" + tt)){
                    Toast.makeText(ExportFilterDate_mt.this, "CSV Berhasil disimpan !!!", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(ExportFilterDate_mt.this, "Terjadi kesalahan !!!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        firstDate.getEditText().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog =  new DatePickerDialog(
                        ExportFilterDate_mt.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        dateListenerFirst,
                        year,month,day
                );
                dialog.getWindow().setBackgroundDrawable( new ColorDrawable( Color.TRANSPARENT ) );
                dialog.show();
            }
        });

        endDate.getEditText().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog =  new DatePickerDialog(
                        ExportFilterDate_mt.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        dateListenerEnd,
                        year,month,day
                );
                dialog.getWindow().setBackgroundDrawable( new ColorDrawable( Color.TRANSPARENT ) );
                dialog.show();
            }
        });

        dateListenerFirst  = new DatePickerDialog.OnDateSetListener() {
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
                firstDate.getEditText().setText( new StringBuilder().append( year ).append( "-" )
                        .append( monthD ).append( "-" ).append( dayD ) );
                cari.setEnabled(checkValue());
                Calendar c = Calendar.getInstance();
                int mHour = c.get(Calendar.HOUR_OF_DAY);
                int mMinute = c.get(Calendar.MINUTE);

                TimePickerDialog timePickerDialog = new TimePickerDialog(
                        ExportFilterDate_mt.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        timeSetListenerFirst,
                        mHour,mMinute,false
                );
                timePickerDialog.show();
            }
        };

        dateListenerEnd  = new DatePickerDialog.OnDateSetListener() {
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
                endDate.getEditText().setText( new StringBuilder().append( year ).append( "-" )
                        .append( monthD ).append( "-" ).append( dayD ) );
                cari.setEnabled(checkValue());
                Calendar c = Calendar.getInstance();
                int mHour = c.get(Calendar.HOUR_OF_DAY);
                int mMinute = c.get(Calendar.MINUTE);

                TimePickerDialog timePickerDialog = new TimePickerDialog(
                        ExportFilterDate_mt.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        timeSetListenerEnd,
                        mHour,mMinute,false
                );
                timePickerDialog.show();
            }
        };

        timeSetListenerFirst = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                String dateTime = firstDate.getEditText().getText().toString();
                firstDate.getEditText().setText(dateTime+" "+hourOfDay + ":" + minute + ":00");
            }
        };

        timeSetListenerEnd = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                String dateTime = endDate.getEditText().getText().toString();
                endDate.getEditText().setText(dateTime+" "+hourOfDay + ":" + minute + ":00");
            }
        };
    }

    public Boolean checkValue(){
        Boolean c = true;
        if (firstDate.getEditText().getText().equals("")){
            c = false;
        }else{
            if (endDate.getEditText().getText().equals("")){
                c = false;
            }
        }
        return c;
    }

}
