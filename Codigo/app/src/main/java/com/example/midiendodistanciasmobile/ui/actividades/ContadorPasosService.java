package com.example.midiendodistanciasmobile.ui.actividades;

import android.app.Service;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.os.IBinder;

import androidx.annotation.Nullable;

public class ContadorPasosService extends Service implements SensorEventListener {

    //SQLiteHelper dbHelper = new SQLiteHelper(getActivity());
    //SQLiteDatabase db = dbHelper.getWritableDatabase();
    //SensorManager mSensorManager;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        return super.onStartCommand(intent, flags, startId);

    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onSensorChanged(SensorEvent event) {

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
