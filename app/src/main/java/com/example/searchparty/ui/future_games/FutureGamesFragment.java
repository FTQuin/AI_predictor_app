package com.example.searchparty.ui.future_games;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.support.annotation.Nullable;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;

import com.example.searchparty.R;

public class FutureGamesFragment extends Fragment {
    
    private FutureGamesViewModel futureGamesViewModel;
    
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        futureGamesViewModel =
                ViewModelProviders.of(this).get(FutureGamesViewModel.class);
        View root = inflater.inflate(R.layout.fragment_future_games, container, false);
        final TextView textView = root.findViewById(R.id.text_future_games);
        futureGamesViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }
}