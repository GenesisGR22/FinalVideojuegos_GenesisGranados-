package com.example.finalandroid.Adapters;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.finalandroid.CuentaActivity;
import com.example.finalandroid.Entities.Cuenta;
import com.example.finalandroid.Entities.Movimiento;
import com.example.finalandroid.R;
import com.example.finalandroid.Services.IMovimiento;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CuentaAdapter extends RecyclerView.Adapter<CuentaAdapter.CuentaViewHolder>{

    List<Cuenta> data;
    public CuentaAdapter(List<Cuenta> data) {
        this.data = data;
    }

    @NonNull
    @Override
    public CuentaAdapter.CuentaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cuenta_item, parent, false);
        CuentaAdapter.CuentaViewHolder vh = new CuentaAdapter.CuentaViewHolder(view,context);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull CuentaAdapter.CuentaViewHolder holder, int position) {
        holder.nombre.setText(data.get(position).getNombre());

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://637427ec08104a9c5f7b28eb.mockapi.io/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        IMovimiento iMovimiento = retrofit.create(IMovimiento.class);
        Call<List<Movimiento>> movimientoAll = iMovimiento.getAll();
        ArrayList<Integer> saldoss = new ArrayList<Integer>();
        movimientoAll.enqueue(new Callback<List<Movimiento>>() {
            @Override
            public void onResponse(Call<List<Movimiento>> call, Response<List<Movimiento>> response) {
                if (response.code() == 200){
                    for (Movimiento mov : response.body()) {
                        if (mov.getIdCuenta() == data.get(position).getIdCuenta()){
                            if (mov.getTipo().length() == "INGRESO".length())
                                saldoss.add(+mov.getMonto());
                            else
                                saldoss.add(-mov.getMonto());
                        }
                    }
                    int saldotote = 0;
                    for (int saldote :saldoss) {
                        saldotote += saldote;
                    }

                    String saldito = String.valueOf(saldotote);
                    holder.saldo.setText(saldito);

                }else{
                    Log.i("CONEXION","BAD");
                }
            }
            @Override
            public void onFailure(Call<List<Movimiento>> call, Throwable t) {
                Log.i("CONEXION","FALLO EN EL SERVIDOR");
            }
        });

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class CuentaViewHolder extends RecyclerView.ViewHolder{
        TextView nombre, saldo;
        public CuentaViewHolder(@NonNull View itemView, Context context) {
            super(itemView);
            nombre = itemView.findViewById(R.id.nombre);
            saldo = itemView.findViewById(R.id.saldo);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String titleToast =  data.get(getAdapterPosition()).getNombre();
                    Toast.makeText(context, titleToast, Toast.LENGTH_SHORT).show();
                    Context context = view.getContext();
                    Intent intent = new Intent(context, CuentaActivity.class);
                    int idCuenta = data.get(getAdapterPosition()).getIdCuenta();
                    intent.putExtra("idCuenta", idCuenta);
                    context.startActivity(intent);
                }
            });
        }
    }
}
