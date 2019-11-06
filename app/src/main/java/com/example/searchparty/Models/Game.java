package com.example.searchparty.Models;

import android.widget.TextView;

import java.sql.Date;

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
    
    public Game(Team homeTeam, Team awayTeam){
        this.homeTeam = homeTeam;
        this.awayTeam = awayTeam;
        this.prediction = new Prediction(this);
    }
    
    
    //Getters and Setters
    public Team getHomeTeam() {
        return homeTeam;
    }
    
    public void setHomeTeam(Team homeTeam) {
        this.homeTeam = homeTeam;
    }
    
    public Team getAwayTeam() {
        return awayTeam;
    }
    
    public void setAwayTeam(Team awayTeam) {
        this.awayTeam = awayTeam;
    }
    
    public Prediction getPrediction() {
        return prediction;
    }
    
    public void setPrediction(Prediction prediction) {
        this.prediction = prediction;
    }
    
    public Date getStartTime() {
        return startTime;
    }
    
    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }
}
