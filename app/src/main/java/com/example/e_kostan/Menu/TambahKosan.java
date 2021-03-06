package com.example.e_kostan.Menu;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.e_kostan.Menu_Pemilik.Dashboard_Pemilik;
import com.example.e_kostan.R;
import com.example.e_kostan.Session.SharedPrefManager;
import com.example.e_kostan.adapter.adapter_kosan;
import com.example.e_kostan.model.Item_Kosan;
import com.example.e_kostan.respon.Response_Kosan;
import com.example.e_kostan.server.ApiConfig;
import com.example.e_kostan.server.ApiServices;
import com.example.e_kostan.server.AppConfig;
import com.example.e_kostan.server.InitRetrofit;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import pub.devrel.easypermissions.EasyPermissions;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TambahKosan extends AppCompatActivity {
    EditText NamaKosan,Alamat,HargaSewa,Fasilitas,DurasiSewa,NamaGambar,Tlp;
    ImageView GambarKosan;
    Button PilihGambar,Simpan,Batal,Readd;
    String mediaPath;
    LinearLayout Atas,Bawah;

    String[] mediaColumns = {MediaStore.Video.Media._ID};
    ProgressDialog progressDialog;
    SharedPrefManager sharedPrefManager;
    private static final int REQUEST_PERMISSIONS_REQUEST_CODE = 34;
    private FusedLocationProviderClient mFusedLocationClient;
    protected Location mLastLocation;
    double Latitude;
    double Longitude;
    private Context context;
    ProgressDialog loading;
    RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah_kosan);
        progressDialog=new ProgressDialog(TambahKosan.this);
        sharedPrefManager=new SharedPrefManager(TambahKosan.this);
        loading=new ProgressDialog(TambahKosan.this);
        NamaKosan=(EditText) findViewById(R.id.namakosan);
        Alamat=(EditText) findViewById(R.id.alamat);
        HargaSewa=(EditText) findViewById(R.id.hargasewa);
        NamaGambar=(EditText) findViewById(R.id.namaGambar);
        Fasilitas=(EditText) findViewById(R.id.fasilitas);
        DurasiSewa=(EditText) findViewById(R.id.durasisewa);
        Tlp=(EditText)findViewById(R.id.telp);

        GambarKosan=(ImageView) findViewById(R.id.gambarkosan);
        PilihGambar=(Button)findViewById(R.id.Pilih_gambar);
        Simpan=(Button) findViewById(R.id.simpan);
        Readd=(Button)findViewById(R.id.btntambahkosan);
        Atas=(LinearLayout)findViewById(R.id.satu);
        Bawah=(LinearLayout)findViewById(R.id.Dua);
        Atas.setVisibility(View.VISIBLE);
        Bawah.setVisibility(View.GONE);
        Readd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Atas.setVisibility(View.VISIBLE);
                Bawah.setVisibility(View.GONE);
            }
        });
        Batal=(Button) findViewById(R.id.batal);
        Simpan.setOnClickListener(v -> {
            String[] galleryPermissions = {Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE};
            if (EasyPermissions.hasPermissions(TambahKosan.this, galleryPermissions)) {
                uploadFile();
            } else {
                EasyPermissions.requestPermissions(TambahKosan.this, "Access for storage",
                        101, galleryPermissions);
            }
        });
        PilihGambar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(galleryIntent,0);
            }
        });
        checkPermission();
        recyclerView = (RecyclerView) findViewById(R.id.List_Kosan);
        recyclerView.setHasFixedSize(true);
        GridLayoutManager gridLayout=new GridLayoutManager(TambahKosan.this,1);
        recyclerView.setLayoutManager(gridLayout);
        getlokasi();
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
        Call<ResponseBody> call = getResponse.uploadFile(fileToUpload, filename);
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
                            Atas.setVisibility(View.GONE);
                            Bawah.setVisibility(View.VISIBLE);

