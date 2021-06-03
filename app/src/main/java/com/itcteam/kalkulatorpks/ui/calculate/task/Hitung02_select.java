package com.itcteam.kalkulatorpks.ui.calculate.task;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.textfield.TextInputLayout;
import com.itcteam.kalkulatorpks.R;
import com.itcteam.kalkulatorpks.ui.about.ExportCSV;
import com.itcteam.kalkulatorpks.ui.about.mutu.ListBerkas_mt;
import com.itcteam.kalkulatorpks.util.DatabaseHandler;

import java.util.Calendar;

public class Hitung02_select extends BottomSheetDialogFragment {

    Button cpo, inti;
    Hitung02 listener;
    Boolean export;

    public Hitung02_select(boolean export) {
        this.export = export;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        listener = (Hitung02) context;
        super.onAttach(context);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.modal_select_tipe, container, false);

        cpo = v.findViewById(R.id.hitung02_btn_cpo);
        inti = v.findViewById(R.id.hitung02_btn_inti);

        cpo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.selectCPO_Inti(1);
            }
        });

        inti.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.selectCPO_Inti(2);
            }
        });

        return v;
    }

    public interface SelectModalListener{
        void selectCPO_Inti(int i);
    }
}
