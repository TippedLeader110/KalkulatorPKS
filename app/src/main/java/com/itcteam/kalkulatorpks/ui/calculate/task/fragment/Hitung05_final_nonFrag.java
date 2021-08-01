package com.itcteam.kalkulatorpks.ui.calculate.task.fragment;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.itcteam.kalkulatorpks.R;
import com.itcteam.kalkulatorpks.util.DatabaseHandler;

public class Hitung05_final_nonFrag extends AppCompatActivity {

    public Hitung05_final_nonFrag() {
        super(R.layout.fragment_hitung05_final);
    }

    TextView hasil, rumus;
    Button back, save;
    String hasils;
    DatabaseHandler databaseHandler;

    @Override
    protected void onCreate(@Nullable  Bundle savedInstanceState) {
        Context context = this;
        databaseHandler = new DatabaseHandler(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_hitung05_final);
        hasil = findViewById(R.id.hitung05_final_hasil);
        rumus = findViewById(R.id.hitung05_final_rumus2);
        final Bundle bundle = getIntent().getExtras();

        if (bundle == null) {
            Log.w("Bunnde", "Empty");
        } else {
            Float fhasil = Float.valueOf(bundle.getString("AV"))*Float.valueOf(bundle.getString("PR"));
            fhasil = fhasil*Float.valueOf(bundle.getString("QU"));
            fhasil *= 100;
            hasils = fhasil + "%";
            rumus.setText("OEE = " + bundle.getString("AV").toString() + "% x " + bundle.getString("PR").toString() + "%" +
                    " x " + bundle.getString("QU").toString() + "%");
            hasil.setText("OEE = " + bundle.getString("oee") + "%");
        }

        DialogInterface.OnClickListener dialogInt = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which){
                    case DialogInterface.BUTTON_POSITIVE:
                        String id = bundle.getString("id");
                        databaseHandler.DeleteRecordData(id);
                        Toast.makeText(context, "Data berhasil di hapus", Toast.LENGTH_SHORT).show();
                        finish();
                    case DialogInterface.BUTTON_NEGATIVE:
                        break;
                }
            }
        };

        back = findViewById(R.id.hitung05_final_retry);
        save = findViewById(R.id.hitung05_save);
        back.setText("Hapus");
        back.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_baseline_delete_24, 0);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(Hitung05_final_nonFrag.this);
                builder.setMessage("Hapus berkas ini ?").setPositiveButton("Ya", dialogInt).
                        setNegativeButton("Tidak", dialogInt).show();
            }
        });

//        back.setVisibility(View.GONE);

        save.setVisibility(View.GONE);
    }



}
