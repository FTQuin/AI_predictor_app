package com.example.searchparty.DatabaseInterfaces;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.searchparty.Models.Game;

public class DatabaseInterface extends SQLiteOpenHelper {
    /*
    methods to add:
    
        getFutureGames: list<Game>
        getPastGames: list<Game>
        getSavedGames: list<Game>
        
        setFavoriteTeam: void
        unsetFavoriteTeam: void
        followTeam: void
        unFollowTeam: void
        
        getFollowedTeams: list<Team>
        getFavoriteTeam: Team
        
        
        **possibly create UpcomingGame and FinishedGame classes**
        Game:
            - homeTeam
            - awayTeam
            - date
            - prediction
            - stats **figure out how to implement this**
            get/set Prediction
            get/set <various stats>
            
        Prediction:
            - stats **figure out how to implement this**
            get/set SliderValue
            
        Team:
            - Name
            - previousGames
            - futureGames
            - team stats
            - logo
    */
    
    
    private static final String TAG = "DatabaseInterface";
    
    private static final String DATABASE_NAME = "win_march.db";
    
    //strings for creating the databases
    //game attributes
    private static final String GAME_TABLE_NAME = "GAME_TABLE";
    private static final String[][] GAME_TABLE_COLS = {{"ID", "INTEGER PRIMARY KEY AUTOINCREMENT"},
            {"HOME_TEAM_ID", "TEXT"}, {"AWAY_TEAM_ID", "TEXT"}, {"PREDICTION_ID", "INTEGER"},
            {"START_TIME", "TEXT"}};
    
    //prediction attributes
    private static final String PREDICTION_TABLE_NAME = "PREDICTION_TABLE";
    private static final String[][] PREDICTION_TABLE_COLS = {{"ID", "INTEGER PRIMARY KEY AUTOINCREMENT"},
            {"PREDICTED_OUTCOME", "DOUBLE"},{"GAME_ID", "INTEGER"}};
    
    //team attributes
    private static final String TEAM_TABLE_NAME = "TEAM_TABLE";
    private static final String[][] TEAM_TABLE_COLS = {{"ID", "INTEGER PRIMARY KEY AUTOINCREMENT"},
            {"NAME", "TEXT"}, {"PREVIOUS_GAMES", "BLOB"}, {"FUTURE_GAMES", "BLOB"}};
    
    public DatabaseInterface(Context context){
        super(context, DATABASE_NAME, null, 1);
    }
    
    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create databases
        // Create string for game table
        StringBuilder createGameTable = new StringBuilder("CREATE TABLE " + GAME_TABLE_NAME+"(");
        for (String[] col : GAME_TABLE_COLS)
            createGameTable.append(col[0]).append(" ").append(col[1]).append(", ");
        createGameTable.delete(createGameTable.lastIndexOf(","), createGameTable.length());
        createGameTable.append(")");
        //create game table
        db.execSQL(createGameTable.toString());
        Log.d(TAG, "Created game table");
        
        // Create string for prediction table
        StringBuilder createPredictionTable = new StringBuilder("CREATE TABLE " + PREDICTION_TABLE_NAME+"(");
        for (String[] col : PREDICTION_TABLE_COLS)
            createPredictionTable.append(col[0]).append(" ").append(col[1]).append(", ");
        createPredictionTable.delete(createPredictionTable.lastIndexOf(","), createPredictionTable.length());
        createPredictionTable.append(")");
        //create prediction table
        db.execSQL(createPredictionTable.toString());
        Log.d(TAG, "Created prediction table");
    
