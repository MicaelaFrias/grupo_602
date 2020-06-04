package com.example.midiendodistanciasmobile.Models;

import androidx.annotation.NonNull;

import java.sql.Time;
import java.util.Date;

public class Salida {

    public int Id;
    public Date Fecha;
    public Time HoraSalida;
    public Time HoraRegreso;
    public float DistanciaMax;
    public float DistanciaRecorrida;
    public Usuario Usuario;


    public Salida(Date fecha, float distMax){
        this.Fecha = fecha;
        this.DistanciaMax = distMax;
    }

    @NonNull
    @Override
    public String toString() {
        return "Fecha: " + Fecha.getDay() + "/" + Fecha.getMonth() + "/" + Fecha.getYear()  + " - Distancia Máxima: " + this.DistanciaMax;
    }
}
