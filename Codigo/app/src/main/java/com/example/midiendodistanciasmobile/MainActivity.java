package com.example.midiendodistanciasmobile;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import com.example.midiendodistanciasmobile.Helpers.SQLiteHelper;
import com.example.midiendodistanciasmobile.Models.Actividad;
import com.example.midiendodistanciasmobile.Models.Usuario;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import java.util.Date;

public class MainActivity extends AppCompatActivity {
    public String UserEmail;
    private SQLiteDatabase db;

    public int UsuarioId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_salidas, R.id.navigation_actividades)
                .build();
        Bundle parametros = this.getIntent().getExtras();
        if (parametros != null) {
            //agarramos el mail de la persona
            UserEmail = parametros.getString("UserEmail");
            //buscamos el id de esa persona para buscar los datos correspondientes en los fragmentos
            SQLiteHelper dbHelper = new SQLiteHelper(MainActivity.this);
            db = dbHelper.getWritableDatabase();
            Cursor u = db.rawQuery("Select * from Usuario WHERE Email = ?", new String[]{UserEmail});
            if (u != null) {
                u.moveToFirst();
                do {
                   UsuarioId = Integer.parseInt(u.getString(u.getColumnIndex("Id")));
                } while (u.moveToNext());
            }
        }
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);
    }

}
