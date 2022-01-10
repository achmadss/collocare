package com.susiapp.collocare.Models;

import android.content.SharedPreferences;

public class User {

    String username;
    String lastChapterReadTitle;
    String lastQuizTakenTitle;

    String dateLastChapterRead;
    String dateLastQuizTaken;

    String timeUpdated;

    int chapterReadCount;
    int quizTakenTotalCount;
    int quizTakenTypeOneCount;
    int quizTakenTypeTwoCount;
    int quizTakenTypeThreeCount;

    int quizTypeOneCorrect;
    int quizTypeTwoCorrect;
    int quizTypeThreeCorrect;

    int quizTypeOneIncorrect;
    int quizTypeTwoIncorrect;
    int quizTypeThreeIncorrect;

    public User(SharedPreferences prefs) {
        this.username = prefs.getString("username", "");
        this.lastChapterReadTitle = prefs.getString("lastChapterReadTitle", "Not Available");
        this.lastQuizTakenTitle = prefs.getString("lastQuizTakenTitle", "Not Available");

        this.dateLastChapterRead = prefs.getString("dateLastChapterRead", "Not Available");
        this.dateLastQuizTaken = prefs.getString("dateLastQuizTaken", "Not Available");

        this.timeUpdated = prefs.getString("timeUpdated", "Not Available");

        this.chapterReadCount = prefs.getInt("chapterReadCount", 0);
        this.quizTakenTotalCount = prefs.getInt("quizTakenTotalCount", 0);
        this.quizTakenTypeOneCount = prefs.getInt("quizTakenTypeOneCount", 0);
        this.quizTakenTypeTwoCount = prefs.getInt("quizTakenTypeTwoCount", 0);
        this.quizTakenTypeThreeCount = prefs.getInt("quizTakenTypeThreeCount", 0);

        this.quizTypeOneCorrect = prefs.getInt("quizTypeOneCorrect", 0);
        this.quizTypeTwoCorrect = prefs.getInt("quizTypeTwoCorrect", 0);
        this.quizTypeThreeCorrect = prefs.getInt("quizTypeThreeCorrect", 0);

        this.quizTypeOneIncorrect = prefs.getInt("quizTypeOneIncorrect", 0);
        this.quizTypeTwoIncorrect = prefs.getInt("quizTypeTwoIncorrect", 0);
        this.quizTypeThreeIncorrect = prefs.getInt("quizTypeThreeIncorrect", 0);

    }

    public void setTimeUpdated(String timeUpdated) {
        this.timeUpdated = timeUpdated;
    }

    public String getLastChapterReadTitle() {
        return lastChapterReadTitle;
    }

    public void setLastChapterReadTitle(String lastChapterReadTitle) {
        this.lastChapterReadTitle = lastChapterReadTitle;
    }

    public String getLastQuizTakenTitle() {
        return lastQuizTakenTitle;
    }

    public void setLastQuizTakenTitle(String lastQuizTakenTitle) {
        this.lastQuizTakenTitle = lastQuizTakenTitle;
    }

    public String getDateLastChapterRead() {
        return dateLastChapterRead;
    }

    public void setDateLastChapterRead(String dateLastChapterRead) {
        this.dateLastChapterRead = dateLastChapterRead;
    }

    public String getDateLastQuizTaken() {
        return dateLastQuizTaken;
    }

    public void setDateLastQuizTaken(String dateLastQuizTaken) {
        this.dateLastQuizTaken = dateLastQuizTaken;
    }

    public int getChapterReadCount() {
        return chapterReadCount;
    }

    public void setChapterReadCount(int chapterReadCount) {
        this.chapterReadCount = chapterReadCount;
    }

    public int getQuizTakenTotalCount() {
        return quizTakenTotalCount;
    }

    public void setQuizTakenTotalCount(int quizTakenTotalCount) {
        this.quizTakenTotalCount = quizTakenTotalCount;
    }

    public int getQuizTakenTypeOneCount() {
        return quizTakenTypeOneCount;
    }

    public void setQuizTakenTypeOneCount(int quizTakenTypeOneCount) {
        this.quizTakenTypeOneCount = quizTakenTypeOneCount;
    }

    public int getQuizTakenTypeTwoCount() {
        return quizTakenTypeTwoCount;
    }

    public void setQuizTakenTypeTwoCount(int quizTakenTypeTwoCount) {
        this.quizTakenTypeTwoCount = quizTakenTypeTwoCount;
    }

    public int getQuizTakenTypeThreeCount() {
        return quizTakenTypeThreeCount;
    }

    public void setQuizTakenTypeThreeCount(int quizTakenTypeThreeCount) {
        this.quizTakenTypeThreeCount = quizTakenTypeThreeCount;
    }

    public int getQuizTypeOneCorrect() {
        return quizTypeOneCorrect;
    }

    public void setQuizTypeOneCorrect(int quizTypeOneCorrect) {
        this.quizTypeOneCorrect = quizTypeOneCorrect;
    }

    public int getQuizTypeTwoCorrect() {
        return quizTypeTwoCorrect;
    }

    public void setQuizTypeTwoCorrect(int quizTypeTwoCorrect) {
        this.quizTypeTwoCorrect = quizTypeTwoCorrect;
    }

    public int getQuizTypeThreeCorrect() {
        return quizTypeThreeCorrect;
    }

    public void setQuizTypeThreeCorrect(int quizTypeThreeCorrect) {
        this.quizTypeThreeCorrect = quizTypeThreeCorrect;
    }

    public int getQuizTypeOneIncorrect() {
        return quizTypeOneIncorrect;
    }

    public void setQuizTypeOneIncorrect(int quizTypeOneIncorrect) {
        this.quizTypeOneIncorrect = quizTypeOneIncorrect;
    }

    public int getQuizTypeTwoIncorrect() {
        return quizTypeTwoIncorrect;
    }

    public void setQuizTypeTwoIncorrect(int quizTypeTwoIncorrect) {
        this.quizTypeTwoIncorrect = quizTypeTwoIncorrect;
    }

    public int getQuizTypeThreeIncorrect() {
        return quizTypeThreeIncorrect;
    }

    public void setQuizTypeThreeIncorrect(int quizTypeThreeIncorrect) {
        this.quizTypeThreeIncorrect = quizTypeThreeIncorrect;
    }
}
