package com.itcteam.kalkulatorpks.ui.calculate.task;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.itcteam.kalkulatorpks.R;
import com.itcteam.kalkulatorpks.ui.calculate.task.task.Hitung04_cpo;
import com.itcteam.kalkulatorpks.ui.calculate.task.task.Hitung04_inti;
import com.itcteam.kalkulatorpks.ui.calculate.task.task.Hitung04_simpan;
import com.itcteam.kalkulatorpks.ui.calculate.task.task.Hitung04_storage;

public class Hitung04 extends AppCompatActivity implements Hitung04_cpo.InterfaceSave{

    SettingsFragment settingsFragment;
    ActionBar actbar;
    FloatingActionButton save;
    String cpo, inti, storage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preference);
        cpo = "";
        inti = "";
        storage = "";

        settingsFragment = new SettingsFragment();
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.pref_menu, settingsFragment)
                .commit();
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        save = this.findViewById(R.id.fab_save);
        save.setEnabled(false);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Hitung04.this, Hitung04_simpan.class);
                intent.putExtra("cpo", cpo);
                intent.putExtra("inti", inti);
                intent.putExtra("storage", storage);
                startActivity(intent);
            }
        });

        actbar = getSupportActionBar();
        actbar.setHomeButtonEnabled(true);
        setJudul();
        actbar.setDisplayHomeAsUpEnabled(true);

    }

    public void setJudul(){
        actbar.setTitle(R.string.cal_04);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Preference h04 = settingsFragment.findPreference("h04_01");
        h04.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                startActivityForResult(new Intent(Hitung04.this, Hitung04_storage.class), 1);
                return true;
            }
        });

        Preference h04_2 = settingsFragment.findPreference("h04_02");
        h04_2.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                startActivityForResult(new Intent(Hitung04.this, Hitung04_cpo.class), 1);
//                startActivity(new Intent(Hitung04.this, Hitung04_cpo.class));
                return true;
            }
        });

        Preference h04_3 = settingsFragment.findPreference("h04_03");
        h04_3.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                startActivityForResult(new Intent(Hitung04.this, Hitung04_inti.class), 1);
                return true;
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            Log.w("ResultCode", String.valueOf(resultCode));

            if(resultCode == 1) {
                if (data.hasExtra("cpo")){
                    cpo = data.getStringExtra("cpo");
                    Toast.makeText(this, "CPO : " + cpo, Toast.LENGTH_SHORT).show();
                }
            }else if (resultCode == 2){
                if (data.hasExtra("inti")){
                    inti = data.getStringExtra("inti");
                    Toast.makeText(this, "Inti : " + inti, Toast.LENGTH_SHORT).show();
                }
            }else{
                if (data.hasExtra("storage")){
                    storage = data.getStringExtra("storage");
                    Toast.makeText(this, "Storage : "+storage, Toast.LENGTH_SHORT).show();
                }
            }
        }
        checkValid();
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
    public void KembalikanData(String a, String b) {
        switch (a){
            case "cpo":
                this.cpo = b;
            case "inti":
                this.inti = b;
            case "storage":
                this.storage = b;
        }

        checkValid();
    }

    private void checkValid() {
        Boolean valid = true;
        if (cpo.equals("")){
            valid = false;
        }else if(inti.equals("")){
            valid = false;
        }else if (storage.equals("")){
            valid = false;
        }

        if (valid){
            save.setEnabled(true);
        }

    }

    public static class SettingsFragment extends PreferenceFragmentCompat {
        @Override
        public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
            setPreferencesFromResource(R.xml.hitung04_daftar, rootKey);
        }
    }
}