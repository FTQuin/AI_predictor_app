package com.example.searchparty.Models;

import java.util.Objects;
import java.util.Random;
import java.util.UUID;

public class Prediction {
    /*
        Prediction:
            - stats **figure out how to implement this**
            get/set SliderValues*/
    private double predictedOutcome;
    private Game game;
    private String ID;
    
    public Prediction(){
        //TODO: change this to initialize to zero once it no longer needs to be tested
        this.predictedOutcome = new Random().nextDouble()*100;
        
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
        if(game.getPrediction() != this) game.setPrediction(this);
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
    
    //hashcode and equals methods
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Prediction that = (Prediction) o;
        return Double.compare(that.predictedOutcome, predictedOutcome) == 0 &&
                game.equals(that.game);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(predictedOutcome, game.ID);
    }
}
