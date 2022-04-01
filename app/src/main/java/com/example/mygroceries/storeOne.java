package com.example.mygroceries;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
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
    TextView name,store_name;
    EditText search;
    ImageView image;
    KfruitsAdapter adapter;
    List<kfruitDomain> mylist;
    DatabaseReference db;
    ProgressDialog progressDialog;
    ImageButton button;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store_one);

        name=findViewById(R.id.user_store_name);
        search=findViewById(R.id.searchme);
        store_name=findViewById(R.id.see_store_name);
        String store=getIntent().getStringExtra("name");
        button=findViewById(R.id.info);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(storeOne.this,AboutStore.class);
                startActivity(intent);
                finish();
            }
        });

        store_name.setText(store);
        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
            @Override
            public void afterTextChanged(Editable s) {
                filter(s.toString());
            }
        });
        String nm=getIntent().getStringExtra("name");
        String img=getIntent().getStringExtra("image");
        name.setText(nm);
        kimaniRecycler=findViewById(R.id.adminstorerecycler);
        kimaniRecycler.setHasFixedSize(true);
        kimaniRecycler.setLayoutManager(new LinearLayoutManager(this));
        db=FirebaseDatabase.getInstance().getReference("Store Items");
        mylist=new ArrayList<>();


    eventchange();

    }
    private void filter(String txt){
        ArrayList<kfruitDomain> filtered=new ArrayList<>();

        for (kfruitDomain domain:mylist ){
            if(domain.getName().toLowerCase().contains(txt.toLowerCase())){
                filtered.add(domain);
            }
        }
        adapter.filterlist(filtered);
    }

    private void eventchange() {
        db.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot :snapshot.getChildren()){
                    kfruitDomain kfruitDomain= dataSnapshot.getValue(com.example.mygroceries.mydomains.kfruitDomain.class);
                    mylist.add(kfruitDomain);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        adapter=new KfruitsAdapter(storeOne.this,mylist);
        kimaniRecycler.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

}