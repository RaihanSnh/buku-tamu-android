package com.example.bukutamu.ui.listtamu;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ListTamuViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public ListTamuViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is list tamu fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}
