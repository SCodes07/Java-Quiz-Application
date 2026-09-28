package com.quizapp.model;

import java.sql.Timestamp;

public class RoundScore {
    private int roundId;
    private int playerId;
    private String level;
    private int roundNo;
    private int score;
    private int total;
    private Timestamp playedAt;

    public int getRoundId() { return roundId; }
    public void setRoundId(int roundId) { this.roundId = roundId; }

    public int getPlayerId() { return playerId; }
    public void setPlayerId(int playerId) { this.playerId = playerId; }

    public String getLevel() { return level; }
    public void setLevel(String level) { this.level = level; }

    public int getRoundNo() { return roundNo; }
    public void setRoundNo(int roundNo) { this.roundNo = roundNo; }

    public int getScore() { return score; }
    public void setScore(int score) { this.score = score; }

    public int getTotal() { return total; }
    public void setTotal(int total) { this.total = total; }

    public Timestamp getPlayedAt() { return playedAt; }
    public void setPlayedAt(Timestamp playedAt) { this.playedAt = playedAt; }
}
