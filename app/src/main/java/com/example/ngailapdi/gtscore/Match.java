package com.example.ngailapdi.gtscore;

public class Match {
    private String name;
    private String player1ID;
    private String player2ID;
    private Integer score1;
    private Integer score2;
    private boolean played;
    private String matchID;
    private String player1Name;
    private String player2Name;
    private String gameID;

    public Match() {

    }

    public Match(String name, String user1ID, String user2ID, String user1Name, String user2Name, String gameID) {
        this.name = name;
        this.player1ID = user1ID;
        this.player2ID = user2ID;
        this.player1Name = user1Name;
        this.player2Name = user2Name;
        this.score1 = 0;
        this.score2 = 0;
        this.played = false;
        this.gameID = gameID;
    }
    public String getName() {
        return this.name;
    }
    public String getPlayer1ID() {
        return this.player1ID;
    }
    public String getPlayer2ID() {
        return this.player2ID;
    }
    public Integer getScore1() {
        return this.score1;
    }
    public Integer getScore2() {
        return this.score2;
    }
    public void setScore1(int score) {
        this.score1 = score;
    }
    public void setScore2(int score) {
        this.score2 = score;
    }
    public boolean getPlayed() {
        return this.played;
    }
    public void setPlayed() {
        this.played = !this.played;
    }
    public void setMatchID(String matchID) {
        this.matchID = matchID;
    }
    public String getMatchID() {return this.matchID;}
    public String getPlayer1Name() {return this.player1Name;}
    public String getPlayer2Name() {return this.player2Name;}
    public String getGameID() {return this.gameID;}
}
