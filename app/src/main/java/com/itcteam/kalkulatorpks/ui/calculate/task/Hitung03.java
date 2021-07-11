package com.itcteam.kalkulatorpks.ui.calculate.task;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.itcteam.kalkulatorpks.R;
import com.itcteam.kalkulatorpks.ui.calculate.task.task.Hitung03_kernel;
import com.itcteam.kalkulatorpks.ui.calculate.task.task.Hitung03_minyak;
import com.itcteam.kalkulatorpks.ui.calculate.task.task.Hitung03_simpan;
import com.itcteam.kalkulatorpks.ui.calculate.task.task.Hitung03_usb;
import com.itcteam.kalkulatorpks.ui.calculate.task.task.Hitung03_usf;
import com.itcteam.kalkulatorpks.ui.calculate.task.task.Hitung04_inti;

import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;

import org.json.JSONException;
import org.json.JSONObject;

public class Hitung03 extends AppCompatActivity {

    SettingsFragment settingsFragment;
    ActionBar actbar;
    FloatingActionButton fab;
    String usbS, usfS, minyakS, kernelS;
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

        fab = this.findViewById(R.id.fab_save);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    JSONObject jsonObject = new JSONObject();
                    JSONObject usbJson = new JSONObject(usbS);
                    JSONObject usfJson = new JSONObject(usfS);
                    JSONObject kernelJson = new JSONObject(kernelS);
                    JSONObject minyakJson = new JSONObject(minyakS);

                    if (usbS!="{}"){

                        jsonObject.put("USB", usbJson);
                    }

                    if (usfS!="{}"){

                        jsonObject.put("USF", usfJson);
                    }

                    if (kernelS!="{}"){

                        jsonObject.put("Kernel", kernelJson);
                    }

                    if (minyakS!="{}"){

                        jsonObject.put("Minyak", minyakJson);
                    }

                    Intent intent = new Intent(Hitung03.this, Hitung03_simpan.class);
                    intent.putExtra("json", jsonObject.toString());
                    startActivity(intent);

                    Log.w("JSON COMBINE", jsonObject.toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

        actbar = getSupportActionBar();
        actbar.setHomeButtonEnabled(true);
        actbar.setTitle(R.string.cal_03);
        actbar.setDisplayHomeAsUpEnabled(true);

        usbS = "{}";
        usfS = "{}";
        minyakS = "{}";
        kernelS = "{}";
        fab.setEnabled(false);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable  Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.w("ResultCode", String.valueOf(resultCode));
        if (resultCode==1){
            usbS = data.getStringExtra("usb");
            Log.w("Trigger", String.valueOf(resultCode));
        }else if (resultCode==2){
            usfS = data.getStringExtra("usf");
            Log.w("Trigger", String.valueOf(resultCode));
        }else if (resultCode==3){
            minyakS = data.getStringExtra("minyak");
            Log.w("Trigger", String.valueOf(resultCode));
        }else if(resultCode==4){
            kernelS = data.getStringExtra("kernel");
            Log.w("Trigger", String.valueOf(resultCode));
        }

        checkValue();

        postValue();
    }

    private void checkValue() {
        if (usbS!="{}" && usfS!="{}" && kernelS!="{}" && minyakS!="{}"){
            fab.setEnabled(true);
        }else{
            fab.setEnabled(false);
        }
    }

    private void postValue() {
        String postVal = "USB : " + usbS + "\n" +
                "USF : " + usfS + "\n" +
                "Minyak : " + minyakS + "\n" +
                "Kernel : " + kernelS;
        Log.w("Backtipe Return", postVal);
//        Toast.makeText(this, postVal, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        usb = settingsFragment.findPreference("h03_usb");
        usb.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                startActivityForResult(new Intent(Hitung03.this, Hitung03_usb.class), 1);
                return true;
            }
        });

        usf = settingsFragment.findPreference("h03_usf");
        usf.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                startActivityForResult(new Intent(Hitung03.this, Hitung03_usf.class), 2);
                return true;
            }
        });

        minyak = settingsFragment.findPreference("h03_minyak");
        minyak.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                startActivityForResult(new Intent(Hitung03.this, Hitung03_minyak.class), 3);
                return true;
            }
        });

        kernel = settingsFragment.findPreference("h03_kernel");
        kernel.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                startActivityForResult(new Intent(Hitung03.this, Hitung03_kernel.class), 4);
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