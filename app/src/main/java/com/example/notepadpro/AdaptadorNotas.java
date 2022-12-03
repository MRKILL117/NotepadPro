package com.example.notepadpro;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class AdaptadorNotas extends RecyclerView.Adapter<AdaptadorNotas.ViewHolder> {

    List<Notas> listaNotas;
    int layout;
    private OnItemClickListener itemListener;

    public AdaptadorNotas(List<Notas> notas, int layout, OnItemClickListener itemListener){
        this.layout=layout;
        this.itemListener=itemListener;
        this.listaNotas = notas;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(layout, parent, false);
        ViewHolder vh = new ViewHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(listaNotas.get(position).getTitulo(), listaNotas.get(position).getFecha(),
                listaNotas.get(position).getContenido(), itemListener);
    }

    @Override
    public int getItemCount() {
        return listaNotas.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        TextView titulo, fecha, contenido;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            titulo =(TextView) itemView.findViewById(R.id.Titulo);
            fecha =(TextView) itemView.findViewById(R.id.Fecha);
            contenido =(TextView) itemView.findViewById(R.id.Contenido);
        }


        public void bind(String titulo, String fecha, String nota, final OnItemClickListener itemListener) {
            this.titulo.setText(titulo);
            this.fecha.setText(fecha);
            this.contenido.setText(nota);
            itemView.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view){
                    itemListener.onItemClick(titulo, fecha, nota, getAdapterPosition());
                }
            });
        }



        }

        public interface OnItemClickListener{
        void onItemClick(String name, String fecha, String nota, int position);
        }

    }
