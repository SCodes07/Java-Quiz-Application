package com.quizapp.ui;

import com.quizapp.dao.PlayerDAO;
import com.quizapp.model.Player;

import javax.swing.*;
import java.awt.*;

public class LoginFrame extends JFrame {
    private JTextField txtUser;
    private JPasswordField txtPass;

    public LoginFrame() {
        setTitle("Login");
        setSize(520, 420);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);

        JLabel lblTitle = new JLabel("Login");
        UITheme.styleTitle(lblTitle);
        lblTitle.setBounds(210, 30, 200, 40);
        add(lblTitle);

        JPanel card = new JPanel(null);
        UITheme.styleCard(card);
        card.setBounds(60, 90, 390, 260);
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

        JButton btnRegister = new JButton("REGISTER");
        UITheme.styleButton(btnRegister);
        btnRegister.setBounds(200, 150, 160, 40);
        card.add(btnRegister);

        btnLogin.addActionListener(e -> doLogin());
        btnRegister.addActionListener(e -> {
            new RegisterFrame().setVisible(true);
            dispose();
        });

        JButton btnAdmin = new JButton("ADMIN LOGIN");
        UITheme.styleButton(btnAdmin);
        btnAdmin.setBounds(115, 200, 160, 40); // Centered below other buttons
        card.add(btnAdmin);

        btnAdmin.addActionListener(e -> {
            new AdminLoginFrame().setVisible(true);
            dispose();
        });
    }
    
    private void doLogin() {
        String u = txtUser.getText().trim();
        String p = new String(txtPass.getPassword()).trim();

        if (u.isEmpty() || p.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Enter username and password");
            return;
        }

        PlayerDAO dao = new PlayerDAO();
        Player player = dao.login(u, p);
        if (player == null) {
            JOptionPane.showMessageDialog(this, "Invalid credentials");
            return;
        }

        new HomeFrame(player).setVisible(true);
        dispose();
    }
}
