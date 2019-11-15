package com.example.searchparty.ui.outcome;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

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