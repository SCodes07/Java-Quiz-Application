package com.quizapp.ui;

import com.quizapp.model.Player;
import com.quizapp.model.Question;
import com.quizapp.service.QuizEngine;

import javax.swing.*;
import java.awt.*;

public class QuizFrame extends JFrame {
    private final Player player;
    private final QuizEngine engine = new QuizEngine();

    private JComboBox<String> cmbLevel;
    private JLabel lblRound, lblQno, lblScore;
    private JTextArea txtQuestion;
    private JRadioButton rA, rB, rC, rD;
    private ButtonGroup group;

    private JButton btnStartRound, btnNext, btnFinishRound, btnBack;

    private int roundNo = 1;

    public QuizFrame(Player player) {
        this.player = player;

        setTitle("Quiz");
        setSize(820, 560);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);

        JLabel title = new JLabel("Quiz Page");
        UITheme.styleTitle(title);
        title.setBounds(340, 15, 200, 40);
        add(title);

        JLabel lLevel = new JLabel("Level:");
        lLevel.setBounds(30, 70, 80, 25);
        add(lLevel);

        cmbLevel = new JComboBox<>(new String[]{"beginner","intermediate","advanced"});
        cmbLevel.setBounds(90, 70, 170, 28);
        add(cmbLevel);

        btnStartRound = new JButton("START ROUND");
        UITheme.styleButton(btnStartRound);
        btnStartRound.setBounds(280, 65, 190, 40);
        add(btnStartRound);

        lblRound = new JLabel("Round: 1/5");
        lblRound.setBounds(30, 115, 200, 25);
        add(lblRound);

        lblQno = new JLabel("Question: -");
        lblQno.setBounds(240, 115, 200, 25);
        add(lblQno);

        lblScore = new JLabel("Score: 0");
        lblScore.setBounds(470, 115, 200, 25);
        add(lblScore);

        txtQuestion = new JTextArea();
        txtQuestion.setLineWrap(true);
        txtQuestion.setWrapStyleWord(true);
        txtQuestion.setEditable(false);
        JScrollPane sp = new JScrollPane(txtQuestion);
        sp.setBounds(30, 150, 740, 120);
        add(sp);

        rA = new JRadioButton("A");
        rB = new JRadioButton("B");
        rC = new JRadioButton("C");
        rD = new JRadioButton("D");
        rA.setBounds(50, 290, 700, 30);
        rB.setBounds(50, 330, 700, 30);
        rC.setBounds(50, 370, 700, 30);
        rD.setBounds(50, 410, 700, 30);
        add(rA); add(rB); add(rC); add(rD);

        group = new ButtonGroup();
        group.add(rA); group.add(rB); group.add(rC); group.add(rD);

        btnNext = new JButton("NEXT");
        UITheme.styleButton(btnNext);
        btnNext.setBounds(30, 460, 170, 45);
        add(btnNext);

        btnFinishRound = new JButton("FINISH ROUND");
        UITheme.styleButton(btnFinishRound);
        btnFinishRound.setBounds(220, 460, 200, 45);
        add(btnFinishRound);

        btnBack = new JButton("BACK");
        UITheme.styleButton(btnBack);
        btnBack.setBounds(600, 460, 170, 45);
        add(btnBack);

        setControlsEnabled(false);

        btnStartRound.addActionListener(e -> startRound());
        btnNext.addActionListener(e -> nextQuestion());
        btnFinishRound.addActionListener(e -> finishRound());
        btnBack.addActionListener(e -> {
            new HomeFrame(player).setVisible(true);
            dispose();
        });
    }

    private void setControlsEnabled(boolean enabled) {
        rA.setEnabled(enabled);
        rB.setEnabled(enabled);
        rC.setEnabled(enabled);
        rD.setEnabled(enabled);
        btnNext.setEnabled(enabled);
        btnFinishRound.setEnabled(enabled);
    }

    private void startRound() {
        String level = (String) cmbLevel.getSelectedItem();
        engine.startRound(level);
        roundNo = Math.max(1, Math.min(roundNo, QuizEngine.TOTAL_ROUNDS));
        lblRound.setText("Round: " + roundNo + "/5");
        lblScore.setText("Score: 0");
        loadQuestion();
        setControlsEnabled(true);
    }

    private void loadQuestion() {
        Question q = engine.getCurrentQuestion();
        if (q == null) {
            JOptionPane.showMessageDialog(this, "Not enough questions in DB for this level.");
            setControlsEnabled(false);
            return;
        }

        lblQno.setText("Question: " + engine.getQuestionNumber() + "/5");
        txtQuestion.setText(q.getText());
        rA.setText("A) " + q.getA());
        rB.setText("B) " + q.getB());
        rC.setText("C) " + q.getC());
        rD.setText("D) " + q.getD());
        group.clearSelection();
    }

    private String chosenOption() {
        if (rA.isSelected()) return "A";
        if (rB.isSelected()) return "B";
        if (rC.isSelected()) return "C";
        if (rD.isSelected()) return "D";
        return null;
    }

    private void nextQuestion() {
        String ch = chosenOption();
        if (ch == null) {
            JOptionPane.showMessageDialog(this, "Select an option first");
            return;
        }

        engine.answer(ch);
        lblScore.setText("Score: " + engine.getScore());

        boolean hasNext = engine.next();
        if (hasNext) {
            loadQuestion();
        } else {
            JOptionPane.showMessageDialog(this, "End of questions. Click FINISH ROUND.");
        }
    }

    private void finishRound() {
        String level = (String) cmbLevel.getSelectedItem();
        boolean saved = engine.saveRound(player.getPlayerId(), level, roundNo);

        JOptionPane.showMessageDialog(this,
                "Round " + roundNo + " finished.\nScore: " + engine.getScore() + "/5\nSaved: " + saved);

        roundNo++;
        if (roundNo > QuizEngine.TOTAL_ROUNDS) {
            JOptionPane.showMessageDialog(this, "All 5 rounds completed!");
            new HomeFrame(player).setVisible(true);
            dispose();
            return;
        }

        setControlsEnabled(false);
        txtQuestion.setText("");
        group.clearSelection();
        lblRound.setText("Round: " + roundNo + "/5");
        lblQno.setText("Question: -");
        lblScore.setText("Score: 0");
    }
}
