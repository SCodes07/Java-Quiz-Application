package com.quizapp.ui;

import com.quizapp.dao.ReportDAO;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.List;

public class LeaderboardFrame extends JFrame {

    private JTable table;
    private DefaultTableModel model;
    private JComboBox<String> cmbLevel;

    public LeaderboardFrame() {
        setTitle("Leaderboard");
        setSize(760, 540);
        setLocationRelativeTo(null);
        setLayout(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JLabel title = new JLabel("Leaderboard (Total Score)");
        UITheme.styleTitle(title);
        title.setBounds(190, 15, 450, 40);
        add(title);

        JLabel lbl = new JLabel("Level:");
        lbl.setBounds(30, 70, 60, 25);
        add(lbl);

        cmbLevel = new JComboBox<>(new String[]{"all", "beginner", "intermediate", "advanced"});
        cmbLevel.setBounds(90, 70, 180, 30);
        add(cmbLevel);

        JButton btnLoad = new JButton("LOAD");
        UITheme.styleButton(btnLoad);
        btnLoad.setBounds(290, 70, 140, 30);
        add(btnLoad);

        model = new DefaultTableModel(
                new Object[]{"Rank", "Player ID", "Name", "Username", "Total Score"}, 0
        );

        table = new JTable(model);
        JScrollPane sp = new JScrollPane(table);
        sp.setBounds(30, 120, 700, 320);
        add(sp);

        JButton btnClose = new JButton("CLOSE");
        UITheme.styleButton(btnClose);
        btnClose.setBounds(290, 455, 180, 40);
        add(btnClose);

        btnClose.addActionListener(e -> dispose());
        btnLoad.addActionListener(e -> loadLeaderboard());

        // load on open
        loadLeaderboard();
    }

    private void loadLeaderboard() {
        model.setRowCount(0);

        try {
            String level = cmbLevel.getSelectedItem().toString().toLowerCase();

            ReportDAO dao = new ReportDAO();
            List<ReportDAO.LeaderRow> rows = dao.leaderboard(level);

            if (rows.isEmpty()) {
                model.addRow(new Object[]{"-", "-", "No data yet", "-", "-"});
                return;
            }

            for (ReportDAO.LeaderRow r : rows) {
                model.addRow(new Object[]{r.rank, r.playerId, r.name, r.username, r.totalScore});
            }

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error loading leaderboard:\n" + ex.getMessage());
        }
    }
}
