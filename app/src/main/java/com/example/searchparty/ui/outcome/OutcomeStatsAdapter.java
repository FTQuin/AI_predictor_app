package com.example.searchparty.ui.outcome;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.searchparty.Models.Game;
import com.example.searchparty.Models.Team;
import com.example.searchparty.R;

public class OutcomeStatsAdapter extends BaseAdapter {
    
    private final Context mContext;
    private final Game game;
    
    public OutcomeStatsAdapter(Context context, Game game) {
        super();
        this.mContext = context;
        this.game = game;
    }
    @Override
    public int getCount() {
        return game.getStatsMap().size();
    }
    
    @Override
    public Object getItem(int position) {
        return null;
    }
    
    @Override
    public long getItemId(int position) {
        return 0;
    }
    
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        
        if (convertView == null) {
            final LayoutInflater layoutInflater = LayoutInflater.from(mContext);
            convertView = layoutInflater.inflate(R.layout.fragment_outcome_stat_item, null);
        }
        
        TextView title = convertView.findViewById(R.id.stat_name);
        title.setText(game.getStatsMap().keySet().toArray(new String[game.getStatsMap().size()])[position].substring(1));
    
        TextView value = convertView.findViewById(R.id.stat_team);
        value.setText(game.getStatsMap().values().toArray(new Double[game.getStatsMap().size()])[position].toString());
        
        return convertView;
        
//        TextView dummyTextView = new TextView(mContext);
//        dummyTextView.setText(String.valueOf(position));
//        dummyTextView.setBackgroundColor(0xFFFFFF);
//        return dummyTextView;
    }
}
