package com.example.midiendodistanciasmobile.Helpers;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class SQLiteHelper extends SQLiteOpenHelper {
    private static final String Usuarios = ("CREATE TABLE Usuario(Email VARCHAR(50),Nombre VARCHAR(50), Apellido VARCHAR(50),Id INTEGER PRIMARY KEY AUTOINCREMENT);");
    private static final String Actividades = ("CREATE TABLE Actividad(Id INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "CantidadPasos INTEGER, Fecha DATETIME,UsuarioId INTEGER NOT NULL, FOREIGN KEY (UsuarioId) REFERENCES " +
            "Usuario(\"+Id+\"));");
    private static final String Salidas = ("CREATE TABLE Salida(Id INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "Fecha DATETIME NOT NULL,HoraSalida TimeSpan,HoraRegreso TimeSpan,DistanciaMax Doble ,DistanciaRecorrida Doble," +
            " UsuarioId INTEGER NOT NULL, FOREIGN KEY (UsuarioId) REFERENCES " +
            "Usuario(\"+Id+\"));");


    private static final String DB_NAME = "midiendoDistancias.sqlite";
    private static final int DB_VERSION = 2;

    //DELETE TABLE
    public SQLiteHelper(Context context) {

        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(Usuarios);
        db.execSQL(Salidas);
        db.execSQL(Actividades);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

}