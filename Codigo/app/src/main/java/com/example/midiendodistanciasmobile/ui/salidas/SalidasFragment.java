package com.example.midiendodistanciasmobile.ui.salidas;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.midiendodistanciasmobile.R;

public class SalidasFragment extends Fragment {

    private SalidasViewModel salidasViewModel;
    private GPSTracker location;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        salidasViewModel =
                ViewModelProviders.of(this).get(SalidasViewModel.class);
        View root = inflater.inflate(R.layout.fragment_salidas, container, false);
        final TextView textView = root.findViewById(R.id.navigation_salidas);
        final Button buttonStart = root.findViewById(R.id.startButton);

        buttonStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (location == null){
                    location = new GPSTracker(getContext());
                    if (!location.isGPSEnabled)
                        return;

                    buttonStart.setText("Detener");
                    textView.setText("La salida ha comenzado, se notificará si supera los límites establecidos.");
                } else {
                    location.stopGps();
                    buttonStart.setText("Comenzar");
                    textView.setText("");
                    location = null;
                    /*Registrar Salida*/
                }
            }
        });



        return root;
    }
}