package com.example.searchparty.Models;

import java.util.UUID;

public class Prediction {
    /*
        Prediction:
            - stats **figure out how to implement this**
            get/set SliderValues*/
    private double predictedOutcome;
    private Game game;
    private String ID;
    
    public Prediction(Game currentGame){
        this.predictedOutcome = 0;
        this.game = currentGame;
        this.ID = UUID.randomUUID().toString();
    }
    
    //getters
    public String getID() {
        return ID;
    }
    public Game getGame() {
        return game;
    }
    public double getPredictedOutcome() {
        return predictedOutcome;
    }
    
    //setters
    public void setPredictedOutcome(double predictedOutcome) {
        this.predictedOutcome = predictedOutcome;
    }
    public void setGame(Game game) {
        this.game = game;
    }
    public void setID(String ID) {
        this.ID = ID;
    }
    
    //toString
    @Override
    public String toString() {
        return "Prediction{" + "\n" +
                "\tpredictedOutcome=" + predictedOutcome + "\n" +
                "\t}";
    }
}
