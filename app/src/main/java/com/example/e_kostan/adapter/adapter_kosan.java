package com.example.e_kostan.adapter;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.e_kostan.Menu.Menu_Detail;
import com.example.e_kostan.Menu.Menu_Rating;
import com.example.e_kostan.R;
import com.example.e_kostan.model.Item_Kosan;
import com.example.e_kostan.server.InitRetrofit;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.Locale;

public class adapter_kosan extends RecyclerView.Adapter<adapter_kosan.MyViewHolder> {
    double Latitude;
    double Longitude;
Context context;
List<Item_Kosan> Menu;
public adapter_kosan (Context context, List<Item_Kosan> Data_Menu){
    this.context=context;
    this.Menu=Data_Menu;

}

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    View view= LayoutInflater.from(context).inflate(R.layout.list_kosan,parent,false);
      MyViewHolder holder=new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
    try {
        holder.Nama_Kosan.setText(Menu.get(position).getNamaKostan());
        holder.Durasi_Kosan.setText(Menu.get(position).getDurasi());
        holder.Alamat_Kosan.setText(Menu.get(position).getAlamat());
        holder.Fasilitas_Kosan.setText(Menu.get(position).getFasilitas());
        holder.Harga_Kosan.setText(Menu.get(position).getHarga());
        holder.No_Hp.setText(Menu.get(position).getNomorHp());
        final String urlgambar = InitRetrofit.BASE_URL+ Menu.get(position).getGambar();
        Picasso.with(context).load(urlgambar).into(holder.Gambar_Kosan);
        String Rating = Menu.get(position).getTotal_rating();
        if (Rating.equals("5")){
            holder.Rating.setText("⭐⭐⭐⭐⭐");
        } else if (Rating.equals("4")){
            holder.Rating.setText("⭐⭐⭐⭐");
        } else if (Rating.equals("3")){
            holder.Rating.setText("⭐⭐⭐");
        }else if (Rating.equals("2")){
            holder.Rating.setText("⭐⭐");
        }else if (Rating.equals("1")){
            holder.Rating.setText("⭐");
        }else if (Rating.equals("0")){
            holder.Rating.setText("Belum Memiliki Rating");
        }else {
            holder.Rating.setText("⭐⭐⭐⭐⭐⭐⭐⭐⭐⭐");
        }
//        holder.Jarak.setText(Menu.get(position).getDistance());
//        if (Menu.get(position).getDistance().equals("null")){
            FusedLocationProviderClient mFusedLocation = LocationServices.getFusedLocationProviderClient(context);
            if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            mFusedLocation.getLastLocation().addOnSuccessListener((Activity) context, new OnSuccessListener<Location>() {
                @Override
                public void onSuccess(Location location) {
                    if (location != null) {
                        Log.d("My Current location", "Lat : " + location.getLatitude() + " Long : " + location.getLongitude());
                        Latitude=location.getLatitude();
                        Longitude=location.getLongitude();
                        Log.d("latlong", String.valueOf(Latitude+Longitude));
                        double Lat=Double.parseDouble(Menu.get(position).getLatitutude());
                        double Long=Double.parseDouble(Menu.get(position).getLongitude());
                        final int R = 3959; // Radious of the earth
//                    double lat1, double lon1, double lat2, double lon2;
                        Double latDistance = toRad(Lat-Latitude);
                        Double lonDistance = toRad(Long-Longitude);
                        Double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2) +
                                Math.cos(toRad(Lat)) * Math.cos(toRad(Latitude)) *
                                        Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
                        Double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
                        Double distance = R * c;
                        System.out.println("Jarak Antara latitude dan Logitude :" + distance);
                        int A, B;
                        String dString = Double.toString(distance);
                        String aString = dString.substring(0,3);
                        holder.Jarak.setText(aString+" Km");
                    }
                }
            });
//        } else {
//            String dString =Menu.get(position).getDistance();
//            Toast.makeText(context, dString, Toast.LENGTH_SHORT).show();
//            String aString = dString.substring(5,3);
//            holder.Jarak.setText(aString+" Km");
//        }
    } catch (Exception e) {
        e.printStackTrace();
    }


        holder.Detail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(context, "data", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(context, Menu_Detail.class);
                intent.putExtra("ID", Menu.get(position).getId());
                intent.putExtra("NamaKosan", Menu.get(position).getNamaKostan());
                intent.putExtra("DurasiKosan", Menu.get(position).getDurasi());
                intent.putExtra("Alamat", Menu.get(position).getAlamat());
                intent.putExtra("Fasilitas", Menu.get(position).getFasilitas());
                intent.putExtra("HargaKosan", Menu.get(position).getHarga());
                intent.putExtra("No_Hp", Menu.get(position).getNomorHp());
                intent.putExtra("Gambar", Menu.get(position).getGambar());
                intent.putExtra("Latitude", Menu.get(position).getLatitutude());
                intent.putExtra("Longitude", Menu.get(position).getLongitude());
                context.startActivity(intent);
            }
        });
        holder.LihatLokasi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String latitude=Menu.get(position).getLatitutude();
                String longitude=Menu.get(position).getLongitude();
//                String uri = String.format(Locale.ENGLISH, "geo", latitude, longitude);
//                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
//                context.startActivity(intent);
//                Intent intent = new Intent(context, Menu_Detail.class);
//                context.startActivity(intent);
                String uri = "http://maps.google.com/maps?saddr=" +latitude + "," + longitude + "&daddr=" + latitude + "," +longitude;
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
                context.startActivity(intent);
            }
        });
        holder.LihatRating.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, Menu_Rating.class);
                intent.putExtra("ID", Menu.get(position).getId());
                context.startActivity(intent);
            }
        });
    }

    private static Double toRad(Double value) {
        Log.d("ffff", String.valueOf(value));
        return value * Math.PI / 180;
    }

    @Override
    public int getItemCount() {
        return Menu.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
    ImageView Gambar_Kosan;
    TextView Nama_Kosan, Alamat_Kosan, Harga_Kosan, Fasilitas_Kosan,Durasi_Kosan,No_Hp,Jarak,Rating;
    Button LihatLokasi,Detail,LihatRating;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            Gambar_Kosan=(ImageView) itemView.findViewById(R.id.gambarkosan);
            Nama_Kosan=(TextView) itemView.findViewById(R.id.namakosan);
            Alamat_Kosan=(TextView) itemView.findViewById(R.id.alamatkosan);
            Harga_Kosan=(TextView)itemView.findViewById(R.id.hargakosan);
            Fasilitas_Kosan=(TextView) itemView.findViewById(R.id.fasilitaskosan);
            Durasi_Kosan=(TextView)itemView.findViewById(R.id.durasikosan);
            No_Hp=(TextView)itemView.findViewById(R.id.no_hp_kosan);
            Jarak=(TextView)itemView.findViewById(R.id.jarak);
            Rating=(TextView)itemView.findViewById(R.id.rating);
            LihatLokasi=(Button)itemView.findViewById(R.id.lihatlokasi);
            Detail=(Button)itemView.findViewById(R.id.detail);
            LihatRating=(Button)itemView.findViewById(R.id.lihatrating);

        }
    }
}
