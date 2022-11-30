package com.example.finalandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.finalandroid.Entities.Movimiento;
import com.example.finalandroid.Services.IMovimiento;
import com.squareup.picasso.Picasso;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MovimientoActivity extends AppCompatActivity {
    public String montoM;
    public String latitud;
    public String longitud;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movimiento);

        TextView tipo = findViewById(R.id.tipo);
        TextView monto = findViewById(R.id.monto);
        TextView motivo = findViewById(R.id.motivo);
        ImageView imagen = findViewById(R.id.imagen);
        Button goMap = findViewById(R.id.goMap);

        Intent intent = getIntent();
        int idMovimiento = intent.getIntExtra("idMovimiento",0);
        latitud = intent.getStringExtra("latitud");
        longitud = intent.getStringExtra("longitud");

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://637427ec08104a9c5f7b28eb.mockapi.io/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        IMovimiento iMovimiento = retrofit.create(IMovimiento.class);
        Call<Movimiento> movimiento = iMovimiento.getMovimientoId(idMovimiento);

        movimiento.enqueue(new Callback<Movimiento>() {
            @Override
            public void onResponse(Call<Movimiento> call, Response<Movimiento> response) {
                if (response.code() == 200){
                    Log.i("CONEXION","OK");
                    Movimiento movieDetail = response.body();
                    montoM = String.valueOf(movieDetail.getMonto());
                    tipo.setText(movieDetail.getTipo());
                    monto.setText(montoM);
                    motivo.setText(movieDetail.getMotivo());
                    Picasso.get().load(movieDetail.getImagen()).into(imagen);
                    Log.i("CONEXION","OK" + movieDetail.getMonto());
                }else{
                    Log.i("CONEXION","BAD");
                }
            }

            @Override
            public void onFailure(Call<Movimiento> call, Throwable t) {
                Log.i("CONEXION","FALLO EN EL SERVIDOR");
                /*AppDataBase db = AppDataBase.getInstance(getApplicationContext());
                Movie movie = db.iMovieDao().getMovie(id);
                titulo.setText(movie.getTitulo());
                sinopsis.setText(movie.getSinopsis());
                Picasso.get().load(movie.getImagen()).into(imagen);*/
            }
        });
        goMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MovimientoActivity.this, MapsActivity.class);
                intent.putExtra("idMovimiento", idMovimiento);
                intent.putExtra("latitud", latitud);
                intent.putExtra("longitud", longitud);
                startActivity(intent);
            }
        });
    }
}