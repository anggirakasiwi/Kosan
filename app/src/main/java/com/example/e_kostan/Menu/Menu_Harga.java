package com.example.e_kostan.Menu;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.e_kostan.R;
import com.example.e_kostan.adapter.Adapter_Terdekat;
import com.example.e_kostan.adapter.adapter_kosan;
import com.example.e_kostan.model.Item_Kosan;
import com.example.e_kostan.respon.Response_Kosan;
import com.example.e_kostan.server.ApiServices;
import com.example.e_kostan.server.InitRetrofit;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Menu_Harga extends AppCompatActivity {
    RecyclerView recyclerView;
    ProgressDialog loading;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu__harga);
        recyclerView = (RecyclerView) findViewById(R.id.listhargakosan);
        loading=new ProgressDialog(Menu_Harga.this);
        recyclerView.setHasFixedSize(true);
        GridLayoutManager gridLayout=new GridLayoutManager(Menu_Harga.this,1);
        recyclerView.setLayoutManager(gridLayout);
        FusedLocationProviderClient mFusedLocation = LocationServices.getFusedLocationProviderClient(this);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        mFusedLocation.getLastLocation().addOnSuccessListener((Activity) this, new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if (location != null) {
                    Tampil_Kosan(location.getLatitude(),location.getLongitude());
                    Log.d("My Current location", "Lat : " + location.getLatitude() + " Long : " + location.getLongitude());
                }
            }
        });

//        Tampil_Kosan();
    }

    private void Tampil_Kosan(double latitude, double longitude) {
        loading.setCancelable(true);
        loading.setMessage("Mohon Tunggu");
        loading.show();
        ApiServices api = InitRetrofit.getInstance().getApi();
        Call<Response_Kosan> menuCall = api.Tampil_Kosan_Murah(latitude,longitude);
        menuCall.enqueue(new Callback<Response_Kosan>() {
            @Override
            public void onResponse(Call<Response_Kosan> call, Response<Response_Kosan> response) {
                if (response.isSuccessful()){
                    Log.d("response api", response.body().toString());
                    List<Item_Kosan> device= response.body().getKosan();
                    boolean status = response.body().isStatus();
                    if (status){
                        loading.dismiss();
                        Adapter_Terdekat adapter = new Adapter_Terdekat(Menu_Harga.this, device);
                        recyclerView.setAdapter(adapter);
                    } else {
                        try {
                            loading.dismiss();
                            Toast.makeText(Menu_Harga.this, "Tidak Ada data Menu saat ini", Toast.LENGTH_SHORT).show();
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
    @Override
    public void onBackPressed(){
        Back();
    }

    private void Back() {
        Intent intent=new Intent(Menu_Harga.this,Menu_Utama.class);
        startActivity(intent);
        finish();
    }
}