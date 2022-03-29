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

public class adminRegistration extends AppCompatActivity {
    EditText email,password,confirmPassword;
    Button register;
    TextView link;
    FirebaseAuth auth;
    ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_registration);

        email=findViewById(R.id.AdminRegisterEmail);
        password=findViewById(R.id.adminCreatePass);
        confirmPassword=findViewById(R.id.adminConfirmPass);
        register=findViewById(R.id.adminRegister);
        link=findViewById(R.id.userregisterlink);
        auth=FirebaseAuth.getInstance();
        dialog=new ProgressDialog(adminRegistration.this);
        link.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginUser();

            }
        });
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(adminRegistration.this,SignUpscreen.class);
                startActivity(intent);
            }
        });


    }

    @Override
    protected void onStart() {
        super.onStart();
        if (auth.getCurrentUser()!null){
            startActivity(new Intent((adminRegistration.this,adminLogin.class)));
            finish();
        }
    }


    private void LoginUser(){
        String Email=email.getText().toString();
        String Password=password.getText().toString();
        String confirmpassword=confirmPassword.getText().toString();
        if(TextUtils.isEmpty(Email)){
            Toast.makeText(this, "please enter your email", Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(Password||Password.length()<8)){
            Toast.makeText(this, "please enter your password of about 8 characters", Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(confirmpassword||!confirmpassword.equals(Password))){
            Toast.makeText(this, "please enter passwords that match", Toast.LENGTH_SHORT).show();
        }
        else {
            dialog.setTitle(("Registration Activity"));
            dialog.setMessage("please wait as we create your account");
            dialog.setCanceledOnTouchOutside(false);
            dialog.show();
            auth.createUserWithEmailAndPassword(Email,Password).addOnCompleteListener(adminRegistration.this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()){
                        Toast.makeText(adminRegistration.this, "Account created successfully", Toast.LENGTH_SHORT).show();
                        Intent intent=new Intent(adminRegistration.this,AdminDashboard.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                    }

                    elif (!task.isSuccessful()){
                        Toast.makeText(adminRegistration.this, "An error ocurred ", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        startActivity(new Intent(adminRegistration.this,adminLogin.class));
                        finish();
                    }
                }
            })

        }

    }

}