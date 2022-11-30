package com.example.finalandroid;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.example.finalandroid.Adapters.MovimientoAdapter;
import com.example.finalandroid.DataBase.AppDataBase;
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
        AppDataBase db = AppDataBase.getInstance(getApplicationContext());
        List<Movimiento> movimientos = db.iMovimientoDao().getMovimientoCuenta(idCuenta);
        rv.setHasFixedSize(true);
        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.setAdapter(new MovimientoAdapter(movimientos));
    }
}