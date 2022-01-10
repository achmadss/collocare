package com.susiapp.collocare.Models;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class Globals {

    private String username;

    private int numberOfChapters;

//    private int numberOfQuizzes;
//    private int numberOfQuizTypeOne;
//    private int numberOfQuizTypeTwo;
//    private int numberOfQuizTypeThree;

    private String jsonDataStory;
    private String jsonDataQuiz;
    private ArrayList<Chapter> chapters = new ArrayList<>();
//    private ArrayList<Quiz> quizzes = new ArrayList<>();
    private ArrayList<Quiz> quizTypeOne = new ArrayList<>();
    private ArrayList<Quiz> quizTypeTwo = new ArrayList<>();
    private ArrayList<Quiz> quizTypeThree = new ArrayList<>();

    private Chapter currentChapter;
    private Quiz currentQuiz;

    public String rootPath = Environment.getExternalStorageDirectory().getPath()
            + "/Android/data/com.susiapp.collocare/";

    public String getTime() {
        Date date = Calendar.getInstance().getTime();
        SimpleDateFormat format = new SimpleDateFormat("EEE, d MMM y H:m:s Z", Locale.getDefault());
        String df = format.format(date);
        return df;
    }

    public String logActivity(String activityName) {
        return "[" + getTime() + "]" + "\nUser opened " + activityName + "\n";
//        Log.e("", getTime());
    }

    public String logReadChapter(String time, String title, String number) {
        return "[" + time + "]" + "\nUser opened CHAPTER :\n" +
                "Title   : \"" + title + "\"\n" +
                "Number  : " + number + "\n";
    }

    public String logQuizAnswer(String time, String title, String number, String type, boolean answer, int count) {
        String quizType = "";
        if(type.equals("1")) {
            quizType = "Level 1 : Multiple choice";
        } else if(type.equals("2")) {
            quizType = "Level 2 : Fill in the blank (with hints)";
        } else if(type.equals("3")) {
            quizType = "Level 3 : Fill in the blank (without hints)";
        }

        return "[" + time + "]" + "\nUser answered QUIZ :\n" +
                "Title   : \"" + title + "\"\n" +
                "Number  : " + number + "\n" +
                "Type    : " + quizType + "\n" +
                "Answer  : " + answer + "\n" +
                "Count   : " + count + "\n";
    }

    public void appendLog(String text)
    {
        try {
            File file = new File(rootPath + "log_"+username+".txt");
            if (!file.exists()) {
                file.createNewFile();   // create file if not exist
            }
            BufferedWriter buf = new BufferedWriter(new FileWriter(file, true));
            buf.append(text);
            buf.newLine();  // pointer will be nextline
            buf.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ArrayList<Quiz> getQuizBasedOnType(int type) {
        ArrayList<Quiz> get = new ArrayList<>();
        if(type == Quiz.QUIZ_MULTIPLE_CHOICE) {
            get.addAll(quizTypeOne);
        } else if(type == Quiz.QUIZ_UNDERSCORE) {
            get.addAll(quizTypeTwo);
        } else if(type == Quiz.QUIZ_FILL_IN_THE_BLANKS) {
            get.addAll(quizTypeThree);
        }
        return get;
    }

    public ArrayList<Quiz> getQuizTypeOne() {
        return quizTypeOne;
    }

    public void setQuizTypeOne(ArrayList<Quiz> quizTypeOne) {
        this.quizTypeOne.clear();
        this.quizTypeOne.addAll(quizTypeOne);
    }

    public ArrayList<Quiz> getQuizTypeTwo() {
        return quizTypeTwo;
    }

    public void setQuizTypeTwo(ArrayList<Quiz> quizTypeTwo) {
        this.quizTypeTwo.clear();
        this.quizTypeTwo.addAll(quizTypeTwo);
    }

    public ArrayList<Quiz> getQuizTypeThree() {
        return quizTypeThree;
    }

    public void setQuizTypeThree(ArrayList<Quiz> quizTypeThree) {
        this.quizTypeThree.clear();
        this.quizTypeThree.addAll(quizTypeThree);
    }

    public String getJsonDataQuiz() {
        return jsonDataQuiz;
    }

    public void setJsonDataQuiz(String jsonDataQuiz) {
        this.jsonDataQuiz = jsonDataQuiz;
    }

    private static Globals instance = null;
    protected Globals() {
        // exist only to defeat instantiation
    }

    public static Globals getInstance() {
        if(instance == null) {
            instance = new Globals();
        }
        return instance;
    }

//    public int getNumberOfQuizTypeOne() {
//        return numberOfQuizTypeOne;
//    }
//
//    public void setNumberOfQuizTypeOne(int numberOfQuizTypeOne) {
//        this.numberOfQuizTypeOne = numberOfQuizTypeOne;
//    }
//
//    public int getNumberOfQuizTypeTwo() {
//        return numberOfQuizTypeTwo;
//    }
//
//    public void setNumberOfQuizTypeTwo(int numberOfQuizTypeTwo) {
//        this.numberOfQuizTypeTwo = numberOfQuizTypeTwo;
//    }
//
//    public int getNumberOfQuizTypeThree() {
//        return numberOfQuizTypeThree;
//    }
//
//    public void setNumberOfQuizTypeThree(int numberOfQuizTypeThree) {
//        this.numberOfQuizTypeThree = numberOfQuizTypeThree;
//    }
//
//    public int getNumberOfQuizzes() {
//        return numberOfQuizzes;
//    }
//
//    public void setNumberOfQuizzes(int numberOfQuizzes) {
//        this.numberOfQuizzes = numberOfQuizzes;
//    }

    public Quiz getCurrentQuiz() {
        return currentQuiz;
    }

    public void setCurrentQuiz(Quiz currentQuiz) {
        this.currentQuiz = currentQuiz;
    }

//    public ArrayList<Quiz> getQuizzes() {
//        return quizzes;
//    }

//    public void setQuizzes(ArrayList<Quiz> quizzes) {
//        this.quizzes.clear();
//        this.quizzes.addAll(quizzes);
//    }

    public int getNumberOfChapters() {
        return numberOfChapters;
    }

    public void setNumberOfChapters(int numberOfChapters) {
        this.numberOfChapters = numberOfChapters;
    }

    public String getJsonDataStory() {
        return jsonDataStory;
    }

    public void setJsonDataStory(String jsonDataStory) {
        this.jsonDataStory = jsonDataStory;
    }

    public ArrayList<Chapter> getChapters() {
        return chapters;
    }

    public void setChapters(ArrayList<Chapter> chapters) {
        this.chapters.clear();
        this.chapters.addAll(chapters);
    }

    public Chapter getCurrentChapter() {
        return currentChapter;
    }

    public void setCurrentChapter(Chapter currentChapter) {
        this.currentChapter = currentChapter;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
