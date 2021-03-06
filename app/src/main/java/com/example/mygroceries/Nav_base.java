package com.example.mygroceries;

import static com.example.mygroceries.R.id.nav_home;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.DialogInterface;
import android.content.Intent;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;

import com.google.android.material.navigation.NavigationView;

public class Nav_base extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    DrawerLayout drawerLayout;


    @Override
    public void setContentView(View view) {
        drawerLayout= (DrawerLayout)getLayoutInflater().inflate(R.layout.activity_nav_base,null);
        FrameLayout container=drawerLayout.findViewById(R.id.container);
        container.addView(view);
        super.setContentView(drawerLayout);
        Toolbar toolbar=drawerLayout.findViewById(R.id.Toolbars);
        setSupportActionBar(toolbar);

        NavigationView navigationView= drawerLayout.findViewById(R.id.nav_view);

        navigationView.setNavigationItemSelectedListener(this);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.menu_drawer_open,R.string.menu_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item){
        drawerLayout.closeDrawer(GravityCompat.START);
        switch (item.getItemId()){
            case  nav_home:
                startActivity(new Intent(this,vendorLogin.class));
                break;
            case  R.id.nav_cart:
                startActivity(new Intent(this,cartPage.class));
                break;
            case  R.id.myOrders:
                startActivity(new Intent(this,ongoingOrders.class));
                break;
            case  R.id.settings:
                startActivity(new Intent(this,settings.class));
                break;
            case  R.id.nav_logout:
                startActivity(new Intent(this,MainActivity.class));
                break;

        }
        return true;
    }
    private void signOut(){
        AlertDialog.Builder builder= new AlertDialog.Builder(this);
        builder.setTitle("SignOut").setMessage("Do you really want to sign out?").setNegativeButton("cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        }).setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent= new Intent(Nav_base.this,MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
            }
        });
    }
    protected void allocatetitle(String titlestring){
        if (getSupportActionBar()!=null){
            getSupportActionBar().setTitle(titlestring);

    }
  }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {
        super.onPointerCaptureChanged(hasCapture);
    }
}