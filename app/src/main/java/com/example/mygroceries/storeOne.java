package com.example.mygroceries;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;

import com.example.mygroceries.adapter.KfruitsAdapter;
import com.example.mygroceries.mydomains.kfruitDomain;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class storeOne extends AppCompatActivity  {
    RecyclerView kimaniRecycler;
    KfruitsAdapter kfruitsAdapter;
    List<kfruitDomain> mylist;
    DatabaseReference db;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store_one);


        kimaniRecycler=findViewById(R.id.kimaniRecycler);
        kimaniRecycler.setHasFixedSize(true);
        kimaniRecycler.setLayoutManager(new LinearLayoutManager(this));
        db=FirebaseDatabase.getInstance().getReference("stores");
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