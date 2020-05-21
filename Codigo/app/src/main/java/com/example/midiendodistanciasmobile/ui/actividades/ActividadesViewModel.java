package com.example.midiendodistanciasmobile.ui.actividades;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ActividadesViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public ActividadesViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is actividades fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}