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

import com.example.mygroceries.mydomains.stores;
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

public class addStore extends AppCompatActivity {

    private EditText name,description;
    private String Description,saveCurrentDate,saveCurrentTime,storename;
    private ImageView selectImage;
    private Button upload,next;
    private static final int galleryPic =1 ;
    private Uri uri;
    private String storeRandomKey, downloadImageUrl;
    private StorageReference storeImagesRef;
    private DatabaseReference storeRef;
    private ProgressDialog loadingBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_store);

        //new code
        storeImagesRef=FirebaseStorage.getInstance().getReference().child("Store Image");
        storeRef=FirebaseDatabase.getInstance().getReference().child("Stores");

        upload=findViewById(R.id.upload);
        selectImage=findViewById(R.id.selectStoreImage);
        name=findViewById(R.id.storeName);
        description=findViewById(R.id.storeDescription);
        loadingBar=new ProgressDialog(this);

        selectImage.setOnClickListener(new View.OnClickListener() {
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
            selectImage.setImageURI(uri);
        }
    }
    private void validateStoreData(){
        Description=description.getText().toString();
        storename=name.getText().toString();
        if (uri==null){
            Toast.makeText(this, "product image is needed", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(Description)){
            Toast.makeText(this, "please write the product description", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(storename)){
            Toast.makeText(this, "please write the store name", Toast.LENGTH_SHORT).show();
        }
        else {
            StoretheInformation();

        }

    }
    private void StoretheInformation(){
        loadingBar.setTitle("Add New Product");
        loadingBar.setMessage("Dear Admin, please wait while we are adding the new product.");
        loadingBar.setCanceledOnTouchOutside(false);
        loadingBar.show();
        Calendar calendar = Calendar.getInstance();

        SimpleDateFormat currentDate = new SimpleDateFormat("MMM dd, yyyy");
        saveCurrentDate = currentDate.format(calendar.getTime());

        SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm:ss a");
        saveCurrentTime = currentTime.format(calendar.getTime());

        storeRandomKey = saveCurrentDate + saveCurrentTime;

        final StorageReference filepath =storeImagesRef.child(uri.getLastPathSegment()+storeRandomKey+".jpg");
        final UploadTask uploadTask =filepath.putFile(uri);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                String message= e.toString();
                Toast.makeText(addStore.this, "Error"+message, Toast.LENGTH_SHORT).show();
                loadingBar.dismiss();
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Toast.makeText(addStore.this, "Product Image uploaded Successfully", Toast.LENGTH_SHORT).show();
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
                            Toast.makeText(addStore.this, "got image url successfully", Toast.LENGTH_SHORT).show();
                            saveinfotoDatabase();
                        }
                    }
                });
            }
        });
    }
    private void saveinfotoDatabase(){
        HashMap<String,Object> storemap =new HashMap<>();
        storemap.put("storeid",storeRandomKey);
        storemap.put("date",saveCurrentDate);
        storemap.put("time",saveCurrentTime);
        storemap.put("description",Description);
        storemap.put("image",downloadImageUrl);
        storemap.put("name",storename);

        storeRef.child(storeRandomKey).updateChildren(storemap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    Intent intent = new Intent(addStore.this, vendorLogin.class);
                    startActivity(intent);
                    loadingBar.dismiss();
                    Toast.makeText(addStore.this, "product is added successfully", Toast.LENGTH_SHORT).show();
                }
                else {
                    loadingBar.dismiss();
                    String message= task.getException().toString();
                    Toast.makeText(addStore.this, "Error:"+message, Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}