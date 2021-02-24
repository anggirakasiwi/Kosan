package com.example.e_kostan.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.e_kostan.R;
import com.example.e_kostan.model.Item_pesan;
import com.example.e_kostan.model.item_rating;

import java.util.List;

public class adapter_rating extends RecyclerView.Adapter<adapter_rating.MyViewHolder> {
    Context context;
    List<item_rating> Menu;
    public adapter_rating (Context context, List<item_rating> Data_Menu){
        this.context=context;
        this.Menu=Data_Menu;
    }

    @NonNull
    @Override
    public adapter_rating.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.item_rating,parent,false);
        adapter_rating.MyViewHolder holder=new adapter_rating.MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull adapter_rating.MyViewHolder holder, int position) {
        String Rating = Menu.get(position).getRating();
        if (Rating.equals("5")){
            holder.rating.setText("⭐⭐⭐⭐⭐");
        } else if (Rating.equals("4")){
            holder.rating.setText("⭐⭐⭐⭐");
        } else if (Rating.equals("3")){
            holder.rating.setText("⭐⭐⭐");
        }else if (Rating.equals("2")){
            holder.rating.setText("⭐⭐");
        }else if (Rating.equals("1")){
            holder.rating.setText("⭐");
        }

//        holder.rating.setText(Menu.get(position).getRating());
        holder.namarating.setText(Menu.get(position).getId_user());
        holder.Komentar.setText(Menu.get(position).getKomentar());

    }

    @Override
    public int getItemCount() {
        return Menu.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView rating, namarating,Komentar;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            rating=(TextView) itemView.findViewById(R.id.itemrating);
            namarating=(TextView) itemView.findViewById(R.id.namarating);
            Komentar=(TextView) itemView.findViewById(R.id.komentar);
        }
    }
}

