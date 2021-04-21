package com.itcteam.kalkulatorpks.ui.about.task;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.itcteam.kalkulatorpks.R;
import com.itcteam.kalkulatorpks.db.DatabaseHandler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ListBerkas extends AppCompatActivity {

    RecyclerView recyclerView;
    List daftarBerkas;
    DatabaseHandler databaseHandler;
    RecyclerListBerkas recyclerListBerkas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_berkas);
        daftarBerkas = new ArrayList();
        databaseHandler = new DatabaseHandler(this);
        recyclerView = this.findViewById(R.id.list_berkas);

        daftarBerkas = getBerkas(1);
        Log.d("DDD", daftarBerkas.get(0).toString());
        recyclerListBerkas = new RecyclerListBerkas(this, daftarBerkas, 1);
        recyclerView.setAdapter(recyclerListBerkas);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    public ArrayList getBerkas(int i){
        List data = new ArrayList();
        data = databaseHandler.getRecord(i);
        return (ArrayList) data;
    };

}