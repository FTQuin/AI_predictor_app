package com.example.searchparty.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.searchparty.DatabaseInterfaces.DatabaseInterface;
import com.example.searchparty.DatabaseInterfaces.WebScraper;
import com.example.searchparty.Models.Game;
import com.example.searchparty.Models.Prediction;
import com.example.searchparty.Models.Team;
import com.example.searchparty.R;

import java.util.Date;
import java.util.Random;

public class HomeFragment extends Fragment {
    
    private HomeViewModel homeViewModel;
    
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        //initialize crucial variables
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        
        //text view live data observer example
        final TextView textView = root.findViewById(R.id.text_home);
        homeViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        
        //test button
        final Button testBtn1 = root.findViewById(R.id.homeTestBtn1);
        final Button testBtn2 = root.findViewById(R.id.homeTestBtn2);
        
        //circular progress bar test
//        final ProgressBar circleProgBar = root.findViewById(R.id.homeTestProgBar);
        final TextView progTxt = root.findViewById(R.id.homeTestProgTxt);
//        circleProgBar.setProgress(10);
        
        //test databaseInterface
        final DatabaseInterface dbi = new DatabaseInterface(this.getContext());
        
        testBtn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //add to progress bar
//                Integer newProg = (circleProgBar.getProgress()+10)%100;
//                circleProgBar.setProgress(newProg);
//                progTxt.setText(newProg.toString()+"%");
            
                // test database
                String modifier = Long.toHexString(new Date().getTime());
                modifier = modifier.substring(modifier.length()-1);
                final Team ATeam = new Team("Away Team " + modifier.toUpperCase());
                final Team BTeam = new Team("Home Team " + modifier.toUpperCase());
                final Game game = new Game(BTeam, ATeam);
                final Prediction prediction = new Prediction(game);
                
                if("abcdef".contains(modifier)) {
                    game.setStartTime(game.getStartTime().getTime()+(1000*60*60*24*(new Random().nextInt(31))));
                }
                
                dbi.addGame(game);
                dbi.addTeam(ATeam);
                dbi.addTeam(BTeam);
                dbi.addPrediction(prediction);
                
                dbi.loadDataFromDB();
                
            }
        });
        testBtn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WebScraper ws = new WebScraper();
                ws.scrape();
                // test database
//                Cursor data = dbi.getData();
//                data.moveToNext();
//                progTxt.setText(data.getString(1));
            }
        });
        
        return root;
    }
}