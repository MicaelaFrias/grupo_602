package com.example.midiendodistanciasmobile;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Parcelable;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.Button;
import android.view.View;

import com.example.midiendodistanciasmobile.Helpers.SQLiteHelper;
import com.example.midiendodistanciasmobile.Utilities.AlertDialog;
import com.example.midiendodistanciasmobile.Utilities.Constants;
import com.example.midiendodistanciasmobile.Utilities.Internet;
import com.example.midiendodistanciasmobile.WebService.AsyncResponse;
import com.example.midiendodistanciasmobile.WebService.PeticionAPIRest;
import com.example.midiendodistanciasmobile.WebService.RegistroEvento;
import com.google.android.material.textfield.TextInputEditText;

import java.net.InetAddress;

public class LoginActivity  extends AppCompatActivity {

    SharedPreferences preferences;
    SharedPreferences.Editor editor;

    Button loginButton;
    Button registrateButton;

    TextInputEditText name;
    TextInputEditText lastname;
    TextInputEditText dni;
    TextInputEditText email;
    TextInputEditText password;
    private SQLiteDatabase db;

    String valueName;
    String valueLastname;
    Integer valueDni;
    String valueEmail;
    String valuePassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        editor = preferences.edit();

        name = findViewById(R.id.name);
        lastname = findViewById(R.id.lastname);
        dni = findViewById(R.id.dni);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);

        //Navegación a mainActivity
        loginButton = findViewById(R.id.loginButton);
        loginButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                if (!validateData()) {
                    return;
                }

                valueName = name.getText().toString();
                valueLastname =  lastname.getText().toString();
                valueDni =  Integer.parseInt(dni.getText().toString());
                valueEmail =  email.getText().toString();
                valuePassword = password.getText().toString();

                try {

                    PeticionAPIRest login =  new PeticionAPIRest(valueName, valueLastname, valueDni, valueEmail, valuePassword, Constants.URI_LOGIN,
                            new AsyncResponse() {
                                @Override
                                public void processFinish(String status, String env, String token) {

                                    if (status == Constants.STATE_ERROR) {
                                        AlertDialog.displayAlertDialog(LoginActivity.this, "Error en Inicio de sesión.",
                                                "No se pudo iniciar sesión. Verifique que los datos sean correctos.", "OK");
                                        Log.e("ERROR", "processFinish: Error en petición rest API." );
                                        return;
                                    }
                                    Log.i("Response", "Response de petición:");
                                    Log.i("Response", "State: " + status);
                                    Log.i("Response", "Env: " + env);
                                    Log.i("Response", "Token: " + token);

                                    //guardamos usuario en base de datos
                                    SQLiteHelper dbHelper = new SQLiteHelper(LoginActivity.this);
                                    db = dbHelper.getWritableDatabase();
                                    Cursor c = db.rawQuery("Select * from Usuario WHERE Email =  ?", new String[]{valueEmail});

                                    if (db != null && c.getCount()==0) {
                                        db.execSQL("INSERT INTO Usuario (Email, Nombre, Apellido) VALUES ('" + valueEmail + "', '" + valueName+ "', '" + valueLastname + "' )");
                                    }

                                    RegistroEvento evento = new RegistroEvento(token, "Login de usuario", "ACTIVO", "Login de usuario: " + valueEmail);
                                    evento.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);

                                    editor.remove("tokenCurrentUser");
                                    editor.putString("tokenCurrentUser", token);

                                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                    intent.putExtra("UserEmail", valueEmail);
                                    intent.putExtra("UserName", valueLastname + ", " + valueName);
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

    public boolean validateData() {

        boolean internetConnection = Internet.isInternetAvailable(LoginActivity.this);

        if (!internetConnection) {
            AlertDialog.displayAlertDialog(LoginActivity.this, "Error de conexión",
                    "Verifique su conexión a internet.", "OK");

            Log.i("Internet", "internet error");
            return false;
        }

        if ( name.getText().toString().length() == 0 || lastname.getText().toString().length() == 0 ||
                dni.getText().toString().length() == 0 || email.getText().toString().length() == 0 ||
                password.getText().toString().length() == 0){

            AlertDialog.displayAlertDialog(LoginActivity.this, "Datos incompletos",
                    "Todos los datos son requeridos, verifique de completar todos.", "OK");

            Log.i("Inputs Error", "onClick: Error en valores");
            return false;
        }

        if ( password.getText().toString().length() < 8){
            AlertDialog.displayAlertDialog(LoginActivity.this, "Datos incorrectos",
                    "Verifique que los datos sean correctos.", "OK");

            Log.i("Password Error", "onClick: Password Menor a 8 caracteres");
            return false;
        }

        return true;
    }


}
