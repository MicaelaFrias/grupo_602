package com.example.midiendodistanciasmobile.Helpers;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class SQLiteHelper extends SQLiteOpenHelper {
    private static final String Usuarios = ("CREATE TABLE Usuario(Id INTEGER PRIMARY KEY AUTOINCREMENT);");
    private static final String Actividades = ("CREATE TABLE Actividad(Id INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "CantidadPasos INTEGER, Fecha TEXT,UsuarioId INTEGER NOT NULL, FOREIGN KEY (UsuarioId) REFERENCES " +
            "Usuario(\"+Id+\"));");

    public static final String SQL_DELETEUSUARIO = "DROP TABLE Usuario";
    public static final String SQL_DELETEACTIVIDAD = "DROP TABLE Actividad";

    private static final String DB_NAME = "midiendoDistancias.sqlite";
    private static final int DB_VERSION = 2;
    //DELETE TABLE
    public SQLiteHelper(Context context) {

        super(context, DB_NAME, null, DB_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        //borrar tablas actidad y comments
        db.execSQL(Usuarios);
        db.execSQL(Actividades);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETEUSUARIO);
        db.execSQL(SQL_DELETEACTIVIDAD);
        onCreate(db);
    }

}