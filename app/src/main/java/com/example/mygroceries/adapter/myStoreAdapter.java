package com.example.mygroceries.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.mygroceries.R;
import com.example.mygroceries.mydomains.mystoreDomain;

import java.util.ArrayList;

public class myStoreAdapter extends RecyclerView.Adapter<myStoreAdapter.storeViewHolder> {
    Context context;
    ArrayList<mystoreDomain> list;

    @NonNull
    @Override
    public storeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.mylayout,parent,false);

        return new storeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull storeViewHolder holder, int position) {
        holder.name.setText(list.get(position).getItemname());
        holder.description.setText(list.get(position).getItemdescription());
        holder.price.setText(list.get(position).getItemprice());
        holder.price.setText(list.get(position).getItemprice());

        Glide.with(holder.image.getContext()).load(list.get(position).getItemimage()).error(R.drawable.noimage).into(holder.image);

    }

    @Override
    public int getItemCount() {
       return list.size();
    }

    public static final class  storeViewHolder extends RecyclerView.ViewHolder{
        private TextView name,description,price;
        private Button addtocart;
        private ImageView image;


     public storeViewHolder(@NonNull View itemView) {
         super(itemView);
         name= itemView.findViewById(R.id.myitemname);
         description=itemView.findViewById(R.id.myitemdescription);
         price=itemView.findViewById(R.id.myitemprice);
         addtocart=itemView.findViewById(R.id.myitemaddtocartbtn);
         image=itemView.findViewById(R.id.myitemimage);

     }
 }
}
