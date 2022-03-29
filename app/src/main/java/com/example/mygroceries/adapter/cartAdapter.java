package com.example.mygroceries.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.mygroceries.R;
import com.example.mygroceries.myInterface.ItemClickListner;
import com.example.mygroceries.mydomains.cart;
import com.example.mygroceries.ongoingOrders;
import com.google.firebase.firestore.DocumentSnapshot;

import java.util.List;

public class cartAdapter extends RecyclerView.ViewHolder implements View.OnClickListener{
    public   ImageView image;
    public TextView total_pricee,name,quantity;
    public TextView overalltotalprice;
    private ItemClickListner itemClickListner;

    public cartAdapter(@NonNull View itemView) {
        super(itemView);
        image=itemView.findViewById(R.id.cart_imgg);
        total_pricee=itemView.findViewById(R.id.cartprice);
        name=itemView.findViewById(R.id.cartname);
        quantity=itemView.findViewById(R.id.cartquantity);


    }

    @Override
    public void onClick(View view) {
        itemClickListner.onClick(view,getAdapterPosition(),false);
    }

    public void setItemClickListner(ItemClickListner itemClickListner) {
        this.itemClickListner = itemClickListner;
    }


}
