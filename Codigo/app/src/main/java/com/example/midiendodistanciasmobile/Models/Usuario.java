package com.example.midiendodistanciasmobile.Models;

import java.util.List;

public class Usuario {

    public int Id;
    public String Nombre;
    public String Apellido;
    public int Dni;
    public String Email;
    public String Password;
    public String Grupo;
    public List<Actividad> Actividades;
    public NivelActividad NivelActividad;
}
