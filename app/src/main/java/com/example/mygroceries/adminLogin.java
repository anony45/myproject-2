package com.example.mygroceries;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class adminLogin extends AppCompatActivity {
    private TextView userlink;
    private EditText adminEmail,adminPassword;
    private Button loginbtn;
    private ProgressDialog loadingbar;
    private String parentdbname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_login);
        userlink=findViewById(R.id.userlink);
        adminEmail=findViewById(R.id.adminEmail);
        adminPassword=findViewById(R.id.adminPassword);
        loginbtn=findViewById(R.id.loginbtn);

        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LoginUser();

            }
        });
        userlink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(adminLogin.this,SignUpscreen.class);
                startActivity(intent);

            }
        });

    }
    private void LoginUser(){
        String email=adminEmail.getText().toString();
        String password=adminPassword.getText().toString();
        if(TextUtils.isEmpty(email)){
            Toast.makeText(this, "please enter your email", Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(password)){
            Toast.makeText(this, "please enter your password", Toast.LENGTH_SHORT).show();
        }
        else {
            loadingbar.setTitle(("login activity"));
            loadingbar.setMessage("please wait as we check your credentials");
            loadingbar.setCanceledOnTouchOutside(false);
            loadingbar.show();

            AllowAccesstoAccount(email,password);

        }

    }
    private void AllowAccesstoAccount(final String email,final String password){
        final DatabaseReference db;
        db= FirebaseDatabase.getInstance().getReference();
        db.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.child(parentdbname).child(email).exists()){
                    StoreUserClass userClass =snapshot.child(parentdbname).child(email).getValue(StoreUserClass.class);
                    if (userClass.getEmail().equals(email)){
                        if (userClass.getPassword().equals(password)){
                            if (parentdbname.equals("Admins")){
                                Toast.makeText(adminLogin.this, "You have successfully been logged in", Toast.LENGTH_SHORT).show();
                                loadingbar.dismiss();
                                Intent intent =new Intent(adminLogin.this,addStore.class);
                                startActivity(intent);
                            }
                        }
                        else {
                            loadingbar.dismiss();
                            Toast.makeText(adminLogin.this, "password incorrect", Toast.LENGTH_SHORT).show();
                        }
                    }

                }
                Toast.makeText(adminLogin.this, "account "+email+"does not exist", Toast.LENGTH_SHORT).show();
                loadingbar.dismiss();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}