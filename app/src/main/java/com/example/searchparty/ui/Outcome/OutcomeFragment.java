package com.example.searchparty.ui.Outcome;

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

public class OutcomeFragment extends Fragment {
    
    private OutcomeViewModel outcomeViewModel;
    
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        outcomeViewModel =
                ViewModelProviders.of(this).get(OutcomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_outcome, container, false);
        final TextView textView = root.findViewById(R.id.text_outcome);
        outcomeViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }
}