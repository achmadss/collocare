package com.susiapp.collocare.Async;

import android.content.SharedPreferences;
import android.os.AsyncTask;

import com.susiapp.collocare.Models.HttpHandler;

import org.json.JSONException;
import org.json.JSONObject;

public class Register extends AsyncTask<String, Integer, String> {

    private String username;
    private String password;

    public Register(String username, String password) {
        this.username = username;
        this.password = password;
    }

    @Override
    protected String doInBackground(String... strings) {
        HttpHandler httpHandler = new HttpHandler();
        String userJson = httpHandler.makeServiceCall(
                "http://websusi.000webhostapp.com/collocare/json/user_add.php?username="
                        +username+"&password="+password
                        +"&lastStory=Not Available"
                        +"&lastStoryDate=Not Available"
                        +"&storyCount=0"
                        +"&lastQuiz=Not Available"
                        +"&lastQuizDate=Not Available"
                        +"&quizCountTotal=0"
                        +"&quizCountLevelOne=0"
                        +"&quizCountLevelTwo=0"
                        +"&quizCountLevelThree=0"
                        +"&quizAnswerCorrectLevelOne=0"
                        +"&quizAnswerCorrectLevelTwo=0"
                        +"&quizAnswerCorrectLevelThree=0"
                        +"&quizAnswerIncorrectLevelOne=0"
                        +"&quizAnswerIncorrectLevelTwo=0"
                        +"&quizAnswerIncorrectLevelThree=0"
                        +"&timeUpdated="
                        +"&intent=register");
        try {
            JSONObject object = new JSONObject(userJson);
            return object.getString("result");
        } catch (JSONException e) {
            e.printStackTrace();
            return "failed";
        }
    }
}