//                            Toast.makeText(Input_Datamenu.this, ""+pesan_login, Toast.LENGTH_SHORT).show();
//                            progressDialog.dismiss();
                            Log.d("response api", jsonRESULTS.toString());
                        } else if (jsonRESULTS.getString("success").equals("false")){
                            String pesan_login=jsonRESULTS.getString("message");
                            Toast.makeText(TambahKosan.this, ""+pesan_login, Toast.LENGTH_SHORT).show();
                            Log.v("ini",pesan_login);
                            progressDialog.dismiss();

                            Atas.setVisibility(View.VISIBLE);
                            Bawah.setVisibility(View.GONE);
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
                        Toast.makeText(TambahKosan.this, ""+pesan, Toast.LENGTH_SHORT).show();
                        Atas.setVisibility(View.VISIBLE);
                        Bawah.setVisibility(View.GONE);
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
                    Toast.makeText(TambahKosan.this, "", Toast.LENGTH_SHORT).show();
                    Log.v("response:","gagal");
                    InputGagal();
                    progressDialog.dismiss();
                    Atas.setVisibility(View.VISIBLE);
                    Bawah.setVisibility(View.GONE);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });

    }

    private void InputGagal() {
    }

    private void RequestMenu(String namaGambar) {
        String id=sharedPrefManager.getSPEmail();
        String Nama_kosan=NamaKosan.getText().toString();
        String alamat=Alamat.getText().toString();
        String hargasewa=HargaSewa.getText().toString();
//        String namagambar=NamaGambar.getText().toString();
        String fasilitas=Fasilitas.getText().toString();
        String durasisewa=DurasiSewa.getText().toString();
        String Telpon=Tlp.getText().toString();
        retrofit2.Call<ResponseBody> call = InitRetrofit.getInstance().getApi().TambahKosan(alamat,Nama_kosan,hargasewa,fasilitas,durasisewa,Latitude,Longitude,id,namaGambar,Telpon);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()){
//                    Toast.makeText(TambahKosan.this, ""+response.body().toString(), Toast.LENGTH_SHORT).show();
                    try {
                        JSONObject jsonRESULTS = new JSONObject(response.body().string());
                        Toast.makeText(TambahKosan.this, ""+jsonRESULTS.toString(), Toast.LENGTH_SHORT).show();
                        if (jsonRESULTS.getString("success").equals("true")){
                            Log.d("response api", jsonRESULTS.toString());
                            String Berhasil_LOGIN=jsonRESULTS.getString("message");
                            loading.dismiss();
                            Toast.makeText(TambahKosan.this, ""+Berhasil_LOGIN, Toast.LENGTH_SHORT).show();
//                            Intent i=new Intent(Menu_Login.this,Menu_Utama.class);
//                            startActivity(i);
//                            finish();
                            Tampil_Kosan();
                        } else {
                            try {
                                Log.d("response api", jsonRESULTS.toString());
                                String Gagal_LOGIN=jsonRESULTS.getString("message");
                                Toast.makeText(TambahKosan.this, "Tambah Kosan Gagal", Toast.LENGTH_SHORT).show();
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
                        Toast.makeText(TambahKosan.this, ""+error_message, Toast.LENGTH_SHORT).show();
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
                    Toast.makeText(TambahKosan.this, ""+error_message, Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });



    }

    private void Tampil_Kosan() {
        NamaGambar.setText("");
        NamaKosan.setText("");
        Alamat.setText("");
        HargaSewa.setText("");
        Fasilitas.setText("");
        DurasiSewa.setText("");
        loading.setCancelable(true);
        loading.setMessage("Mohon Tunggu");
        loading.show();
        ApiServices api = InitRetrofit.getInstance().getApi();
        Call<Response_Kosan> menuCall = api.TampilKosan();
        menuCall.enqueue(new Callback<Response_Kosan>() {
            @Override
            public void onResponse(Call<Response_Kosan> call, Response<Response_Kosan> response) {
                if (response.isSuccessful()){
                    Log.d("response api", response.body().toString());
                    List<Item_Kosan> device= response.body().getKosan();
                    boolean status = response.body().isStatus();
                    if (status){
                        loading.dismiss();
                        adapter_kosan adapter = new adapter_kosan(TambahKosan.this, device);
                        recyclerView.setAdapter(adapter);
                    } else {
                        try {
                            loading.dismiss();
                            Toast.makeText(TambahKosan.this, "Tidak Ada data Menu saat ini", Toast.LENGTH_SHORT).show();
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
                GambarKosan.setImageBitmap(BitmapFactory.decodeFile(mediaPath));
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

//                mediaPath1 = cursor.getString(columnIndex);
//                str2.setText(mediaPath1);
//                // Set the Video Thumb in ImageView Previewing the Media
//                imgView.setImageBitmap(getThumbnailPathForLocalFile(MainActivity.this, selectedVideo));
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
    @SuppressLint("MissingPermission")
    public void getlokasi(){
        FusedLocationProviderClient mFusedLocation = LocationServices.getFusedLocationProviderClient(TambahKosan.this);
        mFusedLocation.getLastLocation().addOnSuccessListener(TambahKosan.this, new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if (location != null){
                    // Do it all with location
                    Log.d("My Current location", "Lat : " + location.getLatitude() + " Long : " + location.getLongitude());
                    // Display in Toast
                    tampildata(location.getLatitude(),location.getLongitude());
                    Toast.makeText(TambahKosan.this,

                            "Lat : " + location.getLatitude() + " Long : " + location.getLongitude(),
                            Toast.LENGTH_LONG).show();
//
//                    Latitude=location.getLatitude();
//                    Longitude=location.getLongitude();
                }
            }
        });
    }

    private void tampildata(double latitude, double longitude) {
        Latitude=latitude;
        Longitude=longitude;
    }
    public void checkPermission(){
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(this,Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED
        ){//Can add more as per requirement

            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.ACCESS_COARSE_LOCATION},
                    123);
            getlokasi();
        }
    }
    @Override
    public void onBackPressed(){
        Back();
    }

    private void Back() {
        Intent intent=new Intent(TambahKosan.this, Dashboard_Pemilik.class);
        startActivity(intent);
        finish();
    }
}