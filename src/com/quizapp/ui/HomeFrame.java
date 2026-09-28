package com.quizapp.ui;

import com.quizapp.model.Player;

import javax.swing.*;

public class HomeFrame extends JFrame {
    private final Player player;

    public HomeFrame(Player player) {
        this.player = player;

        setTitle("Homepage");
        setSize(700, 480);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setLayout(null);

        JLabel lbl = new JLabel("Homepage");
        UITheme.styleTitle(lbl);
        lbl.setBounds(280, 20, 300, 40);
        getContentPane().add(lbl);

        JLabel hello = new JLabel("Welcome, " + player.getName() + "  (ID: " + player.getPlayerId() + ")");
        hello.setBounds(30, 70, 600, 25);
        getContentPane().add(hello);

        JButton btnStart = new JButton("START QUIZ");
        UITheme.styleButton(btnStart);
        btnStart.setBounds(60, 120, 250, 55);
        getContentPane().add(btnStart);

        JButton btnPlayers = new JButton("PLAYER DETAIL");
        UITheme.styleButton(btnPlayers);
        btnPlayers.setBounds(209, 196, 250, 55);
        getContentPane().add(btnPlayers);

        JButton btnHigh = new JButton("LEADERBOARD");
        UITheme.styleButton(btnHigh);
        btnHigh.setBounds(60, 280, 250, 55);
        getContentPane().add(btnHigh);

        JButton btnReports = new JButton("VIEW REPORTS");
        UITheme.styleButton(btnReports);
        btnReports.setBounds(360, 120, 250, 55);
        getContentPane().add(btnReports);

        JButton btnLogout = new JButton("LOGOUT");
        UITheme.styleButton(btnLogout);
        btnLogout.setBounds(360, 280, 250, 55);
        getContentPane().add(btnLogout);

        btnStart.addActionListener(e -> {
            new QuizFrame(player).setVisible(true);
            dispose();
        });

        btnHigh.addActionListener(e -> new LeaderboardFrame().setVisible(true));
        btnPlayers.addActionListener(e -> new PlayerDetailFrame().setVisible(true));
        btnReports.addActionListener(e -> new ReportsFrame().setVisible(true));

        btnLogout.addActionListener(e -> {
            new LoginFrame().setVisible(true);
            dispose();
        });
    }
}
