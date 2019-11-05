package com.example.searchparty.ui.send;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

public class FutureGamesViewModel extends ViewModel {
    
    private MutableLiveData<String> mText;
    
    public FutureGamesViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is Future Games fragment");
    }
    
    public LiveData<String> getText() {
        return mText;
    }
}