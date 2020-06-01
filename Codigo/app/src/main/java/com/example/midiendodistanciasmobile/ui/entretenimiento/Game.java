package com.example.midiendodistanciasmobile.ui.entretenimiento;

import android.content.Context;
import android.graphics.*;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.Log;
import android.view.*;

public class Game extends View implements SensorEventListener {

    Paint pincel = new Paint();
    int height, width;
    int size=40;
    int border=12;
    float ejeX=0, ejeY=0, ejeZ=0;
    String X,Y,Z;
    SensorManager mSensorManager;
    public Game(Context context) {
        super(context);

        mSensorManager = (SensorManager) getContext().getSystemService(Context.SENSOR_SERVICE);
        Sensor sSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        mSensorManager.registerListener(this, sSensor, SensorManager.SENSOR_DELAY_FASTEST);

        Display viewport = ((WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
        width = viewport.getWidth();
        height = viewport.getHeight();

        Log.i("GAME", "Game: TAMAÑO: " + width + " -- " + height);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {

        ejeX -= event.values[0];
        Log.i("EJEX", "onSensorChanged: " + ejeX);
        X = Float.toString(ejeX);
        if (ejeX < (size+border)) {
            ejeX = (size+border);
        } else if (ejeX > (width - (size+border))){
            ejeX = width - (size+border);
        }

        ejeY += event.values[1];
        Y = Float.toString(ejeY);
        if (ejeY < (size+border)) {
            ejeY = (size+border);
        } else if (ejeY > (height - size-170)){
            ejeY = height - size-170;
        }

        ejeZ = event.values[2];
        Z = String.format("%.2f", ejeZ);
        invalidate();
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    @Override
    protected void onDraw(Canvas canvas) {
        pincel.setColor(Color.RED);
        canvas.drawCircle(ejeX, ejeY, ejeZ + size, pincel);
        pincel.setColor(Color.WHITE);
        pincel.setTextSize(25);
        canvas.drawText("Donky", ejeX-35, ejeY+3, pincel);
    }

    public void stopSensor() {
        mSensorManager.unregisterListener(this, mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER));
    }
}
