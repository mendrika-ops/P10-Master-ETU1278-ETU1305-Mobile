package com.example.tongasoa.ui.preference;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class PreferenceViewModel extends ViewModel {
    private final MutableLiveData<String> mText;
    private final MutableLiveData<String> mText2;

    public PreferenceViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("this is preference");

        mText2 = new MutableLiveData<>();
        mText2.setValue("Tay be ty");
    }

    public LiveData<String> getText() {
        return mText;
    }

    public LiveData<String> getText2() {
        return mText2;
    }
}
