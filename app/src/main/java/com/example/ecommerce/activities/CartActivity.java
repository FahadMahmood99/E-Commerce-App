package com.example.ecommerce.activities;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ecommerce.R;
import com.example.ecommerce.adapters.MyCartAdapter;
import com.example.ecommerce.models.MyCartModel;
import com.example.ecommerce.models.NewProductsModel;
import com.example.ecommerce.models.PaymentData;
import com.example.ecommerce.models.ShowAllModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Firebase;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class CartActivity extends AppCompatActivity {


    Toolbar toolbar;
    RecyclerView recyclerView;
    List<MyCartModel> cartModelList;
    MyCartAdapter cartAdapter;
    private FirebaseAuth auth;
    private FirebaseFirestore firestore;
    TextView overAllAmount;
    Button buyNow;
    MyCartModel myCartModel = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_cart);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        auth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();
        toolbar = findViewById(R.id.my_cart_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        buyNow = findViewById(R.id.buy_now);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        // get data from my cart Adapter
        LocalBroadcastManager.getInstance(this)
                .registerReceiver(mMessageReceiver, new IntentFilter("MyTotalAmount"));

        overAllAmount = findViewById(R.id.textView3);
        recyclerView = findViewById(R.id.car_rec);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        cartModelList = new ArrayList<>();
        cartAdapter = new MyCartAdapter(this, cartModelList);
        recyclerView.setAdapter(cartAdapter);

        firestore.collection("AddToCart").document(auth.getCurrentUser().getUid())
                .collection("User").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                MyCartModel mycartModel = document.toObject(MyCartModel.class);
                                cartModelList.add(mycartModel);
                                cartAdapter.notifyDataSetChanged();
                            }
                        } else {
                            // Handle error
                        }
                    }
                });
    }

    public BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            int totalBill = intent.getIntExtra("totalAmount", 0);
            overAllAmount.setText("Total Amount: $" + totalBill);

            buyNow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    PaymentData paymentData = new PaymentData(totalBill);
                    Intent intent = new Intent(CartActivity.this, PaymentActivity.class);
                    intent.putExtra("paymentData", paymentData);
                    startActivity(intent);
                }
            });
        }
    };
}