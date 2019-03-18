package com.intergrupo.usuariosapp;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class AdaptadorUsuarios extends RecyclerView.Adapter<AdaptadorUsuarios.UsuarioViewHolder> {

    List<Lista> listUsuarios;


    public AdaptadorUsuarios(List<Lista> listUsuarios) {
        this.listUsuarios = listUsuarios;
    }

    @Override
    public UsuarioViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.usuario_row,null,false);
        return new UsuarioViewHolder(v);
    }

    @Override
    public void onBindViewHolder(UsuarioViewHolder holder, int position) {

        holder.nombres.setText("Nombre: "+listUsuarios.get(position).getName()+" "+listUsuarios.get(position).getSurname()+listUsuarios.size());
        holder.cedula.setText("Cedula: "+listUsuarios.get(position).getId());
        holder.telefono.setText("Telefono: "+listUsuarios.get(position).getTelephone());


    }

    @Override
    public int getItemCount() {
        return listUsuarios.size();
    }

    public static class UsuarioViewHolder extends RecyclerView.ViewHolder{
        TextView nombres,cedula,telefono,estado;

        public UsuarioViewHolder(View itemView){
            super(itemView);

            nombres=itemView.findViewById(R.id.nombres);
            cedula=itemView.findViewById(R.id.cedula);
            telefono=itemView.findViewById(R.id.telefono);

        }


    }

}
