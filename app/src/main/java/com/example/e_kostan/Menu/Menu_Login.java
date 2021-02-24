package com.example.e_kostan.Menu;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.e_kostan.Menu_Pemilik.Dashboard_Pemilik;
import com.example.e_kostan.R;
import com.example.e_kostan.Session.SharedPrefManager;
import com.example.e_kostan.server.InitRetrofit;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Menu_Login extends AppCompatActivity {
EditText Email, Password;
Button Login;
TextView SignUp;
ProgressDialog loading;
SharedPrefManager sharedPrefManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu__login);
        loading=new ProgressDialog(Menu_Login.this);
        sharedPrefManager=new SharedPrefManager(Menu_Login.this);
        check_session();
        Email=(EditText) findViewById(R.id.email);
        Password=(EditText) findViewById(R.id.password);
        Login=(Button) findViewById(R.id.signin);
        SignUp=(TextView) findViewById(R.id.Signup);
        SignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            register();
            }
        });

        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                check_form();

            }
        });

    }

    private void check_session() {
        if (sharedPrefManager.getSudahLogin()){
            chex_Role();
        }
    }

    private void chex_Role() {
        String Jenis_Akun = sharedPrefManager.getSPRole();
        if (sharedPrefManager.getSPRole().equals("Pemilik")) {
            startActivity(new Intent(Menu_Login.this, Dashboard_Pemilik.class)
                    .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK));
            finish();
//            Toast.makeText(this, "Penyewa", Toast.LENGTH_SHORT).show();
        } else if (sharedPrefManager.getSPRole().equals("Penyewa")){
            startActivity(new Intent(Menu_Login.this, Menu_Utama.class)
                    .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK));
            finish();
//            Toast.makeText(this, "Penyewa", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Terjadi Kesalahan", Toast.LENGTH_SHORT).show();
        }
    }

    private void register (){
        Intent intent=new Intent(Menu_Login.this,Menu_Register.class);
        startActivity(intent);
        finish();
    }
    private void check_form (){
        String inputan_email=Email.getText().toString();
        String inputan_password=Password.getText().toString();
        if (inputan_email.isEmpty()){
            Toast.makeText(this, "Email tidak boleh kosong", Toast.LENGTH_SHORT).show();
        } else if (inputan_password.isEmpty()){
            Toast.makeText(this, "Password tidak boleh kosong", Toast.LENGTH_SHORT).show();
        } else {
            RequestLogin(inputan_email,inputan_password);
        }
    }

    private void RequestLogin(String inputan_email, String inputan_password) {
        loading.setMessage("Mohon tunggu sebentar");
        loading.setCancelable(false);
        loading.show();
        retrofit2.Call<ResponseBody> call = InitRetrofit.getInstance().getApi().userLogin(inputan_email,inputan_password);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()){
                    try {
                        JSONObject jsonRESULTS = new JSONObject(response.body().string());
                        if (jsonRESULTS.getString("success").equals("true")){
                            Log.d("response api", jsonRESULTS.toString());
                            String Berhasil_LOGIN=jsonRESULTS.getString("message");
                            loading.dismiss();
                            String Email=jsonRESULTS.getString("email");
                            sharedPrefManager.saveSPString(SharedPrefManager.EMAIL,Email);
                            String Password=jsonRESULTS.getString("password");
                            sharedPrefManager.saveSPString(SharedPrefManager.PASSWORD,Password);
                            String Nama=jsonRESULTS.getString("nama");
                            sharedPrefManager.saveSPString(SharedPrefManager.NAMA_LENGKAP,Nama);
                            String Nomor_Handphone=jsonRESULTS.getString("no_hp");
                            sharedPrefManager.saveSPString(SharedPrefManager.TELPON,Nomor_Handphone);
                            String Alamat=jsonRESULTS.getString("alamat");
                            sharedPrefManager.saveSPString(SharedPrefManager.ALAMAT,Alamat);
                            String Jenis_Kelamin=jsonRESULTS.getString("jenis_kelamin");
                            sharedPrefManager.saveSPString(SharedPrefManager.JENIS_KELAMIN,Jenis_Kelamin);
                            String Role=jsonRESULTS.getString("role");
                            sharedPrefManager.saveSPString(SharedPrefManager.ROLE,Role);
                            String Gambar=jsonRESULTS.getString("gambar");
                            sharedPrefManager.saveSPString(SharedPrefManager.SP_GAMBAR,Gambar);
                            sharedPrefManager.saveSPBoolean(SharedPrefManager.SP_SUDAH_LOGIN,true);
                            Toast.makeText(Menu_Login.this, ""+Berhasil_LOGIN, Toast.LENGTH_SHORT).show();
//                            Intent i=new Intent(Menu_Login.this,Menu_Utama.class);
//                            startActivity(i);
//                            finish();
                            if (sharedPrefManager.getSPRole().equals("Pemilik")) {
                                startActivity(new Intent(Menu_Login.this, Dashboard_Pemilik.class)
                                        .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK));
                                finish();
//            Toast.makeText(this, "Penyewa", Toast.LENGTH_SHORT).show();
                            } else if (sharedPrefManager.getSPRole().equals("Penyewa")){
                                startActivity(new Intent(Menu_Login.this, Menu_Utama.class)
                                        .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK));
                                finish();
//            Toast.makeText(this, "Penyewa", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(Menu_Login.this, "Terjadi Kesalahan", Toast.LENGTH_SHORT).show();
                            }

//                            String nama = jsonRESULTS.getJSONObject("user").getString("nama");
//                            String alamat = "kalinda";
////                                    jsonRESULTS.getJSONObject("user").getString("alamat");
//                            String email = jsonRESULTS.getJSONObject("user").getString("email");
//                            String telpon = jsonRESULTS.getJSONObject("user").getString("no_hp");
                        } else {
                            try {
                                Log.d("response api", jsonRESULTS.toString());
                                String Gagal_LOGIN=jsonRESULTS.getString("message");
                                Toast.makeText(Menu_Login.this, "Login Gagal", Toast.LENGTH_SHORT).show();
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
                        Toast.makeText(Menu_Login.this, ""+error_message, Toast.LENGTH_SHORT).show();
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
                    Toast.makeText(Menu_Login.this, ""+error_message, Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });

    }

}