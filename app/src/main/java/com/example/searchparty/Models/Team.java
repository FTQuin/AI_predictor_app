package com.example.searchparty.Models;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.jar.Attributes;

public class Team {
    /*
        Team:
            - Name
            - previousGames
            - futureGames
            - team stats
            - logo*/
    
    private String name;
    private List<Game> previousGames;
    private List<Game> futureGames;
    private int ID;
    
    public Team(String name){
        this.name = name;
        this.previousGames = new ArrayList<>();
        this.futureGames = new ArrayList<>();
        this.ID = 0;
    }
    
    //called when a team has completed a game
    protected boolean completeGame(Game finishedGame){
        if(futureGames.contains(finishedGame)){
            futureGames.remove(finishedGame);
            previousGames.add(finishedGame);
            
            return true;
        } else {
            return false;
        }
    }
    
    //add games to a team
    public void addPreviousGame(Game prevGame){
        previousGames.add(prevGame);
    }
    public void addFutureGame(Game futureGame){
        futureGames.add(futureGame);
    }
    
    //getters and setters
    public List<Game> getPreviousGames() {
        return previousGames;
    }
    public List<Game> getFutureGames() {
        return futureGames;
    }
    public int getID() {
        return ID;
    }
    
    //toString
    @Override
    public String toString() {
        return "Team{" + "\n" +
                "\tname='" + name + '\'' + "\n" +
                "\t}";
    }
}
