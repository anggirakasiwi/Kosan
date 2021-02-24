package com.example.e_kostan.Menu;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.e_kostan.Menu_Pemilik.Dashboard_Pemilik;
import com.example.e_kostan.R;
import com.example.e_kostan.Session.SharedPrefManager;
import com.example.e_kostan.adapter.adapter_kosan;
import com.example.e_kostan.adapter.adapter_pesan;
import com.example.e_kostan.adapter.adapter_user;
import com.example.e_kostan.model.Item_Kosan;
import com.example.e_kostan.model.Item_User;
import com.example.e_kostan.model.Item_pesan;
import com.example.e_kostan.respon.Response_Kosan;
import com.example.e_kostan.respon.Response_Pesan;
import com.example.e_kostan.respon.Response_User;
import com.example.e_kostan.server.ApiServices;
import com.example.e_kostan.server.InitRetrofit;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Menu_Massage extends AppCompatActivity {
    RecyclerView recyclerView;
    SharedPrefManager sharedPrefManager;
    ProgressDialog loading;
FloatingActionButton AddPesan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu__massage);
        sharedPrefManager=new SharedPrefManager(Menu_Massage.this);
        loading=new ProgressDialog(Menu_Massage.this);
//        AddPesan=(FloatingActionButton) findViewById(R.id.tambahpesan);
        recyclerView = (RecyclerView) findViewById(R.id.listpesan);
        recyclerView.setHasFixedSize(true);
        GridLayoutManager gridLayout=new GridLayoutManager(Menu_Massage.this,1);
        recyclerView.setLayoutManager(gridLayout);
        TampilPesan();
    }

    private void TampilPesan() {
        String Email = sharedPrefManager.getSPEmail();
            loading.setCancelable(true);
            loading.setMessage("Mohon Tunggu");
            loading.show();
            ApiServices api = InitRetrofit.getInstance().getApi();
            Call<Response_User> menuCall = api.Tampil_User();
            menuCall.enqueue(new Callback<Response_User>() {
                @Override
                public void onResponse(Call<Response_User> call, Response<Response_User> response) {
                    if (response.isSuccessful()){
                        Log.d("response api", response.body().toString());
                        List<Item_User> device= response.body().getUser();
                        boolean status = response.body().isStatus();
                        if (status){
                            loading.dismiss();
                            adapter_user adapter = new adapter_user(Menu_Massage.this, device);
                            recyclerView.setAdapter(adapter);
                        } else {
                            try {
                                loading.dismiss();
                                Toast.makeText(Menu_Massage.this, "Tidak Ada data Pesan", Toast.LENGTH_SHORT).show();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }

                @Override
                public void onFailure(Call<Response_User> call, Throwable t) {
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

    @Override
    public void onBackPressed(){
        Back();
    }

    private void Back() {
//        Intent intent=new Intent(Menu_Massage.this,Menu_Utama.class);
//        startActivity(intent);
//        finish();
        String Jenis_Akun = sharedPrefManager.getSPRole();
        if (sharedPrefManager.getSPRole().equals("Pemilik")) {
//            startActivity(new Intent(Menu_Login.this, Dashboard_Pemilik.class)
//                    .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK));
//            finish();
//            Toast.makeText(this, "Penyewa", Toast.LENGTH_SHORT).show();
            Intent intent=new Intent(Menu_Massage.this, Dashboard_Pemilik.class);
            startActivity(intent);
            finish();
        } else if (sharedPrefManager.getSPRole().equals("Penyewa")){
            Intent intent=new Intent(Menu_Massage.this,Menu_Utama.class);
            startActivity(intent);
            finish();
//            Toast.makeText(this, "Penyewa", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Terjadi Kesalahan", Toast.LENGTH_SHORT).show();
        }
    }
}