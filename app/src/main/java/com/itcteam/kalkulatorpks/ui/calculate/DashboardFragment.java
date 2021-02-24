package com.itcteam.kalkulatorpks.ui.calculate;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.itcteam.kalkulatorpks.R;
import com.itcteam.kalkulatorpks.ui.calculate.task.Hitung01;
import com.itcteam.kalkulatorpks.ui.calculate.task.Hitung02;

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
                Intent intent = new Intent(getActivity(), Hitung01.class);
                startActivity(intent);
            }
        });

        cal02 = root.findViewById(R.id.cal_02);

        cal02.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Hitung02.class);
                startActivity(intent);
            }
        });

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