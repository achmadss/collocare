package com.susiapp.collocare.Async;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.susiapp.collocare.Models.HttpHandler;
import com.susiapp.collocare.Views.HomeActivity;

import org.json.JSONException;
import org.json.JSONObject;

public class Login extends AsyncTask<String, Integer, String> {

    SharedPreferences prefs;

    private String username;
    private String password;

    public Login(String username, String password, SharedPreferences prefs) {
        this.username = username;
        this.password = password;
        this.prefs = prefs;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected String doInBackground(String... strings) {
        String result = "";
        HttpHandler httpHandler = new HttpHandler();
        String userJson = httpHandler.makeServiceCall(
                "http://websusi.000webhostapp.com/collocare/json/user_login.php?username="+username+"&password="+password);

        try {
            JSONObject jsonResult = new JSONObject(userJson);
            String loginResult = jsonResult.getString("loginResult");
            if(loginResult.equals("success")) {
                JSONObject user = jsonResult.getJSONObject("data");
                String username = user.getString("username");
                String lastStory = user.getString("lastStory");
                String lastStoryDate = user.getString("lastStoryDate");
                String storyCount = user.getString("storyCount");
                String lastQuiz = user.getString("lastQuiz");
                String lastQuizDate = user.getString("lastStoryDate");
                String timeUpdated = user.getString("timeUpdated");

                JSONObject quizCount = user.getJSONObject("quizCount");
                String quizCountTotal = quizCount.getString("total");

                JSONObject levelOne = quizCount.getJSONObject("levelOne");
                JSONObject levelTwo = quizCount.getJSONObject("levelTwo");
                JSONObject levelThree = quizCount.getJSONObject("levelThree");

                String levelOneCorrect = levelOne.getString("correct");
                String levelTwoCorrect = levelTwo.getString("correct");
                String levelThreeCorrect = levelThree.getString("correct");

                String levelOneIncorrect = levelOne.getString("incorrect");
                String levelTwoIncorrect = levelTwo.getString("incorrect");
                String levelThreeIncorrect = levelThree.getString("incorrect");

                int storyCountInt = Integer.parseInt(storyCount);
                int quizCountTotalInt = Integer.parseInt(quizCountTotal);

                int levelOneCorrectInt = Integer.parseInt(levelOneCorrect);
                int levelTwoCorrectInt = Integer.parseInt(levelTwoCorrect);
                int levelThreeCorrectInt = Integer.parseInt(levelThreeCorrect);

                int levelOneIncorrectInt = Integer.parseInt(levelOneIncorrect);
                int levelTwoIncorrectInt = Integer.parseInt(levelTwoIncorrect);
                int levelThreeIncorrectInt = Integer.parseInt(levelThreeIncorrect);

                int levelOneTotal = levelOneCorrectInt + levelOneIncorrectInt;
                int levelTwoTotal = levelTwoCorrectInt + levelTwoIncorrectInt;
                int levelThreeTotal = levelThreeCorrectInt + levelThreeIncorrectInt;

                prefs.edit().putString("username", username).apply();
                prefs.edit().putString("lastChapterReadTitle", lastStory).apply();
                prefs.edit().putString("lastQuizTakenTitle", lastQuiz).apply();
                prefs.edit().putString("dateLastChapterRead", lastStoryDate).apply();
                prefs.edit().putString("dateLastQuizTaken", lastQuizDate).apply();
                prefs.edit().putString("timeUpdated", timeUpdated).apply();

                prefs.edit().putInt("chapterReadCount", storyCountInt).apply();
                prefs.edit().putInt("quizTakenTotalCount", quizCountTotalInt).apply();
                prefs.edit().putInt("quizTakenTypeOneCount", levelOneTotal).apply();
                prefs.edit().putInt("quizTakenTypeTwoCount", levelTwoTotal).apply();
                prefs.edit().putInt("quizTakenTypeThreeCount", levelThreeTotal).apply();
                prefs.edit().putInt("quizTypeOneCorrect", levelOneCorrectInt).apply();
                prefs.edit().putInt("quizTypeTwoCorrect", levelTwoCorrectInt).apply();
                prefs.edit().putInt("quizTypeThreeCorrect", levelThreeCorrectInt).apply();
                prefs.edit().putInt("quizTypeOneIncorrect", levelOneIncorrectInt).apply();
                prefs.edit().putInt("quizTypeTwoIncorrect", levelTwoIncorrectInt).apply();
                prefs.edit().putInt("quizTypeThreeIncorrect", levelThreeIncorrectInt).apply();

                result = "success";
            } else {
                result = "failed";
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return result;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);

    }
}
