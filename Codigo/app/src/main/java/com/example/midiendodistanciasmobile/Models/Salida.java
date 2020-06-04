package com.example.midiendodistanciasmobile.Models;

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

}
