package com.example.midiendodistanciasmobile.ui.entretenimiento;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.midiendodistanciasmobile.LoginActivity;
import com.example.midiendodistanciasmobile.RegisterActivity;


public class MainGame extends AppCompatActivity {

    Game entretenimiento;
//entretenimiento crea esta activity, q usa la view Game.
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        entretenimiento = new Game(this);
        //este metodo setea un layout generalmente pero tenemos una vista
        setContentView(entretenimiento);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }

    @Override
    protected void onPause() {
        super.onPause();
        entretenimiento.stopSensor();
    }

    @Override
    protected void onStop() {
        super.onStop();
        entretenimiento.stopSensor();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        entretenimiento.stopSensor();
    }
}
