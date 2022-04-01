package com.example.mygroceries;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.mygroceries.mydomains.items;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class itemsDescriptions extends AppCompatActivity {
    private Button addtocart;
    private ImageView productimg;
    private TextView descriptionofitem,priceofitem,nameofitem,fee;
    private String productid="",state="normal", myimage="";
    FirebaseUser user;
    String userid;
    LinearLayout inc;
    TextView t1,t2,t3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_items_descriptions);
        //quantity button
        inc=findViewById(R.id.inc);
        t1=findViewById(R.id.t1);
        t2=findViewById(R.id.t2);
        t3=findViewById(R.id.t3);
        t1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                increment(false);

            }
        });
        t3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                increment(true);
            }
        });
        //
        priceofitem=findViewById(R.id.price_id);
        nameofitem=findViewById(R.id.name_id);
        addtocart=findViewById(R.id.add_to_cart_button);
        productimg= findViewById(R.id.product_image);
        descriptionofitem =findViewById(R.id.description_of_product);
        fee=findViewById(R.id.fee);
        user= FirebaseAuth.getInstance().getCurrentUser();
        userid=user.getUid();


        //get data from previous activity
        String description=getIntent().getStringExtra("description");
        String img=getIntent().getStringExtra("image");
        String pricee=getIntent().getStringExtra("price");
        String namee=getIntent().getStringExtra("name");

        myimage=img;

        priceofitem.setText(pricee);
        nameofitem.setText(namee);
        descriptionofitem.setText(description);
        Picasso.get().load(img).into(productimg);


        addtocart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                    addingToCartList();
            }
        });

    }
    private void  increment(Boolean x){
        int y=Integer.parseInt(t2.getText().toString());
        if (x){
            y++;
            t2.setText(String.valueOf(y));
        }else {
            y--;
            if (y==0){
                inc.setVisibility(View.GONE);
            }
            else {
                t2.setText(String.valueOf(y));
            }
        }
        Toast.makeText(this, t2.getText(), Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onStart() {
        super.onStart();
        CheckOrderState();
    }
   private void addingToCartList(){
        String saveCurrentTime,saveCurrentDate,itemRandomKey;
       Calendar calForDate = Calendar.getInstance();

       SimpleDateFormat currentDate = new SimpleDateFormat("MMM dd, yyyy");
       saveCurrentDate = currentDate.format(calForDate.getTime());

       SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm:ss a");
       saveCurrentTime = currentTime.format(calForDate.getTime());

       itemRandomKey = saveCurrentDate + saveCurrentTime;
       String name=nameofitem.getText().toString();

       final DatabaseReference cartListRef = FirebaseDatabase.getInstance().getReference("Cart List");
       final HashMap<String, Object> cartMap = new HashMap<>();
       cartMap.put("pid",productid);
       cartMap.put("delivery fee",fee.getText().toString());
       cartMap.put("name",name);
       cartMap.put("quantity",t2.getText().toString());
       cartMap.put("price" ,priceofitem.getText().toString());
       cartMap.put("date",saveCurrentDate);
       cartMap.put("time",saveCurrentTime);
       cartMap.put("image",myimage);
       cartMap.put("userid",userid);

       cartListRef.child("User View").child(name).updateChildren(cartMap).addOnCompleteListener(new OnCompleteListener<Void>() {
           @Override
           public void onComplete(@NonNull Task<Void> task) {
               if(task.isSuccessful()){
                   cartListRef.child("Admin View").child(name).updateChildren(cartMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                       @Override
                       public void onComplete(@NonNull Task<Void> task) {
                           if (task.isSuccessful()){
                               Toast.makeText(itemsDescriptions.this, "Added to cart List", Toast.LENGTH_SHORT).show();
                               Intent intent=new Intent(itemsDescriptions.this,cartPage.class);
                               intent.putExtra("theimage",String.valueOf(productimg));
                               startActivity(intent);
                           }
                       }
                   });
               }

           }
       });

   }
    private void getProductDetails(String productid) {
        DatabaseReference productsRef = FirebaseDatabase.getInstance().getReference().child("Products");
        productsRef.child(productid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()){
                    items itms=dataSnapshot.getValue(items.class);
                    descriptionofitem.setText(itms.getDecription());
                    Picasso.get().load(itms.getImage()).into(productimg);

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void CheckOrderState()
    {
        DatabaseReference ordersRef;
        ordersRef = FirebaseDatabase.getInstance().getReference().child("Orders");
        ordersRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()){
                    String shippingState = dataSnapshot.child("state").getValue().toString();
                    if (shippingState.equals("Shipped")){
                        state ="Order Shipped";
                    }
                    else if (shippingState.equals("Not Shipped")){
                        state ="Order Placed";
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

}