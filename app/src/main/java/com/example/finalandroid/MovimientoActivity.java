package com.example.finalandroid;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.finalandroid.Adapters.MovimientoAdapter;
import com.example.finalandroid.DataBase.AppDataBase;
import com.example.finalandroid.Entities.Movimiento;
import com.example.finalandroid.Services.IMovimiento;
import com.squareup.picasso.Picasso;

import java.util.List;

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

        AppDataBase db = AppDataBase.getInstance(getApplicationContext());
        Movimiento movimiento = db.iMovimientoDao().getMovimiento(idMovimiento);
        montoM = String.valueOf(movimiento.getMonto());
        tipo.setText(movimiento.getTipo());
        monto.setText(montoM);
        motivo.setText(movimiento.getMotivo());
        Picasso.get().load(movimiento.getImagen()).into(imagen);

        goMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MovimientoActivity.this, MapsActivity.class);
                intent.putExtra("idMovimiento", idMovimiento);
                intent.putExtra("latitud", movimiento.getLatitud());
                intent.putExtra("longitud", movimiento.getLongitud());
                startActivity(intent);
            }
        });
    }
}