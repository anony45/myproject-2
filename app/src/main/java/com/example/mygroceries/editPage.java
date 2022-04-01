package com.example.mygroceries;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Map;

public class editPage extends AppCompatActivity {
    ImageView edit_image;
    EditText edit_name,edit_price,edit_description;
    DatabaseReference reference;
    Button update;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_page);

        edit_image=findViewById(R.id.edit_product_img);
        edit_name=findViewById(R.id.edit_product_name);
        edit_price=findViewById(R.id.edit_product_price);
        edit_description=findViewById(R.id.edit_product_description);
        update=findViewById(R.id.update_items);
        reference= FirebaseDatabase.getInstance().getReference("Store Items");

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                update();
            }
        });

        //passing data from admin edit to recycler
        String description=getIntent().getStringExtra("description");
        String image=getIntent().getStringExtra("image");
        String price=getIntent().getStringExtra("price");
        String name=getIntent().getStringExtra("name");
        //set the output
        edit_name.setText(name);
        edit_price.setText(price);
        edit_description.setText(description);
        Picasso.get().load(image).into(edit_image);
    }

    public void update(){
        Map<String,Object> map= new HashMap<>();
        map.put("name",edit_name.getText().toString());
        map.put("description",edit_description.getText().toString());
        map.put("price",edit_price.getText().toString());

      reference.updateChildren(map).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                Toast.makeText(editPage.this, "data updated successfully", Toast.LENGTH_SHORT).show();

                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(editPage.this, "An error occured during updating", Toast.LENGTH_SHORT).show();

                            }
                        });

                    }
}