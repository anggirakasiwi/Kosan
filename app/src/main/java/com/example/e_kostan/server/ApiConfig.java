package com.example.e_kostan.server;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface ApiConfig {
    @Multipart
    @POST("uploadgambar.php")
    Call<ResponseBody> uploadFile(@Part MultipartBody.Part file,
                                  @Part("file") RequestBody name);

    @Multipart
    @POST("uploadgambaruser.php")
    Call<ResponseBody> uploadFotoProfil(@Part MultipartBody.Part file,
                                        @Part("file") RequestBody name);

    @Multipart
    @POST("uploadgambaruser.php")
    Call<ResponseBody> uploadFotoProfilUser(@Part MultipartBody.Part file,
                                            @Part("file") RequestBody name);

}
