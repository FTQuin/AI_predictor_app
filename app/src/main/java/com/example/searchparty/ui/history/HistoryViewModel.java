package com.example.searchparty.ui.history;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

public class HistoryViewModel extends ViewModel {
    
    private MutableLiveData<String> mText;
    
    public HistoryViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is history fragment");
    }
    
    public LiveData<String> getText() {
        return mText;
    }
}