package com.example.mygroceries;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mygroceries.adapter.cartAdapter;
import com.example.mygroceries.mydomains.AdminOrders;
import com.example.mygroceries.mydomains.cart;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class AdminViewOrders extends AppCompatActivity {
    RecyclerView recyclerView;
    DatabaseReference db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_view_orders);

        recyclerView=findViewById(R.id.admin_cart);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        db= FirebaseDatabase.getInstance().getReference("Cart List").child("Admin View");

    }
    public void myadapter(){
        FirebaseRecyclerOptions<AdminOrders> options= new FirebaseRecyclerOptions.Builder<AdminOrders>()
                .setQuery(ordersRef, AdminOrders.class).build();
        FirebaseRecyclerAdapter<AdminOrders, AdminOrdersViewHolder> adapter =
                new FirebaseRecyclerAdapter<AdminOrders, AdminOrdersViewHolder>(options) {
                    @Override
                    protected void onBindViewHolder(@NonNull AdminOrdersViewHolder holder, final int position, @NonNull final AdminOrders model) {

                        holder.userPhoneNumber.setText("Name: "+model.getPhone());
                        holder.userTotalPrice.setText("Total Amount = Ksh."+model.getTotalAmount());
                        holder.userDateTime.setText("Order at: "+model.getDate()+" "+ model.getTime());
                        holder.userShippingAddress.setText("Shipping Address: "+model.getAddress();
                        holder.showOrdersBtn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                String uID = getRef(position).getKey();
                                Intent intent = new Intent(AdminNewOrdersActivity.this,AdminUserProductsActivity.class);
                                intent.putExtra("uid",uID);
                                startActivity(intent);
                            }
                        });

                        holder.itemView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                                CharSequence options[] =new CharSequence[]{
                                        "Yes",
                                        "No"

                                };

                                AlertDialog.Builder builder = new AlertDialog.Builder(AdminNewOrdersActivity.this);
                                builder.setTitle("Have you shipped this order products?");
                                builder.setItems(options, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        if (i==0){
                                            String uID = getRef(position).getKey();
                                            RemoverOrder(uID);

                                        }
                                        else {
                                            finish();
                                        }

                                    }
                                });
                                builder.show();
                            }
                        });

                    }

                    @NonNull
                    @Override
                    public AdminOrdersViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.admincart,parent,false);
                        return new AdminOrdersViewHolder(view);
                    }
                };
        ordersList.setAdapter(adapter);
        adapter.startListening();
    }
    public static class AdminOrdersViewHolder extends RecyclerView.ViewHolder{

        public TextView userPhoneNumber,userTotalPrice,userDateTime,userShippingAddress;
        public Button showOrdersBtn;
        public AdminOrdersViewHolder(View itemView) {
            super(itemView);
            userPhoneNumber = itemView.findViewById(R.id.adminviewphone);
            userTotalPrice = itemView.findViewById(R.id.adminviewtotalprice);
            userDateTime = itemView.findViewById(R.id.adminviewdate);
            userShippingAddress = itemView.findViewById(R.id.adminviewaddress);
            showOrdersBtn = itemView.findViewById(R.id.adminshowbtn);
        }
    }
    private void RemoverOrder(String uID) {
        ordersRef.child(uID).removeValue();
    }
}

}