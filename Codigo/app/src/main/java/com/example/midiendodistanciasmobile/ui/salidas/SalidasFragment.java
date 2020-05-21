package com.example.midiendodistanciasmobile.ui.salidas;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.midiendodistanciasmobile.R;

public class SalidasFragment extends Fragment {

    private SalidasViewModel salidasViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        salidasViewModel =
                ViewModelProviders.of(this).get(SalidasViewModel.class);
        View root = inflater.inflate(R.layout.fragment_salidas, container, false);
        final TextView textView = root.findViewById(R.id.navigation_salidas);
        salidasViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }
}