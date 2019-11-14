package com.example.searchparty.ui.future_games;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.searchparty.Models.Game;
import com.example.searchparty.Models.Team;
import com.example.searchparty.R;
import com.example.searchparty.ui.future_games.FutureGamesFragment.OnListFragmentInteractionListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

public class MyFutureGameRecyclerViewAdapter extends RecyclerView.Adapter<MyFutureGameRecyclerViewAdapter.ViewHolder> {
    
    private final List<Game> mValues;
    private final OnListFragmentInteractionListener mListener;
    
    public MyFutureGameRecyclerViewAdapter(List<Game> items, OnListFragmentInteractionListener listener) {
        items.sort(new Comparator<Game>() {
            @Override
            public int compare(Game o1, Game o2) {
                return ((Long)(o1.getStartTime().getTime() - o2.getStartTime().getTime())).intValue();
            }
        });
        //add a game that will be at the very top of the list as a buffer behind the tool bar
        items.add(0, new Game(new Team("Buffer 1"), new Team("Buffer 2")));
        
        mValues = items;
        mListener = listener;
    }
    
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_future_games_item, parent, false);
        return new ViewHolder(view);
    }
    
    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        Game currGame = mValues.get(position);
        holder.mItem = currGame;
        holder.mTeamsView.setText(currGame.getHomeTeam().getName() + " VS. " +
                currGame.getAwayTeam().getName());
        
        holder.mStartTimeView.setText(new SimpleDateFormat("MMM dd, hh:mm", Locale.getDefault()).format(currGame.getStartTime()));
        
        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onListFragmentInteraction(holder.mItem);
                }
            }
        });
    }
    
    @Override
    public int getItemCount() {
        return mValues.size();
    }
    
    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mTeamsView;
        public final TextView mStartTimeView;
        public Game mItem;
        
        public ViewHolder(View view) {
            super(view);
            mView = view;
            mTeamsView = (TextView) view.findViewById(R.id.future_item_teams);
            mStartTimeView = (TextView) view.findViewById(R.id.future_item_start_time);
        }
        
        @Override
        public String toString() {
            return super.toString() + " '" + mStartTimeView.getText() + "'";
        }
    }
}
