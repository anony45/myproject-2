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

import java.util.List;

import com.example.mygroceries.storeOne;
import com.example.mygroceries.mydomains.homeDomain;

public class homeAdapter extends RecyclerView.Adapter<homeAdapter.homeViewHolder>{
	Context context;
	List<homeDomain> list;

	public homeAdapter(Context context, List<homeDomain> list) {
		this.context = context;
		this.list = list;
	}

	@NonNull
	@Override
	public homeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
		View view= LayoutInflater.from(context).inflate(R.layout.vendorstore,parent,false);
		return new homeViewHolder(view);
	}

	@Override
	public void onBindViewHolder(@NonNull homeViewHolder holder, int position) {
		holder.name.setText(list.get(position).getName());
		Glide.with(context).load(list.get(position).getImage()).error(R.drawable.noimage).into(holder.image);
		holder.itemView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Intent intent =new Intent(view.getContext(), storeOne.class);
				intent.putExtra("image",list.get(position).getImage());
				intent.putExtra("name",list.get(position).getName());
				view.getContext().startActivity(intent);
			}
		});
	}

	@Override
	public int getItemCount() {
		return list.size();
	}

	public final class homeViewHolder extends RecyclerView.ViewHolder{
		ImageView image;
		TextView name;
		public homeViewHolder(@NonNull View itemView) {
			super(itemView);

			image=itemView.findViewById(R.id.storeimg);
			name=itemView.findViewById(R.id.storenm);

		}
	}
}
