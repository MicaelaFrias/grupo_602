package com.example.midiendodistanciasmobile.ui.actividades;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.midiendodistanciasmobile.R;

import java.util.ArrayList;

public class ActividadesFragment extends Fragment {

    private ActividadesViewModel actividadesViewModel;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        actividadesViewModel =
                ViewModelProviders.of(this).get(ActividadesViewModel.class);
        View root = inflater.inflate(R.layout.fragment_actividades, container, false);

        final ListView list = root.findViewById(R.id.list);
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("JAVA");
        arrayList.add("ANDROID");
        arrayList.add("C Language");
        arrayList.add("CPP Language");
        arrayList.add("Go Language");
        arrayList.add("AVN SYSTEMS");
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1, arrayList);
        list.setAdapter(arrayAdapter);

        return root;
    }

}