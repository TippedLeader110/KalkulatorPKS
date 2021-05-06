package com.itcteam.kalkulatorpks.ui.about.perhitungan_rendemen;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;

import com.itcteam.kalkulatorpks.R;
import com.itcteam.kalkulatorpks.ui.about.material_balance.ExportFilterDate_mb;
import com.itcteam.kalkulatorpks.ui.about.material_balance.ListBerkas_mb;
import com.itcteam.kalkulatorpks.ui.about.material_balance.MaterialBalancePreferenceSettings;

public class PRPrefSet extends PreferenceFragmentCompat {

    ProgressDialog dialog;
    RendemenPreferenceSettings rendemenPreferenceSettings;

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.material_balance, rootKey);
        dialog = new ProgressDialog(getContext());
    }

    @Override
    public void onAttach(@NonNull Context context) {
        rendemenPreferenceSettings = (RendemenPreferenceSettings) context;
        super.onAttach(context);
    }

    @Override
    public void onResume() {
        super.onResume();
        Preference berkas = this.findPreference("berkas_mb_01");
        berkas.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public boolean onPreferenceClick(Preference preference) {
                dialog.setTitle("Sedang mengexport.....");
                if (!dialog.isShowing()){
                    dialog.show();
                }
                if (rendemenPreferenceSettings.backProc()){
                    dialog.dismiss();
                }
//                exportAll();
                return true;
            }


        });

        Preference berkas_2 = this.findPreference("berkas_mb_02");
        berkas_2.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                Intent intent = new Intent(getActivity(), ListBerkas_mb.class);
                intent.putExtra("export", "true");
                startActivity(intent);
                return true;
            }
        });

        Preference berkas_3 = this.findPreference("berkas_mb_03");
        berkas_3.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                Intent intent = new Intent(getActivity(), ExportFilterDate_mb.class);
                intent.putExtra("export", "true");
                startActivity(intent);
                return true;
            }
        });
    }

    interface callBack{
        boolean backProc();
    }
}
