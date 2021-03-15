package com.example.e_kostan.Menu;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.e_kostan.MainActivity;
import com.example.e_kostan.Menu_Pemilik.Dashboard_Pemilik;
import com.example.e_kostan.R;
import com.example.e_kostan.Session.SharedPrefManager;
import com.example.e_kostan.server.ApiConfig;
import com.example.e_kostan.server.AppConfig;
import com.example.e_kostan.server.InitRetrofit;
import com.google.android.gms.location.FusedLocationProviderClient;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import pub.devrel.easypermissions.EasyPermissions;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditProfile extends AppCompatActivity {
EditText Email,Password,Nama,NoTelp,NamaGambar;
ImageView PilihGambar;
Button PilihFoto,Simpan;
String mediaPath;
SharedPrefManager sharedPrefManager;
    String[] mediaColumns = {MediaStore.Video.Media._ID};
    ProgressDialog progressDialog;
    private static final int REQUEST_PERMISSIONS_REQUEST_CODE = 34;
    private FusedLocationProviderClient mFusedLocationClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        progressDialog= new ProgressDialog(EditProfile.this);
        sharedPrefManager=new SharedPrefManager(EditProfile.this);
        Email=(EditText) findViewById(R.id.editemail);
        Password=(EditText)findViewById(R.id.editpassword);
        Nama=(EditText)findViewById(R.id.editnama);
        NoTelp=(EditText)findViewById(R.id.editnohp);
        NamaGambar=(EditText)findViewById(R.id.editnamagambar);
        PilihGambar=(ImageView)findViewById(R.id.editgambar);
        PilihFoto=(Button)findViewById(R.id.editpilihfoto);
        Simpan=(Button)findViewById(R.id.btneditsimpan);
        Email.setText(sharedPrefManager.getSPEmail());
        Email.setFocusable(false);
        Nama.setText(sharedPrefManager.getSPNama());
        NoTelp.setText(sharedPrefManager.getSPTelepon());
//        Password.setText(sharedPrefManager.getSPPassword());


        Simpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String[] galleryPermissions = {Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE};
                if (EasyPermissions.hasPermissions(EditProfile.this, galleryPermissions)) {
                    uploadFile();
                } else {
                    EasyPermissions.requestPermissions(EditProfile.this, "Access for storage",
                            101, galleryPermissions);
                }
            }
        });
        PilihFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(galleryIntent,0);
            }
        });

    }

    private void uploadFile() {
        progressDialog.setMessage("Mohon Tunggu");
        progressDialog.show();
        File file = new File(String.valueOf(mediaPath));
        // Parsing any Media type file
        RequestBody requestBody = RequestBody.create(MediaType.parse("/"), file);
        MultipartBody.Part fileToUpload = MultipartBody.Part.createFormData("file", file.getName(), requestBody);
        RequestBody filename = RequestBody.create(MediaType.parse("text/plain"), file.getName());
        ApiConfig getResponse = AppConfig.getRetrofit().create(ApiConfig.class);
        Call<ResponseBody> call = getResponse.uploadFotoProfil(fileToUpload, filename);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
//                Toast.makeText(Input_Datamenu.this, ""+response, Toast.LENGTH_SHORT).show();
                ResponseBody responseBody=response.body();
                Log.v("data",responseBody.toString());
                if (response.isSuccessful()){
                    try {
                        JSONObject jsonRESULTS = new JSONObject(response.body().string());
//                        Toast.makeText(TambahKosan.this, ""+jsonRESULTS, Toast.LENGTH_SHORT).show();
                        if (jsonRESULTS.getString("success").equals("true")){
                            String pesan_login=jsonRESULTS.getString("message");
                            String NamaGambar=jsonRESULTS.getString("tmp_name");
                            RequestMenu(NamaGambar);
                            progressDialog.dismiss();

//                            Toast.makeText(Input_Datamenu.this, ""+pesan_login, Toast.LENGTH_SHORT).show();
//                            progressDialog.dismiss();
                            Log.d("response api", jsonRESULTS.toString());
                        } else if (jsonRESULTS.getString("success").equals("false")){
                            String pesan_login=jsonRESULTS.getString("message");
                            Toast.makeText(EditProfile.this, ""+pesan_login, Toast.LENGTH_SHORT).show();
                            Log.v("ini",pesan_login);
                            progressDialog.dismiss();
//                            InputGagal();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    try {
                        JSONObject jsonRESULTS = new JSONObject(response.body().string());
                        String pesan=jsonRESULTS.getString("message");
                        Toast.makeText(EditProfile.this, ""+pesan, Toast.LENGTH_SHORT).show();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

//                ResponseBody responseBody=response.body();
                try {
                    Toast.makeText(EditProfile.this, "", Toast.LENGTH_SHORT).show();
                    Log.v("response:","gagal");
                    progressDialog.dismiss();
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });
    }

    private void RequestMenu(String namaGambar) {
        if (Password.getText().toString().equals("")) {
            Toast.makeText(this, "Password Tidak Boleh Kosong", Toast.LENGTH_SHORT).show();
        } else {
            String id = sharedPrefManager.getSPEmail();
            String email = Email.getText().toString();
            String nama = Nama.getText().toString();
            String notelepon = NoTelp.getText().toString();
            String password = Password.getText().toString();
//        String namagambar=NamaGambar.getText().toString();
            retrofit2.Call<ResponseBody> call = InitRetrofit.getInstance().getApi().userupadate(email, nama, notelepon, password, namaGambar);
            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    if (response.isSuccessful()) {
//                    Toast.makeText(TambahKosan.this, ""+response.body().toString(), Toast.LENGTH_SHORT).show();
                        try {
                            JSONObject jsonRESULTS = new JSONObject(response.body().string());
                            Toast.makeText(EditProfile.this, "" + jsonRESULTS.toString(), Toast.LENGTH_SHORT).show();
                            if (jsonRESULTS.getString("success").equals("true")) {
                                Log.d("response api", jsonRESULTS.toString());
                                String Berhasil_LOGIN = jsonRESULTS.getString("message");
                                progressDialog.dismiss();
                                Toast.makeText(EditProfile.this, "" + Berhasil_LOGIN, Toast.LENGTH_SHORT).show();
//                            Intent i=new Intent(Menu_Login.this,Menu_Utama.class);
//                            startActivity(i);
//                            finish();
//                            Tampil_Kosan();
                                Keluar();
                            } else {
                                try {
                                    Log.d("response api", jsonRESULTS.toString());
                                    String Gagal_LOGIN = jsonRESULTS.getString("message");
                                    Toast.makeText(EditProfile.this, "Edit Profile Gagal", Toast.LENGTH_SHORT).show();
                                    progressDialog.dismiss();
                                    Log.v("pesan", Gagal_LOGIN);
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
                            progressDialog.dismiss();
                            String error_message = "Ada Masalah Internet";
                            Toast.makeText(EditProfile.this, "" + error_message, Toast.LENGTH_SHORT).show();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    Log.v("debug", "onFailure: ERROR > " + t.toString());
                    try {
                        progressDialog.dismiss();
                        String error_message = "Server Tidak Merespon";
                        Toast.makeText(EditProfile.this, "" + error_message, Toast.LENGTH_SHORT).show();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
            });
        }
    }

    private void Keluar() {
        sharedPrefManager.saveSPBoolean(SharedPrefManager.SP_SUDAH_LOGIN,false);
        Intent intent=new Intent(EditProfile.this,Menu_Login.class);
        startActivity(intent);
        finish();
    }


//    @Override
//    public void onBackPressed(){
//        Kembali();
//    }

    private void Kembali() {
        Intent intent=new Intent(EditProfile.this,Menu_Utama.class);
        startActivity(intent);
        finish();
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            // When an Image is picked
            if (requestCode == 0 && resultCode == RESULT_OK && null != data) {

                // Get the Image from data
                Uri selectedImage = data.getData();
                String[] filePathColumn = {MediaStore.Images.Media.DATA};

                Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);
                assert cursor != null;
                cursor.moveToFirst();

                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                mediaPath = cursor.getString(columnIndex);
                NamaGambar.setText(mediaPath);
                // Set the Image in ImageView for Previewing the Media
                PilihGambar.setImageBitmap(BitmapFactory.decodeFile(mediaPath));
                cursor.close();

            } // When an Video is picked
            else if (requestCode == 1 && resultCode == RESULT_OK && null != data) {

                // Get the Video from data
                Uri selectedVideo = data.getData();
                String[] filePathColumn = {MediaStore.Video.Media.DATA};

                Cursor cursor = getContentResolver().query(selectedVideo, filePathColumn, null, null, null);
                assert cursor != null;
                cursor.moveToFirst();

                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);

                mediaPath = cursor.getString(columnIndex);
                NamaGambar.setText(mediaPath);
//                // Set the Video Thumb in ImageView Previewing the Media
                PilihGambar.setImageBitmap(getThumbnailPathForLocalFile(EditProfile.this, selectedVideo));
                cursor.close();

            } else {
                Toast.makeText(this, "You haven't picked Image/Video", Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            Toast.makeText(this, "Something went wrong", Toast.LENGTH_LONG).show();
        }

    }
    // Providing Thumbnail For Selected Image
    public Bitmap getThumbnailPathForLocalFile(Activity context, Uri fileUri) {
        long fileId = getFileId(context, fileUri);
        return MediaStore.Video.Thumbnails.getThumbnail(context.getContentResolver(),
                fileId, MediaStore.Video.Thumbnails.MICRO_KIND, null);
    }
    // Getting Selected File ID
    public long getFileId(Activity context, Uri fileUri) {
        Cursor cursor = context.managedQuery(fileUri, mediaColumns, null, null, null);
        if (cursor.moveToFirst()) {
            int columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Video.Media._ID);
            return cursor.getInt(columnIndex);
        }
        return 0;
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
            Intent intent=new Intent(EditProfile.this, Dashboard_Pemilik.class);
            startActivity(intent);
            finish();
        } else if (sharedPrefManager.getSPRole().equals("Penyewa")){
            Intent intent=new Intent(EditProfile.this,Menu_Utama.class);
            startActivity(intent);
            finish();
//            Toast.makeText(this, "Penyewa", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Terjadi Kesalahan", Toast.LENGTH_SHORT).show();
        }
    }
}