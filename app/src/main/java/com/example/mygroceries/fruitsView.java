package com.example.mygroceries;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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

public class fruitsView extends AppCompatActivity {
    RecyclerView kfruitRecycler;
    KfruitsAdapter kfruitsAdapter;
    List<kfruitDomain> list;


    DatabaseReference db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fruits_view);



        //load data from firebase

        kfruitRecycler=findViewById(R.id.kfruitRecycler);
        db = FirebaseDatabase.getInstance().getReference("fruits");
        db.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                clearAll();
                for (DataSnapshot snapshot1:snapshot.getChildren()){
                    kfruitDomain kfruitDomain=new kfruitDomain();

                    list.add(kfruitDomain);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        kfruitRecycler.setHasFixedSize(true);
        kfruitRecycler.setLayoutManager(new GridLayoutManager(this,2));

        list= new ArrayList<>();
        kfruitsAdapter= new KfruitsAdapter (this,list);
        kfruitRecycler.setAdapter(kfruitsAdapter);
        
        
        clearAll();

        db.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot :snapshot.getChildren()){
                    kfruitDomain mylist = dataSnapshot.getValue(kfruitDomain.class);
                    list.add(mylist);

                }
                kfruitsAdapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });





    }

    private void clearAll() {
    }

}