package com.quizapp;

import com.quizapp.ui.LoginFrame;

import java.awt.EventQueue;

public class Main {
    public static void main(String[] args) {
        EventQueue.invokeLater(() -> new LoginFrame().setVisible(true));
    }
}
