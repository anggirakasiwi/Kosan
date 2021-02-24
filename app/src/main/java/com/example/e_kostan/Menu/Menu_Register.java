package com.example.e_kostan.Menu;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.e_kostan.R;
import com.example.e_kostan.server.InitRetrofit;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Menu_Register extends AppCompatActivity implements AdapterView.OnItemClickListener {
    EditText Email,Nama_Lengkap,No_Handphone,Alamat_Rumah,Masukan_Passowrd,Ulangi_Password;
    Spinner Jenis_Kelamin,Jenis_Akun;
    Button SignUp,Kembali;
    ProgressDialog loading;
String[] JenisKelamin = {"Laki-laki", "Perempuan"};
String[] JenisAkun = {"Pemilik", "Penyewa"};
String jeniskelamin;
    Context jenisakun;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu__register);
        loading=new ProgressDialog(Menu_Register.this);
        Email=(EditText) findViewById(R.id.email);
        Nama_Lengkap=(EditText)findViewById(R.id.namalengkap);
        No_Handphone=(EditText)findViewById(R.id.no_hp);
        Alamat_Rumah=(EditText)findViewById(R.id.alamatrumah);
        Masukan_Passowrd=(EditText)findViewById(R.id.passwordregis);
        Ulangi_Password=(EditText)findViewById(R.id.ulangipassword);
        Jenis_Kelamin=(Spinner)findViewById(R.id.jeniskelamin);
        Jenis_Akun=(Spinner)findViewById(R.id.jenisakun);
        SignUp=(Button)findViewById(R.id.Signup);
        Kembali=(Button)findViewById(R.id.FormLogin);
//        Jenis_Kelamin.setOnItemClickListener(Menu_Register.this);
//        Jenis_Akun.setOnItemClickListener(Menu_Register.this);
        ArrayAdapter jk = new ArrayAdapter(this, android.R.layout.simple_spinner_item,JenisKelamin);
        jk.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Jenis_Kelamin.setAdapter(jk);

        ArrayAdapter ja = new ArrayAdapter(this, android.R.layout.simple_spinner_item,JenisAkun);
        ja.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Jenis_Akun.setAdapter(ja);

        Kembali.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Ke_menu_login();
            }
        });

        SignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cek_inputan();
            }
        });
    }

    private void cek_inputan() {
        String inputan_email=Email.getText().toString();
        String inputan_nama_lengkap=Nama_Lengkap.getText().toString();
        String inputan_no_hp=No_Handphone.getText().toString();
        String inputan_alamat_rumah=Alamat_Rumah.getText().toString();
        String inputan_password=Masukan_Passowrd.getText().toString();
        String inputan_ulangi_password=Ulangi_Password.getText().toString();
        String inputan_jenis_kelamin=Jenis_Kelamin.getSelectedItem().toString();
        String inputan_jenis_akun=Jenis_Akun.getSelectedItem().toString();
        if (inputan_email.isEmpty()){
            Toast.makeText(this, "Email Tidak Boleh Kosong", Toast.LENGTH_SHORT).show();
        }else if(inputan_nama_lengkap.isEmpty()){
            Toast.makeText(this, "Nama Tidak Boleh Kosong", Toast.LENGTH_SHORT).show();
        }else if(inputan_no_hp.isEmpty()){
            Toast.makeText(this, "Nomor Handphone Tidak Boleh Kosong", Toast.LENGTH_SHORT).show();
        }else if(inputan_alamat_rumah.isEmpty()){
            Toast.makeText(this, "Alamat Rumah Tidak Boleh Kosong", Toast.LENGTH_SHORT).show();
        }else if(inputan_password.isEmpty()){
            Toast.makeText(this, "Password Tidak Boleh Kosong", Toast.LENGTH_SHORT).show();
        }else if(inputan_ulangi_password.isEmpty()){
            Toast.makeText(this, "Konfirmasi Passoword", Toast.LENGTH_SHORT).show();
        }else {
            RequestRegister(inputan_email,inputan_nama_lengkap,inputan_no_hp,inputan_alamat_rumah,inputan_password,inputan_ulangi_password,inputan_jenis_kelamin,inputan_jenis_akun);
        }
    }

    private void RequestRegister(String inputan_email, String inputan_nama_lengkap, String inputan_no_hp, String inputan_alamat_rumah, String inputan_password, String inputan_ulangi_password, String inputan_jenis_kelamin, String inputan_jenis_akun) {
    loading.setMessage("Mohon Tunggu");
    loading.setCancelable(false);
    loading.show();
        retrofit2.Call<ResponseBody> call = InitRetrofit.getInstance().getApi().userregister(inputan_email,inputan_nama_lengkap,inputan_no_hp,inputan_alamat_rumah,inputan_jenis_kelamin,inputan_jenis_akun,inputan_password,inputan_ulangi_password);
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
                            Toast.makeText(Menu_Register.this, ""+Berhasil_LOGIN, Toast.LENGTH_SHORT).show();
//                            Intent i=new Intent(Menu_Login.this,Menu_Utama.class);
//                            startActivity(i);
//                            finish();

                            Ke_menu_login();

//                            String nama = jsonRESULTS.getJSONObject("user").getString("nama");
//                            String alamat = "kalinda";
////                                    jsonRESULTS.getJSONObject("user").getString("alamat");
//                            String email = jsonRESULTS.getJSONObject("user").getString("email");
//                            String telpon = jsonRESULTS.getJSONObject("user").getString("no_hp");
                        } else {
                            try {
                                Log.d("response api", jsonRESULTS.toString());
                                String Gagal_LOGIN=jsonRESULTS.getString("message");
                                Toast.makeText(Menu_Register.this, "Register Gagal", Toast.LENGTH_SHORT).show();
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
                        Toast.makeText(Menu_Register.this, ""+error_message, Toast.LENGTH_SHORT).show();
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
                    Toast.makeText(Menu_Register.this, ""+error_message, Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });
    }


    private void Ke_menu_login() {
        Intent intent=new Intent(Menu_Register.this,Menu_Login.class);
        startActivity(intent);finish();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//        Toast.makeText(getApplicationContext(),JenisAkun[position],Toast.LENGTH_SHORT).show();
//        Toast.makeText(getApplicationContext(),JenisKelamin[position],Toast.LENGTH_SHORT).show();
//        jenisakun=JenisAkun[position];
    }

}