package com.example.searchparty.ui.home;

import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.support.annotation.Nullable;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;

import com.example.searchparty.DatabaseInterfaces.DatabaseInterface;
import com.example.searchparty.R;

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
        homeViewModel.getText().observe(this, new Observer<String>() {
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
                dbi.addData("400%");
                
            }
        });
        testBtn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            
                // test database
                Cursor data = dbi.getData();
                data.moveToNext();
                progTxt.setText(data.getString(1));
            }
        });
        
        return root;
    }
}