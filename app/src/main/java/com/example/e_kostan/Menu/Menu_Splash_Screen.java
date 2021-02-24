package com.example.e_kostan.Menu;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.example.e_kostan.R;

public class Menu_Splash_Screen extends AppCompatActivity {
    private int waktu_loading=1000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu__splash__screen);

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