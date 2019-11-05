package com.example.searchparty.ui.Outcome;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

public class OutcomeViewModel extends ViewModel {
    
    private MutableLiveData<String> mText;
    
    public OutcomeViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is Outcome fragment");
    }
    
    public LiveData<String> getText() {
        return mText;
    }
}