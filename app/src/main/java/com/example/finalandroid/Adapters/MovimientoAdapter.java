package com.example.finalandroid.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.finalandroid.Entities.Movimiento;
import com.example.finalandroid.MovimientoActivity;
import com.example.finalandroid.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class MovimientoAdapter extends RecyclerView.Adapter<MovimientoAdapter.MovimientoViewHolder> {

    List<Movimiento> data;
    public MovimientoAdapter(List<Movimiento> data) {
        this.data = data;
    }

    @NonNull
    @Override
    public MovimientoAdapter.MovimientoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.movimiento_item, parent, false);
        MovimientoAdapter.MovimientoViewHolder vh = new MovimientoAdapter.MovimientoViewHolder(view,context);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull MovimientoAdapter.MovimientoViewHolder holder, int position) {
        //String imagenM = String.valueOf(data.get(position).getImagen());
        holder.tipo.setText(data.get(position).getTipo());
        holder.motivo.setText(data.get(position).getMotivo());
        String mont = String.valueOf(data.get(position).getMonto());
        holder.monto.setText(mont);
        Picasso.get().load(data.get(position).getImagen()).into(holder.imagen);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class MovimientoViewHolder extends RecyclerView.ViewHolder {
        TextView tipo, monto, motivo;
        ImageView imagen;
        public MovimientoViewHolder(@NonNull View itemView, Context context) {
            super(itemView);
            //latitud = itemView.findViewById(R.id.latitud);
            //longitud = itemView.findViewById(R.id.longitud);
            tipo = itemView.findViewById(R.id.tipo);
            monto = itemView.findViewById(R.id.monto);
            motivo = itemView.findViewById(R.id.motivo);
            imagen = itemView.findViewById(R.id.imagen);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String titleToast =  data.get(getAdapterPosition()).getTipo();
                    Toast.makeText(context, titleToast, Toast.LENGTH_SHORT).show();
                    Context context = view.getContext();
                    Intent intent = new Intent(context, MovimientoActivity.class);
                    int idMovimiento = data.get(getAdapterPosition()).getIdMovimiento();
                    intent.putExtra("latitud", data.get(getAdapterPosition()).getLatitud());
                    intent.putExtra("longitud", data.get(getAdapterPosition()).getLongitud());
                    intent.putExtra("idMovimiento", idMovimiento);
                    context.startActivity(intent);
                }
            });
        }
    }
}
