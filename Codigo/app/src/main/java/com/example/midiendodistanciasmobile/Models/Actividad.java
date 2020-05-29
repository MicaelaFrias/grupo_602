package com.example.midiendodistanciasmobile.Models;


import java.util.Date;

public class Actividad {

    public int Id;
    public int CantidadPasos;
    public Date Fecha;
    public Usuario Usuario;

    public Actividad(int id, int cantidadPasos, Date fecha, com.example.midiendodistanciasmobile.Models.Usuario usuario) {
        Id = id;
        CantidadPasos = cantidadPasos;
        Fecha = fecha;
        Usuario = usuario;
    }
    public  Actividad(){

    }

    public String toString()
    {
        return "Nro: " +  Id + " Fecha: " + Fecha.getDay() + "/" + Fecha.getMonth() + "/" + Fecha.getYear()  + " Cantidad de pasos: " +  CantidadPasos;
    }
}

