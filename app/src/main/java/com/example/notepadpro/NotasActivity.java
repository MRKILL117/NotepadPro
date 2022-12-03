package com.example.notepadpro;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.fragment.app.FragmentActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

public class NotasActivity extends AppCompatActivity implements agregarNota.DataListener{

    private List<Notas> lista;
    private RecyclerView myRecyclerView;
    private RecyclerView.Adapter miAdaptador;
    private RecyclerView.LayoutManager miLayoutManager;
    private Button agregar;
    private int counter =0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notas);
        agregar = findViewById(R.id.agregarNota);
        lista = new ArrayList<Notas>();
        //lista = llenarNotas();
        myRecyclerView = (RecyclerView) findViewById(R.id.RecyclerNotas);
        miLayoutManager = new LinearLayoutManager(this);
        miAdaptador = new AdaptadorNotas(lista, R.layout.item_notas, new AdaptadorNotas.OnItemClickListener() {
            @Override
            public void onItemClick(String name, String fecha, String nota, int position) {



            }
        });
        myRecyclerView.setLayoutManager(miLayoutManager);
        myRecyclerView.setAdapter(miAdaptador);
        agregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment fragment = new agregarNota();
                fragment.show(getSupportFragmentManager(), "Nueva nota");
            }
        });
    }

    private List<Notas> llenarNotas(){
        return new ArrayList<Notas>(){{
            add(new Notas("Nota 1", "15/9/2022", "Hoy comi sopa"));
            add(new Notas("Nota 2", "18/9/2022", "Hoy no comi"));
            add(new Notas("Nota 3", "22/9/2022", "Tengo hambre"));
        }};
    }

    public void agregarElemento(Notas nota, int posicion){
        lista.add(nota);
        miAdaptador.notifyItemInserted(posicion);
    }

    public void quitarElemento(Notas nota){

    }

    @Override
    public void enviarInfo(Notas nota) {
        Notas nueva = nota;
        agregarElemento(nueva, ++counter);
    }
}