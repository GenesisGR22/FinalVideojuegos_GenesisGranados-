package com.example.finalandroid.Services;

import com.example.finalandroid.Entities.Imagen;
import com.example.finalandroid.Entities.ImgBase64;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface IImage {
    @Headers("Authorization: Client-ID 8bcc638875f89d9")
    @POST("3/image")
    Call<Imagen> create(@Body ImgBase64 image);
}
