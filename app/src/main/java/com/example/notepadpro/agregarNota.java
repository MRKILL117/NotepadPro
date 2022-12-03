package com.example.notepadpro;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Calendar;

public class agregarNota extends DialogFragment{

    private DataListener callback;
    static String fecha = "";
    EditText contenido, titulo;
    static TextView fechaTitulo;
    Button cambiarFecha, guardar;

    public agregarNota() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_agregar_nota, container, false);
        contenido =  view.findViewById(R.id.ContenidoNuevo);
        titulo = view.findViewById(R.id.TituloNuevo);
        fechaTitulo = view.findViewById(R.id.FechaNuevo);
        cambiarFecha = view.findViewById(R.id.CambiarFecha);
        cambiarFecha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment fragmento = new FechaDialog();
                fragmento.show(getParentFragmentManager(), "Calendario");

            }
        });
        guardar = view.findViewById(R.id.Guardar);
        guardar.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                if(fecha.equals("")) fecha = "Sin fecha";
                Notas nueva = new Notas(titulo.getText().toString(), fecha, contenido.getText().toString());
                callback.enviarInfo(nueva);
            }
        });
        return view;
    }

    public void onAttach() {
        onAttach();
    }
    @Override
    public void onAttach(@NonNull Context context) {
    try {
        callback = (DataListener) context;
    }catch (Exception e){
        e.printStackTrace();
    }
        super.onAttach(context);
    }

    public static class FechaDialog extends DialogFragment implements DatePickerDialog.OnDateSetListener {

        @NonNull
        @Override
        public Dialog onCreateDialog(@Nullable Bundle savedInstanceState){
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);
            return new DatePickerDialog(getActivity(), this, year, month, day);
        }

        @Override
        public void onDateSet(DatePicker datePicker, int day, int month, int year) {
            fecha =  day + "/"+(month+1) + "/" + year;
            fechaTitulo.setText(fecha);
        }

    }

    public interface DataListener{
        public void enviarInfo(Notas nota);
    }
}