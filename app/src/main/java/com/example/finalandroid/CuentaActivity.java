package com.example.finalandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.finalandroid.Entities.Cuenta;
import com.example.finalandroid.Services.ICuentas;
import com.squareup.picasso.Picasso;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CuentaActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cuenta);

        TextView nombre = findViewById(R.id.nombre);
        Button registrar = findViewById(R.id.regMov);
        Button listar = findViewById(R.id.verMov);
        Button sincronizar = findViewById(R.id.sincrony);

        Intent intent = getIntent();
        int idCuenta = intent.getIntExtra("idCuenta",0);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://637427ec08104a9c5f7b28eb.mockapi.io/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ICuentas iCuentas = retrofit.create(ICuentas.class);
        Call<Cuenta> cuenta = iCuentas.getCuentaId(idCuenta);

        cuenta.enqueue(new Callback<Cuenta>() {
            @Override
            public void onResponse(Call<Cuenta> call, Response<Cuenta> response) {
                if (response.code() == 200){
                    Log.i("CONEXION","OK");
                    Cuenta movieDetail = response.body();
                    nombre.setText(movieDetail.getNombre());
                }else{
                    Log.i("CONEXION","BAD");
                }
            }

            @Override
            public void onFailure(Call<Cuenta> call, Throwable t) {
                Log.i("CONEXION","FALLO EN EL SERVIDOR");
                /*AppDataBase db = AppDataBase.getInstance(getApplicationContext());
                Movie movie = db.iMovieDao().getMovie(id);
                titulo.setText(movie.getTitulo());
                sinopsis.setText(movie.getSinopsis());
                Picasso.get().load(movie.getImagen()).into(imagen);*/
            }
        });

        listar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CuentaActivity.this,MovimientosActivity.class);
                intent.putExtra("idCuenta", idCuenta);
                startActivity(intent);
            }
        });
        registrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CuentaActivity.this,NewmovimientoActivity.class);
                intent.putExtra("idCuenta", idCuenta);
                startActivity(intent);
            }
        });
        sincronizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }
}