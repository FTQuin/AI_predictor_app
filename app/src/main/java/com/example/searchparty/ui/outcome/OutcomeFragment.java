package com.example.searchparty.ui.outcome;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.searchparty.Models.Game;
import com.example.searchparty.Models.Team;
import com.example.searchparty.R;

public class OutcomeFragment extends Fragment {
    
    private OutcomeViewModel outcomeViewModel;
    
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        outcomeViewModel =
                ViewModelProviders.of(this).get(OutcomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_outcome, container, false);
    
        GridView gridView = root.findViewById(R.id.gridview_outcome);
        OutcomeStatsAdapter statsAdapter = new OutcomeStatsAdapter(getContext(), new Game(new Team("HI"), new Team("BYE")));
        gridView.setAdapter(statsAdapter);
        
        return root;
    }
}