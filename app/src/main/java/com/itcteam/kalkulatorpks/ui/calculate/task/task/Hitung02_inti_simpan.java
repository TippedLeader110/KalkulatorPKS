package com.itcteam.kalkulatorpks.ui.calculate.task.task;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputLayout;
import com.itcteam.kalkulatorpks.R;
import com.itcteam.kalkulatorpks.util.DatabaseHandler;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.Calendar;

public class Hitung02_inti_simpan extends AppCompatActivity {

    FloatingActionButton simpan;
    LinearLayout linearLayout;
    TextInputLayout nama, datesimpan;
    DatabaseHandler databaseHandler;
    JSONObject retJson;
    DatePickerDialog.OnDateSetListener dateListener;
    TimePickerDialog.OnTimeSetListener timeSetListener;
    Integer tipe = 25;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle extras = getIntent().getExtras();
        if (getIntent().hasExtra("hide")){
            setContentView(R.layout.activity_hitung03_prev);
        }else{
            setContentView(R.layout.activity_hitung03_simpan);
        }

        try {
            retJson = new JSONObject(extras.getString("json"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        linearLayout = findViewById(R.id.hitung03_table);

        creatTable();

        simpan = findViewById(R.id.hitung03_saverecord);
        nama = findViewById(R.id.hitung03_namakebun);
        nama.setHint("Mesin/Alat");
        datesimpan = findViewById(R.id.hitung03_simpantanggal);

        datesimpan.getEditText().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog =  new DatePickerDialog(
                        Hitung02_inti_simpan.this,
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

                Calendar c = Calendar.getInstance();
                int mHour = c.get(Calendar.HOUR_OF_DAY);
                int mMinute = c.get(Calendar.MINUTE);

                TimePickerDialog timePickerDialog = new TimePickerDialog(
                        Hitung02_inti_simpan.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        timeSetListener,
                        mHour,mMinute,false
                );
                timePickerDialog.show();
            }
        };

        timeSetListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                String dateTime = datesimpan.getEditText().getText().toString();
                datesimpan.getEditText().setText(dateTime+" "+hourOfDay + ":" + minute + ":00");
            }
        };

        ActionBar actbar;
        actbar = getSupportActionBar();
        actbar.setHomeButtonEnabled(true);

        if (getIntent().hasExtra("hide")){
            actbar.setTitle("Informasi Berkas");
            datesimpan.setVisibility(View.GONE);
            nama.setVisibility(View.GONE);
            simpan.setBackgroundResource(R.drawable.ic_baseline_delete_24);

            DialogInterface.OnClickListener dialogInt = new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    switch (which){
                        case DialogInterface.BUTTON_POSITIVE:
                            String id = extras.getString("id");
                            databaseHandler.DeleteRecordData(id);
                            Toast.makeText(Hitung02_inti_simpan.this, "Data berhasil di hapus", Toast.LENGTH_SHORT).show();
                            finish();
                            break;
                        case DialogInterface.BUTTON_NEGATIVE:
                            break;
                    }
                }
            };

            simpan.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(Hitung02_inti_simpan.this);
                    builder.setMessage("Hapus berkas ini ?").setPositiveButton("Ya", dialogInt).
                            setNegativeButton("Tidak", dialogInt).show();
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

    private void creatTable() {

        try {
            //Generated
            TableLayout.LayoutParams rowLayout = new TableLayout.LayoutParams(
                    TableLayout.LayoutParams.MATCH_PARENT,
                    TableLayout.LayoutParams.MATCH_PARENT
            );
            rowLayout.setMargins(30, 20, 30, 0);

            TableRow firstRow = new TableRow(Hitung02_inti_simpan.this);
            TextView inti_air = new TextView(Hitung02_inti_simpan.this);
            inti_air.setTextColor(this.getResources().getColor(R.color.white));
            inti_air.setText("inti Air");
            inti_air.setPadding(5,5,40,5);
            TextView inti_kotoran = new TextView(Hitung02_inti_simpan.this);
            inti_kotoran.setText("inti Kotoran");
            inti_kotoran.setTextColor(this.getResources().getColor(R.color.white));
            inti_kotoran.setPadding(5,5,40,5);
            
            firstRow.addView(inti_air);
            firstRow.addView(inti_kotoran);
            firstRow.setBackgroundResource(R.color.colorPrimary);
            firstRow.setLayoutParams(rowLayout);

            TableLayout GeneratedTable = new TableLayout(this);
            LinearLayout.LayoutParams tableLayoutParams = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );
            GeneratedTable.setLayoutParams(tableLayoutParams);

            GeneratedTable.addView(firstRow);
            
            TableRow row = new TableRow(Hitung02_inti_simpan.this);

            TextView val_air = new TextView(Hitung02_inti_simpan.this);
            val_air.setText(new DecimalFormat("##.##").format(Float.valueOf(retJson.get("inti_air").toString())));
            val_air.setPadding(5,5,20,5);
            row.addView(val_air);


            TextView val_kotoran = new TextView(Hitung02_inti_simpan.this);
            val_kotoran.setText(new DecimalFormat("##.##").format(Float.valueOf(retJson.get("inti_kotoran").toString())));
            val_kotoran.setPadding(5,5,20,5);
            row.addView(val_kotoran);

            row.setLayoutParams(rowLayout);

            GeneratedTable.addView(row);

            linearLayout.addView(GeneratedTable);
            //Generated
            //USB

        } catch (JSONException e) {
            e.printStackTrace();
        }

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
        String date = this.datesimpan.getEditText().getText().toString();
        try {
            jsonObject.put("nama", nama);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.d("date_save", date);
        Log.d("nama_kebun", nama);

        Long id_record = databaseHandler.SaveRecord(date, tipe);
        if (id_record!=-1){
            Integer rec = Math.toIntExact(id_record);
            if (databaseHandler.SaveItem(jsonObject.toString(), rec)){
                if (databaseHandler.SaveRecordValue(retJson.toString(), rec)) {
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