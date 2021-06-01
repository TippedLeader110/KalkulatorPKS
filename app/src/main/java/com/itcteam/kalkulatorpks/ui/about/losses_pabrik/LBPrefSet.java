package com.itcteam.kalkulatorpks.ui.about.losses_pabrik;

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
import com.itcteam.kalkulatorpks.ui.about.equipment.EquipmentPreferenceSettings;
import com.itcteam.kalkulatorpks.ui.about.equipment.ExportFilterDate_eq;
import com.itcteam.kalkulatorpks.ui.about.equipment.ListBerkas_eq;

public class LBPrefSet extends PreferenceFragmentCompat {

    ProgressDialog dialog;
    EquipmentPreferenceSettings equipmentPreferenceSettings;

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.equipment, rootKey);
        dialog = new ProgressDialog(getContext());
    }

    @Override
    public void onAttach(@NonNull Context context) {
        equipmentPreferenceSettings = (EquipmentPreferenceSettings) context;
        super.onAttach(context);
    }

    @Override
    public void onResume() {
        super.onResume();
        Preference berkas = this.findPreference("berkas_eq_01");
        berkas.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public boolean onPreferenceClick(Preference preference) {
                dialog.setTitle("Sedang mengexport.....");
                if (!dialog.isShowing()){
                    dialog.show();
                }
                if (equipmentPreferenceSettings.backProc()){
                    dialog.dismiss();
                }
//                exportAll();
                return true;
            }


        });

        Preference berkas_2 = this.findPreference("berkas_eq_02");
        berkas_2.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                Intent intent = new Intent(getActivity(), ListBerkas_eq.class);
                intent.putExtra("export", "true");
                startActivity(intent);
                return true;
            }
        });

        Preference berkas_3 = this.findPreference("berkas_eq_03");
        berkas_3.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                Intent intent = new Intent(getActivity(), ExportFilterDate_eq.class);
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
