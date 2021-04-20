package com.itcteam.kalkulatorpks.ui.home;

import android.os.Bundle;
import android.transition.Slide;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;
import com.itcteam.kalkulatorpks.R;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
//        homeViewModel =
//                ViewModelProviders.of(this).get(HomeViewModel.class);

        View root = inflater.inflate(R.layout.fragment_home, container, false);

        TextView home = root.findViewById(R.id.text_home);

        String homeText = "Aplikasi Kalkulator PKS adalah aplikasi berbasis mobile yang memiliki fitur untuk memudahkan menghitung beberapa jenis kalkulasi yang sering di gunakan di PKS ";

        home.setText(homeText);

        ArrayList<SlideModel> imageList = new ArrayList<>(); // Create image list

        imageList.add(new SlideModel(R.drawable.sawit01, "", ScaleTypes.CENTER_CROP));
        imageList.add(new SlideModel(R.drawable.sawit02, "", ScaleTypes.CENTER_CROP));
        imageList.add(new SlideModel(R.drawable.sawit03, "", ScaleTypes.CENTER_CROP));
        imageList.add(new SlideModel(R.drawable.sawit04, "", ScaleTypes.CENTER_CROP));
        imageList.add(new SlideModel(R.drawable.sawit05, "", ScaleTypes.CENTER_CROP));

        ImageSlider imageSlider = root.findViewById(R.id.img_sawit);
        imageSlider.setImageList(imageList);

//        final TextView textView = root.findViewById(R.id.text_home);
//        homeViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
//            @Override
//            public void onChanged(@Nullable String s) {
//                textView.setText(s);
//            }
//        });
        return root;
    }
}