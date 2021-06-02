package com.itcteam.kalkulatorpks.ui.about.losses_pabrik;

import android.app.DatePickerDialog;
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
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputLayout;
import com.itcteam.kalkulatorpks.R;
import com.itcteam.kalkulatorpks.util.DatabaseHandler;
import com.itcteam.kalkulatorpks.ui.about.ExportCSV;

import java.util.Calendar;

public class ExportFilterDate_lb extends AppCompatActivity {

    Button cari;
    String first, end;
    TextView title;
    DatePickerDialog.OnDateSetListener dateListenerFirst, dateListenerEnd;
    TextInputLayout firstDate, endDate;
    static int tipe = 3;
    

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
                DatabaseHandler databaseHandler = new DatabaseHandler(ExportFilterDate_lb.this);
                ExportCSV exportCSV = new ExportCSV(databaseHandler.getAllFilter(first, end, tipe), ExportFilterDate_lb.this);

                if (exportCSV.DoExportCSV("Filter"+ first +"-sd-"+end+"-OEE")){
                    Toast.makeText(ExportFilterDate_lb.this, "CSV Berhasil disimpan !!!", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(ExportFilterDate_lb.this, "Terjadi kesalahan !!!", Toast.LENGTH_SHORT).show();
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
                        ExportFilterDate_lb.this,
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
                        ExportFilterDate_lb.this,
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
