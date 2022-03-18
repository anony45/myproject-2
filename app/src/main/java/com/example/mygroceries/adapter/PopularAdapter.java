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
import com.example.mygroceries.mydomains.popularDomain;

import java.util.List;

public class PopularAdapter extends RecyclerView.Adapter<PopularAdapter.popularViewHolder> {
    Context context;
    List<popularDomain> popularDomainList;

    public PopularAdapter(Context context, List<popularDomain> popularDomainList) {
        this.context = context;
        this.popularDomainList = popularDomainList;
    }

    @NonNull
    @Override
    public popularViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.popular,parent,false);
        return new PopularAdapter.popularViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull popularViewHolder holder, int position) {
        holder.PopularImage.setImageResource(popularDomainList.get(position).getPic());
        holder.name.setText(popularDomainList.get(position).getTitle());

    }

    @Override
    public int getItemCount() {
        return popularDomainList.size();
    }


    public static final class popularViewHolder extends RecyclerView.ViewHolder {
        ImageView PopularImage;
        TextView name;


        public popularViewHolder(@NonNull View itemView) {
            super(itemView);
            PopularImage=itemView.findViewById(R.id.popularImage);
            name=itemView.findViewById(R.id.Name);
        }
    }
}
