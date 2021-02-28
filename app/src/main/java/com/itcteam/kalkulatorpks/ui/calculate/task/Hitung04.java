package com.itcteam.kalkulatorpks.ui.calculate.task;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;

import com.itcteam.kalkulatorpks.R;
import com.itcteam.kalkulatorpks.ui.calculate.task.task.Hitung04_cpo;
import com.itcteam.kalkulatorpks.ui.calculate.task.task.Hitung04_inti;
import com.itcteam.kalkulatorpks.ui.calculate.task.task.Hitung04_storage;

public class Hitung04 extends AppCompatActivity {

    SettingsFragment settingsFragment;
    ActionBar actbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

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
                startActivity(new Intent(Hitung04.this, Hitung04_storage.class));
                return true;
            }
        });

        Preference h04_2 = settingsFragment.findPreference("h04_02");
        h04_2.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                startActivity(new Intent(Hitung04.this, Hitung04_cpo.class));
                return true;
            }
        });

        Preference h04_3 = settingsFragment.findPreference("h04_03");
        h04_3.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                startActivity(new Intent(Hitung04.this, Hitung04_inti.class));
                return true;
            }
        });
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

    public static class SettingsFragment extends PreferenceFragmentCompat {
        @Override
        public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
            setPreferencesFromResource(R.xml.hitung04_daftar, rootKey);
        }
    }
}