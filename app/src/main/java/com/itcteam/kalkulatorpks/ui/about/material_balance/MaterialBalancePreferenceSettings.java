package com.itcteam.kalkulatorpks.ui.about.material_balance;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.FragmentActivity;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;

import com.itcteam.kalkulatorpks.R;
import com.itcteam.kalkulatorpks.db.DatabaseHandler;
import com.itcteam.kalkulatorpks.ui.about.ExportCSV;
import com.itcteam.kalkulatorpks.ui.calculate.task.task.Hitung04_cpo;
import com.itcteam.kalkulatorpks.ui.calculate.task.task.Hitung04_inti;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MaterialBalancePreferenceSettings extends PreferenceFragmentCompat {

    DatabaseHandler databaseHandler;
    ProgressDialog dialog;
    private Context myContext;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        dialog = new ProgressDialog(getContext());
    }

    @Override
    public void onAttach(@NonNull Context context) {
        myContext=context;
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
                exportAll();
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

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.material_balance, rootKey);
    }

    public interface callBackPref{

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void exportAll() {

        databaseHandler = new DatabaseHandler(getContext());
        List<HashMap<String, String >> dataRecord = new ArrayList(databaseHandler.getRecord(1));

        String lineCSV =  "Tanggal,Nama Kebun,Faksi Matang,Tahun Tanam,TBS,Tangkos Hasil,tangkos Hasil Persen,Serat Hasil,Serat Hasil Persen,Cangkang Hasil,Cangkang Hasil Persen,Inti Hasil,Inti Hasil Peresn,Cpo Hasil,Cpo Hasil Persen,Dirt Hasil,Dirt Hasil Persen\n";
        List<String > arrayCSV = new ArrayList<>();
        arrayCSV.add(lineCSV);

        for (HashMap<String, String> hash : dataRecord){
            try {
                JSONObject jsonRecord = new JSONObject(databaseHandler.getRecordValue(hash.get("id_record"), 1));
                JSONObject jsonItem = new JSONObject(databaseHandler.getItemValue(hash.get("id_record")));

                String lines = hash.get("date")+ "," +
                        jsonItem.get("nama")+ "," +
                        jsonItem.get("matang")+ "," +
                        jsonItem.get("tanam")+ "," +
                        jsonRecord.getString("tbs")+ "," +
                        jsonRecord.getString("tangkosHasil")+ "," +
                        jsonRecord.getString("tangkosHasilp")+ "," +
                        jsonRecord.getString("seratHasil")+ "," +
                        jsonRecord.getString("seratHasilp")+ "," +
                        jsonRecord.getString("cangkangHasil")+ "," +
                        jsonRecord.getString("cangkangHasilp")+ "," +
                        jsonRecord.getString("intiHasil")+ "," +
                        jsonRecord.getString("intiHasilp")+ "," +
                        jsonRecord.getString("cpoHasil")+ "," +
                        jsonRecord.getString("cpoHasilp")+ "," +
                        jsonRecord.getString("dirtHasil")+ "," +
                        jsonRecord.getString("dirtHasilp");

                arrayCSV.add(lines+"\n");

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        ExportCSV exportCSV = new ExportCSV(arrayCSV, getContext());

        if (dialog.isShowing())
            dialog.dismiss();

        if (exportCSV.DoExportCSV("MaterialBalanceALL")){
            Toast.makeText(getContext(), "CSV Berhasil disimpan !!!", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(getContext(), "Terjadi kesalahan !!!", Toast.LENGTH_SHORT).show();
        }


    }
}
