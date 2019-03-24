package com.example.ngailapdi.gtscore;

import java.util.List;

public class User {
    private String name;
    private String email;
    private String uid;
    private List<User> friends;
    private List<Match> matches;

    public User() {

    }

    public User(String name, String email,
                List<User> friends, List<Match> matches) {
        this.name = name;
        this.email = email;
        this.uid = uid;
        this.friends = friends;
        this.matches = matches;
    }

    public String getName() {
        return this.name;
    }
    public  String getEmail() {
        return this.email;
    }
    public String getUid() {
        return this.uid;
    }
    public List<User> getFriends() {
        return this.friends;
    }
    public List<Match> getMatches() {
        return this.matches;
    }
}
