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

import com.example.mygroceries.adapter.adminCart;
import com.example.mygroceries.adapter.cartAdapter;
import com.example.mygroceries.adapter.homeAdapter;
import com.example.mygroceries.mydomains.AdminOrders;
import com.example.mygroceries.mydomains.cart;
import com.example.mygroceries.mydomains.homeDomain;
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

public class AdminViewOrders extends AppCompatActivity {
    RecyclerView recyclerView;
    DatabaseReference db;
    List<AdminOrders> mylist;
    adminCart adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_view_orders);

        recyclerView=findViewById(R.id.admin_cart);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        db= FirebaseDatabase.getInstance().getReference("Final Order");
        mylist=new ArrayList<>();
        eventchange();

    }
    private void eventchange() {
        db.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot :snapshot.getChildren()){
                    AdminOrders orders= dataSnapshot.getValue(com.example.mygroceries.mydomains.AdminOrders.class);
                    mylist.add(orders);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        adapter=new adminCart(AdminViewOrders.this,mylist);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }
}
