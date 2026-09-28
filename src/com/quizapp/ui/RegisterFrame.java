package com.quizapp.ui;

import com.quizapp.dao.PlayerDAO;
import com.quizapp.model.Player;

import javax.swing.*;

public class RegisterFrame extends JFrame {
    private JTextField txtName, txtUser, txtExtra;
    private JPasswordField txtPass;

    public RegisterFrame() {
        setTitle("Register");
        setSize(560, 470);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);

        JLabel lblTitle = new JLabel("Register");
        UITheme.styleTitle(lblTitle);
        lblTitle.setBounds(220, 30, 200, 40);
        add(lblTitle);

        JPanel card = new JPanel(null);
        UITheme.styleCard(card);
        card.setBounds(70, 90, 410, 280);
        add(card);

        JLabel l1 = new JLabel("Name:");
        l1.setBounds(30, 30, 140, 30);
        card.add(l1);

        txtName = new JTextField();
        txtName.setBounds(160, 30, 220, 30);
        card.add(txtName);

        JLabel l2 = new JLabel("Username:");
        l2.setBounds(30, 75, 140, 30);
        card.add(l2);

        txtUser = new JTextField();
        txtUser.setBounds(160, 75, 220, 30);
        card.add(txtUser);

        JLabel l3 = new JLabel("Password:");
        l3.setBounds(30, 120, 140, 30);
        card.add(l3);

        txtPass = new JPasswordField();
        txtPass.setBounds(160, 120, 220, 30);
        card.add(txtPass);

        JLabel l4 = new JLabel("Extra (e.g., Country):");
        l4.setBounds(30, 165, 140, 30);
        card.add(l4);

        txtExtra = new JTextField();
        txtExtra.setBounds(160, 165, 220, 30);
        card.add(txtExtra);

        JButton btnCreate = new JButton("CREATE");
        UITheme.styleButton(btnCreate);
        btnCreate.setBounds(30, 220, 170, 40);
        card.add(btnCreate);

        JButton btnBack = new JButton("BACK");
        UITheme.styleButton(btnBack);
        btnBack.setBounds(210, 220, 170, 40);
        card.add(btnBack);

        btnCreate.addActionListener(e -> doRegister());
        btnBack.addActionListener(e -> {
            new LoginFrame().setVisible(true);
            dispose();
        });
    }

    private void doRegister() {
        String name = txtName.getText().trim();
        String user = txtUser.getText().trim();
        String pass = new String(txtPass.getPassword()).trim();
        String extra = txtExtra.getText().trim();
        if (extra.isEmpty()) extra = "N/A";

        if (name.isEmpty() || user.isEmpty() || pass.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Fill all required fields");
            return;
        }

        PlayerDAO dao = new PlayerDAO();
        if (dao.usernameExists(user)) {
            JOptionPane.showMessageDialog(this, "User already exists");
            return;
        }

        Player p = new Player();
        p.setName(name);
        p.setUsername(user);
        p.setPassword(pass);
        p.setExtraAttr(extra);

        if (!dao.register(p)) {
            JOptionPane.showMessageDialog(this, "Registration failed");
            return;
        }

        JOptionPane.showMessageDialog(this, "Registered successfully. Login now.");
        new LoginFrame().setVisible(true);
        dispose();
    }
}
