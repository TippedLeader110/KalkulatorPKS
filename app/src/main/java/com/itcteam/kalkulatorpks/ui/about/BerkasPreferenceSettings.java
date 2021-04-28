package com.itcteam.kalkulatorpks.ui.about;

import android.content.Context;
import android.content.Intent;
import android.net.sip.SipSession;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;

import com.itcteam.kalkulatorpks.MainMenu;
import com.itcteam.kalkulatorpks.R;
import com.itcteam.kalkulatorpks.ui.about.material_balance.ListBerkas_mb;
import com.itcteam.kalkulatorpks.ui.calculate.task.task.Hitung04_cpo;
import com.itcteam.kalkulatorpks.ui.calculate.task.task.Hitung04_inti;
import com.itcteam.kalkulatorpks.ui.home.HomeFragment;

public class BerkasPreferenceSettings extends PreferenceFragmentCompat {

    BerkasPreferenceFragment listener;
    Fragment mParentFragment;

    @Override
    public void onResume() {
        super.onResume();

        mParentFragment =(BerkasPreferenceFragment) getParentFragment();

        Preference berkas = this.findPreference("berkas_mb_01");
        berkas.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                Intent intent = new Intent(getActivity(), ListBerkas_mb.class);
                intent.putExtra("export", "false");
                startActivity(intent);
                return true;
            }
        });

        Preference berkas_2 = this.findPreference("berkas_mb_02");
        berkas_2.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                if (mParentFragment instanceof BerkasPreferenceFragment){
                    ((BerkasPreferenceFragment) mParentFragment).FagmentSwitch(1);
                }
                return true;
            }
        });

        Preference berkas_3 = this.findPreference("berkas_mb_03");
        berkas_3.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                startActivity(new Intent(getActivity(), Hitung04_inti.class));
                return true;
            }
        });
    }



    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.berkas, rootKey);
    }

    public interface callBackPref{
        void FagmentSwitch(int idFragment);
    }
}
