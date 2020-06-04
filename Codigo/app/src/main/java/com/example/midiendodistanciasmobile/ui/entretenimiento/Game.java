package com.example.midiendodistanciasmobile.ui.entretenimiento;

import android.app.Activity;
import android.content.Context;
import android.graphics.*;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.Log;
import android.view.*;

import com.example.midiendodistanciasmobile.Utilities.AlertDialog;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class Game extends View implements SensorEventListener {

    Paint pincel = new Paint();
    int height, width;
    int size=40;
    int sizeComida=5;
    int border=12;
    float ejeX=0, ejeY=0, ejeZ=0;
    float ejeXComida=0, ejeYComida=0;
    int puntaje = 0;
    int timer = 30;

    Context mContext;
    String X,Y,Z;
    SensorManager mSensorManager;

    public Game(Context context) {
        super(context);
        mContext = context;
        mSensorManager = (SensorManager) getContext().getSystemService(Context.SENSOR_SERVICE);
        Sensor sSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        mSensorManager.registerListener(this, sSensor, SensorManager.SENSOR_DELAY_FASTEST);

        Display viewport = ((WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
        width = viewport.getWidth();
        height = viewport.getHeight();
        setRandomPosition();

        Timer time = new Timer();
        time.schedule(new TimerGame(), 0, 1000);
        Log.i("GAME", "Game: viewport: " + width + " -- " + height);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {

        if (timer == 0){
            this.stopSensor();
            AlertDialog.displayAlertDialogGame((Activity) mContext,"Fin del juego", "Donky morfo lo suficiente? Su aumento de calorias fue: " + puntaje, "Ok");
        }

        ejeX -= event.values[0];
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

        if ( ejeXComida <= (ejeX+(size/2)) && ejeXComida>=(ejeX-(size/2)) &&
                ejeYComida <= (ejeY+(size/2)) && ejeYComida >= (ejeY-(size/2))
            ){
            setRandomPosition();
            puntaje++;
            size+=1;
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

        pincel.setColor(Color.BLUE);
        canvas.drawCircle(ejeXComida, ejeYComida, ejeZ + sizeComida, pincel);

        pincel.setColor(Color.RED);
        canvas.drawCircle(ejeX, ejeY, ejeZ + size, pincel);
        pincel.setColor(Color.WHITE);
        pincel.setTextSize(25);
        canvas.drawText("Donky", ejeX-35, ejeY+3, pincel);

        pincel.setColor(Color.BLACK);
        pincel.setTextSize(35);
        canvas.drawText("Calorias: " + puntaje, 30, height - 65, pincel);
        canvas.drawText("Tiempo: " + timer, width-200, height - 65, pincel);
    }

    public void stopSensor() {
        mSensorManager.unregisterListener(this, mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER));
    }

    public void setRandomPosition() {
        Random r = new Random();
        int min = 50;
        int maxWidth = width - min;
        int maxHeight = height - min - 170;

        ejeXComida = r.nextInt(maxWidth - min) + min;
        ejeYComida = r.nextInt(maxHeight - min) + min;
    }

    class TimerGame extends TimerTask {
        @Override
        public void run() {
            timer-=1;
        }
    }

}
