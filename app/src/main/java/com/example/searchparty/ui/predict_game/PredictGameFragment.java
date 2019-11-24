package com.example.searchparty.ui.predict_game;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

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

import java.util.Map;
import java.util.Random;

public class PredictGameFragment extends Fragment {
    
    private PredictGameViewModel predictGameViewModel;
    DatabaseInterface dbi;
    
    Game game;
    
    private ProgressBar pbCircle;
    private TextView tvPercent;
    private Double FG = 0.5, threePT = 0.5, fouls = 0.5;
    
    int percent;
    int moreFG = new Random().nextDouble()<.5?1:-1;
    int more3pt = new Random().nextDouble()<.5?1:-1;
    int moreFouls = new Random().nextDouble()<.5?1:-1;
    
    
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        predictGameViewModel =
                ViewModelProviders.of(this).get(PredictGameViewModel.class);
        View root = inflater.inflate(R.layout.fragment_predict_game, container, false);
        final TextView tvHomeTeam = root.findViewById(R.id.TV_Predict_Game_Team1);
        final TextView tvAwayTeam = root.findViewById(R.id.TV_Predict_Game_Team2);
        pbCircle = root.findViewById(R.id.Pb_Predict_Game_Circle);
        tvPercent = root.findViewById(R.id.Tv_Predict_Game_Prog_Percent);
        game = null;
        
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
                    pred.setPredictedOutcome(percent);
                    pred.setGame(savedGame);
                    savedGame.setPrediction(pred);
                    dbi.addPrediction(savedGame.getPrediction());
                    dbi.addGame(savedGame);
                    Toast.makeText(getContext(),"Saved Prediction",Toast. LENGTH_LONG).show();
                }
            });
    
            SeekBar sbFG = root.findViewById(R.id.SB_Predict_Game_Shot);
            SeekBar sb3pt = root.findViewById(R.id.SB_Predict_Game_3Point);
            SeekBar sbFouls = root.findViewById(R.id.SB_Predict_Game_FoulsGame);
            
            sbFG.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                    FG = progress/100.0;
                    updatePrediction();
                }
    
                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {
        
                }
    
                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {
        
                }
            });
            sb3pt.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                    threePT = progress/100.0;
                    updatePrediction();
                }
    
                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {
        
                }
    
                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {
        
                }
            });
            sbFouls.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                    fouls = progress/100.0;
                    updatePrediction();
                }
    
                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {
        
                }
    
                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {
        
                }
            });
        }
        
        updatePrediction();
        
        
        
        return root;
    }
    
    public void updatePrediction(){
        
        double calculatedPred = (moreFG*FG + more3pt*threePT + moreFouls*fouls + 3)/6 * 100;
    
        percent = new Double(calculatedPred).intValue();
        
        tvPercent.setText(Integer.toString(new Double(calculatedPred).intValue()));
        pbCircle.setProgress(new Double(calculatedPred).intValue());
    }
    
}