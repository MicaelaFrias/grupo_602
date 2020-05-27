package com.example.midiendodistanciasmobile;

import android.content.Context;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.util.Log;
import android.widget.Button;
import android.view.View;

import com.example.midiendodistanciasmobile.Utilities.Constants;
import com.example.midiendodistanciasmobile.WebService.AsyncResponse;
import com.example.midiendodistanciasmobile.WebService.PeticionAPIRest;
import com.example.midiendodistanciasmobile.WebService.RegistroEvento;
import com.google.android.material.textfield.TextInputEditText;

import java.net.InetAddress;

public class LoginActivity  extends AppCompatActivity {
    Button loginButton;
    Button registrateButton;

    TextInputEditText name;
    TextInputEditText lastname;
    TextInputEditText dni;
    TextInputEditText email;
    TextInputEditText password;

    String valueName;
    String valueLastname;
    Integer valueDni;
    String valueEmail;
    String valuePassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        name = findViewById(R.id.name);
        lastname = findViewById(R.id.lastname);
        dni = findViewById(R.id.dni);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);

        //Navegación a mainActivity
        loginButton = findViewById(R.id.loginButton);
        loginButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                boolean isInternetAvailable = isInternetAvailable();
                Log.i("INTERNET", "onClick INTERNET: " + isInternetAvailable);

                if (!isInternetAvailable) {
                    /*MOSTRAR MENSAJE DE ERROR DE CONEXION*/
                    return;
                }

                try {

                    if ( name.getText().toString().length() == 0 || lastname.getText().toString().length() == 0 ||
                            dni.getText().toString().length() == 0 || email.getText().toString().length() == 0 ||
                            password.getText().toString().length() == 0){
                        Log.i("Inputs Error", "onClick: Error en valores");
                        return;
                    }

                    if ( password.getText().toString().length() < 8){
                        Log.i("Password Error", "onClick: Password Menor a 8 caracteres");
                        return;
                    }

                    valueName = name.getText().toString();
                    valueLastname =  lastname.getText().toString();
                    valueDni =  Integer.parseInt(dni.getText().toString());
                    valueEmail =  email.getText().toString();
                    valuePassword = password.getText().toString();

                    PeticionAPIRest login =  new PeticionAPIRest(valueName, valueLastname, valueDni, valueEmail, valuePassword, Constants.URI_LOGIN,
                            new AsyncResponse() {
                                @Override
                                public void processFinish(String status, String env, String token) {

                                    if (status == Constants.STATE_ERROR) {
                                        Log.e("ERROR", "processFinish: Error en petición rest API." );
                                        return;
                                    }
                                    Log.i("Response", "Response de petición:");
                                    Log.i("Response", "State: " + status);
                                    Log.i("Response", "Env: " + env);
                                    Log.i("Response", "Token: " + token);

                                    RegistroEvento evento = new RegistroEvento(token, "Login de usuario", "ACTIVO", "Login de usuario: " + valueEmail);
                                    evento.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);

                                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                    startActivity(intent);
                                }
                            });

                    login.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);

                }catch (Exception e) {
                    Log.e("ERROR", "onClick ERROR: " + e.getMessage(), e );
                }
            }
        });

        //Navegación a registerActivity
        registrateButton = findViewById(R.id.registrateButton);
        registrateButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);

            }
        });
    }

    private boolean isInternetAvailable() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        if ( cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnected() ){
            Log.i("INTERNET", "isInternetAvailable: there is network");
            try {
                final String command = "ping -c 1 google.com";
                return Runtime.getRuntime().exec(command).waitFor() == 0;

            } catch (Exception e) {
                Log.i("INTERNET", "error: " + e.getMessage());
            }
        }
        return false;
    }


}
