package com.example.midiendodistanciasmobile.ui.actividades;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.midiendodistanciasmobile.Helpers.SQLiteHelper;
import com.example.midiendodistanciasmobile.LoginActivity;
import com.example.midiendodistanciasmobile.MainActivity;
import com.example.midiendodistanciasmobile.Models.Actividad;
import com.example.midiendodistanciasmobile.Models.Usuario;
import com.example.midiendodistanciasmobile.R;

import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import static androidx.core.content.ContextCompat.getSystemService;
import static java.util.Calendar.DATE;

public class ActividadesFragment extends Fragment implements SensorEventListener {

    private ActividadesViewModel actividadesViewModel;
    private SQLiteDatabase db;
    public int contadorPasos = 0;
    public int usuarioID = 0;
    private ListView list;
    ArrayList<Actividad> actividades = new ArrayList<Actividad>();
    private SensorManager mSensorManager;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        actividadesViewModel =
                ViewModelProviders.of(this).get(ActividadesViewModel.class);
        View root = inflater.inflate(R.layout.fragment_actividades, container, false);
        usuarioID = ((MainActivity) getActivity()).UsuarioId;
        SQLiteHelper dbHelper = new SQLiteHelper(getActivity());
        db = dbHelper.getWritableDatabase();

        initSensor();

        if (db != null) {
            actividades = GetActividades(db);
        }

        list = root.findViewById(R.id.list);
        ArrayAdapter<Actividad> arrayAdapter = new ArrayAdapter<Actividad>(getActivity(), android.R.layout.simple_list_item_1, actividades);
        list.setAdapter(arrayAdapter);
        return root;
    }

    private void initSensor() {

        mSensorManager = (SensorManager) getContext().getSystemService(Context.SENSOR_SERVICE);
        Sensor sSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_STEP_DETECTOR);
        mSensorManager.registerListener(this, sSensor, SensorManager.SENSOR_DELAY_FASTEST);

    }

    public ArrayList<Actividad> GetActividades(SQLiteDatabase db) {
        ArrayList<Actividad> actividades = new ArrayList<Actividad>();
        Cursor c = db.rawQuery("Select * from Actividad WHERE UsuarioID = ?",
                new String[]{String.valueOf(usuarioID)});
        if (c != null && c.getCount() != 0) {
            c.moveToFirst();
            do {
                //Agregamos actividades del usuario loggeado
                actividades.add(new Actividad(Integer.parseInt(c.getString(c.getColumnIndex("Id"))),
                        Integer.parseInt(c.getString(c.getColumnIndex("CantidadPasos"))
                        ), new Date(), new Usuario()));
            } while (c.moveToNext());
        }

        //Cerramos el cursor y la conexion con la base de datos
        c.close();
        db.close();

        return actividades;
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        float step = event.values[0];
        Log.i("SENSOR_STEP", "onSensorChanged: Se detecto paso nro: " + contadorPasos++);

        //guardamos usuario en base de datos
        SQLiteHelper dbHelper = new SQLiteHelper(this.getContext());
        db = dbHelper.getWritableDatabase();
        Cursor c = db.rawQuery("Select * from Actividad WHERE Fecha =  ? AND UsuarioId = usuarioID",
                new String[]{String.valueOf(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()))});

        //si habia una actividad asociada a este usuario en el dia de hoy
        if (db != null && c.getCount() != 0) {
            ContentValues cv = new ContentValues();
            cv.put("CantidadPasos", contadorPasos++);

            db.update("Actividad",
                    cv,
                    "Fecha = ? AND UsuarioId = ?",
                    new String[]{String.valueOf(Calendar.getInstance().getTime()),
                            String.valueOf(usuarioID)});
            actividades.get(actividades.size()-1).setCantidadPasos(contadorPasos);

         } else if (c.getCount() == 0) {
            db.execSQL("INSERT INTO Actividad (Fecha,CantidadPasos, UsuarioId) VALUES ('" +
                    new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()) +
                    "', 1," + usuarioID + ")");
            actividades.add(new Actividad(Integer.parseInt(c.getString(c.getColumnIndex("Id"))),
                    Integer.parseInt(c.getString(c.getColumnIndex("CantidadPasos"))
                    ), new Date(), new Usuario()));
        }
        ArrayAdapter<Actividad> arrayAdapter = new ArrayAdapter<Actividad>(getActivity(), android.R.layout.simple_list_item_1, actividades);
        list.setAdapter(arrayAdapter);
    }


    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}