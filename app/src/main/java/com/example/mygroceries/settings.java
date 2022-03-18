package com.example.mygroceries;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.mygroceries.databinding.ActivityAddStoreBinding;
import com.example.mygroceries.databinding.ActivityCartPageBinding;
import com.example.mygroceries.databinding.ActivitySettingsBinding;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class settings extends Nav_base{
    ActivitySettingsBinding settingsBinding;
    EditText phone,address,email;
    ImageView select_profile;
    Button upload;
    DatabaseReference db;
    ProgressDialog progress;
    String downloadImageUrl,randomkey,saveCurrentDate,saveCurrentTime,sphone,saddress,semail;
    private static final int galleryPic =1 ;
    private StorageReference storeImagesRef;
    private Uri uri;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        settingsBinding=ActivitySettingsBinding.inflate(getLayoutInflater());
        setContentView(settingsBinding.getRoot());
        allocatetitle("Settings");

        phone=findViewById(R.id.settings_phone);
        address=findViewById(R.id.settings_address);
        email=findViewById(R.id.settings_email);
        select_profile=findViewById(R.id.settings_image);
        upload=findViewById(R.id.settings_button);
        progress=new ProgressDialog(this);

        storeImagesRef= FirebaseStorage.getInstance().getReference().child("Settings Image Profile");
        db= FirebaseDatabase.getInstance().getReference().child("person settings");

        select_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openGallery();
            }
        });
        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validateStoreData();
            }
        });

    }
    private void openGallery(){
        Intent galleryIntent=new Intent();
        galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
        galleryIntent.setType("image/*");
        startActivityForResult(galleryIntent,galleryPic);

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==galleryPic && resultCode==RESULT_OK && data!=null){
            uri =data.getData();
            select_profile.setImageURI(uri);
        }
    }
    private void validateStoreData(){
        sphone=phone.getText().toString();
        semail=email.getText().toString();
        saddress=address.getText().toString();
        if (uri==null){
            Toast.makeText(this, "product image is needed", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(sphone)){
            Toast.makeText(this, "please write your phone number", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(semail)){
            Toast.makeText(this, "please write your email", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(saddress)){
            Toast.makeText(this, "please write your address", Toast.LENGTH_SHORT).show();
        }
        else {
            StoretheInformation();

        }

    }
    private void StoretheInformation(){
        progress.setTitle("Updating Your Profile");
        progress.setMessage("Dear user, please wait while we are updating your details.");
        progress.setCanceledOnTouchOutside(false);
        progress.show();
        Calendar calendar = Calendar.getInstance();

        SimpleDateFormat currentDate = new SimpleDateFormat("MMM dd, yyyy");
        saveCurrentDate = currentDate.format(calendar.getTime());

        SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm:ss a");
        saveCurrentTime = currentTime.format(calendar.getTime());

        randomkey = saveCurrentDate + saveCurrentTime;

        final StorageReference filepath =storeImagesRef.child(uri.getLastPathSegment()+randomkey+".jpg");
        final UploadTask uploadTask =filepath.putFile(uri);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                String message= e.toString();
                Toast.makeText(settings.this, "Error"+message, Toast.LENGTH_SHORT).show();
                progress.dismiss();
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Toast.makeText(settings.this, "Profile Image uploaded Successfully", Toast.LENGTH_SHORT).show();
                Task<Uri> uriTask= uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                    @Override
                    public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {

                        if (!task.isSuccessful()){
                            throw task.getException();
                        }
                        downloadImageUrl =filepath.getDownloadUrl().toString();
                        return filepath.getDownloadUrl();
                    }
                }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                    @Override
                    public void onComplete(@NonNull Task<Uri> task) {
                        if (task.isSuccessful()){
                            downloadImageUrl=task.getResult().toString();
                            Toast.makeText(settings.this, "got image url successfully", Toast.LENGTH_SHORT).show();
                            saveinfotoDatabase();
                        }
                    }
                });
            }
        });
    }
    private void saveinfotoDatabase(){
        HashMap<String,Object> hashMap =new HashMap<>();
        hashMap.put("id",randomkey);
        hashMap.put("date",saveCurrentDate);
        hashMap.put("time",saveCurrentTime);
        hashMap.put("personemail",semail);
        hashMap.put("personimg",downloadImageUrl);
        hashMap.put("personphone",sphone);
        hashMap.put("personaddress",saddress);

        db.child(randomkey).updateChildren(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    Intent intent = new Intent(settings.this, vendorLogin.class);
                    startActivity(intent);
                    progress.dismiss();
                    Toast.makeText(settings.this, "product is added successfully", Toast.LENGTH_SHORT).show();
                }
                else {
                    progress.dismiss();
                    String message= task.getException().toString();
                    Toast.makeText(settings.this, "Error:"+message, Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}