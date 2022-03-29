package com.example.mygroceries;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

public class editPage extends AppCompatActivity {
    ImageView edit_image;
    EditText edit_name,edit_price,edit_description;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_page);

        edit_image=findViewById(R.id.edit_product_img);
        edit_name=findViewById(R.id.edit_product_name);
        edit_price=findViewById(R.id.edit_product_price);
        edit_description=findViewById(R.id.edit_product_description);
        reference= FirebaseDatabase.getInstance().getReference("Store Items");

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
        if (isNamechanged()||isDescriptionchanged()||isPriceChanged()){
            Toast.makeText(this, "data has been updated successfully", Toast.LENGTH_SHORT).show();

        }


    }
    private boolean isNamechanged(){
     return true;

    }
    private boolean isDescriptionchanged(){
        return true;
    }
    private boolean isPriceChanged(){
        return true;
    }

}