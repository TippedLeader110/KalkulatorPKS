package com.itcteam.kalkulatorpks.ui.calculate.task;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.itcteam.kalkulatorpks.R;
import com.itcteam.kalkulatorpks.ui.calculate.task.task.Hitung02_alb;
import com.itcteam.kalkulatorpks.ui.calculate.task.task.Hitung02_cpo_air;
import com.itcteam.kalkulatorpks.ui.calculate.task.task.Hitung02_cpo_kotoran;
import com.itcteam.kalkulatorpks.ui.calculate.task.task.Hitung02_cpo_simpan;
import com.itcteam.kalkulatorpks.ui.calculate.task.task.Hitung02_dobi;
import com.itcteam.kalkulatorpks.ui.calculate.task.task.Hitung02_inti_air;
import com.itcteam.kalkulatorpks.ui.calculate.task.task.Hitung02_inti_kotoran;
import com.itcteam.kalkulatorpks.ui.calculate.task.task.Hitung02_inti_simpan;

import org.json.JSONException;
import org.json.JSONObject;

public class Hitung02 extends AppCompatActivity implements Hitung02_select.SelectModalListener{

    SettingsFragment settingsFragment;
    ActionBar actbar;
    FloatingActionButton save;
    Preference alb, dobi, air_cpo, kotoran_cpo, air_inti, kotoran_inti;
    float cpo_alb, cpo_air, cpo_kotoran, cpo_dobi;
    float inti_air, inti_kotoran;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setDefault();
        setContentView(R.layout.activity_preference);
        settingsFragment = new SettingsFragment();
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.pref_menu, settingsFragment)
                .commit();
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        save = findViewById(R.id.fab_save);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Hitung02_select hitung02_select = new Hitung02_select(false);
                hitung02_select.show(getSupportFragmentManager(), "");
            }
        });

        actbar = getSupportActionBar();
        actbar.setHomeButtonEnabled(true);
        actbar.setTitle(R.string.cal_02);
        actbar.setDisplayHomeAsUpEnabled(true);

    }

    public void setDefault(){
        cpo_alb = Float.valueOf(0);
        cpo_air = Float.valueOf(0);
        cpo_kotoran = Float.valueOf(0);
        cpo_dobi = Float.valueOf(0);
        inti_air = Float.valueOf(0);
        inti_kotoran = Float.valueOf(0);
    }

    @Override
    protected void onResume() {
        super.onResume();
        alb = settingsFragment.findPreference("h02_alb");
        alb.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                startActivityForResult(new Intent(Hitung02.this, Hitung02_alb.class), 1);
                return true;
            }
        });

        dobi = settingsFragment.findPreference("h02_dobi");
        dobi.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                startActivityForResult(new Intent(Hitung02.this, Hitung02_dobi.class), 1);
                return true;
            }
        });

        air_cpo = settingsFragment.findPreference("h02_air_cpo");
        air_cpo.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                startActivityForResult(new Intent(Hitung02.this, Hitung02_cpo_air.class), 1);
                return true;
            }
        });

        kotoran_cpo = settingsFragment.findPreference("h02_kotoran_cpo");
        kotoran_cpo.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                startActivityForResult(new Intent(Hitung02.this, Hitung02_cpo_kotoran.class), 1);
                return true;
            }
        });

        air_inti = settingsFragment.findPreference("h02_air_inti");
        air_inti.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                startActivityForResult(new Intent(Hitung02.this, Hitung02_inti_air.class), 1);
                return true;
            }
        });

        kotoran_inti = settingsFragment.findPreference("h02_kotoran_inti");
        kotoran_inti.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                startActivityForResult(new Intent(Hitung02.this, Hitung02_inti_kotoran.class), 1);
                return true;
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.w("ResultCode", String.valueOf(resultCode));
        if (resultCode==1){
            cpo_alb = Float.valueOf(data.getStringExtra("alb"));
            Log.w("cpo_alb", String.valueOf(cpo_alb));
            Toast.makeText(Hitung02.this, "CPO ALB : " + String.valueOf(cpo_alb), Toast.LENGTH_SHORT).show();
        }else if (resultCode==2){
            cpo_air = Float.valueOf(data.getStringExtra("air"));
            Log.w("cpo_air", String.valueOf(cpo_air));
            Toast.makeText(Hitung02.this, "CPO Air : "+ String.valueOf(cpo_alb), Toast.LENGTH_SHORT).show();
        }else if (resultCode==3){
            cpo_kotoran = Float.valueOf(data.getStringExtra("kotoran"));
            Log.w("cpo_kotoran", String.valueOf(cpo_kotoran));
            Toast.makeText(Hitung02.this, "CPO Kotoran : " + cpo_kotoran, Toast.LENGTH_SHORT).show();
        }else if(resultCode==4){
            cpo_dobi = Float.valueOf(data.getStringExtra("dobi"));
            Log.w("cpo_dobi", String.valueOf(cpo_dobi));
            Toast.makeText(Hitung02.this, "CPO DOBI :" + cpo_dobi, Toast.LENGTH_SHORT).show();
        }else if (resultCode==5){
            inti_air = Float.valueOf(data.getStringExtra("inti_air"));
            Log.w("inti_air", String.valueOf(inti_air));
            Toast.makeText(Hitung02.this, "Inti Air :" + inti_air, Toast.LENGTH_SHORT).show();
        }else if(resultCode==6){
            inti_kotoran = Float.valueOf(data.getStringExtra("inti_kotoran"));
            Log.w("inti_kotoran", String.valueOf(inti_kotoran));
            Toast.makeText(Hitung02.this, "Inti Kotoran : " + inti_kotoran, Toast.LENGTH_SHORT).show();
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

    @Override
    public void selectCPO_Inti(int i) {
        if (i==1){

            try {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("cpo_alb", cpo_alb);
                jsonObject.put("cpo_air", cpo_air);
                jsonObject.put("cpo_kotoran", cpo_kotoran);
                jsonObject.put("cpo_dobi", cpo_dobi);
                Toast.makeText(this, "Menyimpan CPO", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(Hitung02.this, Hitung02_cpo_simpan.class);
                intent.putExtra("json", jsonObject.toString());
                startActivity(intent);
            }catch (JSONException e) {
                e.printStackTrace();
            }

        }else{

            try {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("inti_air", inti_air);
                jsonObject.put("inti_kotoran", inti_kotoran);
                Toast.makeText(this, "Menyimpan Inti", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(Hitung02.this, Hitung02_inti_simpan.class);
                intent.putExtra("json", jsonObject.toString());
                startActivity(intent);
            }catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }

    public static class SettingsFragment extends PreferenceFragmentCompat {
        @Override
        public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
            setPreferencesFromResource(R.xml.hitung02_daftar, rootKey);
        }
    }
}