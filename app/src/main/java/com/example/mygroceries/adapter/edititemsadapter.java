package com.example.mygroceries.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.bumptech.glide.Glide;
import com.example.mygroceries.R;
import com.example.mygroceries.editPage;
import com.example.mygroceries.mydomains.edititemsdomain;

import java.util.List;

public class edititemsadapter extends RecyclerView.Adapter<edititemsadapter.edititemViewHolder>{
    Context context;
    List<edititemsdomain> list;
    public edititemsadapter(Context context, List<edititemsdomain> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public edititemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.editpage,parent,false);
        return new edititemsadapter.edititemViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull edititemViewHolder holder, int position) {
        holder.name.setText(list.get(position).getName());
        holder.description.setText(list.get(position).getDescription());
        holder.price.setText(list.get(position).getPrice());
        Glide.with(context).load(list.get(position).getImage()).error(R.drawable.noimage).into(holder.image);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(view.getContext(), editPage.class);
                intent.putExtra("description",  list.get(position).getDescription());
                intent.putExtra("image",list.get(position).getImage());
                intent.putExtra("price",list.get(position).getPrice());
                intent.putExtra("name",list.get(position).getName());

                view.getContext().startActivity(intent);

            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
    public final class edititemViewHolder extends RecyclerView.ViewHolder{
        ImageView image;
        TextView name,description,price;



        public edititemViewHolder(@NonNull View itemView) {
            super(itemView);

            image=itemView.findViewById(R.id.item_image);
            name=itemView.findViewById(R.id.item_name);
            description=itemView.findViewById(R.id.item_description);
            price=itemView.findViewById(R.id.item_pricee);

        }

    }

}
