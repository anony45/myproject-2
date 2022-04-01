package com.example.mygroceries.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;


import com.bumptech.glide.Glide;
import com.example.mygroceries.R;
import com.example.mygroceries.editPage;
import com.example.mygroceries.mydomains.edititemsdomain;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class edititemsadapter extends RecyclerView.Adapter<edititemsadapter.edititemViewHolder>{
    Context context;
    List<edititemsdomain> list;
    int overalprice=0;
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

        holder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final DialogPlus dialogPlus=DialogPlus.newDialog(holder.image.getContext()).setContentHolder(
                        new ViewHolder(R.layout.update_popup)
                ).setExpanded(true,1500).create();

                View view= dialogPlus.getHolderView();
                EditText name= view.findViewById(R.id.admineditnm);
                EditText description=view.findViewById(R.id.admineditdescr);
                EditText price =view.findViewById(R.id.admineditprc);
                Button update=view.findViewById(R.id.updatebtn);

                name.setText(holder.name.getText());
                description.setText(holder.description.getText());
                price.setText(holder.price.getText());

                String myname=name.getText().toString();
                String mydescription=description.getText().toString();
                String myprice=price.getText().toString();
                dialogPlus.show();

                update.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        DatabaseReference reference;
                        reference= FirebaseDatabase.getInstance().getReference("Store Items");
                        HashMap<String,Object> map =new HashMap<>();
                        map.put("name",myname);
                        map.put("description",mydescription);
                        map.put("price",myprice);

                        reference.child(myname).updateChildren(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                Toast.makeText(holder.name.getContext(), "data updated successfully", Toast.LENGTH_SHORT).show();
                                dialogPlus.dismiss();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(holder.name.getContext(), "An error occured during updating", Toast.LENGTH_SHORT).show();
                                dialogPlus.dismiss();
                            }
                        });

                    }
                });

            }
        });

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
        Button edit;

        public edititemViewHolder(@NonNull View itemView) {
            super(itemView);

            image=itemView.findViewById(R.id.item_image);
            name=itemView.findViewById(R.id.item_name);
            description=itemView.findViewById(R.id.item_description);
            price=itemView.findViewById(R.id.item_pricee);
            edit=itemView.findViewById(R.id.adminedit);


        }

    }

}
