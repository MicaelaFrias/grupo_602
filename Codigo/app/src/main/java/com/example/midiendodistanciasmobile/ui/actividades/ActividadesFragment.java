package com.example.midiendodistanciasmobile.ui.actividades;

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
import com.example.midiendodistanciasmobile.Models.Actividad;
import com.example.midiendodistanciasmobile.Models.Usuario;
import com.example.midiendodistanciasmobile.R;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static androidx.core.content.ContextCompat.getSystemService;

public class ActividadesFragment extends Fragment implements SensorEventListener {

    private ActividadesViewModel actividadesViewModel;
    private SQLiteDatabase db;
    public int contadorPasos = 0;
    private SensorManager mSensorManager;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        actividadesViewModel =
                ViewModelProviders.of(this).get(ActividadesViewModel.class);
        View root = inflater.inflate(R.layout.fragment_actividades, container, false);

        SQLiteHelper dbHelper = new SQLiteHelper(getActivity());
        db = dbHelper.getWritableDatabase();
        ArrayList<Actividad> actividades = new ArrayList<Actividad>();

        initSensor();

        if (db != null) {
            //inserto valores de prueba para actividades (proximamente se insertaria al hacer efectivamente una actividad)
           /* db.execSQL("INSERT INTO Usuario (Id) VALUES (1)");
            db.execSQL("INSERT INTO Actividad (CantidadPasos, UsuarioId) VALUES (200,1)");*/
            actividades = GetActividades(db);
        }

        final ListView list = root.findViewById(R.id.list);
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
            Cursor c = db.rawQuery("Select * from Actividad ", null);
            if (c != null) {
                c.moveToFirst();
                do {
                    //Asignamos el valor en nuestras variables para usarlos en lo que necesitemos
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
    }


    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}