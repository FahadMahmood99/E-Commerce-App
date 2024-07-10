package com.example.ecommerce.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.ecommerce.R;
import com.example.ecommerce.models.PaymentData;

import org.w3c.dom.Text;


public class PaymentActivity extends AppCompatActivity {

    Toolbar toolbar;

    TextView subtotal,discount,shipping,total;

    Button paymentBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_payment);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        toolbar=findViewById(R.id.payment_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


       Intent intent = getIntent();
//        PaymentData paymentData = (PaymentData) intent.getSerializableExtra("paymentData");
        double amount = intent.getDoubleExtra("amount", 0.0);
        int receivedValue = 0;
//
//        if (paymentData != null) {
//            receivedValue = paymentData.getTotalBill();
//        } else
//
        if (amount != 0.0) {
            receivedValue = (int) amount;
        }

        subtotal=findViewById(R.id.sub_total);
        discount=findViewById(R.id.textView17);
        shipping=findViewById(R.id.textView18);
        total=findViewById(R.id.total_amt);
        paymentBtn=findViewById(R.id.pay_btn);


        subtotal.setText(String.valueOf("$"+receivedValue));
        total.setText(String.valueOf("$"+receivedValue));

        int finalReceivedValue = receivedValue;
        paymentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(PaymentActivity.this, "Your $" + finalReceivedValue+ "has been Successfully recved", Toast.LENGTH_SHORT).show();



            }
        });
    }


}