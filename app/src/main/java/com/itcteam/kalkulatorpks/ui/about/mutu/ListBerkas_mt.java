package com.itcteam.kalkulatorpks.ui.about.mutu;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.itcteam.kalkulatorpks.R;
import com.itcteam.kalkulatorpks.ui.about.perhitungan_rendemen.RecyclerFilterModal_pr;
import com.itcteam.kalkulatorpks.ui.about.perhitungan_rendemen.RecyclerListBerkas_pr;
import com.itcteam.kalkulatorpks.util.DatabaseHandler;

import java.util.ArrayList;
import java.util.List;

public class ListBerkas_mt extends AppCompatActivity implements RecyclerFilterModal_mt.FilterModalListener {

    RecyclerView recyclerView;
    String firstDate, endDate;
    List daftarBerkas;
    FloatingActionButton fab;
    Boolean export;
    DatabaseHandler databaseHandler;
    RecyclerListBerkas_mt recyclerListBerkas;
    int tipe = 2;

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
        Bundle extras = getIntent().getExtras();

        tipe = Integer.valueOf(extras.getString("tipe"));

        if (extras.getString("export").equals("true")){
            export = true;
        }else{
            export = false;
        }

        fab = this.findViewById(R.id.fab_filter_tanggal);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RecyclerFilterModal_mt recyclerFilterModal = new RecyclerFilterModal_mt(false);
                recyclerFilterModal.show(getSupportFragmentManager(), "");
            }
        });

        daftarBerkas = getBerkas(tipe, false);

        recyclerListBerkas = new RecyclerListBerkas_mt(this, daftarBerkas, tipe, export);

        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        recyclerView.setAdapter(recyclerListBerkas);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search_charsec, menu);

        MenuItem menuItem = menu.findItem(R.id.menu_search);
        androidx.appcompat.widget.SearchView searchView = (androidx.appcompat.widget.SearchView) menuItem.getActionView();
        searchView.setOnQueryTextListener(new androidx.appcompat.widget.SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                recyclerListBerkas.getFilter().filter(newText);
                return false;
            }
        });


        return super.onCreateOptionsMenu(menu);
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
        Log.w("Data Berkas", String.valueOf(data));
        return (ArrayList) data;
    };


    public void prosesData(Boolean filter){
        daftarBerkas = getBerkas(tipe, filter);
        recyclerListBerkas.triggerDate(daftarBerkas);
    }

    @Override
    public void TanggalFilter(String first, String end) {
        this.firstDate = first;
        this.endDate = end;
        prosesData(true);
    }

}