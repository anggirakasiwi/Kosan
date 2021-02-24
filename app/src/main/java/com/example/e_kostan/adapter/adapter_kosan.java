package com.example.e_kostan.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.e_kostan.Menu.Menu_Detail;
import com.example.e_kostan.Menu.Menu_Rating;
import com.example.e_kostan.R;
import com.example.e_kostan.model.Item_Kosan;
import com.example.e_kostan.server.InitRetrofit;
import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.Locale;

public class adapter_kosan extends RecyclerView.Adapter<adapter_kosan.MyViewHolder> {
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
holder.Nama_Kosan.setText(Menu.get(position).getNamaKostan());
holder.Durasi_Kosan.setText(Menu.get(position).getDurasi());
holder.Alamat_Kosan.setText(Menu.get(position).getAlamat());
holder.Fasilitas_Kosan.setText(Menu.get(position).getFasilitas());
holder.Harga_Kosan.setText(Menu.get(position).getHarga());
holder.No_Hp.setText(Menu.get(position).getNomorHp());
final String urlgambar = InitRetrofit.BASE_URL+ Menu.get(position).getGambar();
        Picasso.with(context).load(urlgambar).into(holder.Gambar_Kosan);
        holder.Detail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
