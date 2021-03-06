package com.example.e_kostan.Menu;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;

import com.example.e_kostan.Menu.Menu_Login;
import com.example.e_kostan.R;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;

public class Menu_Splash_Screen extends AppCompatActivity {
    private int waktu_loading=1000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu__splash__screen);
        FusedLocationProviderClient mFusedLocation = LocationServices.getFusedLocationProviderClient(Menu_Splash_Screen.this);
        if (ActivityCompat.checkSelfPermission(Menu_Splash_Screen.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(Menu_Splash_Screen.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent home=new Intent(Menu_Splash_Screen.this, Menu_Login.class);
                startActivity(home);
                finish();
            }
        },waktu_loading);
    }
}