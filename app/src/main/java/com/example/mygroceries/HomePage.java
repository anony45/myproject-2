package com.example.mygroceries;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.mygroceries.adapter.CategoryAdapter;
import com.example.mygroceries.adapter.PopularAdapter;
import com.example.mygroceries.mydomains.categoryDomain;
import com.example.mygroceries.mydomains.popularDomain;
import com.google.android.material.bottomnavigation.BottomNavigationView;


import java.util.ArrayList;
import java.util.List;

public class HomePage extends AppCompatActivity {

    RecyclerView categoriesRecycler,popularRecycler;
    CategoryAdapter categoryAdapter;
    PopularAdapter popularAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);





        List<categoryDomain>categoryDomainList=new ArrayList<>();

        categoryDomainList.add(new categoryDomain("vegetables",R.drawable.fruit));
        categoryDomainList.add(new categoryDomain("vegetables",R.drawable.fruit));
        categoryDomainList.add(new categoryDomain("vegetables",R.drawable.fruit));
        categoryDomainList.add(new categoryDomain("vegetables",R.drawable.fruit));

        setCategoriesRecycler(categoryDomainList);







    }
    private void setCategoriesRecycler(List<categoryDomain> categoryDomainList){
        categoriesRecycler=findViewById(R.id.categoriesRecycler);
        RecyclerView.LayoutManager layoutManager =new LinearLayoutManager(this,RecyclerView.HORIZONTAL,false);
        categoriesRecycler.setLayoutManager(layoutManager);
        categoryAdapter =new CategoryAdapter(this,categoryDomainList);
        categoriesRecycler.setAdapter(categoryAdapter);



    }
    private void setPopularRecycler(List<popularDomain>popularDomainList){
        popularRecycler=findViewById(R.id.popularRecycler);
        RecyclerView.LayoutManager layoutManager =new LinearLayoutManager(this,RecyclerView.VERTICAL,false);
        popularRecycler.setLayoutManager(layoutManager);
        popularAdapter = new PopularAdapter(this,popularDomainList);
        categoriesRecycler.setAdapter(popularAdapter);
    }
}