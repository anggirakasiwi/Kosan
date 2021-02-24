package com.example.e_kostan.Menu;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.e_kostan.R;
import com.example.e_kostan.Session.SharedPrefManager;
import com.example.e_kostan.adapter.adapter_pesan;
import com.example.e_kostan.adapter.adapter_rating;
import com.example.e_kostan.model.Item_pesan;
import com.example.e_kostan.model.item_rating;
import com.example.e_kostan.respon.Response_Pesan;
import com.example.e_kostan.respon.Response_Rating;
import com.example.e_kostan.server.ApiServices;
import com.example.e_kostan.server.InitRetrofit;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.mapbox.geojson.Feature;
import com.mapbox.geojson.FeatureCollection;
import com.mapbox.geojson.Point;
import com.mapbox.mapboxsdk.Mapbox;
import com.mapbox.mapboxsdk.maps.MapView;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback;
import com.mapbox.mapboxsdk.maps.Style;
import com.mapbox.mapboxsdk.style.layers.PropertyValue;
import com.mapbox.mapboxsdk.style.layers.SymbolLayer;
import com.mapbox.mapboxsdk.style.sources.GeoJsonSource;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.iconAllowOverlap;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.iconIgnorePlacement;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.iconImage;

public class Menu_Detail extends AppCompatActivity implements OnMapReadyCallback {
    ImageView GambarKosan;
    TextView NamaKosan, Alamat, Fasilitas, Harga, DurasiSewa, No_Telp,Komentar;
    Button BeriRating;
    RecyclerView recyclerViewrating;
    private Activity root;
    private static final String SOURCE_ID = "SOURCE_ID";
    private static final String ICON_ID = "ICON_ID";
    private static final String LAYER_ID = "LAYER_ID";
    private MapView mapView;
    private RecyclerView recyclerView;
    Button Get;
    RatingBar Rating;
    ProgressDialog progressDialog;

    //    private List<LocationModel> mListMarker = new ArrayList<>();
    private static final String TAG = Menu_Detail.class.getSimpleName();
    private static final int REQUEST_PERMISSIONS_REQUEST_CODE = 34;
    private FusedLocationProviderClient mFusedLocationClient;
    protected Location mLastLocation;
    double Latitude;
    double Longitude;
    double latitude;
    double longitude;
    private Context context;
    String id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Mapbox.getInstance(Menu_Detail.this, getString(R.string.mapbox_access_token));
        setContentView(R.layout.activity_menu__detail);
        GambarKosan = (ImageView) findViewById(R.id.gambardetailkosan);
        NamaKosan = (TextView) findViewById(R.id.detailnamakosan);
        Alamat = (TextView) findViewById(R.id.detailalamat);
        Fasilitas = (TextView) findViewById(R.id.detailfasilitas);
        Harga = (TextView) findViewById(R.id.detailharga);
        DurasiSewa = (TextView) findViewById(R.id.detaildurasisewa);
        No_Telp = (TextView) findViewById(R.id.detailnohp);
        Komentar = (TextView) findViewById(R.id.komentar);
        BeriRating = (Button) findViewById(R.id.berirating);
        getlokasi();
        Intent intent = getIntent();
        id = intent.getStringExtra("ID");
        String gambar = intent.getStringExtra("Gambar");
        String namakosan = intent.getStringExtra("NamaKosan");
        String alamat = intent.getStringExtra("Alamat");
        String fasilitas = intent.getStringExtra("Fasilitas");
        String harga = intent.getStringExtra("HargaKosan");
        String durasisewa = intent.getStringExtra("DurasiKosan");
        String notlp = intent.getStringExtra("No_Hp");
        latitude = Double.parseDouble(intent.getStringExtra("Latitude"));
        longitude = Double.parseDouble(intent.getStringExtra("Longitude"));
        NamaKosan.setText(namakosan);
        Alamat.setText(alamat);
        Fasilitas.setText(fasilitas);
        Harga.setText(harga);
        DurasiSewa.setText(durasisewa);
        No_Telp.setText(notlp);
        final String urlgambar = InitRetrofit.BASE_URL + gambar;
        Picasso.with(Menu_Detail.this).load(urlgambar).into(GambarKosan);
        mapView = (MapView) findViewById(R.id.mapView);
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(this);
        progressDialog=new ProgressDialog(Menu_Detail.this);

//        Judul = (TextView) findViewById(R.id.judul);
        Get = (Button) findViewById(R.id.berirating);
        Rating = (RatingBar) findViewById(R.id.rating_bar);

