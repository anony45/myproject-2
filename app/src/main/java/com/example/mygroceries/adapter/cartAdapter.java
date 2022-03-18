package com.example.mygroceries.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.mygroceries.R;
import com.example.mygroceries.mydomains.cart;

import java.util.List;

public class cartAdapter extends RecyclerView.Adapter<cartAdapter.cartViewHolder> {
    Context context;
    List<cart>list;

    public cartAdapter(Context context, List<cart> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public cartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.cart_items,parent,false);
        return new cartViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull cartViewHolder holder, int position) {
        holder.name.setText(list.get(position).getName());
        holder.total_pricee.setText(list.get(position).getPrice());
        holder.quantity.setText(list.get(position).getQuantity());
        Glide.with(context).load(list.get(position).getImage()).error(R.drawable.ic_launcher_foreground).into(holder.image);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public final  class  cartViewHolder extends RecyclerView.ViewHolder{
        ImageView image;
        TextView total_pricee,name,quantity;


        public cartViewHolder(@NonNull View itemView) {
            super(itemView);
            image=itemView.findViewById(R.id.cart_imgg);
            total_pricee=itemView.findViewById(R.id.cartprice);
            name=itemView.findViewById(R.id.cartname);
            quantity=itemView.findViewById(R.id.cartquantity);
        }
    }

}
