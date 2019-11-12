package com.example.searchparty.Models;

import android.support.annotation.Nullable;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
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
    private String ID;
    
    public Team(String name){
        this.name = name;
        this.previousGames = new ArrayList<>();
        this.futureGames = new ArrayList<>();
        this.ID = UUID.randomUUID().toString();
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
    
    //getters
    public String getName() {
        return name;
    }
    public List<Game> getPreviousGames() {
        return previousGames;
    }
    public List<Game> getFutureGames(){
        return futureGames;
    }
    public String getID(){
        return ID;
    }
    
    //setters
    public void setName(String name){
        this.name = name;
    }
    public void setID(String ID){
        this.ID = ID;
    }
    
    //toString
    @Override
    public String toString() {
        return "Team{" + "\n" +
                "\tname='" + name + '\'' + "\n" +
                "\t}";
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Team team = (Team) o;
        return name.equals(team.name);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
