package com.example.mygroceries;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.mygroceries.adapter.cartAdapter;
import com.example.mygroceries.databinding.ActivityCartPageBinding;
import com.example.mygroceries.mydomains.cart;
import com.example.mygroceries.mydomains.kfruitDomain;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

public class cartPage extends Nav_base {
    ActivityCartPageBinding pageBinding;
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    List<cart> mylist;
    DatabaseReference db;
    Button proceed;
    TextView total_amount,message,ffinal,total,fee;
    int overalprice=0;
    int myttl=0;
    cartAdapter cartAdapter;
    Button submitorder;
    ImageView productimg;
    TextView descriptionofitem,priceofitem,nameofitem;
    String productid="",mytotal;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pageBinding=ActivityCartPageBinding.inflate(getLayoutInflater());
        setContentView(pageBinding.getRoot());
        allocatetitle("Your Cart");

        //declare the contents
        fee=findViewById(R.id.myfee);
        ffinal=findViewById(R.id.ftotal);
        total=findViewById(R.id.final_total);
        recyclerView=findViewById(R.id.cart_recycler);
        recyclerView.setHasFixedSize(true);
        layoutManager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        proceed=findViewById(R.id.submitbtn);

        //get image
        String img=getIntent().getStringExtra("image");

        proceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(cartPage.this, "Order placed successfully", Toast.LENGTH_SHORT).show();
                Intent intent= new Intent(cartPage.this,deliveryDetails.class);
                intent.putExtra("Total price",String.valueOf(total));

                startActivity(intent);
                finish();
            }
        });
        debate();
    }
    public void debate(){
        final DatabaseReference reference=FirebaseDatabase.getInstance().getReference("Cart List").child("User View");
        FirebaseRecyclerOptions<cart>options =new FirebaseRecyclerOptions.Builder<cart>().setQuery(reference,cart.class).build();

        FirebaseRecyclerAdapter<cart,cartAdapter> adapter= new FirebaseRecyclerAdapter<cart, cartAdapter>(options) {
            @Override
            protected void onBindViewHolder(@NonNull cartAdapter holder, int position, @NonNull cart model) {
                holder.name.setText(model.getName());
                holder.total_pricee.setText(model.getPrice());
                holder.quantity.setText(model.getQuantity());
                //calculating the total
                try{

                    int totalAmount= (Integer.valueOf(model.getPrice()))*(Integer.valueOf(model.getQuantity()));
                    overalprice=overalprice+totalAmount;
                    myttl=overalprice+100;
                    ffinal.setText("Total price = Ksh."+String.valueOf(overalprice));
                    total.setText("sub total= Ksh."+String.valueOf(myttl));

                } catch(NumberFormatException e){ // handle your exception
                }

                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        CharSequence options[] = new CharSequence[]
                                {
                                        "Edit",
                                        "Remove"
                                };
                        AlertDialog.Builder builder = new AlertDialog.Builder(cartPage.this);
                        builder.setTitle("Cart Options: ");
                        builder.setItems(options, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if (which==0){
                                    Intent intent=new Intent(cartPage.this,itemsDescriptions.class);
                                    intent.putExtra("name",model.getName());
                                    startActivity(intent);
                                }
                                if (which==1){
                                    reference.child("user view").child("products").child(model.getName()).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if(task.isSuccessful()){
                                                Toast.makeText(cartPage.this, "Item deleted successfully", Toast.LENGTH_SHORT).show();
                                                Intent intent= new Intent(cartPage.this,storeOne.class);
                                                startActivity(intent);
                                            }
                                        }
                                    });
                                }
                            }
                        });
                        builder.show();
                    }
                });

            }


            @NonNull
            @Override
            public cartAdapter onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_items,parent,false);
                cartAdapter adapter1 = new cartAdapter(view);
                return adapter1;
            }
        };
        recyclerView.setAdapter(adapter);
        adapter.startListening();
    }
    @Override
    protected void onStart() {
        super.onStart();


    }


}