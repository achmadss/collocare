package com.susiapp.collocare.Models;

import java.util.ArrayList;

public class Quiz {

    public static final int QUIZ_MULTIPLE_CHOICE = 712;
    public static final int QUIZ_UNDERSCORE = 657;
    public static final int QUIZ_FILL_IN_THE_BLANKS = 760;

    private String quizNumber;
    private String quizTitle;
    private String quizType;
    private int quizTypeConst;
    private ArrayList<String> imageURLS;

    private String imageQuiz;
    private ArrayList<String> optionsQuiz;
    private String answerQuiz;

    // multiple choice
    public Quiz(
            String quizNumber,
            String quizTitle,
            String quizType,
            ArrayList<String> imageURLS,
            String imageQuiz,
            ArrayList<String> optionsQuiz,
            String answerQuiz) {
        setQuizNumber(quizNumber);
        setQuizTitle(quizTitle);
        setQuizType(quizType);
        setImageURLS(imageURLS);
        setImageQuiz(imageQuiz);
        setOptionsQuiz(optionsQuiz);
        setAnswerQuiz(answerQuiz);
//        this.quizNumber = quizNumber;
//        this.quizTitle = quizTitle;
//        this.quizType = quizType;
//        this.imageURLS = imageURLS;
//        this.imageQuiz = imageQuiz;
//        this.optionsQuiz = optionsQuiz;
//        this.answerQuiz = answerQuiz;

    }

    // underscore and fill in the blank
    public Quiz(
            String quizNumber,
            String quizTitle,
            String quizType,
            ArrayList<String> imageURLS,
            String imageQuiz,
            String answerQuiz) {
        setQuizNumber(quizNumber);
        setQuizTitle(quizTitle);
        setQuizType(quizType);
        setImageURLS(imageURLS);
        setImageQuiz(imageQuiz);
        setAnswerQuiz(answerQuiz);
//        this.quizNumber = quizNumber;
//        this.quizTitle = quizTitle;
//        this.quizType = quizType;
//        this.imageURLS = imageURLS;
//        this.imageQuiz = imageQuiz;
//        this.answerQuiz = answerQuiz;
    }

    public String getImageQuiz() {
        return imageQuiz;
    }

    public void setImageQuiz(String imageQuiz) {
        this.imageQuiz = imageQuiz;
    }

    public ArrayList<String> getOptionsQuiz() {
        return optionsQuiz;
    }

    public void setOptionsQuiz(ArrayList<String> optionsQuiz) {
        this.optionsQuiz = optionsQuiz;
    }

    public String getAnswerQuiz() {
        return answerQuiz;
    }

    public void setAnswerQuiz(String answerQuiz) {
        this.answerQuiz = answerQuiz;
    }

    public String getQuizType() {
        return quizType;
    }

    public void setQuizType(String quizType) {
        this.quizType = quizType;
    }

    public int getQuizTypeConst() {
        if(quizType.equals("1")) {
            quizTypeConst = QUIZ_MULTIPLE_CHOICE;
        } else if(quizType.equals("2")) {
            quizTypeConst = QUIZ_UNDERSCORE;
        } else if(quizType.equals("3")) {
            quizTypeConst = QUIZ_FILL_IN_THE_BLANKS;
        }
        return quizTypeConst;
    }

    public String getQuizNumber() {
        return quizNumber;
    }

    public void setQuizNumber(String quizNumber) {
        this.quizNumber = quizNumber;
    }

    public String getQuizTitle() {
        return quizTitle;
    }

    public void setQuizTitle(String quizTitle) {
        this.quizTitle = quizTitle;
    }

    public ArrayList<String> getImageURLS() {
        return imageURLS;
    }

    public void setImageURLS(ArrayList<String> imageURLS) {
        this.imageURLS = imageURLS;
    }
}
