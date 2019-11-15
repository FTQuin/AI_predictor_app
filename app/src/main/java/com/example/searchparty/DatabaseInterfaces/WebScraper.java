package com.example.searchparty.DatabaseInterfaces;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

public class WebScraper {
    public WebScraper(){}
    
    public void scrape() {
        new Scrape().execute();
    }
    
    private class Scrape extends AsyncTask<Void, Void, Void>{
        @Override
        protected Void doInBackground(Void... voids) {
            try {
                URL url = new URL("https://www.ncaa.com/scoreboard/basketball-men/d1/2019/11/16/top-25");
                HttpURLConnection urlConn = (HttpURLConnection) url.openConnection();
                String line;
                StringBuilder tmp = new StringBuilder();
                BufferedReader in = new BufferedReader(new InputStreamReader(urlConn.getInputStream()));
        
                while ((line = in.readLine()) != null) {
                    tmp.append(line);
                }
        
                Document doc = Jsoup.parse(tmp.toString());
        
                Element game0 = doc.getElementsByClass("gamePod-game-team-name").first();
                Log.d("WEB SCRAPER", game0.text());
        
            } catch(Exception e){
                e.printStackTrace();
            }
            return null;
        }
    }

}
