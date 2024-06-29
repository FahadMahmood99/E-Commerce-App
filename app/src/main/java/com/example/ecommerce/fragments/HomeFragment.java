package com.example.ecommerce.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.denzcoskun.imageslider.models.SlideModel;
import com.example.ecommerce.R;
import com.example.ecommerce.adapters.CategoryAdapter;
import com.example.ecommerce.models.CategoryModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.ktx.Firebase;

import java.util.ArrayList;
import java.util.List;


public class HomeFragment extends Fragment {

    RecyclerView catRecyclerview;

    //Category RecyclerView
    CategoryAdapter categoryAdapter;
    List<CategoryModel> categoryModelList;

    FirebaseFirestore db;


    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root= inflater.inflate(R.layout.fragment_home, container, false);

        catRecyclerview=root.findViewById(R.id.rec_category);

        db= FirebaseFirestore.getInstance();


        ImageSlider imagesSlider=root.findViewById(R.id.image_slider);

        List<SlideModel> slidesModel=new ArrayList<>();
        slidesModel.add(new SlideModel(R.drawable.banner1,"Discount on Shoes",ScaleTypes.CENTER_CROP));
        slidesModel.add(new SlideModel(R.drawable.banner2,"Sale on Perfume",ScaleTypes.CENTER_CROP));
        slidesModel.add(new SlideModel(R.drawable.banner3,"40% off",ScaleTypes.CENTER_CROP));

        imagesSlider.setImageList(slidesModel);

        catRecyclerview.setLayoutManager(new LinearLayoutManager(getActivity(),RecyclerView.HORIZONTAL,false));

        categoryModelList =new ArrayList<>();
        categoryAdapter=new CategoryAdapter(getContext(),categoryModelList);
        catRecyclerview.setAdapter(categoryAdapter);



        db.collection("Category")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){
                            for(QueryDocumentSnapshot document: task.getResult())
                            {
                                CategoryModel categoryModel=document.toObject(CategoryModel.class);
                                categoryModelList.add(categoryModel);
                                categoryAdapter.notifyDataSetChanged();
                            }
                        }
                        else {

                        }
                    }
                });




        return root;

    }
}