package com.example.mygroceries.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mygroceries.R;
import com.example.mygroceries.mydomains.categoryDomain;


import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.categoryViewHolder> {
    Context context;
    List <categoryDomain> categoryDomainList;

    public CategoryAdapter(Context context, List<categoryDomain> categoryDomainList) {
        this.context = context;
        this.categoryDomainList = categoryDomainList;
    }

    @NonNull
    @Override
    public categoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view =LayoutInflater.from(context).inflate(R.layout.categories,parent,false);
        return new categoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull categoryViewHolder holder, int position) {
        holder.groceryImage.setImageResource(categoryDomainList.get(position).getPic());
        holder.name.setText(categoryDomainList.get(position).getTitle());
    }

    @Override
    public int getItemCount() {
        return categoryDomainList.size();
    }

    public static final class categoryViewHolder extends RecyclerView.ViewHolder{
        ImageView groceryImage;
        TextView name;


        public categoryViewHolder(@NonNull View itemView) {
            super(itemView);

            groceryImage=itemView.findViewById(R.id.groceryImage);
            name=itemView.findViewById(R.id.Name);
        }

    }












}
