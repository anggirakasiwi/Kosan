package com.example.e_kostan.Menu;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.e_kostan.Menu_Pemilik.Dashboard_Pemilik;
import com.example.e_kostan.R;
import com.example.e_kostan.Session.SharedPrefManager;
import com.example.e_kostan.server.InitRetrofit;
import com.squareup.picasso.Picasso;

public class Menu_Profile extends AppCompatActivity {
TextView Email,Password,Nama,No_Hp,Alamat,Jenis_Kelamin,Jenis_Akun,Readd;
ImageView GambarProfile;
Button Logout,EditProfile;
SharedPrefManager sharedPrefManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu__profile);
        sharedPrefManager=new SharedPrefManager(Menu_Profile.this);
        Email=(TextView) findViewById(R.id.emailprofile);
        Email.setText(sharedPrefManager.getSPEmail());
//        Password=(TextView)findViewById(R.id.passwordprofile);
//        Password.setText(sharedPrefManager.getSPPassword());
        Nama=(TextView) findViewById(R.id.namaprofile);
        Nama.setText(sharedPrefManager.getSPNama());
        No_Hp=(TextView) findViewById(R.id.no_hp_profile);
        No_Hp.setText(sharedPrefManager.getSPTelepon());
        Alamat=(TextView) findViewById(R.id.alamatprofile);
        Alamat.setText(sharedPrefManager.getSPAlamat());
        Jenis_Kelamin=(TextView) findViewById(R.id.jeniskelaminprofile);
        Jenis_Kelamin.setText(sharedPrefManager.getSPJenis_Kelamin());
        Jenis_Akun=(TextView)findViewById(R.id.jenisakunprofile);
        Jenis_Akun.setText(sharedPrefManager.getSPRole());
        GambarProfile=(ImageView) findViewById(R.id.gambarprofile);
        final String urlgambar = InitRetrofit.BASE_URL+sharedPrefManager.getSpGambar();
        Log.d("image",urlgambar);
        Picasso.with(Menu_Profile.this).load(urlgambar).into(GambarProfile);
        Logout=(Button) findViewById(R.id.logoutprofile);
        EditProfile=(Button) findViewById(R.id.editprofile);
        EditProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in=new Intent(Menu_Profile.this, com.example.e_kostan.Menu.EditProfile.class);
                startActivity(in);
                finish();
            }
        });

        Logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Keluar();

            }
        });

    }

    private void Keluar() {
        sharedPrefManager.saveSPBoolean(SharedPrefManager.SP_SUDAH_LOGIN,false);
        Intent intent=new Intent(Menu_Profile.this,Menu_Login.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onBackPressed(){
        Back();
 }

    private void Back() {
//        Intent intent=new Intent(Menu_Profile.this,Menu_Utama.class);
//        startActivity(intent);
//        finish();
        String Jenis_Akun = sharedPrefManager.getSPRole();
        if (sharedPrefManager.getSPRole().equals("Pemilik")) {
//            startActivity(new Intent(Menu_Login.this, Dashboard_Pemilik.class)
//                    .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK));
//            finish();
//            Toast.makeText(this, "Penyewa", Toast.LENGTH_SHORT).show();
            Intent intent=new Intent(Menu_Profile.this,Dashboard_Pemilik.class);
          startActivity(intent);
            finish();
        } else if (sharedPrefManager.getSPRole().equals("Penyewa")){
            Intent intent=new Intent(Menu_Profile.this,Menu_Utama.class);
        startActivity(intent);
        finish();
//            Toast.makeText(this, "Penyewa", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Terjadi Kesalahan", Toast.LENGTH_SHORT).show();
        }
    }
}