package com.quizapp.dao;

import com.quizapp.db.DB;
import com.quizapp.model.Player;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class PlayerDAO {

    public boolean usernameExists(String username) {
        String sql = "SELECT 1 FROM players WHERE username = ?";
        try (Connection c = DB.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setString(1, username);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }

        } catch (Exception e) {
            System.out.println("usernameExists error: " + e.getMessage());
            return false;
        }
    }

    public boolean register(Player p) {
        String sql = "INSERT INTO players(name, username, password, extra_attr) VALUES(?,?,?,?)";
        try (Connection c = DB.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setString(1, p.getName());
            ps.setString(2, p.getUsername());
            ps.setString(3, p.getPassword());
            ps.setString(4, p.getExtraAttr());

            return ps.executeUpdate() == 1;

        } catch (Exception e) {
            System.out.println("register error: " + e.getMessage());
            return false;
        }
    }

    public Player login(String username, String password) {
        String sql = "SELECT player_id, name, username, extra_attr FROM players WHERE username = ? AND password = ?";
        try (Connection c = DB.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setString(1, username);
            ps.setString(2, password);

            try (ResultSet rs = ps.executeQuery()) {
                if (!rs.next()) return null;

                return new Player(
                        rs.getInt("player_id"),
                        rs.getString("name"),
                        rs.getString("username"),
                        rs.getString("extra_attr")
                );
            }

        } catch (Exception e) {
            System.out.println("login error: " + e.getMessage());
            return null;
        }
    }

    public Player findById(int playerId) {
        String sql = "SELECT player_id, name, username, extra_attr FROM players WHERE player_id = ?";
        try (Connection c = DB.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setInt(1, playerId);

            try (ResultSet rs = ps.executeQuery()) {
                if (!rs.next()) return null;

                return new Player(
                        rs.getInt("player_id"),
                        rs.getString("name"),
                        rs.getString("username"),
                        rs.getString("extra_attr")
                );
            }

        } catch (Exception e) {
            System.out.println("findById error: " + e.getMessage());
            return null;
        }
    }

    // ✅ for "load entire summary of all players"
    public List<Player> findAll() {
        String sql = "SELECT player_id, name, username, extra_attr FROM players ORDER BY player_id ASC";
        List<Player> list = new ArrayList<>();

        try (Connection c = DB.getConnection();
             PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                list.add(new Player(
                        rs.getInt("player_id"),
                        rs.getString("name"),
                        rs.getString("username"),
                        rs.getString("extra_attr")
                ));
            }

        } catch (Exception e) {
            System.out.println("findAll error: " + e.getMessage());
        }

        return list;
    }

    // ✅ for "delete player by id" (safe delete with transaction)
    public boolean deleteById(int playerId) {
        try (Connection c = DB.getConnection()) {
            c.setAutoCommit(false);

            try {
                // delete round_scores for that player's attempts
                try (PreparedStatement ps = c.prepareStatement(
                        "DELETE rs FROM round_scores rs JOIN results r ON rs.attempt_id = r.attempt_id WHERE r.player_id = ?")) {
                    ps.setInt(1, playerId);
                    ps.executeUpdate();
                }

                // delete attempts/results
                try (PreparedStatement ps = c.prepareStatement(
                        "DELETE FROM results WHERE player_id = ?")) {
                    ps.setInt(1, playerId);
                    ps.executeUpdate();
                }

                // delete player
                int affected;
                try (PreparedStatement ps = c.prepareStatement(
                        "DELETE FROM players WHERE player_id = ?")) {
                    ps.setInt(1, playerId);
                    affected = ps.executeUpdate();
                }

                c.commit();
                c.setAutoCommit(true);
                return affected > 0;

            } catch (Exception e) {
                c.rollback();
                c.setAutoCommit(true);
                System.out.println("deleteById error: " + e.getMessage());
                return false;
            }

        } catch (Exception e) {
            System.out.println("deleteById connection error: " + e.getMessage());
            return false;
        }
    }
}
