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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
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
    FirebaseAuth auth;
    private String parentdbname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_login);
        userlink=findViewById(R.id.userlink);
        adminEmail=findViewById(R.id.adminEmail);
        adminPassword=findViewById(R.id.adminPassword);
        loginbtn=findViewById(R.id.loginbtn);
        auth=FirebaseAuth.getInstance();
        loadingbar=new ProgressDialog(adminLogin.this);

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
            auth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()){
                        Toast.makeText(adminLogin.this, "Login successfull", Toast.LENGTH_SHORT).show();
                        loadingbar.dismiss();
                        Intent intent=new Intent(adminLogin.this,AdminDashboard.class);
                       // intent.putExtra("email",String.valueOf(adminEmail));
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);

                    }
                    if (!task.isSuccessful()){
                        Toast.makeText(adminLogin.this, "An error ocurred "+task.getException().toString(), Toast.LENGTH_SHORT).show();

                    }
                }
            });
        }
    }
}