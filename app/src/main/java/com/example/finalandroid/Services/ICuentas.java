package com.example.finalandroid.Services;

import com.example.finalandroid.Entities.Cuenta;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface ICuentas {
    @GET("/Cuenta")
    Call<List<Cuenta>> getAll();

    @GET("/Cuenta/{id}")
    Call<Cuenta> getCuentaId(@Path("id") int id);

    @POST("/Cuenta")
    Call<Cuenta> create(@Body Cuenta cuenta);

    @PUT("/Cuenta/{id}")
    Call<Cuenta> update(@Path("id") int id, @Body Cuenta cuenta);

    @DELETE("/Cuenta/{id}")
    Call<Void> delete(@Path("id") int id);
}
