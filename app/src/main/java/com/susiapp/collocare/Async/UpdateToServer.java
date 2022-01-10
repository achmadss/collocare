package com.susiapp.collocare.Async;

import android.content.SharedPreferences;
import android.os.AsyncTask;

import com.susiapp.collocare.Models.Globals;
import com.susiapp.collocare.Models.HttpHandler;

import org.json.JSONException;
import org.json.JSONObject;

public class UpdateToServer extends AsyncTask<String, Integer, String> {

    SharedPreferences prefs;
    private String username;

    public UpdateToServer(String username, SharedPreferences prefs) {
        this.username = username;
        this.prefs = prefs;
    }

    @Override
    protected String doInBackground(String... strings) {
        HttpHandler httpHandler = new HttpHandler();
        String userJson = httpHandler.makeServiceCall(
                "http://websusi.000webhostapp.com/collocare/json/user_add.php?username="
                        +username
                        +"&lastStory="+prefs.getString("lastChapterReadTitle", "")
                        +"&lastStoryDate="+prefs.getString("dateLastChapterRead", "")
                        +"&storyCount="+prefs.getInt("chapterReadCount", 0)
                        +"&lastQuiz="+prefs.getString("lastQuizTakenTitle", "")
                        +"&lastQuizDate="+prefs.getString("dateLastQuizTaken", "")

                        +"&quizCountTotal="+prefs.getInt("quizTakenTotalCount", 0)
                        +"&quizCountLevelOne="+prefs.getInt("quizTakenTypeOneCount", 0)
                        +"&quizCountLevelTwo="+prefs.getInt("quizTakenTypeTwoCount", 0)
                        +"&quizCountLevelThree="+prefs.getInt("quizTakenTypeThreeCount", 0)

                        +"&quizAnswerCorrectLevelOne="+prefs.getInt("quizTypeOneCorrect", 0)
                        +"&quizAnswerCorrectLevelTwo="+prefs.getInt("quizTypeTwoCorrect", 0)
                        +"&quizAnswerCorrectLevelThree="+prefs.getInt("quizTypeThreeCorrect", 0)

                        +"&quizAnswerIncorrectLevelOne="+prefs.getInt("quizTypeOneIncorrect", 0)
                        +"&quizAnswerIncorrectLevelTwo="+prefs.getInt("quizTypeTwoIncorrect", 0)
                        +"&quizAnswerIncorrectLevelThree="+prefs.getInt("quizTypeThreeIncorrect", 0)

                        +"&timeUpdated="+prefs.getString("timeUpdated", "")
                        +"&intent=update");
        try {
            JSONObject object = new JSONObject(userJson);
            return object.getString("result");
        } catch (JSONException e) {
            e.printStackTrace();
            return "failed";
        }

    }
}
