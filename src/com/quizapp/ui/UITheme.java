package com.quizapp.ui;

import javax.swing.*;
import java.awt.*;

public class UITheme {
    public static final Color PURPLE = new Color(48, 16, 92);
    public static final Color WHITE = Color.WHITE;

    public static void styleButton(JButton b) {
        b.setBackground(PURPLE);
        b.setForeground(WHITE);
        b.setFocusPainted(false);
        b.setFont(new Font("Segoe UI", Font.BOLD, 16));
    }

    public static void styleTitle(JLabel l) {
        l.setFont(new Font("Segoe UI", Font.BOLD, 24));
    }

    public static void styleCard(JPanel p) {
        p.setBackground(new Color(245, 245, 245));
        p.setBorder(BorderFactory.createLineBorder(new Color(220, 220, 220)));
    }
}
