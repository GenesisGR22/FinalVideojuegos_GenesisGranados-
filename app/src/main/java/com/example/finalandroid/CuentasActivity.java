package com.example.finalandroid;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Movie;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.finalandroid.Adapters.CuentaAdapter;
import com.example.finalandroid.Entities.Cuenta;
import com.example.finalandroid.Services.ICuentas;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CuentasActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cuentas);
        RecyclerView rv = findViewById(R.id.rvCuentas);
        FloatingActionButton floatingActionButton = findViewById(R.id.floatButton);

        floatingActionButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CuentasActivity.this,NewcuentaActivity.class);
                startActivity(intent);
            }
        });

        rv.setHasFixedSize(true);
        rv.setLayoutManager(new LinearLayoutManager(this));

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://637427ec08104a9c5f7b28eb.mockapi.io/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ICuentas iCuentas = retrofit.create(ICuentas.class);
        Call<List<Cuenta>> cuentasAll = iCuentas.getAll();

        cuentasAll.enqueue(new Callback<List<Cuenta>>() {
            @Override
            public void onResponse(Call<List<Cuenta>> call, Response<List<Cuenta>> response) {
                if (response.code() == 200){
                    Log.i("CONEXION","OK");
                    List<Cuenta> movieList = response.body();
                    rv.setAdapter(new CuentaAdapter(movieList));
                }else{
                    Log.i("CONEXION","BAD");
                }
            }
            @Override
            public void onFailure(Call<List<Cuenta>> call, Throwable t) {
                Log.i("CONEXION","FALLO EN EL SERVIDOR");
                /*AppDataBase db = AppDataBase.getInstance(getApplicationContext());
                List<Movie> movie = db.iMovieDao().getAll();
                rv.setAdapter(new MovieAdapter(movie));
                Log.i("CONEXION", movie.toString());*/
            }
        });

    }
}