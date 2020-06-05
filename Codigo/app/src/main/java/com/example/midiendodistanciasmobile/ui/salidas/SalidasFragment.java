package com.example.midiendodistanciasmobile.ui.salidas;

import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.midiendodistanciasmobile.MainActivity;
import com.example.midiendodistanciasmobile.Models.Salida;
import com.example.midiendodistanciasmobile.R;
import com.example.midiendodistanciasmobile.WebService.RegistroEvento;

import java.util.ArrayList;
import java.util.Date;

public class SalidasFragment extends Fragment {

    private SalidasViewModel salidasViewModel;
    private GPSTracker location;
    private String email;
    private String token;

    ArrayAdapter<String> arrayAdapter;
    ArrayList<String> salidas;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        salidasViewModel =
                ViewModelProviders.of(this).get(SalidasViewModel.class);
        View root = inflater.inflate(R.layout.fragment_salidas, container, false);
        final TextView textView = root.findViewById(R.id.navigation_salidas);
        final Button buttonStart = root.findViewById(R.id.startButton);

        preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        editor = preferences.edit();

        token = preferences.getString("tokenCurrentUser", "");
        salidas = setSalidas();

        email = ((MainActivity)getActivity()).UserEmail;

        final ListView list = root.findViewById(R.id.listSalidas);
        arrayAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, salidas);
        list.setAdapter(arrayAdapter);

        buttonStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (location == null){
                    location = new GPSTracker(getContext());
                    if (!location.isGPSEnabled)
                        return;

                    buttonStart.setText("Detener");
                    textView.setText("La salida ha comenzado, se notificará si supera los límites establecidos. (" + location.getLimite() + "mts)");
                } else {
                    location.stopGps();

                    if (token != ""){
                        RegistroEvento evento = new RegistroEvento(token, "Salida", "ACTIVO", "Usuario " + email + " ha realizado un recorrido." );
                        evento.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                    } else {
                        Log.e("ERROR", "onClick: ERROR DE TOKEN." );
                    }


                    salidas.add(new Salida(new Date(), location.getDistMax()).toString());
                    arrayAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, salidas);
                    list.setAdapter(arrayAdapter);
                    buttonStart.setText("Comenzar");
                    textView.setText("");
                    location = null;
                }
            }
        });

        return root;
    }


    @Override
    public void onPause() {
        int cantidad = salidas.size();
        int cantPrevia = preferences.getInt("cantRegSalidas", 0);
        editor.putInt("cantRegSalidas",cantidad).apply();

        for(int i = cantPrevia; i < cantidad; i++){
            editor.putString("numRegSalida_" + i, salidas.get(i));
            editor.commit();
            Log.i("DATO GUARDADO", "onPause: " + salidas.get(i) + " ---" + preferences.getString("numRegSalida_" + i,""));
        }

        super.onPause();
    }

    private ArrayList<String> setSalidas() {

        int cantidad = preferences.getInt("cantRegSalidas", 0);
        ArrayList<String> salidas = new ArrayList<String>();
        if (cantidad != 0){

            Log.i("SPREFERENCE", "Registros");
            for(int i = 0; i < cantidad; i++){
                String registro = preferences.getString("numRegSalida_" + i, "");
                if (registro != ""){
                    salidas.add(registro);
                    Log.i("SPREFERENCE", "Registro detectado: " + registro);
                }
            }
        }
        return salidas;
    }


}