        Get.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                int noofstars = Rating.getNumStars();
//                float getrating = Rating.getRating();
//                Judul.setText("Rating: "+getrating);
                progressDialog.setMessage("Mohon Tunggu");
                progressDialog.setCancelable(true);
                progressDialog.show();
                Toast.makeText(Menu_Detail.this, id, Toast.LENGTH_SHORT).show();
                simpan_rating(id);
            }
        });
        recyclerViewrating = (RecyclerView) findViewById(R.id.listrating);
        recyclerViewrating.setHasFixedSize(true);
        GridLayoutManager gridLayout=new GridLayoutManager(Menu_Detail.this,1);
        recyclerViewrating.setLayoutManager(gridLayout);
        TampilRating(id);

    }

    private void TampilRating(String id) {
//        Toast.makeText(Menu_Detail.this, id, Toast.LENGTH_SHORT).show();
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
                        adapter_rating adapter = new adapter_rating(Menu_Detail.this,rating);
                        recyclerViewrating.setAdapter(adapter);
                    } else {
                        try {
                            progressDialog.dismiss();
                            Toast.makeText(Menu_Detail.this, "Tidak Ada data Menu saat ini", Toast.LENGTH_SHORT).show();
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

    private void simpan_rating(String id_kosan) {
        int noofstars = Rating.getNumStars();
        String komentar = Komentar.getText().toString();
        float getrating = Rating.getRating();
//                Judul.setText("Rating: "+getrating);
        SharedPrefManager shareprefManager=new SharedPrefManager(Menu_Detail.this);
        String id_user=shareprefManager.getSPEmail();
//        String id_coffeeshop=getIntent().getStringExtra("ID");
//        Log.v("data",id_coffeeshop+id_user);
//        String nilai = Rating.getText().toString();
        retrofit2.Call<ResponseBody> call = InitRetrofit.getInstance().getApi().AddRating(id_kosan,id_user,getrating,komentar);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()){
                    try {
                        JSONObject jsonRESULTS = new JSONObject(response.body().string());
                        if (jsonRESULTS.getString("success").equals("true")){
//                            progressDialog.dismiss();
                            String pesan_login=jsonRESULTS.getString("message");
                            Toast.makeText(Menu_Detail.this, "Berhasil memberikan rating"+pesan_login, Toast.LENGTH_SHORT).show();
//                            Intent intent =new Intent(DetailRating.this, BerandaFragment.class);
//                            startActivity(intent);
                            TampilRating(id);
                            progressDialog.dismiss();
//                            finish();
                            Log.d("response api", jsonRESULTS.toString());
                        } else if (jsonRESULTS.getString("success").equals("false")){
                            progressDialog.dismiss();
                            String pesan_login=jsonRESULTS.getString("message");
                            Toast.makeText(Menu_Detail.this, "Gagal"+pesan_login, Toast.LENGTH_SHORT).show();
                            Log.v("ini",pesan_login);
//                            InputGagal();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                }
            }
            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                try {
                    progressDialog.dismiss();
//                    InputGagal();
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });
    }

    @Override
    public void onMapReady(@NonNull final MapboxMap mapboxMap) {

        List<Feature> symbolLayerIconFeatureList = new ArrayList<>();
        symbolLayerIconFeatureList.add(Feature.fromGeometry(
                Point.fromLngLat(Longitude,Latitude)));
        symbolLayerIconFeatureList.add(Feature.fromGeometry(
                Point.fromLngLat(longitude, latitude)));
//        symbolLayerIconFeatureList.add(Feature.fromGeometry(
//                Point.fromLngLat(-56.990533, -30.583266)));

        mapboxMap.setStyle(new Style.Builder().fromUri("mapbox://styles/mapbox/cjf4m44iw0uza2spb3q0a7s41")

// Add the SymbolLayer icon image to the map style
                .withImage(ICON_ID, BitmapFactory.decodeResource(
                       Menu_Detail.this.getResources(), R.drawable.mapbox_marker_icon_default))

// Adding a GeoJson source for the SymbolLayer icons.
                .withSource(new GeoJsonSource(SOURCE_ID,
                        FeatureCollection.fromFeatures(symbolLayerIconFeatureList)))

// Adding the actual SymbolLayer to the map style. An offset is added that the bottom of the red
// marker icon gets fixed to the coordinate, rather than the middle of the icon being fixed to
// the coordinate point. This is offset is not always needed and is dependent on the image
// that you use for the SymbolLayer icon.
                .withLayer(new SymbolLayer(LAYER_ID, SOURCE_ID)
                        .withProperties(
                                iconImage(ICON_ID),
                                iconAllowOverlap(true),
                                iconIgnorePlacement(true)
                        )
                ), new Style.OnStyleLoaded() {
            @Override
            public void onStyleLoaded(@NonNull Style style) {

// Map is set up and the style has loaded. Now you can add additional data or make other map adjustments.


            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    protected void onStart() {
        super.onStart();
        mapView.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mapView.onStop();
    }

    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

    public void getlokasi() {
        FusedLocationProviderClient mFusedLocation = LocationServices.getFusedLocationProviderClient(Menu_Detail.this);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        mFusedLocation.getLastLocation().addOnSuccessListener(Menu_Detail.this, new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if (location != null) {
                    // Do it all with location
                    Log.d("My Current location", "Lat : " + location.getLatitude() + " Long : " + location.getLongitude());
                    // Display in Toast
//                    tampildata(location.getLatitude(), location.getLongitude());
//                    Toast.makeText(getContext(),
//                            "Lat : " + location.getLatitude() + " Long : " + location.getLongitude(),
//                            Toast.LENGTH_LONG).show();
//
                    Latitude=location.getLatitude();
                    Longitude=location.getLongitude();
                }
            }
        });
    }

}