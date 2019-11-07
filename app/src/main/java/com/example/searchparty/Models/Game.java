package com.example.searchparty.Models;

import android.widget.TextView;

import java.util.Date;
import java.util.List;

public class Game {
    
    /* Game:
            - homeTeam
            - awayTeam
            - date
            - prediction
            - stats **figure out how to implement this**
            get/set Prediction
            get/set <various stats>*/
    
    private Team homeTeam, awayTeam;
    private Prediction prediction;
    Date startTime;
    int ID;
    
    public Game(Team homeTeam, Team awayTeam){
        this.homeTeam = homeTeam;
        this.awayTeam = awayTeam;
        
        homeTeam.addFutureGame(this);
        awayTeam.addFutureGame(this);
        
        this.prediction = new Prediction(this);
        
        this.startTime = new Date();
        startTime.setTime(startTime.getTime());
        
        this.ID = 0;
    }
    
    public boolean completeGame(){
        return homeTeam.completeGame(this) && awayTeam.completeGame(this);
    }
    
    //Getters and Setters
    public Team getHomeTeam() {
        return homeTeam;
    }
    public Team getAwayTeam() {
        return awayTeam;
    }
    public Prediction getPrediction() {
        return prediction;
    }
    public Date getStartTime() {
        return startTime;
    }
    public int getID() {
        return ID;
    }
    
    //toString
    @Override
    public String toString() {
        return "Game{" + "\n" +
                "\thomeTeam=" + homeTeam + "\n" +
                "\t, awayTeam=" + awayTeam + "\n" +
                "\t, prediction=" + prediction + "\n" +
                "\t, startTime=" + startTime + "\n" +
                "\t}";
    }
}
