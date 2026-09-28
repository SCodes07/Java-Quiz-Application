package com.quizapp.dao;

import com.quizapp.db.DB;
import com.quizapp.model.Question;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class QuestionDAO {

    public List<Question> getRandomQuestions(String level, int limit) {
        List<Question> list = new ArrayList<>();
        String sql = "SELECT * FROM questions WHERE level = ? ORDER BY RAND() LIMIT ?";
        try (Connection c = DB.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, level);
            ps.setInt(2, limit);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(new Question(
                            rs.getInt("question_id"),
                            rs.getString("level"),
                            rs.getString("question_text"),
                            rs.getString("opt_a"),
                            rs.getString("opt_b"),
                            rs.getString("opt_c"),
                            rs.getString("opt_d"),
                            rs.getString("correct_opt")
                    ));
                }
            }
        } catch (Exception e) {
            return list;
        }
        return list;
    }

    public List<Question> getAll() {
        List<Question> list = new ArrayList<>();
        String sql = "SELECT * FROM questions ORDER BY question_id DESC";
        try (Connection c = DB.getConnection();
             PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                list.add(new Question(
                        rs.getInt("question_id"),
                        rs.getString("level"),
                        rs.getString("question_text"),
                        rs.getString("opt_a"),
                        rs.getString("opt_b"),
                        rs.getString("opt_c"),
                        rs.getString("opt_d"),
                        rs.getString("correct_opt")
                ));
            }
        } catch (Exception e) {
            return list;
        }
        return list;
    }

    public boolean add(Question q) {
        String sql = "INSERT INTO questions(level, question_text, opt_a, opt_b, opt_c, opt_d, correct_opt) VALUES(?,?,?,?,?,?,?)";
        try (Connection c = DB.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, q.getLevel());
            ps.setString(2, q.getText());
            ps.setString(3, q.getA());
            ps.setString(4, q.getB());
            ps.setString(5, q.getC());
            ps.setString(6, q.getD());
            ps.setString(7, q.getCorrect());
            return ps.executeUpdate() == 1;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean update(Question q) {
        String sql = "UPDATE questions SET level=?, question_text=?, opt_a=?, opt_b=?, opt_c=?, opt_d=?, correct_opt=? WHERE question_id=?";
        try (Connection c = DB.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, q.getLevel());
            ps.setString(2, q.getText());
            ps.setString(3, q.getA());
            ps.setString(4, q.getB());
            ps.setString(5, q.getC());
            ps.setString(6, q.getD());
            ps.setString(7, q.getCorrect());
            ps.setInt(8, q.getQuestionId());
            return ps.executeUpdate() == 1;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean delete(int questionId) {
        String sql = "DELETE FROM questions WHERE question_id=?";
        try (Connection c = DB.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, questionId);
            return ps.executeUpdate() == 1;
        } catch (Exception e) {
            return false;
        }
    }
}
