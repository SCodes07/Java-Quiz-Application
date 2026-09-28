package com.quizapp.dao;

import com.quizapp.db.DB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class ReportDAO {

    public static class LeaderRow {
        public int rank;
        public int playerId;
        public String name;
        public String username;
        public int totalScore;

        public LeaderRow(int rank, int playerId, String name, String username, int totalScore) {
            this.rank = rank;
            this.playerId = playerId;
            this.name = name;
            this.username = username;
            this.totalScore = totalScore;
        }
    }

    // ✅ Leaderboard: total score summed across attempts
    // level = "beginner"/"intermediate"/"advanced"/"all"
    public List<LeaderRow> leaderboard(String level) throws Exception {
        String sqlAll = """
            SELECT p.player_id, p.name, p.username, COALESCE(SUM(r.total_score),0) AS total
            FROM players p
            LEFT JOIN results r ON r.player_id = p.player_id
            GROUP BY p.player_id, p.name, p.username
            ORDER BY total DESC, p.player_id ASC
            LIMIT 20
        """;

        String sqlByLevel = """
            SELECT p.player_id, p.name, p.username, COALESCE(SUM(r.total_score),0) AS total
            FROM players p
            LEFT JOIN results r ON r.player_id = p.player_id AND r.level = ?
            GROUP BY p.player_id, p.name, p.username
            ORDER BY total DESC, p.player_id ASC
            LIMIT 20
        """;

        List<LeaderRow> rows = new ArrayList<>();

        try (Connection c = DB.getConnection();
             PreparedStatement ps = c.prepareStatement(level.equals("all") ? sqlAll : sqlByLevel)) {

            if (!level.equals("all")) ps.setString(1, level);

            try (ResultSet rs = ps.executeQuery()) {
                int rank = 1;
                while (rs.next()) {
                    rows.add(new LeaderRow(
                            rank++,
                            rs.getInt("player_id"),
                            rs.getString("name"),
                            rs.getString("username"),
                            rs.getInt("total")
                    ));
                }
            }
        }
        return rows;
    }

    // ✅ Summary for one player (their attempts count + best score + average)
    public String playerScoreSummary(int playerId) throws Exception {
        String attemptsSql = "SELECT COUNT(*) c FROM results WHERE player_id=?";
        String bestSql = "SELECT MAX(total_score) m FROM results WHERE player_id=?";
        String avgSql = "SELECT AVG(total_score) a FROM results WHERE player_id=?";

        int attempts = 0;
        int best = 0;
        double avg = 0;

        try (Connection c = DB.getConnection()) {
            try (PreparedStatement ps = c.prepareStatement(attemptsSql)) {
                ps.setInt(1, playerId);
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) attempts = rs.getInt("c");
                }
            }

            try (PreparedStatement ps = c.prepareStatement(bestSql)) {
                ps.setInt(1, playerId);
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) best = rs.getInt("m");
                }
            }

            try (PreparedStatement ps = c.prepareStatement(avgSql)) {
                ps.setInt(1, playerId);
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) avg = rs.getDouble("a");
                }
            }
        }

        return "Attempts: " + attempts +
               "\nBest total score: " + best + "/25" +
               "\nAverage total score: " + String.format("%.2f", avg) + "/25";
    }

    // ✅ Overall game statistics
    public String statisticalSummary() throws Exception {
        int players = 0;
        int attempts = 0;
        double avg = 0;
        String top = "No results yet.";

        try (Connection c = DB.getConnection()) {
            try (PreparedStatement ps = c.prepareStatement("SELECT COUNT(*) c FROM players");
                 ResultSet rs = ps.executeQuery()) {
                if (rs.next()) players = rs.getInt("c");
            }

            try (PreparedStatement ps = c.prepareStatement("SELECT COUNT(*) c FROM results");
                 ResultSet rs = ps.executeQuery()) {
                if (rs.next()) attempts = rs.getInt("c");
            }

            try (PreparedStatement ps = c.prepareStatement("SELECT AVG(total_score) a FROM results");
                 ResultSet rs = ps.executeQuery()) {
                if (rs.next()) avg = rs.getDouble("a");
            }

            try (PreparedStatement ps = c.prepareStatement("""
                SELECT p.name, p.username, r.level, r.total_score
                FROM results r JOIN players p ON p.player_id=r.player_id
                ORDER BY r.total_score DESC, r.played_at DESC
                LIMIT 1
            """);
                 ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    top = rs.getString("name") + " (" + rs.getString("username") + ") - " +
                          rs.getInt("total_score") + "/25 [" + rs.getString("level") + "]";
                }
            }
        }

        return "Total players: " + players +
               "\nTotal attempts: " + attempts +
               "\nAverage total score: " + String.format("%.2f", avg) + "/25" +
               "\nTop performer: " + top;
    }
}
