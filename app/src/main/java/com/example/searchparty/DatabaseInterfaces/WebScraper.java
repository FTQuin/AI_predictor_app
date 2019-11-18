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
                SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd/");
                String urlString = "https://www.espn.com/mens-college-basketball/schedule/_/date/";
                urlString += ymd.format(now);
        
                URL url = new URL(urlString);
                HttpURLConnection urlConn = (HttpURLConnection) url.openConnection();
                String line;
                StringBuilder tmp = new StringBuilder();
                BufferedReader in = new BufferedReader(new InputStreamReader(urlConn.getInputStream()));
        
                while ((line = in.readLine()) != null) {
                    tmp.append(line);
                }
        
                Document doc = Jsoup.parse(tmp.toString());
        
                Element scoreboard = doc.getElementsByClass("schedule").first();
                Elements games = scoreboard.getElementsByTag("tbody").first().getElementsByTag("tr");
        
                DatabaseInterface dbi = new DatabaseInterface(contexts[0]);
        
                Log.d("WEB SCRAPER", "games:");
                for (Element game : games) {
                    Elements teams = game.getElementsByTag("td");
                    
                    Team homeTeam = new Team(teams.get(1).getElementsByClass("team-name").first().getElementsByTag("span").first().text());
                    Team awayTeam = new Team(teams.get(0).getElementsByClass("team-name").first().getElementsByTag("span").first().text());
                    Game currGame = new Game(homeTeam, awayTeam);
            
//                    String[] dateStringArgs =  teams.get(2).getElementsByTag("a").first().text().split(" ");
//                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd/hh:mmaa");
//                    String dateString = ymd.format(now)+dateStringArgs[0];
////                    sdf.setTimeZone(TimeZone.getTimeZone(dateStringArgs[1]));
//                    Log.d("WEB SCRAPER", sdf.parse(dateString).toString());
                    currGame.setStartTime(now.getTime());
            
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
                SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd/");
                String urlString = "https://www.espn.com/mens-college-basketball/schedule/_/date/";
                urlString += ymd.format(now);
        
                URL url = new URL(urlString);
                HttpURLConnection urlConn = (HttpURLConnection) url.openConnection();
                String line;
                StringBuilder tmp = new StringBuilder();
                BufferedReader in = new BufferedReader(new InputStreamReader(urlConn.getInputStream()));
        
                while ((line = in.readLine()) != null) {
                    tmp.append(line);
                }
        
                Document doc = Jsoup.parse(tmp.toString());
        
                Element scoreboard = doc.getElementsByClass("schedule").first();
                Elements games = scoreboard.getElementsByTag("tbody").first().getElementsByTag("tr");
        
                DatabaseInterface dbi = new DatabaseInterface(contexts[0]);
        
                Log.d("WEB SCRAPER", "games:");
                for (Element game : games) {
                    Elements teams = game.getElementsByTag("td");
    
                    Team homeTeam = new Team(teams.get(1).getElementsByClass("team-name").first().getElementsByTag("span").first().text());
                    Team awayTeam = new Team(teams.get(0).getElementsByClass("team-name").first().getElementsByTag("span").first().text());
                    Game currGame = new Game(homeTeam, awayTeam);
            
                    currGame.setStartTime(now.getTime());
                    //get stats
                    String statUrlString = "https://www.espn.com";
                    statUrlString += teams.get(2).getElementsByTag("a").first().attr("href");
                    statUrlString = statUrlString.replace("game?", "matchup?");
    
                    URL statUrl = new URL(statUrlString);
                    HttpURLConnection statUrlConn = (HttpURLConnection) statUrl.openConnection();
                    
                    StringBuilder statTmp = new StringBuilder();
                    BufferedReader statIn = new BufferedReader(new InputStreamReader(statUrlConn.getInputStream()));
    
                    while ((line = statIn.readLine()) != null) {
                        statTmp.append(line);
                    }
    
                    Document statDoc = Jsoup.parse(statTmp.toString());
    
                    Map<String, Double> tempStatsMap = currGame.getStatsMap();
                    
                    //TODO:pick up vhanging website from here
                    Elements statTable = statDoc.getElementById("gamepackage-matchup").getElementsByTag("tbody").first().getElementsByTag("tr");
    
                    tempStatsMap.put("HFGM", Double.parseDouble(statTable.get(1).getElementsByTag("td").get(2).text()));
                    tempStatsMap.put("AFGM", Double.parseDouble(statTable.get(1).getElementsByTag("td").get(1).text()));
    
                    tempStatsMap.put("H3PM", Double.parseDouble(statTable.get(3).getElementsByTag("td").get(2).text()));
                    tempStatsMap.put("A3PM", Double.parseDouble(statTable.get(3).getElementsByTag("td").get(1).text()));
                    
                    tempStatsMap.put("HAST", Double.parseDouble(statTable.get(9).getElementsByTag("td").get(2).text()));
                    tempStatsMap.put("AAST", Double.parseDouble(statTable.get(9).getElementsByTag("td").get(1).text()));
    
                    tempStatsMap.put("HTO", Double.parseDouble(statTable.get(12).getElementsByTag("td").get(2).text()));
                    tempStatsMap.put("ATO", Double.parseDouble(statTable.get(12).getElementsByTag("td").get(1).text()));
    
                    tempStatsMap.put("HPTS", Double.parseDouble(statDoc.getElementById("linescore").getElementsByTag("tr").get(1).getElementsByTag("td").get(3).text()));
                    tempStatsMap.put("APTS", Double.parseDouble(statDoc.getElementById("linescore").getElementsByTag("tr").get(2).getElementsByTag("td").get(3).text()));
                    
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
