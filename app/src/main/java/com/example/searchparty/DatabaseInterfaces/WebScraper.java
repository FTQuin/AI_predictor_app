package com.example.searchparty.DatabaseInterfaces;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class WebScraper {
    public WebScraper(){}
    
    public void scrape() {
        try {
            URL url = new URL("http://google.com/");
            HttpURLConnection urlConn = (HttpURLConnection) url.openConnection();
            String line = null;
            StringBuilder tmp = new StringBuilder();
            BufferedReader in = new BufferedReader(new InputStreamReader(urlConn.getInputStream()));
        
            while ((line = in.readLine()) != null) {
                tmp.append(line);
            }
        
            Document doc = Jsoup.parse(tmp.toString());
            
        } catch(Exception e){
        }
    }

}
