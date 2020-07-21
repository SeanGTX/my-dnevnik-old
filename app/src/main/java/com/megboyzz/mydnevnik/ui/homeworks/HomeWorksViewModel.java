package com.megboyzz.mydnevnik.ui.homeworks;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class HomeWorksViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public HomeWorksViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue(" ");
    }

    public LiveData<String> getText() {
        return mText;
    }
}