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

public class addItems extends AppCompatActivity {
    private EditText name,description,price;
    private String Description,saveCurrentDate,saveCurrentTime,itemName,Price;
    private ImageView selectItemImage;
    private Button submit;
    private static final int galleryPic =1 ;
    private Uri uri;
    private String itemRandomKey, downloadImageUrl;
    private StorageReference itemImagesRef;
    private DatabaseReference itemRef;
    private ProgressDialog loadingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_items);

        itemImagesRef= FirebaseStorage.getInstance().getReference().child("Items Images");
        itemRef= FirebaseDatabase.getInstance().getReference().child("Store Items");

        submit=findViewById(R.id.submitButton);
        selectItemImage=findViewById(R.id.edit_image);
        name=findViewById(R.id.edit_name);
        price=findViewById(R.id.edit_price);
        description=findViewById(R.id.edit_description);
        loadingBar=new ProgressDialog(this);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validateItemData();

            }
        });

        selectItemImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openGallery();

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
            selectItemImage.setImageURI(uri);
        }
    }
    private void validateItemData(){
        Description=description.getText().toString();
        itemName=name.getText().toString();
        Price=price.getText().toString();


        if (uri==null){
            Toast.makeText(this, "product image is needed", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(Description)){
            Toast.makeText(this, "please write the product description", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(Price)){
            Toast.makeText(this, "please write price of item", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(itemName)){
            Toast.makeText(this, "please write the item name", Toast.LENGTH_SHORT).show();
        }
        else {
            StoretheInformation();

        }

    }
    private void StoretheInformation(){
        loadingBar.setTitle("Add New Product");
        loadingBar.setMessage("Dear Admin, please wait while we are adding the new item.");
        loadingBar.setCanceledOnTouchOutside(false);
        loadingBar.show();
        Calendar calendar = Calendar.getInstance();

        SimpleDateFormat currentDate = new SimpleDateFormat("MMM dd, yyyy");
        saveCurrentDate = currentDate.format(calendar.getTime());

        SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm:ss a");
        saveCurrentTime = currentTime.format(calendar.getTime());

        itemRandomKey = saveCurrentDate + saveCurrentTime;

        final StorageReference filepath =itemImagesRef.child(uri.getLastPathSegment()+itemRandomKey+".jpg");
        final UploadTask uploadTask =filepath.putFile(uri);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                String message= e.toString();
                Toast.makeText(addItems.this, "Error"+message, Toast.LENGTH_SHORT).show();
                loadingBar.dismiss();
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Toast.makeText(addItems.this, "Product Image uploaded Successfully", Toast.LENGTH_SHORT).show();
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
                            Toast.makeText(addItems.this, "got image url successfully", Toast.LENGTH_SHORT).show();
                            saveinfotoDatabase();
                        }
                    }
                });
            }
        });
    }
    private void saveinfotoDatabase(){
        HashMap<String,Object> itemmap =new HashMap<>();
        itemmap.put("storeid",itemRandomKey);
        itemmap.put("date",saveCurrentDate);
        itemmap.put("time",saveCurrentTime);
        itemmap.put("description",Description);
        itemmap.put("image",downloadImageUrl);
        itemmap.put("name",itemName);
        itemmap.put("price",Price);

        itemRef.child(itemRandomKey).updateChildren(itemmap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    Intent intent = new Intent(addItems.this,vendorLogin.class);
                    startActivity(intent);
                    loadingBar.dismiss();
                    Toast.makeText(addItems.this, "product is added successfully", Toast.LENGTH_SHORT).show();
                }
                else {
                    loadingBar.dismiss();
                    String message= task.getException().toString();
                    Toast.makeText(addItems.this, "Error:"+message, Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}