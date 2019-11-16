package com.example.searchparty.DatabaseInterfaces;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.example.searchparty.Models.Game;
import com.example.searchparty.Models.Prediction;
import com.example.searchparty.Models.Team;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class WebScraper {
    public WebScraper(){}
    
    public void scrape(Context context) {
        new Scrape().execute(context);
    }
    
    private static class Scrape extends AsyncTask<Context, Void, Void>{
        @Override
        protected Void doInBackground(Context... contexts) {
            try {
                URL url = new URL("https://www.ncaa.com/scoreboard/basketball-men/d1/2019/11/17/top-25");
                HttpURLConnection urlConn = (HttpURLConnection) url.openConnection();
                String line;
                StringBuilder tmp = new StringBuilder();
                BufferedReader in = new BufferedReader(new InputStreamReader(urlConn.getInputStream()));
        
                while ((line = in.readLine()) != null) {
                    tmp.append(line);
                }
        
                Document doc = Jsoup.parse(tmp.toString());
        
                Element scoreboard = doc.getElementById("scoreboardGames");
                Elements games = scoreboard.getElementsByClass("gamePod");
                
                DatabaseInterface dbi = new DatabaseInterface(contexts[0]);
                
                Log.d("WEB SCRAPER", "games:");
                for (Element game : games) {
                    Elements teams = game.getElementsByClass("gamePod-game-team-name");
    
                    Team homeTeam = new Team(teams.first().text());
                    Team awayTeam = new Team(teams.last().text());
                    Game currgame = new Game(homeTeam, awayTeam);
                    Prediction blankPred = new Prediction(currgame);
                    
                    String[] dateStringArgs =  game.getElementsByClass("game-time").first().text().split(" ");
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd/hh:mmaa");
                    String dateString = "2019/11/17/"+dateStringArgs[0];
                    sdf.setTimeZone(TimeZone.getTimeZone(dateStringArgs[1]));
                    Log.d("WEB SCRAPER", sdf.parse(dateString).toString());
                    currgame.setStartTime(sdf.parse(dateString).getTime());
                    
                    dbi.addTeam(homeTeam);
                    dbi.addTeam(awayTeam);
                    dbi.addGame(currgame);
//                    dbi.addPrediction(blankPred);

                    Log.d("WEB SCRAPER", teams.first().text());
                    Log.d("WEB SCRAPER", teams.last().text());
                }
                dbi.loadDataFromDB();
    
                Log.d("WEB SCRAPER", "done");
        
            } catch(Exception e){
                e.printStackTrace();
                Log.d("WEB SCRAPER", "error");
            }
            return null;
        }
    }

}
