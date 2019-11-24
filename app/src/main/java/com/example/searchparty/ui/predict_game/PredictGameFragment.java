package com.example.searchparty.ui.predict_game;

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

import com.example.searchparty.DatabaseInterfaces.DatabaseInterface;
import com.example.searchparty.Models.Game;
import com.example.searchparty.Models.Prediction;
import com.example.searchparty.Models.Team;
import com.example.searchparty.R;

public class PredictGameFragment extends Fragment {
    
    private PredictGameViewModel predictGameViewModel;
    DatabaseInterface dbi;
    
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        predictGameViewModel =
                ViewModelProviders.of(this).get(PredictGameViewModel.class);
        View root = inflater.inflate(R.layout.fragment_predict_game, container, false);
        final TextView tvHomeTeam = root.findViewById(R.id.TV_Predict_Game_Team1);
        final TextView tvAwayTeam = root.findViewById(R.id.TV_Predict_Game_Team2);
        final ProgressBar pbCircle = root.findViewById(R.id.Pb_Predict_Game_Circle);
        final TextView tvPercent = root.findViewById(R.id.Tv_Predict_Game_Prog_Percent);
        Game game = null;
        
        dbi = new DatabaseInterface(getContext());
//        predictGameViewModel.getText().observe(this, new Observer<String>() {
//            @Override
//            public void onChanged(@Nullable String s) {
//                textView.setText(s);
//            }
//        });
        try {
            if (getArguments() != null) {
                String futureGameID = PredictGameFragmentArgs.fromBundle(getArguments()).getFutureGameID();
                if (!futureGameID.equals("")) {
                    game = dbi.getGame(futureGameID);
                    tvHomeTeam.setText(game.getHomeTeam().getName());
                    tvAwayTeam.setText(game.getAwayTeam().getName());
                    
                    pbCircle.setProgress(((Double)game.getPrediction().getPredictedOutcome()).intValue());
                    tvPercent.setText(((Integer)(((Double)game.getPrediction().getPredictedOutcome()).intValue())).toString() + "%");
                }
            }
        } catch (Exception e){
            e.printStackTrace();
        }
        
        if(game != null) {
            final Game savedGame = game;
    
            Button btn_save_pred = root.findViewById(R.id.BTN_Predict_Game_GeneratePrediction);
            btn_save_pred.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Prediction pred = new Prediction();
                    pred.setGame(savedGame);
                    savedGame.setPrediction(pred);
                    dbi.addPrediction(savedGame.getPrediction());
                    dbi.addGame(savedGame);
                }
            });
        }
        
        return root;
    }
}