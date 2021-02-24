package com.example.e_kostan.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.e_kostan.Menu.Menu_Detail;
import com.example.e_kostan.Menu.Menu_Detail_Massage;
import com.example.e_kostan.R;
import com.example.e_kostan.model.Item_Kosan;
import com.example.e_kostan.model.Item_pesan;
import com.example.e_kostan.server.InitRetrofit;
import com.squareup.picasso.Picasso;

import java.util.List;

public class adapter_pesan extends RecyclerView.Adapter<adapter_pesan.MyViewHolder> {
    Context context;
    List<Item_pesan> Menu;
    public adapter_pesan (Context context, List<Item_pesan> Data_Menu){
        this.context=context;
        this.Menu=Data_Menu;
    }

    @NonNull
    @Override
    public adapter_pesan.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.item_pesan,parent,false);
        adapter_pesan.MyViewHolder holder=new adapter_pesan.MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull adapter_pesan.MyViewHolder holder, int position) {
        holder.NamaPengirim.setText(Menu.get(position).getNamaPengirim());
        holder.Pesan.setText(Menu.get(position).getIsiPesan());
        holder.Tanggal.setText(Menu.get(position).getTanggal());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(context, Menu_Detail_Massage.class);
//                intent.putExtra("ID", Menu.get(position).getId());
//                intent.putExtra("ISIPESAN", Menu.get(position).getIsiPesan());
//                intent.putExtra("PENGIRIM", Menu.get(position).getPengirim());
//                intent.putExtra("PENERIMA", Menu.get(position).getPenerima());
//                intent.putExtra("NAMA_PENGIRIM", Menu.get(position).getNamaPengirim());
//                context.startActivity(intent);
            }
        });

    }


    @Override
    public int getItemCount() {
        return Menu.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView NamaPengirim,Pesan,Tanggal;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            NamaPengirim=(TextView) itemView.findViewById(R.id.pengirimpesan);
            Pesan=(TextView) itemView.findViewById(R.id.pesan);
            Tanggal=(TextView) itemView.findViewById(R.id.tanggal);
        }
    }
}


