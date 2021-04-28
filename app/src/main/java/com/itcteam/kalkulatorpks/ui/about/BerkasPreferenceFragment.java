package com.itcteam.kalkulatorpks.ui.about;

import android.app.ActionBar;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;

import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.itcteam.kalkulatorpks.MainMenu;
import com.itcteam.kalkulatorpks.R;
import com.itcteam.kalkulatorpks.ui.about.material_balance.MaterialBalancePreferenceSettings;
import com.itcteam.kalkulatorpks.ui.calculate.task.fragment.Hitung05_perfomance;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class BerkasPreferenceFragment extends Fragment implements BerkasPreferenceSettings.callBackPref{

    PreferenceFragmentCompat preferenceFragmentCompat;
    FragmentManager childFragMan;
    FragmentTransaction childFragTrans;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.activity_preference_berkas, container, false);

//        Toolbar toolbar = root.findViewById(R.id.toolbar);
//        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);

        Toolbar mToolbar = (Toolbar) root.findViewById(R.id.toolbar_berkas);
        if (mToolbar != null) {
            ((AppCompatActivity)getActivity()).setSupportActionBar(mToolbar);
        }
        mToolbar.setTitle("Berkas Tersmipan");

        try {
            Log.w("DATE CEK", "DATE CC : " + isWithinRange(new SimpleDateFormat("yyyy-MM-dd").parse("2021-03-15")));
        } catch (ParseException e) {
            e.printStackTrace();
        }
//
//        childFragMan = getChildFragmentManager();
//        childFragTrans = childFragMan.beginTransaction();
//        childFragTrans.add(R.id.frame_berkas, BerkasPreferenceSettings.class, null).commit();

        getChildFragmentManager().beginTransaction().
                replace(R.id.frame_berkas, BerkasPreferenceSettings.class, null).
                setReorderingAllowed(true).
                commit();

        return root;
    }

    boolean isWithinRange(Date testDate) {
        Date start = null, end = null;
        try {
            String sDate1="2021-04-01";
            String sDate2="2021-04-26";
            start = new SimpleDateFormat("yyyy-MM-dd").parse(sDate1);
            end = new SimpleDateFormat("yyyy-MM-dd").parse(sDate2);

        } catch (ParseException e) {
            e.printStackTrace();
        }
        return !(testDate.before(start) || testDate.after(end));
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        try {
            ((MainMenu) getActivity()).onListenerBerkas(this);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void FagmentSwitch(int idFragment) {
        switch (idFragment){
            case 1:
                getChildFragmentManager().beginTransaction().
                        replace(R.id.frame_berkas, MaterialBalancePreferenceSettings.class, null).
                        setReorderingAllowed(true).
                        commit();
//                childFragTrans.replace(R.id.frame_berkas, MaterialBalancePreferenceSettings.class, null).commit();
            default:

        }
    }
}