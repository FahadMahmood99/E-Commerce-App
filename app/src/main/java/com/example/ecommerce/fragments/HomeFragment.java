package com.example.ecommerce.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.denzcoskun.imageslider.models.SlideModel;
import com.example.ecommerce.R;

import java.util.ArrayList;
import java.util.List;


public class HomeFragment extends Fragment {

    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root= inflater.inflate(R.layout.fragment_home, container, false);

        ImageSlider imagesSlider=root.findViewById(R.id.image_slider);

        List<SlideModel> slidesModel=new ArrayList<>();
        slidesModel.add(new SlideModel(R.drawable.banner1,"Discount on shoes",ScaleTypes.CENTER_CROP));
        slidesModel.add(new SlideModel(R.drawable.banner2,"Discount on perfume",ScaleTypes.CENTER_CROP));
        slidesModel.add(new SlideModel(R.drawable.banner3,"70% off",ScaleTypes.CENTER_CROP));

        imagesSlider.setImageList(slidesModel);

        return root;

    }
}