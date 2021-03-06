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

import org.json.JSONObject;

import java.util.List;

public class Adapter_Terdekat extends RecyclerView.Adapter<Adapter_Terdekat.MyViewHolder> {
    double Latitude;
    double Longitude;
    Context context;
    List<Item_Kosan> Menu;
    public Adapter_Terdekat (Context context, List<Item_Kosan> Data_Menu){
        this.context=context;
        this.Menu=Data_Menu;

    }

    @NonNull
    @Override
    public Adapter_Terdekat.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.list_kosan,parent,false);
        Adapter_Terdekat.MyViewHolder holder=new Adapter_Terdekat.MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull Adapter_Terdekat.MyViewHolder holder, int position) {
        try {
            holder.Nama_Kosan.setText(Menu.get(position).getNamaKostan());
            holder.Durasi_Kosan.setText(Menu.get(position).getDurasi());
            holder.Alamat_Kosan.setText(Menu.get(position).getAlamat());
            holder.Fasilitas_Kosan.setText(Menu.get(position).getFasilitas());
            holder.Harga_Kosan.setText(Menu.get(position).getHarga());
            holder.No_Hp.setText(Menu.get(position).getNomorHp());
            final String urlgambar = InitRetrofit.BASE_URL+ Menu.get(position).getGambar();
            Picasso.with(context).load(urlgambar).into(holder.Gambar_Kosan);
//            holder.Jarak.setText(Menu.get(position).getDistance());
            String data=Menu.get(position).getDistance();
            Log.d("anunya",data);
//            int array=
            char[] ch = new char[data.length()];
            for (int j = 0; j < data.length(); j++) {
                ch[j] = data.charAt(j);
//                Log.d("crotnya",ch[0])
//                String datafinal=data.charAt(3)+data.charAt(4);
                Log.v("Anunyalagi", String.valueOf(data.charAt(j)));
                Log.v("Anunyalagibudi", String.valueOf(data.charAt(3)));
                String datafinal= String.valueOf(data.charAt(3));
                String datafinal1=String.valueOf(data.charAt(4));
                String datafinal2=String.valueOf(data.charAt(5));
                holder.Jarak.setText(datafinal+datafinal1+datafinal2+"Km");
            }
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

            String[] stringArray1 = new String[] {Menu.get(position).getDistance()};
            String[] strArray3 = {Menu.get(position).getDistance()};
            for (int i = 0; i < strArray3.length; i++) {
                System.out.print(strArray3[i]);
            }
//        }
        } catch (Exception e) {
            e.printStackTrace();
        }
//        Double lati = Double.valueOf(Menu.get(position).getLatitutude());
//        Double longi = Double.valueOf(Menu.get(position).getLongitude());
//        FusedLocationProviderClient mFusedLocation = LocationServices.getFusedLocationProviderClient(context);
//        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//            return;
//        }
//        mFusedLocation.getLastLocation().addOnSuccessListener((Activity) context, new OnSuccessListener<Location>() {
//            @Override
//            public void onSuccess(Location location) {
//                if (location != null) {
//                    Log.d("My Current location", "Lat : " + location.getLatitude() + " Long : " + location.getLongitude());
//                    Location lokCek = new Location("Titik Lokasi");
//                    lokCek.setLatitude(lati);
//                    lokCek.setLongitude(longi);
//
////Misal titik GPS tempat kita berada di Tugu Jogja
//                    Double lat = location.getLatitude();
//                    Double lng = location.getLongitude();
//
//                    Location newLoc = new Location("Titik GPS");
//                    newLoc.setLatitude(lat);
//                    newLoc.setLongitude(lng);
//
//                    double distance = 6371 * Math.acos( Math.cos( Math.toRadians(newLoc.getLatitude()) ) * Math.cos( Math.toRadians( lokCek.getLatitude() ) )
//                            * Math.cos( Math.toRadians( lokCek.getLongitude() ) - Math.toRadians(newLoc.getLongitude()) )
//                            + Math.sin( Math.toRadians(newLoc.getLatitude()) ) * Math.sin( Math.toRadians( lokCek.getLatitude() ) ) ) ;
////                    holder.Jarak.setText((int) distance);
//
//                    Log.d("jarak", String.valueOf(distance));
//                }
//            }
//        });



//Maka Jaraknya :
//        Toast.makeText(getApplicationContext(), "lat:" + String.valueOf(lokCek.getLatitude()) + "\n lng:" + String.valueOf(lokCek.getLongitude()) + "\n lat:" + String.valueOf(newLoc.getLatitude()) + "\n lng:" + String.valueOf(newLoc.getLongitude()) + "\n jarak:" + String.valueOf(distance) , Toast.LENGTH_LONG).show()


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
