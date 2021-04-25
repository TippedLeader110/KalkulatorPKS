package com.itcteam.kalkulatorpks.ui.about.material_balance;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.itcteam.kalkulatorpks.R;
import com.itcteam.kalkulatorpks.db.DatabaseHandler;

import java.util.ArrayList;
import java.util.List;

public class ListBerkas_mb extends AppCompatActivity implements  RecyclerFilterModal_mb.FilterModalListener{

    RecyclerView recyclerView;
    String firstDate, endDate;
    List daftarBerkas;
    FloatingActionButton fab;
    DatabaseHandler databaseHandler;
    RecyclerListBerkas_mb recyclerListBerkas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_berkas);
        daftarBerkas = new ArrayList();
        databaseHandler = new DatabaseHandler(this);
        recyclerView = this.findViewById(R.id.list_berkas);
        ActionBar actbar;
        actbar = getSupportActionBar();
        actbar.setHomeButtonEnabled(true);
        actbar.setTitle("Daftar Berkas");
        actbar.setDisplayHomeAsUpEnabled(true);

        fab = this.findViewById(R.id.fab_filter_tanggal);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RecyclerFilterModal_mb recyclerFilterModal = new RecyclerFilterModal_mb();
                recyclerFilterModal.show(getSupportFragmentManager(), "");
            }
        });

        daftarBerkas = getBerkas(1, false);

        recyclerListBerkas = new RecyclerListBerkas_mb(this, daftarBerkas, 1);

        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        recyclerView.setAdapter(recyclerListBerkas);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    public ArrayList getBerkas(int i, Boolean filter){
        List data = new ArrayList();
        if (filter){
            data = databaseHandler.getRecordFilter(i, firstDate, endDate);
            Log.w("DB", "Filter");
        }else{
            data = databaseHandler.getRecord(i);
            Log.w("DB", "Non-Filter");
        }
        return (ArrayList) data;
    };


    public void prosesData(Boolean filter){
        daftarBerkas = getBerkas(1, filter);
        recyclerListBerkas.triggerDate(daftarBerkas);
    }

    @Override
    public void TanggalFilter(String first, String end) {
        this.firstDate = first;
        this.endDate = end;
        prosesData(true);
    }

}