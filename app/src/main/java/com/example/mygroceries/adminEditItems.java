package com.example.mygroceries;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;

import com.example.mygroceries.adapter.edititemsadapter;
import com.example.mygroceries.mydomains.kfruitDomain;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.example.mygroceries.mydomains.edititemsdomain;

import java.util.ArrayList;
import java.util.List;

public class adminEditItems extends AppCompatActivity {
    RecyclerView recyclerView;
    edititemsadapter adapter;
    List<edititemsdomain> mylist;
    DatabaseReference db;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_edit_items);

        recyclerView=findViewById(R.id.MyStoreRecycler);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        db= FirebaseDatabase.getInstance().getReference("Store Items");
        mylist=new ArrayList<>();
        adapter=new edititemsadapter(adminEditItems.this,mylist);
        recyclerView.setAdapter(adapter);
        eventchange();

    }
    private void eventchange() {
        db.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot :snapshot.getChildren()){
                    edititemsdomain domain= dataSnapshot.getValue(com.example.mygroceries.mydomains.edititemsdomain.class);
                    mylist.add(domain);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}