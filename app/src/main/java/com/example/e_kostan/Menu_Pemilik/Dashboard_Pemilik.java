package com.example.e_kostan.Menu_Pemilik;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.e_kostan.Menu.Menu_Massage;
import com.example.e_kostan.Menu.Menu_Profile;
import com.example.e_kostan.Menu.Menu_Utama;
import com.example.e_kostan.Menu.Notifikasi;
import com.example.e_kostan.Menu.TambahKosan;
import com.example.e_kostan.R;

public class Dashboard_Pemilik extends AppCompatActivity {
ImageView Profile, TambahKosan, Chating, Notifikasi;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard__pemilik);
        Profile=(ImageView) findViewById(R.id.iconprofilepemilik);
        TambahKosan=(ImageView) findViewById(R.id.iconmenambahkosan);
        Chating=(ImageView) findViewById(R.id.iconchatpemilik);
        Notifikasi=(ImageView) findViewById(R.id.iconnotifikasi);
        Profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goto_Profile ();
            }
        });
        Chating.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goto_Chating ();
            }
        });
        TambahKosan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goto_TambahKosan ();
            }
        });
        Notifikasi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goto_notifikasi ();
            }
        });
    }

    private void goto_notifikasi() {
        Intent intent=new Intent(Dashboard_Pemilik.this, Notifikasi.class);
        startActivity(intent);
        finish();
    }

    private void goto_TambahKosan() {
        Intent intent=new Intent(Dashboard_Pemilik.this, TambahKosan.class);
        startActivity(intent);
        finish();
    }

    private void goto_Chating() {
//       Pindah ke chating
        Intent intent=new Intent(Dashboard_Pemilik.this, Menu_Massage.class);
        startActivity(intent);
        finish();
    }

    private void goto_Profile() {
//        Pindah Kemenu Profile
        Intent intent=new Intent(Dashboard_Pemilik.this, Menu_Profile.class);
        startActivity(intent);
        finish();
    }

}