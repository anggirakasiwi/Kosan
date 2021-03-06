package com.example.e_kostan.server;

import com.example.e_kostan.respon.Response_Kosan;
import com.example.e_kostan.respon.Response_Pesan;
import com.example.e_kostan.respon.Response_Rating;
import com.example.e_kostan.respon.Response_User;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ApiServices {

    @FormUrlEncoded
    @POST("login.php")
    Call<ResponseBody> userLogin(
            @Field("email") String email,
            @Field("password") String password
    );
    @FormUrlEncoded
    @POST("tampilpesan.php")
    Call<Response_Pesan> TampilPesan(
            @Field("penerima") String penerima
    );
    @FormUrlEncoded
    @POST("detailpesan.php")
    Call<Response_Pesan> TampilPesanDetail(
            @Field("penerima") String penerima,
            @Field("pengirim") String pengirim
    );
    @FormUrlEncoded
    @POST("kirimpesan.php")
    Call<ResponseBody> Kirim(
            @Field("isi") String isi,
            @Field("namapengirim") String namapengirim,
            @Field("penerima") String penerima,
            @Field("pengirim") String pengirim
    );

    @FormUrlEncoded
    @POST("register.php")
    Call<ResponseBody> userregister(
            @Field("email") String email,
            @Field("nama") String nama,
            @Field("no_hp") String no_hp,
            @Field("alamat") String alamat,
            @Field("jenis_kelamin") String jenis_kelamin,
            @Field("role") String role,
            @Field("password") String password,
            @Field("confirm_password") String confrim_password
    );
    @GET("tampilkosan.php")
    Call <Response_Kosan> TampilKosan();
        @FormUrlEncoded
        @POST("tampilkosan.php")
        Call<Response_Kosan> Tampil_Kosan(
                @Field("latitude") double latitude,
                @Field("longitude") double longitude
        );


    @FormUrlEncoded
    @POST("tambahkosan.php")
    Call<ResponseBody> TambahKosan(
            @Field("alamat") String alamat,
            @Field("nama_kostan") String nama_kostan,
            @Field("harga") String harga,
            @Field("fasilitas") String fasilitas,
            @Field("durasi") String durasi,
            @Field("latitutude") double latitutude,
            @Field("longitude") double longitude,
            @Field("id_user") String id_user,
            @Field("gambar") String gambar,
            @Field("no_hp") String Tlp
    );
    @FormUrlEncoded
    @POST("editprofile.php")
    Call<ResponseBody> userupadate(
            @Field("email") String email,
            @Field("nama") String nama,
            @Field("no_hp") String no_hp,
            @Field("password") String password,
            @Field("gambar") String gambar
    );
    @FormUrlEncoded
    @POST("tambahrating.php")
    Call<ResponseBody> AddRating(
            @Field("id_kosan") String id_kosan,
            @Field("id_user") String id_user,
            @Field("rating") Float rating,
            @Field("komentar") String komentar);

    @FormUrlEncoded
    @POST("tampilrating.php")
    Call<Response_Rating> GetRating(
            @Field("id") String id_kosan

    );

    @GET("tampiluser.php")
    Call <Response_User> Tampil_User();

//    @GET("tampil_murah.php")
//    Call <Response_Kosan> Tampil_Kosan_Murah();
        @FormUrlEncoded
        @POST("tampil_murah.php")
        Call<Response_Kosan>Tampil_Kosan_Murah(
                @Field("latitude") double latitude,
                @Field("longitude") double longitude
        );

//    @GET("tampil_termahal.php")
//    Call <Response_Kosan> Tampil_mahal();
        @FormUrlEncoded
        @POST("tampil_termahal.php")
        Call<Response_Kosan>Tampil_mahal(
                @Field("latitude") double latitude,
                @Field("longitude") double longitude
        );



//    @GET("rating.php")
//    Call <Response_Kosan> Rating();
        @FormUrlEncoded
        @POST("Rating.php")
        Call<Response_Kosan> Rating(
                @Field("latitude") double latitude,
                @Field("longitude") double longitude
        );

//    @GET("tampil_terdekat.php")
//    Call<Response_Kosan> Tampil_Terdekat_mas();

    @FormUrlEncoded
    @POST("tampil_terdekat.php")
    Call<Response_Kosan> Tampil_Terdekat_mas(
            @Field("latitude") double latitude,
            @Field("longitude") double longitude
    );
}
