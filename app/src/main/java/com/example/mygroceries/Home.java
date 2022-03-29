package com.example.mygroceries;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;

import com.example.mygroceries.adapter.KfruitsAdapter;
import com.example.mygroceries.adapter.homeAdapter;
import com.example.mygroceries.adapter.storesAdapter;
import com.example.mygroceries.databinding.ActivityHomeBinding;
import com.example.mygroceries.mydomains.homeDomain;
import com.example.mygroceries.mydomains.kfruitDomain;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Home extends Nav_base {
	RecyclerView recyclerView;
	homeAdapter adapter;
	DatabaseReference db;
	ActivityHomeBinding homeBinding;
	List<homeDomain> mylist;
	ProgressDialog progressDialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		homeBinding=ActivityHomeBinding.inflate(getLayoutInflater());
		setContentView(homeBinding.getRoot());
		allocatetitle("Home");

		recyclerView=findViewById(R.id.homeRecycler);
		recyclerView.setHasFixedSize(true);
		GridLayoutManager layoutManager=new GridLayoutManager(getApplicationContext(),2);
		recyclerView.setLayoutManager(layoutManager);
		db= FirebaseDatabase.getInstance().getReference("stores");
		mylist=new ArrayList<>();
		adapter=new homeAdapter(Home.this,mylist);
		recyclerView.setAdapter(adapter);
		eventchange();

	}

	private void eventchange() {
		db.addValueEventListener(new ValueEventListener() {
			@Override
			public void onDataChange(@NonNull DataSnapshot snapshot) {
				for (DataSnapshot dataSnapshot :snapshot.getChildren()){
					homeDomain home= dataSnapshot.getValue(com.example.mygroceries.mydomains.homeDomain.class);
					mylist.add(home);
				}
				adapter.notifyDataSetChanged();
			}

			@Override
			public void onCancelled(@NonNull DatabaseError error) {

			}
		});
	}

}