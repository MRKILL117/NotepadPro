package com.example.notepadpro;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ToggleButton;

import java.util.ArrayList;
import java.util.List;

public class NotasActivity extends AppCompatActivity implements agregarNota.DataListener{

    private boolean eliminar = false;
    private ToggleButton cambiarAccion;
    private List<Notas> lista;
    private RecyclerView myRecyclerView;
    private RecyclerView.Adapter miAdaptador;
    private RecyclerView.LayoutManager miLayoutManager;
    private int counter =0;

    // Menu methods
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.notes_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.addNoteMenuButton:
                DialogFragment fragment = new agregarNota();
                fragment.show(getSupportFragmentManager(), "Nueva nota");
                return true;
            case R.id.goToTasksMenuButton:
                Intent i = new Intent(this, TasksList.class);
                startActivity(i);
                return true;
            default: return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notas);
        lista = new ArrayList<Notas>();
        myRecyclerView = (RecyclerView) findViewById(R.id.RecyclerNotas);
        miLayoutManager = new LinearLayoutManager(this);
        miAdaptador = new AdaptadorNotas(lista, R.layout.item_notas, new AdaptadorNotas.OnItemClickListener() {
            @Override
            public void onItemClick(String name, String fecha, String nota, int position) {
                Notas nuevo = getNota(position);
                if(eliminar){
                    quitarElemento(nuevo, position);
                }
                else{
                    agregarNota.setNota(nuevo);
                    DialogFragment fragment = new agregarNota();
                    fragment.show(getSupportFragmentManager(), "Editar nota");
                    quitarElemento(nuevo, position);
                }
            }
        });
        cambiarAccion = findViewById(R.id.toggle);
        cambiarAccion.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(!compoundButton.isChecked())eliminar = false;
                else eliminar = true;
            }
        });
        myRecyclerView.setLayoutManager(miLayoutManager);
        myRecyclerView.setAdapter(miAdaptador);

    }

    public void agregarElemento(Notas nota, int posicion){
        lista.add(nota);
        miAdaptador.notifyItemInserted(posicion);
    }

    public Notas getNota(int posicion){
        return lista.get(posicion);
    }

    public void quitarElemento(Notas nota, int posicion){
        lista.remove(nota);
        miAdaptador.notifyItemRemoved(posicion);
    }

    @Override
    public void enviarInfo(Notas nota) {
        Notas nueva = nota;
        agregarElemento(nueva, ++counter);
    }
}