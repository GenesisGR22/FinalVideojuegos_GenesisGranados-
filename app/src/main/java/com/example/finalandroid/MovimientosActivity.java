package com.example.finalandroid;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.example.finalandroid.Adapters.MovimientoAdapter;
import com.example.finalandroid.Entities.Movimiento;
import com.example.finalandroid.Services.IMovimiento;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MovimientosActivity extends AppCompatActivity {
public int idCuenta;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movimientos);
        RecyclerView rv = findViewById(R.id.rvMovimientos);

        Intent intent = getIntent();
        idCuenta = intent.getIntExtra("idCuenta",0);

        rv.setHasFixedSize(true);
        rv.setLayoutManager(new LinearLayoutManager(this));

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://637427ec08104a9c5f7b28eb.mockapi.io/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        IMovimiento iMovimiento = retrofit.create(IMovimiento.class);
        Call<List<Movimiento>> movimientoAll = iMovimiento.getAll();

        movimientoAll.enqueue(new Callback<List<Movimiento>>() {
            @Override
            public void onResponse(Call<List<Movimiento>> call, Response<List<Movimiento>> response) {
                if (response.code() == 200){
                    Log.i("CONEXION","OK");
                    List<Movimiento> movieList = new ArrayList<Movimiento>();
                    for (Movimiento movimientoslistas : response.body()) {
                        if (movimientoslistas.getIdCuenta() == idCuenta){
                            movieList.add(movimientoslistas);
                        }
                    }
                    rv.setAdapter(new MovimientoAdapter(movieList));
                }else{
                    Log.i("CONEXION","BAD");
                }
            }
            @Override
            public void onFailure(Call<List<Movimiento>> call, Throwable t) {
                Log.i("CONEXION","FALLO EN EL SERVIDOR");
                /*AppDataBase db = AppDataBase.getInstance(getApplicationContext());
                List<Movie> movie = db.iMovieDao().getAll();
                rv.setAdapter(new MovieAdapter(movie));
                Log.i("CONEXION", movie.toString());*/
            }
        });

    }
}