package com.itcteam.kalkulatorpks.ui.about.mutu;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.textfield.TextInputLayout;
import com.itcteam.kalkulatorpks.R;
import com.itcteam.kalkulatorpks.ui.about.ExportCSV;
import com.itcteam.kalkulatorpks.ui.about.perhitungan_rendemen.ListBerkas_pr;
import com.itcteam.kalkulatorpks.util.DatabaseHandler;

import java.util.Calendar;

public class RecyclerFilterModal_mt extends BottomSheetDialogFragment {

    Button cari;
    ListBerkas_mt listener;
    String first, end;
    DatePickerDialog.OnDateSetListener dateListenerFirst, dateListenerEnd;
    TimePickerDialog.OnTimeSetListener timeSetListenerFirst, timeSetListenerEnd;
    TextInputLayout firstDate, endDate;
    Boolean export;

    public RecyclerFilterModal_mt(boolean export) {
        this.export = export;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        listener = (ListBerkas_mt) context;
        super.onAttach(context);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.modal_filter_tanggal_berkas, container, false);

        cari = v.findViewById(R.id.modal_filter_cari);
        firstDate = v.findViewById(R.id.modal_filter_berkas_simpantanggal_awal);
        endDate = v.findViewById(R.id.modal_filter_berkas_simpantanggal_akhir);



        cari.setEnabled(false);

        if (export){
            cari.setOnClickListener(new View.OnClickListener() {
                @RequiresApi(api = Build.VERSION_CODES.O)
                @Override
                public void onClick(View v) {
                    first = firstDate.getEditText().getText().toString();
                    end = endDate.getEditText().getText().toString();
                    DatabaseHandler databaseHandler = new DatabaseHandler(getContext());
                    ExportCSV exportCSV = new ExportCSV(databaseHandler.getAllFilter(first, end, 4), getContext());

                    if (exportCSV.DoExportCSV("Filter"+ first +"-sd-"+end+"-MaterialBalanceDate")){
                        Toast.makeText(getContext(), "CSV Berhasil disimpan !!!", Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(getContext(), "Terjadi kesalahan !!!", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }else{
            cari.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    first = firstDate.getEditText().getText().toString();
                    end = endDate.getEditText().getText().toString();
                    listener.TanggalFilter(first, end);
                }
            });
        }

        firstDate.getEditText().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog =  new DatePickerDialog(
                        listener,
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
                        listener,
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
                        listener,
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
                        listener,
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

        return v;
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

    public interface FilterModalListener{
        void TanggalFilter(String first, String end);
    }
}
