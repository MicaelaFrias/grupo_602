package com.example.midiendodistanciasmobile;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.midiendodistanciasmobile.Constants.Constants;
import com.example.midiendodistanciasmobile.WebService.AsyncResponse;
import com.example.midiendodistanciasmobile.WebService.PeticionAPIRest;
import com.google.android.material.textfield.TextInputEditText;

public class RegisterActivity extends AppCompatActivity {

    Button registerButton;

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

                    PeticionAPIRest registro =  new PeticionAPIRest(valueName, valueLastname, valueDni, valueEmail, valuePassword, Constants.URI_REGISTER,
                        new AsyncResponse() {
                            @Override
                            public void processFinish(String status, String env, String token) {

                                if (status == Constants.STATE_ERROR) {
                                    Log.e("ERROR", "processFinish: Error en petición rest API." );
                                    return;
                                }

                                Log.i("Response", "State: " + status);
                                Log.i("Response", "Env: " + env);

                                if (env == Constants.ENV_DEV )  {
                                    Log.i("Response", "Token: " + token);
                                }

                                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                                startActivity(intent);
                            }
                    });

                   registro.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);

                }catch (Exception e) {
                    Log.e("Register Error", "onClick: Error" + e.getMessage(),e);
                }
            }
        });
    }

    public void showToolbar(String title, boolean upButton ){
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(title);
        getSupportActionBar().setDisplayHomeAsUpEnabled(upButton);
    }

}
