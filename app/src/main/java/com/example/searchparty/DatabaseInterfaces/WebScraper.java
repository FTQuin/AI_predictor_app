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
import java.util.Map;
import java.util.TimeZone;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class WebScraper {
    public WebScraper(){}
    
    public void scrape(Context context) {
        Log.d("WEBSCRAPER", "scraping");
        new Scrape().execute(context);
    }
    
    private static class Scrape extends AsyncTask<Context, Void, Void>{
        @Override
        protected Void doInBackground(Context... contexts) {
    
            //future games
            try {
                Date now = new Date();
                now.setTime(now.getTime()+(1000*60*60*24));
                SimpleDateFormat ymd = new SimpleDateFormat("yyyy/MM/dd/");
                String urlString = "https://www.ncaa.com/scoreboard/basketball-men/d1/";
                urlString += ymd.format(now);
                urlString += "top-25";
        
                URL url = new URL(urlString);
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
                    Game currGame = new Game(homeTeam, awayTeam);
                    Prediction blankPred = new Prediction(currGame);
            
                    String[] dateStringArgs =  game.getElementsByClass("game-time").first().text().split(" ");
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd/hh:mmaa");
                    String dateString = ymd.format(now)+dateStringArgs[0];
                    sdf.setTimeZone(TimeZone.getTimeZone(dateStringArgs[1]));
                    Log.d("WEB SCRAPER", sdf.parse(dateString).toString());
                    currGame.setStartTime(sdf.parse(dateString).getTime());
            
                    dbi.addTeam(homeTeam);
                    dbi.addTeam(awayTeam);
                    dbi.addGame(currGame);
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
            
            //past games
            try {
                Date now = new Date();
                now.setTime(now.getTime()-(1000*60*60*24));
                SimpleDateFormat ymd = new SimpleDateFormat("yyyy/MM/dd/");
                String urlString = "https://www.ncaa.com/scoreboard/basketball-men/d1/";
                urlString += ymd.format(now);
                urlString += "top-25";
        
                URL url = new URL(urlString);
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
                    Game currGame = new Game(homeTeam, awayTeam);
                    Prediction blankPred = new Prediction(currGame);
            
                    currGame.setStartTime(now.getTime());
                    
                    //get stats
                    String statUrlString = "https://www.ncaa.com";
                    statUrlString += game.getElementsByClass("gamePod-link").attr("href");
    
                    URL statUrl = new URL(statUrlString);
                    HttpURLConnection statUrlConn = (HttpURLConnection) statUrl.openConnection();
                    
                    StringBuilder statTmp = new StringBuilder();
                    BufferedReader statIn = new BufferedReader(new InputStreamReader(statUrlConn.getInputStream()));
    
                    while ((line = statIn.readLine()) != null) {
                        statTmp.append(line);
                    }
    
                    Document statDoc = Jsoup.parse(tmp.toString());
    
                    Map<String, Double> tempStatsMap = currGame.getStatsMap();
    
                    Element awayBox = statDoc.getElementsByClass("boxscore-table_player_visitor").first();
                    Element homeBox = statDoc.getElementsByClass("boxscore-table_player_home").first();
                    
                    Elements homeTotals = homeBox.getElementsByClass("total-row").first().getElementsByTag("td");
                    Elements awayTotals = awayBox.getElementsByClass("total-row").first().getElementsByTag("td");
                    
                    Elements homePercents = homeBox.getElementsByClass("percentages-row").first().getElementsByTag("td");
                    Elements awayPercents = awayBox.getElementsByClass("percentages-row").first().getElementsByTag("td");
    
                    tempStatsMap.put("HPTS", Double.parseDouble(homeTotals.get(12).text()));
                    tempStatsMap.put("APTS", Double.parseDouble(awayTotals.get(12).text()));
                    
                    tempStatsMap.put("HAST", Double.parseDouble(homeTotals.get(7).text()));
                    tempStatsMap.put("AAST", Double.parseDouble(awayTotals.get(7).text()));
    
                    tempStatsMap.put("HTO", Double.parseDouble(homeTotals.get(10).text()));
                    tempStatsMap.put("ATO", Double.parseDouble(awayTotals.get(10).text()));
    
                    tempStatsMap.put("HFGM", Double.parseDouble(homePercents.get(1).text()));
                    tempStatsMap.put("AFGM", Double.parseDouble(awayPercents.get(1).text()));
                    
                    tempStatsMap.put("H3PM", Double.parseDouble(homePercents.get(2).text()));
                    tempStatsMap.put("A3PM", Double.parseDouble(awayPercents.get(2).text()));
                    
                    currGame.setStatsMap(tempStatsMap);
                    
                    Log.d("WEBSCRAPER", "done stats");
                    
                    //add teams
                    dbi.addTeam(homeTeam);
                    dbi.addTeam(awayTeam);
                    dbi.addGame(currGame);
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
