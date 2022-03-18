package com.example.mygroceries;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.mygroceries.adapter.cartAdapter;
import com.example.mygroceries.databinding.ActivityCartPageBinding;
import com.example.mygroceries.mydomains.cart;
import com.example.mygroceries.mydomains.kfruitDomain;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class cartPage extends Nav_base {
    ActivityCartPageBinding pageBinding;
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    List<cart> mylist;
    DatabaseReference db;
    Button proceed;
    TextView total_amount,message;
    int total_price=0;
    cartAdapter cartAdapter;
    private Button submitorder;
    private ImageView productimg;
    private TextView descriptionofitem,priceofitem,nameofitem;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pageBinding=ActivityCartPageBinding.inflate(getLayoutInflater());
        setContentView(pageBinding.getRoot());
        allocatetitle("Your Cart");

        //declare the contents
        recyclerView=findViewById(R.id.cart_recycler);
        recyclerView.setHasFixedSize(true);
        layoutManager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        db=FirebaseDatabase.getInstance().getReference("Cart List");
        mylist=new ArrayList<>();
        cartAdapter=new cartAdapter(cartPage.this,mylist);
        recyclerView.setAdapter(cartAdapter);
        //find view

        //from store activity
        String description=getIntent().getStringExtra("description");
        String img=getIntent().getStringExtra("image");
        String pricee=getIntent().getStringExtra("price");
        String namee=getIntent().getStringExtra("name");



        proceed=findViewById(R.id.submitbtn);
        total_amount=findViewById(R.id.total_price);
        message=findViewById(R.id.msg1);

        proceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                total_amount.setText("Total price = Ksh"+String.valueOf(total_price));
                Intent intent= new Intent(cartPage.this,ongoingOrders.class);
                intent.putExtra("Total price",String.valueOf(total_price));
                startActivity(intent);
                finish();
            }
        });
        eventchange();
    }

    @Override
    protected void onStart() {
        super.onStart();

    }
    private void eventchange() {
        db.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot :snapshot.getChildren()){
                    cart cartdomain= dataSnapshot.getValue(cart.class);
                    mylist.add(cartdomain);
                }
                cartAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    private void CheckOrderState(){
        DatabaseReference ordersRef;
        ordersRef = FirebaseDatabase.getInstance().getReference().child("Orders");
        ordersRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    String shippingstate = snapshot.child("state").getValue().toString();
                    String username= snapshot.child("name").getValue().toString();
                    if (shippingstate.equals("shipped")){
                        total_amount.setText("Order is shipped successfully");
                        recyclerView.setVisibility(View.GONE);
                        message.setVisibility(View.VISIBLE);
                        message.setText("Congratulations, Your Final order has been shipped successfully. Soon you will received your order");
                        proceed.setVisibility(View.GONE);
                        Toast.makeText(cartPage.this, "You can purchase more products, Once you received your first order", Toast.LENGTH_SHORT).show();

                    }
                    else if (shippingstate.equals("Not Shipped")){
                        total_amount.setText("Shipping State = Not Shipped");
                        recyclerView.setVisibility(View.GONE);
                        message.setVisibility(View.VISIBLE);

                        proceed.setVisibility(View.GONE);
                        Toast.makeText(cartPage.this,"You can purchase more products, Once you received your first order",Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}