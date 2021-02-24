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
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.e_kostan.R;
import com.example.e_kostan.Session.SharedPrefManager;
import com.example.e_kostan.adapter.adapter_pesan;
import com.example.e_kostan.model.Item_pesan;
import com.example.e_kostan.respon.Response_Pesan;
import com.example.e_kostan.server.ApiServices;
import com.example.e_kostan.server.InitRetrofit;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Menu_Detail_Massage extends AppCompatActivity {
    TextView NamaPengirim;
    EditText IsiPesan;
    Button KirimPesan;
    RecyclerView recyclerView,recyclerView1;
    ProgressDialog loading;
    String penerima,pengirim;
    SharedPrefManager sharedPrefManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu__detail__massage);
        loading=new ProgressDialog(this);
        sharedPrefManager=new SharedPrefManager(this);
        NamaPengirim=(TextView)findViewById(R.id.namapengirim);
        IsiPesan=(EditText)findViewById(R.id.isipesan);
        KirimPesan=(Button)findViewById(R.id.kirimpesan);
        recyclerView=(RecyclerView)findViewById(R.id.listdetailpesan);
        recyclerView1=(RecyclerView)findViewById(R.id.listdetailpesan1);
        Intent intent = getIntent();
       String id = intent.getStringExtra("ID");
        String isipesan = intent.getStringExtra("ISIPESAN");
        pengirim = intent.getStringExtra("PENGIRIM");
        penerima = sharedPrefManager.getSPEmail();
        String namapengirim = intent.getStringExtra("NAMA_PENGIRIM");
        NamaPengirim.setText(namapengirim);
//        Toast.makeText(this, penerima+pengirim, Toast.LENGTH_SHORT).show();
        GridLayoutManager gridLayout=new GridLayoutManager(Menu_Detail_Massage.this,1);
        Log.d("isi",penerima+pengirim);
        recyclerView.setLayoutManager(gridLayout);
        GridLayoutManager gridLayout1=new GridLayoutManager(Menu_Detail_Massage.this,1);
        recyclerView1.setLayoutManager(gridLayout1);
        loading.setMessage("Loading.....");
        loading.setCancelable(true);
        loading.show();
        TampilPesan(penerima,pengirim);
        TampilPesan1(pengirim,penerima);
        KirimPesan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String isi=IsiPesan.getText().toString();
                String Pengirim=sharedPrefManager.getSPEmail();
                String Namapengirim=sharedPrefManager.getSPNama();
                Kirim(isi,Pengirim,pengirim,Namapengirim);
            }
        });
    }

    private void TampilPesan1(String pengirim, String penerima) {
        loading.setCancelable(true);
        loading.setMessage("Mohon Tunggu");
        loading.show();
        ApiServices api = InitRetrofit.getInstance().getApi();
        Call<Response_Pesan> menuCall = api.TampilPesanDetail(penerima,pengirim);
        menuCall.enqueue(new Callback<Response_Pesan>() {
            @Override
            public void onResponse(Call<Response_Pesan> call, Response<Response_Pesan> response) {
                if (response.isSuccessful()){
                    Log.d("response api", response.body().toString());
                    List<Item_pesan> device= response.body().getPesan();
                    boolean status = response.body().isStatus();
                    if (status){
                        loading.dismiss();
                        adapter_pesan adapter = new adapter_pesan(Menu_Detail_Massage.this, device);
                        recyclerView1.setAdapter(adapter);
                    } else {
                        try {
                            loading.dismiss();
//                            Toast.makeText(Menu_Detail_Massage.this, "Tidak Ada data Menu saat ini", Toast.LENGTH_SHORT).show();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<Response_Pesan> call, Throwable t) {
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

    private void Kirim(String isi, String Pengirim, String Penerima, String namapengirim) {
        loading.setMessage("Mohon Tunggu");
        loading.setCancelable(false);
        loading.show();
        retrofit2.Call<ResponseBody> call = InitRetrofit.getInstance().getApi().Kirim(isi,namapengirim,Penerima,Pengirim);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()){
//                    Toast.makeText(Menu_Register.this, ""+response.body().toString(), Toast.LENGTH_SHORT).show();
                    try {
                        JSONObject jsonRESULTS = new JSONObject(response.body().string());
                        if (jsonRESULTS.getString("success").equals("true")){
                            Log.d("response api", jsonRESULTS.toString());
                            String Berhasil_LOGIN=jsonRESULTS.getString("message");
                            loading.dismiss();
                            Toast.makeText(Menu_Detail_Massage.this, ""+Berhasil_LOGIN, Toast.LENGTH_SHORT).show();
                            TampilPesan(penerima,pengirim);
                            TampilPesan1(pengirim,penerima);
                            IsiPesan.setText("");
                            Log.d("pesan",penerima+pengirim);
                        } else {
                            try {
                                Log.d("response api", jsonRESULTS.toString());
                                String Gagal_LOGIN=jsonRESULTS.getString("message");
                                Toast.makeText(Menu_Detail_Massage.this, "Pesan gagal di kirim", Toast.LENGTH_SHORT).show();
                                loading.dismiss();
                                Log.v("pesan",Gagal_LOGIN);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    try {
                        loading.dismiss();
                        String error_message ="Ada Masalah Internet";
                        Toast.makeText(Menu_Detail_Massage.this, ""+error_message, Toast.LENGTH_SHORT).show();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.v("debug", "onFailure: ERROR > " + t.toString());
                try {
                    loading.dismiss();
                    String error_message ="Server Tidak Merespon";
                    Toast.makeText(Menu_Detail_Massage.this, ""+error_message, Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });

    }

    private void TampilPesan(String penerima,String pengirim) {
        loading.setCancelable(true);
        loading.setMessage("Mohon Tunggu");
        loading.show();
        ApiServices api = InitRetrofit.getInstance().getApi();
        Call<Response_Pesan> menuCall = api.TampilPesanDetail(pengirim,penerima);
        menuCall.enqueue(new Callback<Response_Pesan>() {
            @Override
            public void onResponse(Call<Response_Pesan> call, Response<Response_Pesan> response) {
                if (response.isSuccessful()){
                    Log.d("response api", response.body().toString());
                    List<Item_pesan> device= response.body().getPesan();
                    boolean status = response.body().isStatus();
                    if (status){
                        loading.dismiss();
                        adapter_pesan adapter = new adapter_pesan(Menu_Detail_Massage.this, device);
                        recyclerView.setAdapter(adapter);
                    } else {
                        try {
                            loading.dismiss();
//                            Toast.makeText(Menu_Detail_Massage.this, "Tidak Ada data Menu saat ini", Toast.LENGTH_SHORT).show();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<Response_Pesan> call, Throwable t) {
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
}