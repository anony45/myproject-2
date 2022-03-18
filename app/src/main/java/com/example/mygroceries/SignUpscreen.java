package com.example.mygroceries;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.FirebaseException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class SignUpscreen extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_upscreen);

        final EditText inputMobile =findViewById(R.id.inputMobile);
        Button getOTPButton =findViewById(R.id.getOTPButton);

        final ProgressBar progressBar =findViewById(R.id.progressBar);


        getOTPButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (inputMobile.getText().toString().trim().isEmpty()){
                    Toast.makeText(SignUpscreen.this, "Enter Mobile",Toast.LENGTH_SHORT).show();
                    return;
                }
                progressBar.setVisibility(View.VISIBLE);
                getOTPButton.setVisibility(View.INVISIBLE);

                PhoneAuthProvider.getInstance().verifyPhoneNumber(
                        "+254"+inputMobile.getText().toString(),60, TimeUnit.MILLISECONDS,
                        SignUpscreen.this,
                        new PhoneAuthProvider.OnVerificationStateChangedCallbacks(){
                            @Override
                            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                                progressBar.setVisibility(View.GONE);
                                getOTPButton.setVisibility(View.VISIBLE);

                            }

                            @Override
                            public void onVerificationFailed(@NonNull FirebaseException e) {
                                progressBar.setVisibility(View.GONE);
                                getOTPButton.setVisibility(View.VISIBLE);
                                Toast.makeText(SignUpscreen.this,e.getMessage(),Toast.LENGTH_SHORT).show();

                            }

                            @Override
                            public void onCodeSent(@NonNull String verificationId, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                                progressBar.setVisibility(View.GONE);
                                getOTPButton.setVisibility(View.VISIBLE);
                                Intent intent = new Intent(getApplicationContext(),VerificationScreen.class);
                                intent.putExtra("mobile",inputMobile.getText().toString());
                                intent.putExtra("verificationId",verificationId);
                                startActivity(intent);
                            }
                        }
                );
                Intent intent = new Intent(getApplicationContext(),VerificationScreen.class);
                intent.putExtra("mobile",inputMobile.getText().toString());
                startActivity(intent);
            }
        });
    }
}