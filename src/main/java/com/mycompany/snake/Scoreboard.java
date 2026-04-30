package com.mycompany.snake;

public class Scoreboard {
    private int score = 0;
    private String playerName = "Player";

    public Scoreboard() {}

    public Scoreboard(String playerName) {
        this.playerName = playerName;
    }

    public void addPoints(int points) { score += points; }
    public int getScore() { return score; }
    public String getPlayerName() { return playerName; }
    public void setPlayerName(String playerName) { this.playerName = playerName; }
    public void reset() { score = 0; }
}
