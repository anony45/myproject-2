package com.example.mygroceries.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.mygroceries.R;
import com.example.mygroceries.cartPage;
import com.example.mygroceries.itemsDescriptions;
import com.example.mygroceries.myInterface.ItemClickListner;
import com.example.mygroceries.mydomains.cart;
import com.example.mygroceries.ongoingOrders;
import com.example.mygroceries.storeOne;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;

import java.util.List;

public class cartAdapter extends RecyclerView.ViewHolder implements View.OnClickListener{
    public TextView name,total_pricee,quantity;
    public ImageView image;
    private ItemClickListner itemClickListner;

    public cartAdapter(@NonNull View itemView) {
        super(itemView);
        name=itemView.findViewById(R.id.cartname);
        total_pricee=itemView.findViewById(R.id.cartprice);
        quantity=itemView.findViewById(R.id.cartquantity);
        image=itemView.findViewById(R.id.cart_imgg);
    }


    @Override
    public void onClick(View v) {
        itemClickListner.onClick(v,getAdapterPosition(),false);
    }
    public void setItemClickListner(ItemClickListner itemClickListner) {
        this.itemClickListner = itemClickListner;
    }
}
