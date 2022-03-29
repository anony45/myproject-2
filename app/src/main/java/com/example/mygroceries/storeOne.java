package com.example.mygroceries;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mygroceries.adapter.KfruitsAdapter;
import com.example.mygroceries.mydomains.kfruitDomain;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class storeOne extends AppCompatActivity  {
    RecyclerView kimaniRecycler;
    TextView name;
    ImageView image;
    KfruitsAdapter kfruitsAdapter;
    List<kfruitDomain> mylist;
    DatabaseReference db;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store_one);

        name=findViewById(R.id.user_store_name);
        image=findViewById(R.id.user_store_image);

        String nm=getIntent().getStringExtra("name");
        String img=getIntent().getStringExtra("image");

        name.setText(nm);

        kimaniRecycler=findViewById(R.id.adminstorerecycler);
        kimaniRecycler.setHasFixedSize(true);
        kimaniRecycler.setLayoutManager(new LinearLayoutManager(this));
        db=FirebaseDatabase.getInstance().getReference("Store Items");
        mylist=new ArrayList<>();
        kfruitsAdapter=new KfruitsAdapter(storeOne.this,mylist);
        kimaniRecycler.setAdapter(kfruitsAdapter);
    eventchange();

    }

    private void eventchange() {
        db.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot :snapshot.getChildren()){
                    kfruitDomain kfruitDomain= dataSnapshot.getValue(com.example.mygroceries.mydomains.kfruitDomain.class);
                    mylist.add(kfruitDomain);
                }
                kfruitsAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


}