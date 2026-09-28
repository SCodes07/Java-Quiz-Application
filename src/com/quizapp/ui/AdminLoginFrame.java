package com.quizapp.ui;

import com.quizapp.db.DB;

import javax.swing.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class AdminLoginFrame extends JFrame {
    private JTextField txtUser;
    private JPasswordField txtPass;

    public AdminLoginFrame() {
        setTitle("Admin Login");
        setSize(520, 420);
        setLocationRelativeTo(null);
        setLayout(null);

        JLabel lblTitle = new JLabel("Admin Login");
        UITheme.styleTitle(lblTitle);
        lblTitle.setBounds(180, 30, 250, 40);
        add(lblTitle);

        JPanel card = new JPanel(null);
        UITheme.styleCard(card);
        card.setBounds(60, 90, 390, 220);
        add(card);

        JLabel l1 = new JLabel("Username:");
        l1.setBounds(30, 40, 120, 30);
        card.add(l1);

        txtUser = new JTextField();
        txtUser.setBounds(140, 40, 220, 30);
        card.add(txtUser);

        JLabel l2 = new JLabel("Password:");
        l2.setBounds(30, 90, 120, 30);
        card.add(l2);

        txtPass = new JPasswordField();
        txtPass.setBounds(140, 90, 220, 30);
        card.add(txtPass);

        JButton btnLogin = new JButton("LOGIN");
        UITheme.styleButton(btnLogin);
        btnLogin.setBounds(30, 150, 160, 40);
        card.add(btnLogin);

        JButton btnBack = new JButton("BACK");
        UITheme.styleButton(btnBack);
        btnBack.setBounds(200, 150, 160, 40);
        card.add(btnBack);

        btnBack.addActionListener(e -> {
            new LoginFrame().setVisible(true);
            dispose();
        });
        btnLogin.addActionListener(e -> doLogin());
    }

    private void doLogin() {
        String u = txtUser.getText().trim();
        String p = new String(txtPass.getPassword()).trim();
        if (u.isEmpty() || p.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Enter username and password");
            return;
        }

        String sql = "SELECT 1 FROM admins WHERE username=? AND password=?";
        try (Connection c = DB.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, u);
            ps.setString(2, p);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    new AdminPanelFrame().setVisible(true);
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(this, "Invalid admin credentials");
                }
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "DB error: " + ex.getMessage());
        }
    }
}
