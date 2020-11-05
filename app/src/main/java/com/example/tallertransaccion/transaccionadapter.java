package com.example.tallertransaccion;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class transaccionadapter extends RecyclerView.Adapter<transaccionadapter.transaccionviewholder> {

    ArrayList <listatransferir> listatransaccion;

    public transaccionadapter (ArrayList <listatransferir> listatransaccion){

        this.listatransaccion = listatransaccion;

    }

    @NonNull
    @Override
    public transaccionadapter.transaccionviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_lista, null, false);

        return new transaccionviewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull transaccionadapter.transaccionviewholder holder, int position) {

        holder.nrotransacl.setText(listatransaccion.get(position).getNrotransac().toString()); // trae a la posicion la informacion que tiene el arreglo
        holder.nrocuentaorigenl.setText(listatransaccion.get(position).getNrocuentaorigen().toString());
        holder.nrocuentadestinol.setText(listatransaccion.get(position).getNrocuentadestino().toString());
        holder.horal.setText(listatransaccion.get(position).getHora().toString());
        holder.fechal.setText(listatransaccion.get(position).getFecha().toString());
        holder.valorl.setText(listatransaccion.get(position).getValor().toString());
    }

    @Override
    public int getItemCount() {
        return listatransaccion.size();
    }

    public class transaccionviewholder extends RecyclerView.ViewHolder {

        TextView nrotransacl,nrocuentaorigenl, nrocuentadestinol, horal, fechal, valorl;

        public transaccionviewholder(@NonNull View itemView) {
            super(itemView);

            nrotransacl = itemView.findViewById(R.id.etnrotransac);
            nrocuentaorigenl = itemView.findViewById(R.id.etnrocuentaorigen);
            nrocuentadestinol = itemView.findViewById(R.id.etnrocuentadestino);
            horal = itemView.findViewById(R.id.etnhora);
            fechal = itemView.findViewById(R.id.etnfecha);
            valorl = itemView.findViewById(R.id.etnvalor);




        }
    }
}
