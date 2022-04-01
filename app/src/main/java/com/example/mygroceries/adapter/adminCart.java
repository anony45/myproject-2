package com.example.mygroceries.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mygroceries.AdminViewOrders;
import com.example.mygroceries.R;
import com.example.mygroceries.itemsDescriptions;
import com.example.mygroceries.myInterface.ItemClickListner;
import com.example.mygroceries.mydomains.AdminOrders;
import com.example.mygroceries.storeOne;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.List;

public class adminCart extends RecyclerView.Adapter<adminCart.adminViewHolder> {
	Context context;
	List<AdminOrders> list;

	public adminCart(Context context, List<AdminOrders> list) {
		this.context = context;
		this.list = list;
	}

	@NonNull
	@Override
	public adminViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

		View view= LayoutInflater.from(context).inflate(R.layout.admincart,parent,false);
		return new adminCart.adminViewHolder(view);

	}

	@Override
	public void onBindViewHolder(@NonNull adminViewHolder holder, int position) {
		FirebaseUser user;
		String userid;
		user= FirebaseAuth.getInstance().getCurrentUser();
		userid=user.getUid();
		holder.phone.setText(list.get(position).getPhone());
		holder.price.setText(list.get(position).getTotalAmount());
		holder.date.setText(list.get(position).getDate());
		holder.shippingaddress.setText(list.get(position).getAddress());
		holder.vieworder.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {


			}
		});

	}

	@Override
	public int getItemCount() {
		return list.size();
	}


	public final class adminViewHolder extends RecyclerView.ViewHolder{
		public TextView phone,date,price,shippingaddress;
		public Button vieworder;

		public adminViewHolder(@NonNull View itemView) {
			super(itemView);
			phone= itemView.findViewById(R.id.adminviewphone);
			price=itemView.findViewById(R.id.adminviewtotalprice);
			date=itemView.findViewById(R.id.adminviewdate);
			shippingaddress=itemView.findViewById(R.id.adminviewaddress);
			vieworder=itemView.findViewById(R.id.adminshowbtn);

		}
	}


}
