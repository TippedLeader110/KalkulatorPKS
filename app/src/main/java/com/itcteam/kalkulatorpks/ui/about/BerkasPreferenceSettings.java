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
import com.itcteam.kalkulatorpks.ui.about.equipment.EquipmentPreferenceSettings;
import com.itcteam.kalkulatorpks.ui.about.equipment.ListBerkas_eq;
import com.itcteam.kalkulatorpks.ui.about.losses_pabrik.ListBerkas_lb;
import com.itcteam.kalkulatorpks.ui.about.losses_pabrik.LossesPabrikPreferenceSettings;
import com.itcteam.kalkulatorpks.ui.about.material_balance.ListBerkas_mb;
import com.itcteam.kalkulatorpks.ui.about.material_balance.MaterialBalancePreferenceSettings;
import com.itcteam.kalkulatorpks.ui.about.mutu.ListBerkas_mt;
import com.itcteam.kalkulatorpks.ui.about.perhitungan_rendemen.ListBerkas_pr;
import com.itcteam.kalkulatorpks.ui.about.perhitungan_rendemen.RendemenPreferenceSettings;
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
                Intent intent = new Intent(getActivity(), MaterialBalancePreferenceSettings.class);
                intent.putExtra("export", "false");
                startActivity(intent);
                return true;
            }
        });


        Preference berkas_4 = this.findPreference("berkas_pr_01");
        berkas_4.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                Intent intent = new Intent(getActivity(), ListBerkas_pr.class);
                intent.putExtra("export", "false");
                startActivity(intent);
                return true;
            }
        });

        Preference berkas_5 = this.findPreference("berkas_pr_02");
        berkas_5.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                Intent intent = new Intent(getActivity(), RendemenPreferenceSettings.class);
                intent.putExtra("export", "false");
                startActivity(intent);
                return true;
            }
        });



        Preference berkas_7 = this.findPreference("berkas_eq_01");
        berkas_7.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                Intent intent = new Intent(getActivity(), ListBerkas_eq.class);
                intent.putExtra("export", "false");
                startActivity(intent);
                return true;
            }
        });

        Preference berkas_8 = this.findPreference("berkas_eq_02");
        berkas_8.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                Intent intent = new Intent(getActivity(), EquipmentPreferenceSettings.class);
                intent.putExtra("export", "false");
                startActivity(intent);
                return true;
            }
        });



        Preference berkas_losses_list = this.findPreference("berkas_lb_01");
        berkas_losses_list.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                Intent intent = new Intent(getActivity(), ListBerkas_lb.class);
                intent.putExtra("export", "false");
                startActivity(intent);
                return true;
            }
        });

        Preference berkas_losses_export = this.findPreference("berkas_lb_02");
        berkas_losses_export.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                Intent intent = new Intent(getActivity(), ListBerkas_lb.class);
                intent.putExtra("export", "true");
                startActivity(intent);
                return true;
            }
        });

        Preference berkas_mutu_cpo = this.findPreference("berkas_mt_01");
        berkas_mutu_cpo.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                Intent intent = new Intent(getActivity(), ListBerkas_mt.class);
                intent.putExtra("export", "false");
                intent.putExtra("tipe", "1");
                startActivity(intent);
                return true;
            }
        });

        Preference berkas_mutu_inti = this.findPreference("berkas_mt_02");
        berkas_mutu_inti.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                Intent intent = new Intent(getActivity(), ListBerkas_mt.class);
                intent.putExtra("export", "false");
                intent.putExtra("tipe", "25");
                startActivity(intent);
                return true;
            }
        });

        Preference berkas_mutu_export_cpo = this.findPreference("berkas_mt_03");
        berkas_mutu_export_cpo.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                Intent intent = new Intent(getActivity(), ListBerkas_mt.class);
                intent.putExtra("export", "true");
                intent.putExtra("tipe", "2");
                startActivity(intent);
                return true;
            }
        });

        Preference berkas_mutu_export_inti = this.findPreference("berkas_mt_04");
        berkas_mutu_export_inti.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                Intent intent = new Intent(getActivity(), ListBerkas_mt.class);
                intent.putExtra("export", "true");
                intent.putExtra("tipe", "25");
                startActivity(intent);
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
