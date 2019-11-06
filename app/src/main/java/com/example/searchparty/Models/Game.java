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
        
        homeTeam.addFutureGame(this);
        awayTeam.addFutureGame(this);
        
        this.prediction = new Prediction(this);
    }
    
    public boolean completeGame(){
        return homeTeam.completeGame(this) && awayTeam.completeGame(this);
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
