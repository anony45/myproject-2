package com.example.mygroceries;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class deliveryDetails extends AppCompatActivity {
    EditText name,phone,address;
    TextView price;
    Button submit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delivery_details);

        name=findViewById(R.id.deliveryName);
        phone=findViewById(R.id.deliveryPhone);
        address=findViewById(R.id.deliveryAdress);
        price=findViewById(R.id.deliveryPrice);
        submit=findViewById(R.id.deliveryConfirmButton);

        //get total price from previous ativity
        String total= getIntent().getStringExtra("Total price");

        price.setText(total);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                check();
            }
        });
    }
    private void check (){
        if(TextUtils.isEmpty(name.getText().toString())){
            Toast.makeText(this,"Please Provide Your Full Name",Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(phone.getText().toString())){
            Toast.makeText(this,"Please Provide Your Phone Number",Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(address.getText().toString())){
            Toast.makeText(this,"Please Provide Your Valid Address.",Toast.LENGTH_SHORT).show();
        }
        else {
            ConfirmOrder();
        }

    }
    private void ConfirmOrder(){
        final String saveCurrentTime,saveCurrentDate;
        Calendar calForDate = Calendar.getInstance();
        SimpleDateFormat currentDate = new SimpleDateFormat("MMM dd. yyy");
        saveCurrentDate = currentDate.format(calForDate.getTime());
        SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm:ss a");
        saveCurrentTime = currentDate.format(calForDate.getTime());
        final DatabaseReference ordersRef= FirebaseDatabase.getInstance().getReference()
                .child("Orders").child(Prevalent.currentOnlineUser.getPhone());
        HashMap<String, Object> ordersMap = new HashMap<>();
        ordersMap.put("totalAmount",price);
        ordersMap.put("name",name.getText().toString());
        ordersMap.put("phone",phone.getText().toString());
        ordersMap.put("address",address.getText().toString());
        ordersMap.put("date",saveCurrentDate);
        ordersMap.put("time",saveCurrentTime);
        ordersRef.updateChildren(ordersMap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    FirebaseDatabase.getInstance().getReference()
                            .child("Cart List")
                            .child("User view")
                            .removeValue()
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()){
                                        Toast.makeText(deliveryDetails.this,"Your final Order has been placed successfully.",Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(deliveryDetails.this,Home.class);
                                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                        startActivity(intent);
                                        finish();
                                    }
                                }
                            });
                }
            }
        });


    }
}