package com.example.mygroceries;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class loginscreen extends AppCompatActivity {
    EditText fullname,email,phonenumber;
    Button signupbtn;
    FirebaseDatabase rootNode;
    DatabaseReference reference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loginscreen);

        //creating instances
        fullname=findViewById(R.id.fullname);
        email=findViewById(R.id.email);
        phonenumber=findViewById(R.id.phonenumber);
        signupbtn=findViewById(R.id.signupbtn);

        //save data to db onclick
        signupbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rootNode = FirebaseDatabase.getInstance();
                reference= rootNode.getReference("users");

                //get data from text fields
                String name = fullname.getEditableText().toString();
                String emails = email.getEditableText().toString();
                String phoneNo = phonenumber.getEditableText().toString();

                //go to next screen
                Intent intent = new Intent(loginscreen.this,vendorLogin.class);
                intent.putExtra("phoneNo",phoneNo);
                startActivity(intent);

                StoreUserClass userClass =new StoreUserClass(name,emails,phoneNo);
               reference.child(phoneNo).setValue(userClass);



            }
        });


    }
}