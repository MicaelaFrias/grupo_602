package com.example.midiendodistanciasmobile.ui.entretenimiento;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.midiendodistanciasmobile.LoginActivity;
import com.example.midiendodistanciasmobile.MainActivity;
import com.example.midiendodistanciasmobile.R;

public class Entretenimiento extends Fragment {

    private EntretenimientoViewModel entretenimientoViewModel;

    Button play;


    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        entretenimientoViewModel = ViewModelProviders.of(this).get(EntretenimientoViewModel.class);

        View root = inflater.inflate(R.layout.fragment_entretenimiento, container, false);

        play = root.findViewById(R.id.play);
        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent( getActivity(), MainGame.class);
                startActivity(intent);
            }
        });

        return root;
    }


}
