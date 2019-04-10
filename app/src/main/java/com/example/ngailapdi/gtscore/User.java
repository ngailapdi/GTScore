package com.example.ngailapdi.gtscore;

import java.util.List;

public class User {
    private String name;
    private String email;
    private String uid;
    private String deviceToken;
    private List<User> friends;
    private List<Game> games;

    public User() {

    }

    public User(String name, String email,
                List<User> friends, List<Game> games) {
        this.name = name;
        this.email = email;
        this.uid = uid;
        this.friends = friends;
        this.games = games;
    }

    public String getName() {
        return this.name;
    }
    public String getEmail() {
        return this.email;
    }
    public String getUid() {
        return this.uid;
    }
    public void setUid(String uid) {this.uid = uid;}
    public List<User> getFriends() {
        return this.friends;
    }
    public List<Game> getGames() {
        return this.games;
    }
    public String getDeviceToken() {return this.deviceToken;}
    public void setDeviceToken(String deviceToken) {this.deviceToken = deviceToken;}
}
