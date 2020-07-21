package com.megboyzz.mydnevnik.ui.marks;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class MarksViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public MarksViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is home fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}