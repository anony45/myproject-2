package com.example.mygroceries;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.example.mygroceries.databinding.ActivityOngoingOrdersBinding;

public class ongoingOrders extends Nav_base {
    ActivityOngoingOrdersBinding ongoingOrdersBinding;
    TextView ttl;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ongoingOrdersBinding=ActivityOngoingOrdersBinding.inflate(getLayoutInflater());
        setContentView(ongoingOrdersBinding.getRoot());
        allocatetitle("Ongoing Orders");
        String total =getIntent().getStringExtra("totalAmount");
        ttl.setText(total);


    }
}