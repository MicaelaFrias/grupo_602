package com.example.midiendodistanciasmobile.Service;

import android.app.Service;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;

public class SensorBackgroundService extends Service implements SensorEventListener {
    @Nullable

    /*
            startService( new Intent(LoginActivity.this, SensorBackgroundService.class));
            return;

            stopService( new Intent(LoginActivity.this, SensorBackgroundService.class));
            return;
    */

    private SensorManager mSensorManager;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);

        Sensor sSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        mSensorManager.registerListener(this, sSensor, SensorManager.SENSOR_DELAY_NORMAL);

        Log.i("::PRUEBA SERVICES::", "onStartCommand: HOLAAAA");
        return START_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy(){
        Log.i("::PRUEBA SERVICES::", "onDestroy: Destroy");

        super.onDestroy();
    }

    @Override
    public void onSensorChanged(SensorEvent event) {

        Sensor sensor = event.sensor;
        float[] values = event.values;

        float ejeX=-3, ejeY=-3;

        if (values.length > 0) {
            ejeX = (int) values[0];
            ejeY = (int) values[1];
        }


        if (sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            Log.i("::Acelerometro::", "EjeX::" + ejeX + "   ejeY::" + ejeY);
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }



}
