package com.example.finalandroid.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.finalandroid.CuentaActivity;
import com.example.finalandroid.Entities.Cuenta;
import com.example.finalandroid.R;

import java.util.List;

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
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class CuentaViewHolder extends RecyclerView.ViewHolder{
        TextView nombre;
        public CuentaViewHolder(@NonNull View itemView, Context context) {
            super(itemView);
            nombre = itemView.findViewById(R.id.nombre);
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
