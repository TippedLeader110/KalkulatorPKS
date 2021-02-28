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
import com.itcteam.kalkulatorpks.ui.calculate.task.task.Hitung02_alb;
import com.itcteam.kalkulatorpks.ui.calculate.task.task.Hitung02_cpo_air;
import com.itcteam.kalkulatorpks.ui.calculate.task.task.Hitung02_cpo_kotoran;
import com.itcteam.kalkulatorpks.ui.calculate.task.task.Hitung02_dobi;
import com.itcteam.kalkulatorpks.ui.calculate.task.task.Hitung02_inti_air;
import com.itcteam.kalkulatorpks.ui.calculate.task.task.Hitung02_inti_kotoran;

public class Hitung02 extends AppCompatActivity {

    SettingsFragment settingsFragment;
    ActionBar actbar;
    Preference alb, dobi, air_cpo, kotoran_cpo, air_inti, kotoran_inti;

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
        actbar.setTitle(R.string.cal_02);
        actbar.setDisplayHomeAsUpEnabled(true);

    }

    @Override
    protected void onResume() {
        super.onResume();
        alb = settingsFragment.findPreference("h02_alb");
        alb.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                startActivity(new Intent(Hitung02.this, Hitung02_alb.class));
                return true;
            }
        });

        dobi = settingsFragment.findPreference("h02_dobi");
        dobi.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                startActivity(new Intent(Hitung02.this, Hitung02_dobi.class));
                return true;
            }
        });

        air_cpo = settingsFragment.findPreference("h02_air_cpo");
        air_cpo.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                startActivity(new Intent(Hitung02.this, Hitung02_cpo_air.class));
                return true;
            }
        });

        kotoran_cpo = settingsFragment.findPreference("h02_kotoran_cpo");
        kotoran_cpo.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                startActivity(new Intent(Hitung02.this, Hitung02_cpo_kotoran.class));
                return true;
            }
        });

        air_inti = settingsFragment.findPreference("h02_air_inti");
        air_inti.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                startActivity(new Intent(Hitung02.this, Hitung02_inti_air.class));
                return true;
            }
        });

        kotoran_inti = settingsFragment.findPreference("h02_kotoran_inti");
        kotoran_inti.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                startActivity(new Intent(Hitung02.this, Hitung02_inti_kotoran.class));
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
            setPreferencesFromResource(R.xml.hitung02_daftar, rootKey);
        }
    }
}