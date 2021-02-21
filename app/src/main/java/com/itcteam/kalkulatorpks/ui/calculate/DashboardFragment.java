package com.itcteam.kalkulatorpks.ui.calculate;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.itcteam.kalkulatorpks.R;
import com.itcteam.kalkulatorpks.ui.calculate.task.Calculate01;

public class DashboardFragment extends Fragment {

    private DashboardViewModel dashboardViewModel;

    Button cal01, cal02, cal03, cal04, cal05;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
//        dashboardViewModel =
//                ViewModelProviders.of(this).get(DashboardViewModel.class);
        View root = inflater.inflate(R.layout.fragment_calculate, container, false);

        cal01 = root.findViewById(R.id.cal_01);

        cal01.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Calculate01.class);
                startActivity(intent);
            }
        });

        cal02 = root.findViewById(R.id.cal_02);
        cal03 = root.findViewById(R.id.cal_03);
        cal04 = root.findViewById(R.id.cal_04);
        cal05 = root.findViewById(R.id.cal_05);


//        final TextView textView = root.findViewById(R.id.text_dashboard);
//        dashboardViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
//            @Override
//            public void onChanged(@Nullable String s) {
//                textView.setText(s);
//            }
//        });
        return root;
    }
}