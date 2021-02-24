package com.example.e_kostan.Menu;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.e_kostan.R;
import com.example.e_kostan.adapter.adapter_rating;
import com.example.e_kostan.model.item_rating;
import com.example.e_kostan.respon.Response_Rating;
import com.example.e_kostan.server.ApiServices;
import com.example.e_kostan.server.InitRetrofit;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Menu_Rating extends AppCompatActivity {
    String Id;
    RecyclerView recyclerViewrating;
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu__rating);
        Intent intent = getIntent();
        Id = intent.getStringExtra("ID");
        progressDialog=new ProgressDialog(this);
        recyclerViewrating = (RecyclerView) findViewById(R.id.List_Rating);
        recyclerViewrating.setHasFixedSize(true);
        GridLayoutManager gridLayout=new GridLayoutManager(Menu_Rating.this,1);
        recyclerViewrating.setLayoutManager(gridLayout);
        Tampil_rating(Id);
    }

    private void Tampil_rating(String id) {
        progressDialog.setCancelable(true);
        progressDialog.setMessage("Mohon Tunggu");
        progressDialog.show();
        ApiServices api = InitRetrofit.getInstance().getApi();
        Call<Response_Rating> menuCall = api.GetRating(id);
        menuCall.enqueue(new Callback<Response_Rating>() {
            @Override
            public void onResponse(Call<Response_Rating> call, Response<Response_Rating> response) {
                if (response.isSuccessful()){
                    Log.d("response api", response.body().toString());
                    List<item_rating> rating= response.body().getRating();
                    boolean status = response.body().isStatus();
                    if (status){
                        progressDialog.dismiss();
                        adapter_rating adapter = new adapter_rating(Menu_Rating.this,rating);
                        recyclerViewrating.setAdapter(adapter);
                    } else {
                        try {
                            progressDialog.dismiss();
                            Toast.makeText(Menu_Rating.this, "Belum Memliki Rating", Toast.LENGTH_SHORT).show();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<Response_Rating> call, Throwable t) {
                try {
                    progressDialog.dismiss();
//                    Toast.makeText(getActivity(), "Server Tidak Merespon", Toast.LENGTH_SHORT).show();
                    t.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });
    }
}