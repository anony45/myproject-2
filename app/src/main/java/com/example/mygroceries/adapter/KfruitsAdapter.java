package com.example.mygroceries.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.mygroceries.R;
import com.example.mygroceries.itemsDescriptions;
import com.example.mygroceries.mydomains.homeDomain;
import com.example.mygroceries.mydomains.kfruitDomain;

import java.util.ArrayList;
import java.util.List;

public class KfruitsAdapter extends RecyclerView.Adapter<KfruitsAdapter.kfruitViewHolder> implements Filterable {
    Context context;
    List<kfruitDomain>kfruitDomainList;
    List<kfruitDomain> filtered;

    public KfruitsAdapter(Context context, List<kfruitDomain> kfruitDomainList) {
        this.context = context;
        this.kfruitDomainList = kfruitDomainList;
        this.filtered = filtered;
    }

    @NonNull
    @Override
    public kfruitViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.kfruits,parent,false);
        return new kfruitViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull kfruitViewHolder holder, @SuppressLint("RecyclerView") int position) {

        holder.name.setText(kfruitDomainList.get(position).getName());
        holder.description.setText(kfruitDomainList.get(position).getDescription());
        holder.price.setText(kfruitDomainList.get(position).getPrice());
        Glide.with(context).load(kfruitDomainList.get(position).getImage()).error(R.drawable.noimage).into(holder.image);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(view.getContext(), itemsDescriptions.class);
                intent.putExtra("description",  kfruitDomainList.get(position).getDescription());
                intent.putExtra("image",kfruitDomainList.get(position).getImage());
                intent.putExtra("price",kfruitDomainList.get(position).getPrice());
                intent.putExtra("name",kfruitDomainList.get(position).getName());

                view.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return kfruitDomainList.size();
    }

    @Override
    public Filter getFilter() {
        return myfilter;
    }
    private final Filter myfilter =new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            ArrayList<kfruitDomain> llist=new ArrayList<>();
            if (constraint==null||constraint.length()==0){
                llist.addAll(filtered);
            }
            else {
                String filterpattern=constraint.toString().toLowerCase().trim();
                for (kfruitDomain domain: filtered){
                    if (domain.name.toLowerCase().contains(filterpattern)){
                        llist.add(domain);
                    }
                }
            }
            FilterResults results=new FilterResults();
            results.values=llist;
            results.count=llist.size();
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            kfruitDomainList.clear();
            kfruitDomainList.addAll((ArrayList)results.values);
            notifyDataSetChanged();

        }
    };
    public void filterlist(ArrayList<kfruitDomain> filteredlist){
        kfruitDomainList=filteredlist;
        notifyDataSetChanged();
    }


    public final class kfruitViewHolder extends RecyclerView.ViewHolder{
        //declaration for cart
        ImageView image,delivery_fee;
        TextView name,description,price;



        public kfruitViewHolder(@NonNull View itemView) {
            super(itemView);

            image=itemView.findViewById(R.id.cart_product_image);
            name=itemView.findViewById(R.id.cart_product_name);
            description=itemView.findViewById(R.id.quantity);
            price=itemView.findViewById(R.id.item_price);

        }
    }
}
