package com.itcteam.kalkulatorpks.ui.calculate.task.task;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
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
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputLayout;
import com.itcteam.kalkulatorpks.R;
import com.itcteam.kalkulatorpks.ui.calculate.task.fragment.Hitung05_final_nonFrag;
import com.itcteam.kalkulatorpks.util.DatabaseHandler;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;

public class Hitung04_simpan extends AppCompatActivity {

    Button simpan;
    TextInputLayout nama, date;
    DatabaseHandler databaseHandler;
    JSONObject jsonVal;
    TextView cpo, inti, storage;
    DatePickerDialog.OnDateSetListener dateListener;
    TimePickerDialog.OnTimeSetListener timeSetListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Context context = this;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hitung04_simpan);

        databaseHandler = new DatabaseHandler(this);
        Bundle extras = getIntent().getExtras();

        Toast.makeText(this, "CPO: " + extras.getString("cpo") + "\n " +
                "Inti : " + extras.getString("inti") + "\n" +
                "Storage : " + extras.getString("storage"), Toast.LENGTH_SHORT).show();


        simpan = findViewById(R.id.hitung04_saverecord);
        nama = findViewById(R.id.hitung04_namakebun);
        nama.setHint("Nama PKS");

        date = findViewById(R.id.hitung04_simpantanggal);
        cpo = findViewById(R.id.tv4_cpo_v);
        cpo.setText(extras.getString("cpo"));
        inti = findViewById(R.id.tv4_inti_v);
        inti.setText(extras.getString("inti"));
        storage = findViewById(R.id.tv4_storage_v);
        storage.setText(extras.getString("storage"));



        date.getEditText().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog =  new DatePickerDialog(
                        Hitung04_simpan.this,
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

                Calendar c = Calendar.getInstance();
                int mHour = c.get(Calendar.HOUR_OF_DAY);
                int mMinute = c.get(Calendar.MINUTE);

                TimePickerDialog timePickerDialog = new TimePickerDialog(
                Hitung04_simpan.this,
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
                String dateTime = date.getEditText().getText().toString();
                date.getEditText().setText(dateTime+" "+hourOfDay + ":" + minute + ":00");
            }
        };

        ActionBar actbar;
        actbar = getSupportActionBar();
        actbar.setHomeButtonEnabled(true);

        if (getIntent().hasExtra("hide")){
            String id = extras.getString("id");

            DialogInterface.OnClickListener dialogInt = new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    switch (which){
                        case DialogInterface.BUTTON_POSITIVE:
                            databaseHandler.DeleteRecordData(id);
                            Toast.makeText(context, "Data berhasil di hapus", Toast.LENGTH_SHORT).show();
                            finish();
                            break;
                        case DialogInterface.BUTTON_NEGATIVE:
                            break;
                    }
                }
            };

            actbar.setTitle("Informasi Berkas");
            date.setVisibility(View.GONE);
            nama.setVisibility(View.GONE);
            simpan.setText("Hapus");
            simpan.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_baseline_delete_24, 0);
            simpan.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
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
        String date = this.date.getEditText().getText().toString();
        try {
            jsonObject.put("nama", nama);
            jsonObjectval.put("cpo", cpo.getText());
            jsonObjectval.put("inti", inti.getText());
            jsonObjectval.put("storage", storage.getText());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.d("date_save", date);
        Log.d("nama_kebun", nama);
        Log.d("cpo", cpo.getText().toString());
        Log.d("inti", inti.getText().toString());
        Log.d("storage", storage.getText().toString());

        Long id_record = databaseHandler.SaveRecord(date, 4);
        if (id_record!=-1){
            Integer rec = Math.toIntExact(id_record);
            if (databaseHandler.SaveItem(jsonObject.toString(), rec)){
                if (databaseHandler.SaveRecordValue(jsonObjectval.toString(), rec))
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