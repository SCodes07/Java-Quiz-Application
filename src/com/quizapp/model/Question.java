package com.quizapp.model;

public class Question {
    private int questionId;
    private String level;
    private String text;
    private String a, b, c, d;
    private String correct; // "A" "B" "C" "D"

    public Question() {}

    public Question(int questionId, String level, String text, String a, String b, String c, String d, String correct) {
        this.questionId = questionId;
        this.level = level;
        this.text = text;
        this.a = a; this.b = b; this.c = c; this.d = d;
        this.correct = correct;
    }

    public int getQuestionId() { return questionId; }
    public void setQuestionId(int questionId) { this.questionId = questionId; }

    public String getLevel() { return level; }
    public void setLevel(String level) { this.level = level; }

    public String getText() { return text; }
    public void setText(String text) { this.text = text; }

    public String getA() { return a; }
    public void setA(String a) { this.a = a; }

    public String getB() { return b; }
    public void setB(String b) { this.b = b; }

    public String getC() { return c; }
    public void setC(String c) { this.c = c; }

    public String getD() { return d; }
    public void setD(String d) { this.d = d; }

    public String getCorrect() { return correct; }
    public void setCorrect(String correct) { this.correct = correct; }
}
