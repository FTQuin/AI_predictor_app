package com.example.searchparty.Models;

public class Prediction {
    /*
        Prediction:
            - stats **figure out how to implement this**
            get/set SliderValues*/
    private float predictedOutcome;
    Game currentGame;
    
    public Prediction(Game currentGame){
        this.predictedOutcome = 0;
        this.currentGame = currentGame;
    }
    
    //toString
    @Override
    public String toString() {
        return "Prediction{" + "\n" +
                "\tpredictedOutcome=" + predictedOutcome + "\n" +
                "\t}";
    }
}
