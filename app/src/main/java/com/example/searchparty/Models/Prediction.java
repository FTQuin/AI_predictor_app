package com.example.searchparty.Models;

public class Prediction {
    /*
        Prediction:
            - stats **figure out how to implement this**
            get/set SliderValues*/
    private double predictedOutcome;
    private Game game;
    private int ID;
    
    public Prediction(Game currentGame){
        this.predictedOutcome = 0;
        this.game = currentGame;
        this.ID = 0;
    }
    
    //getters and setters
    public int getID() {
        return ID;
    }
    public Game getGame() {
        return game;
    }
    
    //toString
    @Override
    public String toString() {
        return "Prediction{" + "\n" +
                "\tpredictedOutcome=" + predictedOutcome + "\n" +
                "\t}";
    }
}
