package com.example.midiendodistanciasmobile.ui.salidas;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class SalidasViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public SalidasViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is salidas fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}