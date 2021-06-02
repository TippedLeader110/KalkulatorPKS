package com.itcteam.kalkulatorpks.ui.about.losses_pabrik;

import android.app.DatePickerDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.textfield.TextInputLayout;
import com.itcteam.kalkulatorpks.R;
import com.itcteam.kalkulatorpks.util.DatabaseHandler;
import com.itcteam.kalkulatorpks.ui.about.ExportCSV;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import jxl.CellView;
import jxl.Workbook;
import jxl.WorkbookSettings;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;

public class RecyclerFilterModal_lb extends BottomSheetDialogFragment {

    Button cari;
    ListBerkas_lb listener;
    String first, end;
    DatePickerDialog.OnDateSetListener dateListenerFirst, dateListenerEnd;
    TextInputLayout firstDate, endDate;
    Boolean export;
    DatabaseHandler databaseHandler;

    public RecyclerFilterModal_lb(boolean export) {
        this.export = export;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        listener = (ListBerkas_lb) context;
        super.onAttach(context);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.modal_filter_tanggal_berkas, container, false);

        databaseHandler = new DatabaseHandler(getContext());
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


                    try {
                        exportExcel();
                    } catch (JSONException e) {
                        e.printStackTrace();
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

        return v;
    }

    private void exportExcel() throws JSONException {
        List<String> listDataName = new ArrayList();
        listDataName.add("USF");
        listDataName.add("USB");
        listDataName.add("Minyak");
        listDataName.add("Kernel");

        List USF = new ArrayList<>();
        List USB = new ArrayList<>();
        List minyak = new ArrayList<>();
        List kernel = new ArrayList<>();
        List nama = new ArrayList<>();
        List tanggal = new ArrayList<>();


        List dataRet = databaseHandler.getAllFilterJSON(first, end, 3);

        for (int i = 0; i < dataRet.size(); i++){

            HashMap<String, String > dataRetHash = (HashMap<String, String>) dataRet.get(i);
            JSONObject jsonObject = new JSONObject(dataRetHash.get("data").toString());



            for (String namaKey:listDataName){
                if (namaKey.equals("USF")){
                    USF.add(jsonObject.get(namaKey).toString());
                }else if (namaKey.equals("USB")){
                    USB.add(jsonObject.get(namaKey).toString());
                }else if (namaKey.equals("Minyak")){
                    minyak.add(jsonObject.get(namaKey).toString());
                }else if (nama.equals("Kernel")){
                    kernel.add(jsonObject.get(namaKey).toString());
                }
            }

        }


        try {


            File sd = Environment.getExternalStorageDirectory();
            String csvFile = "LossesPabrikRange-"+ first +"-sd-"+ end +"-"+".xls";

            File directory = new File(sd.getAbsolutePath());

            //create directory if not exist
            if (!directory.isDirectory()) {
                directory.mkdirs();
            }
            //file path
            File file = new File(directory, csvFile);
            WorkbookSettings wbSettings = new WorkbookSettings();
            wbSettings.setLocale(new Locale(Locale.US.getLanguage(), Locale.US.getCountry()));
            WritableWorkbook workbook;
            workbook = Workbook.createWorkbook(file, wbSettings);




            workbook.write();
            workbook.close();
            Toast.makeText(getContext(), "File berhasil di buat", Toast.LENGTH_SHORT).show();

        } catch (WriteException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }



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
