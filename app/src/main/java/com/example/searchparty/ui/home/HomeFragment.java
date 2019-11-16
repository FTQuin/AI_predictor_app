package com.example.searchparty.ui.home;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.searchparty.DatabaseInterfaces.DatabaseInterface;
import com.example.searchparty.DatabaseInterfaces.WebScraper;
import com.example.searchparty.Models.Game;
import com.example.searchparty.Models.Prediction;
import com.example.searchparty.Models.Team;
import com.example.searchparty.R;
import com.example.searchparty.ui.future_games.FutureGamesFragment;
import com.example.searchparty.ui.future_games.MyFutureGameRecyclerViewAdapter;

import java.util.Date;
import java.util.List;
import java.util.Random;

public class HomeFragment extends Fragment {
    
    private HomeViewModel homeViewModel;
    private DatabaseInterface dbi;
    
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        //initialize crucial variables
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        
        //text view live data observer example
        final TextView tvNextGame = root.findViewById(R.id.tv_home_next_game);
//        homeViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
//            @Override
//            public void onChanged(@Nullable String s) {
//                tvNextGame.setText(s);
//            }
//        });
        
        dbi = new DatabaseInterface(getContext());
    
        List<Game> games = dbi.getGames();
        
        if(games.size() > 0) {
            Game nextGame = games.get(0);
            tvNextGame.setText(nextGame.getHomeTeam().getName() + " VS " + nextGame.getAwayTeam().getName());
            final ProgressBar circleProgBar = root.findViewById(R.id.homeTestProgBar);
            final TextView progTxt = root.findViewById(R.id.homeTestProgTxt);
            Integer nextGamePred = Double.valueOf(nextGame.getPrediction().getPredictedOutcome()).intValue();
            circleProgBar.setProgress(nextGamePred);
            progTxt.setText(nextGamePred.toString() + "%");
            
            RecyclerView recyclerView = root.findViewById(R.id.home_future_games_list);
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            recyclerView.setAdapter(new MyFutureGameRecyclerViewAdapter(games, new FutureGamesFragment.OnListFragmentInteractionListener() {
                @Override
                public void onListFragmentInteraction(Game item) {
            
                }
            }));
        }
        else
            tvNextGame.setText("Welcome");
        
        return root;
    }
}