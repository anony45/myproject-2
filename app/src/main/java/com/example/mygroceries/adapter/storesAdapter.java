package com.example.mygroceries.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.mygroceries.R;
import com.example.mygroceries.myInterface.recyclerviewInterface;
import com.example.mygroceries.mydomains.storesDomain;
import com.example.mygroceries.storeOne;
import com.example.mygroceries.vendorLogin;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

import java.util.ArrayList;

public class storesAdapter extends FirebaseRecyclerAdapter<storesDomain, storesAdapter.storesViewholder> {


    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public storesAdapter(@NonNull FirebaseRecyclerOptions<storesDomain> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull storesViewholder holder, int position, @NonNull storesDomain model) {

        holder.name.setText(model.getName());
        Glide.with(holder.image.getContext()).load(model.getImage()).placeholder(R.drawable.apple).error(R.drawable.ic_launcher_foreground).into(holder.image);


    }

    @NonNull
    @Override
    public storesViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view
                = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.vendorstore, parent, false);
        return new storesAdapter.storesViewholder(view);

    }

    public class storesViewholder extends RecyclerView.ViewHolder {
        TextView name;
        ImageView image;

        public storesViewholder(@NonNull View itemView) {

            super(itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent= new Intent(view.getContext(),storeOne.class);
                    view.getContext().startActivity(intent);
                }
            });
            name=itemView.findViewById(R.id.storenm);
            image=itemView.findViewById(R.id.storeimg);

        }
    }
}
