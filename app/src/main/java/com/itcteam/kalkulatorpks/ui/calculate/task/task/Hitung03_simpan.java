package com.itcteam.kalkulatorpks.ui.calculate.task.task;

import android.app.DatePickerDialog;
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
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputLayout;
import com.itcteam.kalkulatorpks.R;
import com.itcteam.kalkulatorpks.util.DatabaseHandler;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;

public class Hitung03_simpan extends AppCompatActivity {

    FloatingActionButton simpan;
    LinearLayout linearLayout;
    TextInputLayout nama, datesimpan;
    DatabaseHandler databaseHandler;
    JSONObject retJson;
    DatePickerDialog.OnDateSetListener dateListener;
    Integer tipe = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hitung03_simpan);

        databaseHandler = new DatabaseHandler(this);
        Bundle extras = getIntent().getExtras();

        try {
            retJson = new JSONObject(extras.getString("json"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        linearLayout = findViewById(R.id.hitung03_table);

        creatTable();

        simpan = findViewById(R.id.hitung03_saverecord);
        nama = findViewById(R.id.hitung03_namakebun);
        datesimpan = findViewById(R.id.hitung03_simpantanggal);

        datesimpan.getEditText().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog =  new DatePickerDialog(
                        Hitung03_simpan.this,
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
            simpan.setVisibility(View.GONE);
            actbar.setTitle("Informasi Berkas");
            datesimpan.setVisibility(View.GONE);
            nama.setVisibility(View.GONE);
            simpan.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(Hitung03_simpan.this, "Hapus ", Toast.LENGTH_SHORT).show();
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
        List listDataName = new ArrayList();
        listDataName.add("USF");
        listDataName.add("USB");
        listDataName.add("Minyak");
        listDataName.add("Kernel");


        for (int id = 0; id < listDataName.size(); id++){
            Iterator nameKey;
            List listKey = new ArrayList<>();

            try {
                //Generated
                JSONObject GeneratedJSON = (JSONObject) retJson.get(listDataName.get(id).toString());
                nameKey = GeneratedJSON.keys();
                while (nameKey.hasNext()){
                    listKey.add(nameKey.next().toString());
                    Log.w("Key Name", nameKey.toString());
                }
                TableLayout.LayoutParams rowLayout = new TableLayout.LayoutParams(
                        TableLayout.LayoutParams.MATCH_PARENT,
                        TableLayout.LayoutParams.MATCH_PARENT
                );
                rowLayout.setMargins(30, 20, 30, 0);

                TableRow firstRow = new TableRow(Hitung03_simpan.this);
                TextView sampel = new TextView(Hitung03_simpan.this);
                sampel.setTextColor(this.getResources().getColor(R.color.white));
                sampel.setText("Sampel " + listDataName.get(id).toString());
                sampel.setPadding(5,5,40,5);
                TextView hsl_sampel = new TextView(Hitung03_simpan.this);
                hsl_sampel.setText("Hasil Sampel");
                hsl_sampel.setTextColor(this.getResources().getColor(R.color.white));
                hsl_sampel.setPadding(5,5,40,5);
                TextView ontbs = new TextView(Hitung03_simpan.this);
                ontbs.setText("ON TBS");
                ontbs.setTextColor(this.getResources().getColor(R.color.white));
                ontbs.setPadding(5,5,40,5);
                firstRow.addView(sampel);
                firstRow.addView(hsl_sampel);
                firstRow.addView(ontbs);
                firstRow.setBackgroundResource(R.color.colorPrimary);
                firstRow.setLayoutParams(rowLayout);

                TableLayout GeneratedTable = new TableLayout(this);
                LinearLayout.LayoutParams tableLayoutParams = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                );
                GeneratedTable.setLayoutParams(tableLayoutParams);

                Float onsampel = Float.valueOf(0);
                Float ontbs_t = Float.valueOf(0);

                GeneratedTable.addView(firstRow);
                for (int i=0; i < listKey.size(); i++) {
                    Log.w("Selected Key", String.valueOf(listKey.get(i)));
                    JSONObject valueJSON = (JSONObject) GeneratedJSON.get(String.valueOf(listKey.get(i)));
                    TableRow row = new TableRow(Hitung03_simpan.this);

                    TextView sampel_name = new TextView(Hitung03_simpan.this);
                    sampel_name.setText(String.valueOf(listKey.get(i)));
                    sampel_name.setPadding(5,5,20,5);
                    row.addView(sampel_name);

                    TextView s_val = new TextView(Hitung03_simpan.this);
                    s_val.setText(String.valueOf(valueJSON.get("Hasil Sampel").toString()));
                    s_val.setPadding(5,5,20,5);
                    row.addView(s_val);

                    onsampel += Float.valueOf(valueJSON.get("Hasil Sampel").toString());

                    TextView tbs_val = new TextView(Hitung03_simpan.this);
                    tbs_val.setText(String.valueOf(valueJSON.get("Hasil ON TBS").toString()));
                    tbs_val.setPadding(5,5,20,5);
                    row.addView(tbs_val);

                    ontbs_t += Float.valueOf(valueJSON.get("Hasil ON TBS").toString());

                    row.setLayoutParams(rowLayout);

                    GeneratedTable.addView(row);
                }
                TableRow row = new TableRow(Hitung03_simpan.this);
                TextView total = new TextView(Hitung03_simpan.this);
                total.setText("Total");
//                total.setTextColor(this.getResources().getColor(R.color.white));
                total.setPadding(5,5,20,5);
                row.addView(total);

                TextView total_sampel = new TextView(Hitung03_simpan.this);
                total_sampel.setText(String.valueOf(onsampel));
                total_sampel.setPadding(5,5,20,5);
//                total_sampel.setTextColor(this.getResources().getColor(R.color.white));
                row.addView(total_sampel);

                TextView total_tbs = new TextView(Hitung03_simpan.this);
                total_tbs.setText(String.valueOf(ontbs_t));
                total_tbs.setPadding(5,5,20,5);
//                total_tbs.setTextColor(this.getResources().getColor(R.color.white));
                row.addView(total_tbs);

//                row.setBackgroundResource(R.color.colorAccent);
                rowLayout.setMargins(0,0,0,15);
                row.setLayoutParams(rowLayout);

                GeneratedTable.addView(row);

                linearLayout.addView(GeneratedTable);
                //Generated
                //USB

            } catch (JSONException e) {
                e.printStackTrace();
            }
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