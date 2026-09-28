package com.quizapp.dao;

import com.quizapp.db.DB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class RoundScoreDAO {

    public boolean saveRound(int playerId, String level, int roundNo, int score, int total) {
        String sql = "INSERT INTO round_scores(player_id, level, round_no, score, total) VALUES(?,?,?,?,?)";
        try (Connection c = DB.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, playerId);
            ps.setString(2, level);
            ps.setInt(3, roundNo);
            ps.setInt(4, score);
            ps.setInt(5, total);
            return ps.executeUpdate() == 1;
        } catch (Exception e) {
            return false;
        }
    }

    public int getTotalScoreAllRounds(int playerId) {
        String sql = "SELECT COALESCE(SUM(score),0) AS total_score FROM round_scores WHERE player_id=?";
        try (Connection c = DB.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, playerId);
            try (ResultSet rs = ps.executeQuery()) {
                if (!rs.next()) return 0;
                return rs.getInt("total_score");
            }
        } catch (Exception e) {
            return 0;
        }
    }
}
