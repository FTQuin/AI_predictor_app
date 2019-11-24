package com.example.searchparty.ui.outcome;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
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

import org.w3c.dom.Text;

public class OutcomeFragment extends Fragment {
    
    private OutcomeViewModel outcomeViewModel;
    
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        outcomeViewModel =
                ViewModelProviders.of(this).get(OutcomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_outcome, container, false);
    
        GridView gridView = root.findViewById(R.id.gridview_outcome);;
    
        DatabaseInterface dbi = new DatabaseInterface(getContext());
    
        try {
            if (getArguments() != null) {
                String predID = OutcomeFragmentArgs.fromBundle(getArguments()).getPredID();
                if (!predID.equals("")) {
                    dbi.loadDataFromDB();
                    Prediction pred = dbi.getPrediction(predID);
                    Game game = pred.getGame();
                    OutcomeStatsAdapter statsAdapter = new OutcomeStatsAdapter(getContext(), game);
                    gridView.setAdapter(statsAdapter);
    
                    ProgressBar progCirc = root.findViewById(R.id.outTestProgBar2);
                    TextView progText = root.findViewById(R.id.outTestProgTxt2);
    
                    progCirc.setProgress(new Double(pred.getPredictedOutcome()).intValue());
                    progText.setText(new Double(pred.getPredictedOutcome()).intValue()+"%");
                    
                    Integer homePts = game.getStatsMap().get("HPTS").intValue();
                    Integer awayPts = game.getStatsMap().get("APTS").intValue();
                    
                    TextView homeName = root.findViewById(R.id.out_home_name);
                    TextView awayName = root.findViewById(R.id.out_away_name);
    
                    TextView winnerName = root.findViewById(R.id.out_winner);
                    winnerName.setText(game.getStatsMap().get("HPTS") > game.getStatsMap().get("APTS")?game.getHomeTeam().getName():game.getAwayTeam().getName());
                    
                    homeName.setText(pred.getGame().getHomeTeam().getName());
                    awayName.setText(pred.getGame().getAwayTeam().getName());
    
                    TextView pts = root.findViewById(R.id.out_pts);
                    pts.setText((homePts>awayPts?homePts:awayPts).toString()+" - "+(homePts>awayPts?awayPts:homePts).toString());
                }
            }
        } catch (Exception e){
            e.printStackTrace();
        }
        
        return root;
    }
}