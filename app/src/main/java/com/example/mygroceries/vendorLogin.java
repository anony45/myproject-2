package com.example.mygroceries;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mygroceries.adapter.storesAdapter;
import com.example.mygroceries.databinding.ActivityVendorLoginBinding;
import com.example.mygroceries.myInterface.recyclerviewInterface;
import com.example.mygroceries.mydomains.storesDomain;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class vendorLogin extends Nav_base {

    RecyclerView recyclerView;
    storesAdapter adapter;
    DatabaseReference db;

    ActivityVendorLoginBinding activityVendorLoginBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityVendorLoginBinding = ActivityVendorLoginBinding.inflate(getLayoutInflater());

        setContentView(activityVendorLoginBinding.getRoot());
        allocatetitle("Home ");

        recyclerView=findViewById(R.id.storesReycler);
        recyclerView.setLayoutManager(new WrapContentLinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        db= FirebaseDatabase.getInstance().getReference("stores");

        FirebaseRecyclerOptions<storesDomain> options =new FirebaseRecyclerOptions.Builder<storesDomain>().setQuery(db,storesDomain.class).build();
        adapter=new storesAdapter(options);
        recyclerView.setAdapter(adapter);
    }




    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();

    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }




    public class WrapContentLinearLayoutManager extends LinearLayoutManager {
        public WrapContentLinearLayoutManager(Context context) {
            super(context);
        }

        public WrapContentLinearLayoutManager(Context context, int orientation, boolean reverseLayout) {
            super(context, orientation, reverseLayout);
        }

        public WrapContentLinearLayoutManager(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
            super(context, attrs, defStyleAttr, defStyleRes);
        }

        @Override
        public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {
            try {
                super.onLayoutChildren(recycler, state);
            } catch (IndexOutOfBoundsException e) {
                Log.e("TAG", "meet a IOOBE in RecyclerView");
            }
        }
    }

}