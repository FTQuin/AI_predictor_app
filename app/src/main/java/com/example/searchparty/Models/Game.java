package com.example.searchparty.Models;

import java.util.Date;
import java.util.Objects;
import java.util.UUID;

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
    String ID;
    
    public Game(Team homeTeam, Team awayTeam){
        this.homeTeam = homeTeam;
        this.awayTeam = awayTeam;
        
        homeTeam.addFutureGame(this);
        awayTeam.addFutureGame(this);
        
        this.prediction = new Prediction(this);
        
        this.startTime = new Date();
        startTime.setTime(startTime.getTime());
        
        this.ID = UUID.randomUUID().toString();
    }
    
    public boolean completeGame(){
        return homeTeam.completeGame(this) && awayTeam.completeGame(this);
    }
    
    //Getters
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
    public String getID() {
        return ID;
    }
    
    //setters
    public void setHomeTeam(Team homeTeam) {
        this.homeTeam = homeTeam;
    }
    public void setAwayTeam(Team awayTeam) {
        this.awayTeam = awayTeam;
    }
    public void setPrediction(Prediction prediction) {
        this.prediction = prediction;
    }
    public void setStartTime(Long startTime) {
        this.startTime = new Date(startTime);
    }
    public void setID(String ID) {
        this.ID = ID;
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
    
    //hashcode and equals methods
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Game game = (Game) o;
        return homeTeam.equals(game.homeTeam) &&
                awayTeam.equals(game.awayTeam) &&
                startTime.equals(game.startTime);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(homeTeam, awayTeam, startTime);
    }
}
