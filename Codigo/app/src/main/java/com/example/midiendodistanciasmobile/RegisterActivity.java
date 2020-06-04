package com.example.midiendodistanciasmobile;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.midiendodistanciasmobile.Helpers.SQLiteHelper;
import com.example.midiendodistanciasmobile.Models.Actividad;
import com.example.midiendodistanciasmobile.Utilities.AlertDialog;
import com.example.midiendodistanciasmobile.Utilities.Constants;
import com.example.midiendodistanciasmobile.Utilities.Internet;
import com.example.midiendodistanciasmobile.WebService.AsyncResponse;
import com.example.midiendodistanciasmobile.WebService.PeticionAPIRest;
import com.example.midiendodistanciasmobile.WebService.RegistroEvento;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;

public class RegisterActivity extends AppCompatActivity {

    Button registerButton;
    private SQLiteDatabase db;
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
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        showToolbar(getResources().getString(R.string.create_account), true);

        registerButton = findViewById(R.id.registerButton);
        name = findViewById(R.id.name);
        lastname = findViewById(R.id.lastname);
        dni = findViewById(R.id.dni);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);

        registerButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                if (!validateData()) {
                    return;
                }

                valueName = name.getText().toString();
                valueLastname = lastname.getText().toString();
                valueDni = Integer.parseInt(dni.getText().toString());
                valueEmail = email.getText().toString();
                valuePassword = password.getText().toString();

                try {

                    PeticionAPIRest registro = new PeticionAPIRest(valueName, valueLastname, valueDni, valueEmail, valuePassword, Constants.URI_REGISTER,
                            new AsyncResponse() {
                                @Override
                                public void processFinish(String status, String env, String token) {

                                    if (status == Constants.STATE_ERROR) {
                                        AlertDialog.displayAlertDialog(RegisterActivity.this, "Algo ha ocurrido.",
                                                "No se pudo completar el registro. Verifique que los datos sean correctos.", "OK");
                                        Log.e("ERROR", "processFinish: Error en petición rest API.");
                                        return;
                                    }
                                    Log.i("Response", "Response de petición:");
                                    Log.i("Response", "State: " + status);
                                    Log.i("Response", "Env: " + env);
                                    Log.i("Response", "Token: " + token);

                                    RegistroEvento evento = new RegistroEvento(token, "Registro de usuario", "ACTIVO", "Registro de usuario.");
                                    evento.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);

                                    //guardamos usuario en base de datos
                                    SQLiteHelper dbHelper = new SQLiteHelper(RegisterActivity.this);
                                    db = dbHelper.getWritableDatabase();

                                    if (db != null) {

                                        db.execSQL("INSERT INTO Usuario (Email) VALUES (valueEmail)");
                                    }
                                    Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                                    startActivity(intent);
                                }
                            });

                    registro.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);

                } catch (Exception e) {
                    Log.e("Register Error", "onClick: Error" + e.getMessage(), e);
                }
            }
        });
    }

    public void showToolbar(String title, boolean upButton) {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(title);
        getSupportActionBar().setDisplayHomeAsUpEnabled(upButton);
        getSupportActionBar().setDisplayShowHomeEnabled(upButton);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);

            }
        });
    }

    public boolean validateData() {

        boolean internetConnection = Internet.isInternetAvailable(RegisterActivity.this);

        if (!internetConnection) {
            AlertDialog.displayAlertDialog(RegisterActivity.this, "Error de conexión",
                    "Verifique su conexión a internet.", "OK");

            Log.i("Internet", "internet error");
            return false;
        }

        if (name.getText().toString().length() == 0 || lastname.getText().toString().length() == 0 ||
                dni.getText().toString().length() == 0 || email.getText().toString().length() == 0 ||
                password.getText().toString().length() == 0) {

            AlertDialog.displayAlertDialog(RegisterActivity.this, "Datos incompletos",
                    "Todos los datos son requeridos, verifique de completar todos.", "OK");

            Log.i("Inputs Error", "onClick: Error en valores");
            return false;
        }

        if (password.getText().toString().length() < 8) {
            AlertDialog.displayAlertDialog(RegisterActivity.this, "Contraseña debil",
                    "La contraseña debe tener al menos 8 caracteres.", "OK");

            Log.i("Password Error", "onClick: Password Menor a 8 caracteres");
            return false;
        }

        return true;
    }

}
