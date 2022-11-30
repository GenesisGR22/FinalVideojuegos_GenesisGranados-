package com.example.finalandroid.Services;

import com.example.finalandroid.Entities.Movimiento;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface IMovimiento {
    @GET("/Movimiento")
    Call<List<Movimiento>> getAll();

    @GET("/Movimiento/{id}")
    Call<Movimiento> getMovimientoId(@Path("id") int id);

    @POST("/Movimiento")
    Call<Movimiento> create(@Body Movimiento movimiento);

    @PUT("/Movimiento/{id}")
    Call<Movimiento> update(@Path("id") int id, @Body Movimiento movimiento);

    @DELETE("/Movimiento/{id}")
    Call<Void> delete(@Path("id") int id);
}
