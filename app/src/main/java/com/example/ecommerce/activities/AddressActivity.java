package com.example.ecommerce.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;


import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;

import com.example.ecommerce.R;
import com.example.ecommerce.adapters.AddressAdapter;
import com.example.ecommerce.models.AddressModel;

import com.example.ecommerce.models.NewProductsModel;
import com.example.ecommerce.models.PopularProductsModel;
import com.example.ecommerce.models.ShowAllModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class AddressActivity extends AppCompatActivity implements AddressAdapter.SelectedAddress{

    Button addAddress;
    RecyclerView recyclerView;
    private List<AddressModel> addressModelList;
    private AddressAdapter addressAdapter;
    FirebaseFirestore firestore;
    FirebaseAuth auth;
    Button paymentBtn;
    Toolbar toolbar;

    String mAddress="";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_address);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        toolbar=findViewById(R.id.address_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        //get Data from DetailedActivity
        Object obj=getIntent().getSerializableExtra("item");




        firestore=FirebaseFirestore.getInstance();
        auth=FirebaseAuth.getInstance();

        addAddress=findViewById(R.id.add_address_btn);
        recyclerView=findViewById(R.id.address_recycler);
        paymentBtn=findViewById(R.id.payment_btn);

        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        addressModelList=new ArrayList<>();
        addressAdapter=new AddressAdapter(getApplicationContext(),addressModelList,this);
        recyclerView.setAdapter(addressAdapter);

        firestore.collection("CurrentUser").document(auth.getCurrentUser().getUid())
                .collection("Address").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){
                            for(QueryDocumentSnapshot document: task.getResult())
                            {
                                AddressModel addressModel=document.toObject(AddressModel.class);
                                addressModelList.add(addressModel);
                                Log.d("Firestore", "Address added: " + addressModel.getUserAddress());
                                addressAdapter.notifyDataSetChanged();
                            }
                            addressAdapter.notifyDataSetChanged();
                        }
                        else {
                            Log.d("Firestore", "Error getting documents: ", task.getException());
                        }
                    }
                });

        paymentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                double amount=0.0;

                if(obj instanceof NewProductsModel)
                {
                    NewProductsModel newProductsModel=(NewProductsModel)  obj;
                    amount=newProductsModel.getPrice();
                }
                if(obj instanceof PopularProductsModel)
                {
                    PopularProductsModel popularProductsModel=(PopularProductsModel)  obj;
                    amount=popularProductsModel.getPrice();
                }
                if(obj instanceof ShowAllModel)
                {
                    ShowAllModel showAllModel=(ShowAllModel)  obj;
                    amount=showAllModel.getPrice();
                }

                Intent intent= new Intent(AddressActivity.this,PaymentActivity.class);
                intent.putExtra("amount",amount);
                startActivity(intent);

            }
        });


        addAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AddressActivity.this,AddAdressActivity.class));
            }
        });
    }

    @Override
    public void setAddress(String address) {
        mAddress=address;
    }
}