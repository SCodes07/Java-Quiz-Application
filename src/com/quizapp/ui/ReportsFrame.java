package com.quizapp.ui;

import com.quizapp.dao.ReportDAO;

import javax.swing.*;

public class ReportsFrame extends JFrame {
    public ReportsFrame() {
        setTitle("Reports");
        setSize(740, 540);
        setLocationRelativeTo(null);
        setLayout(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JLabel title = new JLabel("Reports / Summary");
        UITheme.styleTitle(title);
        title.setBounds(260, 15, 300, 40);
        add(title);

        JTextArea area = new JTextArea();
        area.setEditable(false);
        JScrollPane sp = new JScrollPane(area);
        sp.setBounds(30, 80, 660, 360);
        add(sp);

        JButton btnLoad = new JButton("LOAD SUMMARY");
        UITheme.styleButton(btnLoad);
        btnLoad.setBounds(140, 455, 200, 40);
        add(btnLoad);

        JButton btnClose = new JButton("CLOSE");
        UITheme.styleButton(btnClose);
        btnClose.setBounds(360, 455, 200, 40);
        add(btnClose);

        btnClose.addActionListener(e -> dispose());

        btnLoad.addActionListener(e -> {
            try {
                ReportDAO dao = new ReportDAO();
                area.setText(dao.statisticalSummary());
            } catch (Exception ex) {
                area.setText("Error loading summary:\n" + ex.getMessage());
            }
        });
    }
}
