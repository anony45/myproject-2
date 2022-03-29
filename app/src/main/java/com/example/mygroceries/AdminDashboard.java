package com.example.mygroceries;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class AdminDashboard extends AppCompatActivity {
    Button add,edit,view,button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_dashboard);

        add=findViewById(R.id.add_items_dashboard);
        view=findViewById(R.id.orders_dashboard);
        edit=findViewById(R.id.Edit_items_dashboard);
        button=findViewById(R.id.button7);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminDashboard.this,Home.class);
                startActivity(intent);

            }
        });

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminDashboard.this,addItems.class);
                startActivity(intent);
            }
        });
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminDashboard.this,AdminViewOrders.class);
                startActivity(intent);

            }
        });
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminDashboard.this,adminEditItems.class);
                startActivity(intent);

            }
        });
    }
}