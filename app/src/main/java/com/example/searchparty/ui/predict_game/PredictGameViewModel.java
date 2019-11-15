package com.example.searchparty.ui.predict_game;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class PredictGameViewModel extends ViewModel {
    
    private MutableLiveData<String> mText;
    
    public PredictGameViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is predict game fragment");
    }
    
    public LiveData<String> getText() {
        return mText;
    }
}