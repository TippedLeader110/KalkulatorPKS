package com.itcteam.kalkulatorpks.ui.calculate.task;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.itcteam.kalkulatorpks.R;
import com.itcteam.kalkulatorpks.ui.calculate.task.task.Hitung03_kernel;
import com.itcteam.kalkulatorpks.ui.calculate.task.task.Hitung03_minyak;
import com.itcteam.kalkulatorpks.ui.calculate.task.task.Hitung03_usb;
import com.itcteam.kalkulatorpks.ui.calculate.task.task.Hitung03_usf;

import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;

public class Hitung03 extends AppCompatActivity {

    SettingsFragment settingsFragment;
    ActionBar actbar;
    Preference usb, usf, minyak, kernel;

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
        usb = settingsFragment.findPreference("h03_usb");
        usb.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                startActivity(new Intent(Hitung03.this, Hitung03_usb.class));
                return true;
            }
        });

        usf = settingsFragment.findPreference("h03_usf");
        usf.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                startActivity(new Intent(Hitung03.this, Hitung03_usf.class));
                return true;
            }
        });

        minyak = settingsFragment.findPreference("h03_minyak");
        minyak.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                startActivity(new Intent(Hitung03.this, Hitung03_minyak.class));
                return true;
            }
        });

        kernel = settingsFragment.findPreference("h03_kernel");
        kernel.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                startActivity(new Intent(Hitung03.this, Hitung03_kernel.class));
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
            setPreferencesFromResource(R.xml.hitung03_daftar, rootKey);
        }
    }
}