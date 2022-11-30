package com.example.finalandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.finalandroid.Entities.Cuenta;
import com.example.finalandroid.Services.ICuentas;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NewcuentaActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newcuenta);
        EditText nombre = findViewById(R.id.nombre);
        Button crear = findViewById(R.id.newMovie);

        crear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String tituloM = nombre.getText().toString().trim();

                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl("https://637427ec08104a9c5f7b28eb.mockapi.io/")
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

                ICuentas service = retrofit.create(ICuentas.class);
                Cuenta cuenta = new Cuenta();
                cuenta.setNombre(tituloM);

                Call<Cuenta> call = service.create(cuenta);

                call.enqueue(new Callback<Cuenta>() {
                    @Override
                    public void onResponse(Call<Cuenta> call, Response<Cuenta> response) {
                        if (response.code() == 200){
                            Log.i("CONEXION","OK");
                        }else{
                            Log.i("CONEXION","BAD");
                        }
                    }

                    @Override
                    public void onFailure(Call<Cuenta> call, Throwable t) {
                        Log.i("CONEXION","FALLO EN EL SERVIDOR");
                        /*AppDataBase db = AppDataBase.getInstance(getApplicationContext());
                        db.iMovieDao().insert(movie);*/
                    }
                });
                Intent intent = new Intent(NewcuentaActivity.this,CuentasActivity.class);
                startActivity(intent);
            }
        });
    }
}