package com.example.bukutamu.ui.tamu;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class TamuViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public TamuViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is tambah tamu fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}