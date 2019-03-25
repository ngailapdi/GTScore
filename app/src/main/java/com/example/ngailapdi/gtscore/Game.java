package com.example.ngailapdi.gtscore;

public class Game {
    private String name;
    private String description;
    private String gameID;
    public Game() {

    }
    public Game (String name, String description) {
        this.name = name;
        this.description = description;
//        this.gameId = gameId;
    }
    public String getName() {
        return this.name;
    }
    public String getDescription() {
        return this.description;
    }
    public String getGameID() {
        return this.gameID;
    }
    public void setGameID(String gameID) {
        this.gameID = gameID;
    }
}
