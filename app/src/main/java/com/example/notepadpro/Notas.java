package com.example.notepadpro;

import android.widget.TextView;

public class Notas {

    private String titulo;
    private String fecha;
    private String contenido;

    public Notas(String titulo, String fecha, String contenido) {
        this.titulo = titulo;
        this.fecha = fecha;
        this.contenido = contenido;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getContenido() {
        return contenido;
    }

    public void setContenido(String contenido) {
        this.contenido = contenido;
    }
}
