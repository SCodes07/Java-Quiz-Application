package com.quizapp.model;

public class Player {
    private int playerId;
    private String name;
    private String username;
    private String password;
    private String extraAttr;

    public Player() {}

    public Player(int playerId, String name, String username, String extraAttr) {
        this.playerId = playerId;
        this.name = name;
        this.username = username;
        this.extraAttr = extraAttr;
    }

    public int getPlayerId() { return playerId; }
    public void setPlayerId(int playerId) { this.playerId = playerId; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getExtraAttr() { return extraAttr; }
    public void setExtraAttr(String extraAttr) { this.extraAttr = extraAttr; }
}
