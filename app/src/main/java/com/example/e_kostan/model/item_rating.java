package com.example.e_kostan.model;

import com.google.gson.annotations.SerializedName;

public class item_rating {
    @SerializedName("id")
    private String id;

    @SerializedName("id_kosan")
    private String id_kosan;

    @SerializedName("id_user")
    private String id_user;

    @SerializedName("rating")
    private String rating;
    @SerializedName("komentar")
    private String komentar;

    public void setId(String id){
        this.id = id;
    }

    public String getId(){
        return id;
    }
    public void setId_kosan(String id_kosan){
        this.id_kosan = id_kosan;
    }

    public String getId_kosan(){
        return id_kosan;
    }
    public void setId_user(String id_user){
        this.id_user = id_user;
    }

    public String getId_user(){
        return id_user;
    }
    public void setRating(String rating){
        this.rating = rating;
    }

    public String getRating(){
        return rating;
    }
    public void setKomentar(String komentar){
        this.komentar = komentar;
    }

    public String getKomentar(){
        return komentar;
    }
    @Override
    public String toString(){
        return
                "item_rating{" +
                        "id = '" + id + '\'' +
                        ",id_kosan = '" + id_kosan + '\'' +
                        ",id_user = '" + id_user + '\'' +
                        ",rating = '" + rating + '\'' +
                        ",komentar = '" + komentar + '\'' +
                        "}";
    }
}
