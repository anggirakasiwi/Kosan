package com.example.e_kostan.Menu;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.e_kostan.R;
import com.example.e_kostan.Session.SharedPrefManager;
import com.example.e_kostan.adapter.adapter_kosan;
import com.example.e_kostan.model.Item_Kosan;
import com.example.e_kostan.respon.Response_Kosan;
import com.example.e_kostan.server.ApiServices;
import com.example.e_kostan.server.InitRetrofit;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Menu_Utama extends AppCompatActivity {
Button Log_Out, Termurah,Termahal,Terdekat,Rating;
RecyclerView recyclerView;
SharedPrefManager sharedPrefManager;
ProgressDialog loading;
ImageView Profile,Help,Message;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu__utama);
//        Log_Out=(Button) findViewById(R.id.btnLogout);
        sharedPrefManager=new SharedPrefManager(this);
        loading=new ProgressDialog(Menu_Utama.this);
//        Log_Out.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Keluar();
//            }
//        });
        Termurah=(Button)findViewById(R.id.btn_temurah);
        Termahal=(Button)findViewById(R.id.btn_termahal);
        Terdekat=(Button)findViewById(R.id.btn_terdekat);
        Rating=(Button)findViewById(R.id.btn_rating);
        Profile=(ImageView) findViewById(R.id.profile);
        Help=(ImageView) findViewById(R.id.help);
        Message=(ImageView) findViewById (R.id.massage);
        recyclerView = (RecyclerView) findViewById(R.id.list_kosan);
        recyclerView.setHasFixedSize(true);
        GridLayoutManager gridLayout=new GridLayoutManager(Menu_Utama.this,1);
        recyclerView.setLayoutManager(gridLayout);
        Tampil_Kosan();
        Profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goto_profile();
            }
        });
        Help.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goto_help();
            }
        });
        Message.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goto_messege();
            }
        });
        Termurah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Menu_Utama.this,Menu_Harga.class);
                startActivity(intent);
                finish();
            }
        });
        Termahal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Menu_Utama.this,Menu_Termahal.class);
                startActivity(intent);
                finish();
            }
        });
        Terdekat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Menu_Utama.this,Menu_Terdekat.class);
                startActivity(intent);
                finish();
            }
        });
        Rating.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Menu_Utama.this,Rating.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void goto_messege() {
        Intent intent=new Intent(Menu_Utama.this,Menu_Massage.class);
        startActivity(intent);
        finish();
    }

    private void goto_help() {
        Intent intent=new Intent(Menu_Utama.this,Menu_Help.class);
        startActivity(intent);
        finish();
    }

    private void goto_profile() {
        Intent intent=new Intent(Menu_Utama.this,Menu_Profile.class);
        startActivity(intent);
        finish();
    }

    private void Tampil_Kosan() {
        loading.setCancelable(true);
        loading.setMessage("Mohon Tunggu");
        loading.show();
        ApiServices api = InitRetrofit.getInstance().getApi();
        Call<Response_Kosan> menuCall = api.Tampil_Kosan();
        menuCall.enqueue(new Callback<Response_Kosan>() {
            @Override
            public void onResponse(Call<Response_Kosan> call, Response<Response_Kosan> response) {
                if (response.isSuccessful()){
                    Log.d("response api", response.body().toString());
                    List<Item_Kosan> device= response.body().getKosan();
                    boolean status = response.body().isStatus();
                    if (status){
                        loading.dismiss();
                        adapter_kosan adapter = new adapter_kosan(Menu_Utama.this, device);
                        recyclerView.setAdapter(adapter);
                    } else {
                        try {
                            loading.dismiss();
                            Toast.makeText(Menu_Utama.this, "Tidak Ada data Menu saat ini", Toast.LENGTH_SHORT).show();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<Response_Kosan> call, Throwable t) {
                try {
                    loading.dismiss();
//                    Toast.makeText(getActivity(), "Server Tidak Merespon", Toast.LENGTH_SHORT).show();
                    t.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });
    }

    private void Keluar() {
        sharedPrefManager.saveSPBoolean(SharedPrefManager.SP_SUDAH_LOGIN,false);
        Intent intent=new Intent(Menu_Utama.this,Menu_Login.class);
        startActivity(intent);
        finish();
    }
}