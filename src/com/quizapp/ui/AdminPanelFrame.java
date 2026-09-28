package com.quizapp.ui;

import com.quizapp.dao.QuestionDAO;
import com.quizapp.model.Question;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.List;

public class AdminPanelFrame extends JFrame {
    private DefaultTableModel model;
    private JTable table;

    public AdminPanelFrame() {
        setTitle("Admin Panel");
        setSize(950, 560);
        setLocationRelativeTo(null);
        setLayout(null);

        JLabel title = new JLabel("Admin Panel - Manage Questions");
        UITheme.styleTitle(title);
        title.setBounds(260, 15, 500, 40);
        add(title);

        model = new DefaultTableModel(new Object[]{
                "ID","Level","Question","A","B","C","D","Correct"
        }, 0);
        table = new JTable(model);
        JScrollPane sp = new JScrollPane(table);
        sp.setBounds(20, 80, 900, 340);
        add(sp);

        JButton btnLoad = new JButton("LOAD");
        UITheme.styleButton(btnLoad);
        btnLoad.setBounds(20, 440, 160, 45);
        add(btnLoad);

        JButton btnAdd = new JButton("ADD");
        UITheme.styleButton(btnAdd);
        btnAdd.setBounds(200, 440, 160, 45);
        add(btnAdd);

        JButton btnUpdate = new JButton("UPDATE");
        UITheme.styleButton(btnUpdate);
        btnUpdate.setBounds(380, 440, 160, 45);
        add(btnUpdate);

        JButton btnDelete = new JButton("DELETE");
        UITheme.styleButton(btnDelete);
        btnDelete.setBounds(560, 440, 160, 45);
        add(btnDelete);

        JButton btnClose = new JButton("CLOSE");
        UITheme.styleButton(btnClose);
        btnClose.setBounds(740, 440, 180, 45);
        add(btnClose);

        btnClose.addActionListener(e -> dispose());
        btnLoad.addActionListener(e -> loadAll());
        btnAdd.addActionListener(e -> addQuestion());
        btnUpdate.addActionListener(e -> updateQuestion());
        btnDelete.addActionListener(e -> deleteQuestion());

        loadAll();
    }

    private void loadAll() {
        model.setRowCount(0);
        QuestionDAO dao = new QuestionDAO();
        List<Question> list = dao.getAll();
        for (Question q : list) {
            model.addRow(new Object[]{
                    q.getQuestionId(), q.getLevel(), q.getText(),
                    q.getA(), q.getB(), q.getC(), q.getD(), q.getCorrect()
            });
        }
    }

    private Question readDialog(Question base) {
        JTextField txtLevel = new JTextField(base == null ? "beginner" : base.getLevel());
        JTextField txtQ = new JTextField(base == null ? "" : base.getText());
        JTextField txtA = new JTextField(base == null ? "" : base.getA());
        JTextField txtB = new JTextField(base == null ? "" : base.getB());
        JTextField txtC = new JTextField(base == null ? "" : base.getC());
        JTextField txtD = new JTextField(base == null ? "" : base.getD());
        JTextField txtCorrect = new JTextField(base == null ? "A" : base.getCorrect());

        Object[] fields = {
                "Level (beginner/intermediate/advanced):", txtLevel,
                "Question:", txtQ,
                "Option A:", txtA,
                "Option B:", txtB,
                "Option C:", txtC,
                "Option D:", txtD,
                "Correct (A/B/C/D):", txtCorrect
        };

        int ok = JOptionPane.showConfirmDialog(this, fields, "Question", JOptionPane.OK_CANCEL_OPTION);
        if (ok != JOptionPane.OK_OPTION) return null;

        String level = txtLevel.getText().trim().toLowerCase();
        String corr = txtCorrect.getText().trim().toUpperCase();

        if (!(level.equals("beginner") || level.equals("intermediate") || level.equals("advanced"))) {
            JOptionPane.showMessageDialog(this, "Invalid level");
            return null;
        }
        if (!(corr.equals("A") || corr.equals("B") || corr.equals("C") || corr.equals("D"))) {
            JOptionPane.showMessageDialog(this, "Invalid correct option");
            return null;
        }

        Question q = (base == null) ? new Question() : base;
        q.setLevel(level);
        q.setText(txtQ.getText().trim());
        q.setA(txtA.getText().trim());
        q.setB(txtB.getText().trim());
        q.setC(txtC.getText().trim());
        q.setD(txtD.getText().trim());
        q.setCorrect(corr);
        return q;
    }

    private void addQuestion() {
        Question q = readDialog(null);
        if (q == null) return;

        QuestionDAO dao = new QuestionDAO();
        if (dao.add(q)) {
            loadAll();
        } else {
            JOptionPane.showMessageDialog(this, "Add failed");
        }
    }

    private void updateQuestion() {
        int row = table.getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(this, "Select a row");
            return;
        }

        Question base = new Question(
                Integer.parseInt(model.getValueAt(row, 0).toString()),
                model.getValueAt(row, 1).toString(),
                model.getValueAt(row, 2).toString(),
                model.getValueAt(row, 3).toString(),
                model.getValueAt(row, 4).toString(),
                model.getValueAt(row, 5).toString(),
                model.getValueAt(row, 6).toString(),
                model.getValueAt(row, 7).toString()
        );

        Question updated = readDialog(base);
        if (updated == null) return;

        QuestionDAO dao = new QuestionDAO();
        if (dao.update(updated)) {
            loadAll();
        } else {
            JOptionPane.showMessageDialog(this, "Update failed");
        }
    }

    private void deleteQuestion() {
        int row = table.getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(this, "Select a row");
            return;
        }
        int id = Integer.parseInt(model.getValueAt(row, 0).toString());
        int ok = JOptionPane.showConfirmDialog(this, "Delete question ID " + id + " ?", "Confirm", JOptionPane.YES_NO_OPTION);
        if (ok != JOptionPane.YES_OPTION) return;

        QuestionDAO dao = new QuestionDAO();
        if (dao.delete(id)) {
            loadAll();
        } else {
            JOptionPane.showMessageDialog(this, "Delete failed");
        }
    }
}
