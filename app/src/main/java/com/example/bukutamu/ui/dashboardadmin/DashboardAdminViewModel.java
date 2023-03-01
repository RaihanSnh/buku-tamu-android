package com.example.bukutamu.ui.dashboardadmin;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class DashboardAdminViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public DashboardAdminViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is dashboard admin fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}