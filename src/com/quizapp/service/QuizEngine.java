package com.quizapp.service;

import com.quizapp.dao.QuestionDAO;
import com.quizapp.dao.RoundScoreDAO;
import com.quizapp.model.Question;

import java.util.List;

public class QuizEngine {
    public static final int TOTAL_ROUNDS = 5;
    public static final int QUESTIONS_PER_ROUND = 5;

    private final QuestionDAO questionDAO = new QuestionDAO();
    private final RoundScoreDAO roundScoreDAO = new RoundScoreDAO();

    private List<Question> currentQuestions;
    private int index;
    private int score;

    public void startRound(String level) {
        currentQuestions = questionDAO.getRandomQuestions(level, QUESTIONS_PER_ROUND);
        index = 0;
        score = 0;
    }

    public Question getCurrentQuestion() {
        if (currentQuestions == null || currentQuestions.isEmpty()) return null;
        if (index < 0 || index >= currentQuestions.size()) return null;
        return currentQuestions.get(index);
    }

    public int getQuestionNumber() { return index + 1; }
    public int getScore() { return score; }

    public boolean answer(String chosenOpt) {
        Question q = getCurrentQuestion();
        if (q == null) return false;
        if (chosenOpt != null && chosenOpt.equalsIgnoreCase(q.getCorrect())) {
            score++;
            return true;
        }
        return false;
    }

    public boolean next() {
        index++;
        return currentQuestions != null && index < currentQuestions.size();
    }

    public boolean saveRound(int playerId, String level, int roundNo) {
        return roundScoreDAO.saveRound(playerId, level, roundNo, score, QUESTIONS_PER_ROUND);
    }
}
