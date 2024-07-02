package com.example.ecommerce.fragments;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.denzcoskun.imageslider.models.SlideModel;
import com.example.ecommerce.R;
import com.example.ecommerce.adapters.CategoryAdapter;
import com.example.ecommerce.adapters.NewProductsAdapter;
import com.example.ecommerce.adapters.PopularProductsAdapter;
import com.example.ecommerce.models.CategoryModel;
import com.example.ecommerce.models.NewProductsModel;
import com.example.ecommerce.models.PopularProductsModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.ktx.Firebase;

import java.util.ArrayList;
import java.util.List;


public class HomeFragment extends Fragment {

    LinearLayout linearLayout;

    ProgressDialog progressDialog;

    RecyclerView catRecyclerview,newProductRecyclerview,popularRecyclerview;

    //Category Adapter and List
    CategoryAdapter categoryAdapter;
    List<CategoryModel> categoryModelList;

    //New Product Adapter and List
    NewProductsAdapter newProductsAdapter;
    List<NewProductsModel> newProductsModelsList;

    //PopularProducts Adapter and List
    PopularProductsAdapter popularProductsAdapter;
    List<PopularProductsModel> popularProductsModelList;

    FirebaseFirestore db;


    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root= inflater.inflate(R.layout.fragment_home, container, false);

        progressDialog=new ProgressDialog(getActivity());
        catRecyclerview=root.findViewById(R.id.rec_category);
        newProductRecyclerview=root.findViewById(R.id.new_product_rec);
        popularRecyclerview=root.findViewById(R.id.popular_rec);

        db= FirebaseFirestore.getInstance();

        linearLayout=root.findViewById(R.id.home_layout);
        linearLayout.setVisibility(View.GONE);


        ImageSlider imagesSlider=root.findViewById(R.id.image_slider);

        List<SlideModel> slidesModel=new ArrayList<>();
        slidesModel.add(new SlideModel(R.drawable.banner1,"Discount on Shoes",ScaleTypes.CENTER_CROP));
        slidesModel.add(new SlideModel(R.drawable.banner2,"Sale on Perfume",ScaleTypes.CENTER_CROP));
        slidesModel.add(new SlideModel(R.drawable.banner3,"40% off",ScaleTypes.CENTER_CROP));

        imagesSlider.setImageList(slidesModel);


        progressDialog.setTitle("Welcome to E-Commerce Store");
        progressDialog.setMessage("Please Wait...");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();


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
                                linearLayout.setVisibility(View.VISIBLE);
                                progressDialog.dismiss();
                            }
                        }
                        else {

                            Toast.makeText(getActivity(), ""+task.getException(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });

        //New Products Recyclerview
        newProductRecyclerview.setLayoutManager(new LinearLayoutManager(getActivity(),RecyclerView.HORIZONTAL,false));

        newProductsModelsList =new ArrayList<>();
        newProductsAdapter=new NewProductsAdapter(getContext(),newProductsModelsList);
        newProductRecyclerview.setAdapter(newProductsAdapter);


        db.collection("NewProducts")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){
                            for(QueryDocumentSnapshot document: task.getResult())
                            {
                                NewProductsModel newProductsModel=document.toObject(NewProductsModel.class);
                                newProductsModelsList.add(newProductsModel);
                                newProductsAdapter.notifyDataSetChanged();
                            }
                        }
                        else {

                            Toast.makeText(getActivity(), ""+task.getException(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });


        //Popular Products Recyclerview
        popularRecyclerview.setLayoutManager(new GridLayoutManager(getActivity(),3));

        popularProductsModelList =new ArrayList<>();
        popularProductsAdapter=new PopularProductsAdapter(getContext(),popularProductsModelList);
        popularRecyclerview.setAdapter(popularProductsAdapter);


        db.collection("AllProducts")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){
                            for(QueryDocumentSnapshot document: task.getResult())
                            {
                                PopularProductsModel popularProductsModel=document.toObject(PopularProductsModel.class);
                                popularProductsModelList.add(popularProductsModel);
                                popularProductsAdapter.notifyDataSetChanged();
                            }
                        }
                        else {

                            Toast.makeText(getActivity(), ""+task.getException(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });


        return root;

    }
}