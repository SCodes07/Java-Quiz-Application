package com.quizapp.ui;

import com.quizapp.dao.PlayerDAO;
import com.quizapp.dao.ReportDAO;
import com.quizapp.model.Player;

import javax.swing.*;

public class PlayerDetailFrame extends JFrame {
    private JTextField txtId;
    private JTextArea area;

    public PlayerDetailFrame() {
        setTitle("Player Detail");
        setSize(720, 520);
        setLocationRelativeTo(null);
        setLayout(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JLabel title = new JLabel("Player Detail (Search by ID)");
        UITheme.styleTitle(title);
        title.setBounds(170, 15, 500, 40);
        add(title);

        JLabel l = new JLabel("Player ID:");
        l.setBounds(30, 80, 100, 30);
        add(l);

        txtId = new JTextField();
        txtId.setBounds(110, 80, 160, 30);
        add(txtId);

        JButton btnSearch = new JButton("SEARCH");
        UITheme.styleButton(btnSearch);
        btnSearch.setBounds(290, 75, 160, 40);
        add(btnSearch);

        JButton btnClose = new JButton("CLOSE");
        UITheme.styleButton(btnClose);
        btnClose.setBounds(470, 75, 160, 40);
        add(btnClose);

        area = new JTextArea();
        area.setEditable(false);
        JScrollPane sp = new JScrollPane(area);
        sp.setBounds(30, 140, 640, 320);
        add(sp);

        btnSearch.addActionListener(e -> search());
        btnClose.addActionListener(e -> dispose());

        getRootPane().setDefaultButton(btnSearch);
    }

    private void search() {
        String s = txtId.getText().trim();
        int id;

        try {
            id = Integer.parseInt(s);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Enter valid numeric ID");
            return;
        }

        try {
            PlayerDAO pdao = new PlayerDAO();
            Player p = pdao.findById(id);

            if (p == null) {
                area.setText("No player found for ID: " + id);
                return;
            }

            ReportDAO rdao = new ReportDAO();

            StringBuilder sb = new StringBuilder();
            sb.append("Full Details\n");
            sb.append("-----------\n");
            sb.append("PlayerID: ").append(p.getPlayerId()).append("\n");
            sb.append("Name: ").append(p.getName()).append("\n");
            sb.append("Username: ").append(p.getUsername()).append("\n");
            sb.append("Extra: ").append(p.getExtraAttr()).append("\n\n");

            // ✅ If your ReportDAO has scoreFrequencyForPlayer()
            // ✅ otherwise fallback to playerScoreSummary()
            String statsText;
            try {
            	statsText = rdao.playerScoreSummary(id);
            } catch (Exception ex) {
                statsText = rdao.playerScoreSummary(id);
            }

            sb.append(statsText);
            area.setText(sb.toString());

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error:\n" + ex.getMessage());
        }
    }
}
