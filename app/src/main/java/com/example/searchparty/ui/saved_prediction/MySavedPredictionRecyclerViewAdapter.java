package com.example.searchparty.ui.saved_prediction;

import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.searchparty.Models.Prediction;
import com.example.searchparty.R;
import com.example.searchparty.ui.saved_prediction.SavedPredictionFragment.OnListFragmentInteractionListener;

import java.util.Comparator;
import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link com.example.searchparty.Models.Prediction} and makes a call to the
 * specified {@link OnListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class MySavedPredictionRecyclerViewAdapter extends RecyclerView.Adapter<MySavedPredictionRecyclerViewAdapter.ViewHolder> {
    
    private final List<Prediction> mValues;
    private final OnListFragmentInteractionListener mListener;
    
    public MySavedPredictionRecyclerViewAdapter(List<Prediction> items, OnListFragmentInteractionListener listener) {
        items.sort(new Comparator<Prediction>() {
            @Override
            public int compare(Prediction o1, Prediction o2) {
                return ((Long)(o2.getGame().getStartTime().getTime() - o1.getGame().getStartTime().getTime())).intValue();
            }
        });
        
        mValues = items;
        mListener = listener;
    }
    
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_saved_prediction_item, parent, false);
        return new ViewHolder(view);
    }
    
    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        Prediction currPrediction = mValues.get(position);
        holder.mItem = currPrediction;
        holder.mContentView.setText(currPrediction.getGame().getHomeTeam().getName() + " VS. " +
                currPrediction.getGame().getAwayTeam().getName());
        holder.mProgressCircle.setProgress(((Double) currPrediction.getPredictedOutcome()).intValue());
        
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
        public final ProgressBar mProgressCircle;
        public final TextView mContentView;
        public Prediction mItem;
        
        public ViewHolder(View view) {
            super(view);
            mView = view;
            mProgressCircle = (ProgressBar) view.findViewById(R.id.predict_item_circle);
            mContentView = (TextView) view.findViewById(R.id.predict_item_teams);
        }
        
        @Override
        public String toString() {
            return super.toString() + " '" + mContentView.getText() + "'";
        }
    }
}
