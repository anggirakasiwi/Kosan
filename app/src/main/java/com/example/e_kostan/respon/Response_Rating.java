package com.example.e_kostan.respon;

import com.example.e_kostan.model.Item_Kosan;
import com.example.e_kostan.model.item_rating;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Response_Rating {
    @SerializedName("status")
    private boolean status;

    @SerializedName("rating")
    private List<item_rating> rating;

    public void setStatus(boolean status){
        this.status = status;
    }

    public boolean isStatus(){
        return status;
    }

    public void setRating(List<item_rating> kosan){
        this.rating = rating;
    }

    public List<item_rating> getRating(){
        return rating;
    }

    @Override
    public String toString(){
        return
                "Response_Rating{" +
                        "status = '" + status + '\'' +
                        ",rating = '" + rating + '\'' +
                        "}";
    }
}