        // Create string for team table
        StringBuilder createTeamTable = new StringBuilder("CREATE TABLE " + TEAM_TABLE_NAME+"(");
        for (String[] col : TEAM_TABLE_COLS)
            createTeamTable.append(col[0]).append(" ").append(col[1]).append(", ");
        createTeamTable.delete(createTeamTable.lastIndexOf(","), createTeamTable.length());
        createTeamTable.append(")");
        //create team table
        db.execSQL(createTeamTable.toString());
        Log.d(TAG, "Created team table");
        
        
        Log.d(TAG, "Created databases");
    }
    
    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        System.out.println("test");
        db.execSQL("DROP IF TABLE EXISTS " + GAME_TABLE_NAME);
        db.execSQL("DROP IF TABLE EXISTS " + PREDICTION_TABLE_NAME);
        db.execSQL("DROP IF TABLE EXISTS " + TEAM_TABLE_NAME);
        onCreate(db);
    }
    
    public boolean addData(String item) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(TEAM_TABLE_COLS[1][0], item);
        
        Log.d(TAG, "addData: Adding " + item + " to " + TEAM_TABLE_NAME);
        
        long result = db.insert(TEAM_TABLE_NAME, null, contentValues);
        
        //if date as inserted incorrectly it will return -1
        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }
    
    // reference for inserting content values
    // {{"ID", "INTEGER PRIMARY KEY AUTOINCREMENT"},{"HOME_TEAM_ID", "TEXT"}, {"AWAY_TEAM_ID", "TEXT"},
    // {"PREDICTION_ID", "INTEGER"}, {"START_TIME", "TEXT"}};
    public boolean addGame(Game game) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        
        //home team
        contentValues.put(GAME_TABLE_COLS[1][0], game.getHomeTeam().getID());
        //away team
        contentValues.put(GAME_TABLE_COLS[2][0], game.getAwayTeam().getID());
        //prediction
        contentValues.put(GAME_TABLE_COLS[2][0], game.getPrediction().getID());
        //date
        contentValues.put(GAME_TABLE_COLS[2][0], game.getStartTime().toString());
        
        Log.d(TAG, "addData: Adding items to " + GAME_TABLE_NAME);
        
        long result = db.insert(GAME_TABLE_NAME, null, contentValues);
        
        //if data is inserted incorrectly it will return -1
        return result == -1;
    }
//
//    /**
//     * Returns all the data from database
//     * @return
//     */
//    public Cursor getData(){
//        SQLiteDatabase db = this.getWritableDatabase();
//        String query = "SELECT * FROM " + TABLE_NAME;
//        Cursor data = db.rawQuery(query, null);
//        return data;
//    }
//
//    /**
//     * Returns only the ID that matches the name passed in
//     * @param name
//     * @return
//     */
//    public Cursor getItemID(String name){
//        SQLiteDatabase db = this.getWritableDatabase();
//        String query = "SELECT " + COL1 + " FROM " + TABLE_NAME +
//                " WHERE " + COL2 + " = '" + name + "'";
//        Cursor data = db.rawQuery(query, null);
//        return data;
//    }
//
//    /**
//     * Updates the name field
//     * @param newName
//     * @param id
//     * @param oldName
//     */
//    public void updateName(String newName, int id, String oldName){
//        SQLiteDatabase db = this.getWritableDatabase();
//        String query = "UPDATE " + TABLE_NAME + " SET " + COL2 +
//                " = '" + newName + "' WHERE " + COL1 + " = '" + id + "'" +
//                " AND " + COL2 + " = '" + oldName + "'";
//        Log.d(TAG, "updateName: query: " + query);
//        Log.d(TAG, "updateName: Setting name to " + newName);
//        db.execSQL(query);
//    }
//
//    /**
//     * Delete from database
//     * @param id
//     * @param name
//     */
//    public void deleteName(int id, String name){
//        SQLiteDatabase db = this.getWritableDatabase();
//        String query = "DELETE FROM " + TABLE_NAME + " WHERE "
//                + COL1 + " = '" + id + "'" +
//                " AND " + COL2 + " = '" + name + "'";
//        Log.d(TAG, "deleteName: query: " + query);
//        Log.d(TAG, "deleteName: Deleting " + name + " from database.");
//        db.execSQL(query);
//    }
}
