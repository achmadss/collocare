package com.susiapp.collocare.Async;

import android.os.AsyncTask;

import com.susiapp.collocare.Models.Globals;
import com.susiapp.collocare.Models.HttpHandler;
import com.susiapp.collocare.Models.Quiz;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class GetQuiz extends AsyncTask<String, Integer, String> {


//    private ArrayList<Quiz> quizListAll = new ArrayList<>();
    private ArrayList<Quiz> quizListTypeOne = new ArrayList<>();
    private ArrayList<Quiz> quizListTypeTwo = new ArrayList<>();
    private ArrayList<Quiz> quizListTypeThree = new ArrayList<>();
    private Globals globals = Globals.getInstance();

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
//        quizListAll.clear();
    }

    private void fillQuiz(ArrayList<String> type, JSONObject object) {
        for (int i = 0; i < type.size(); i++) {
            try {
                JSONArray array = object.getJSONArray(type.get(i));
                for (int j = 0; j < array.length(); j++) {
                    Quiz quizGet = null;
                    JSONObject question = array.getJSONObject(j);
                    String questionNumber = question.getString("question");
                    String questionTitle = question.getString("title");
                    String questionType = question.getString("type");
                    String questionAnswer = question.getString("answer");
                    String questionImageQuiz = question.getString("imageQuiz");
                    JSONArray imgArray = question.getJSONArray("images");
                    ArrayList<String> imgURLS = new ArrayList<>();
                    for (int k = 0; k < imgArray.length(); k++) {
                        imgURLS.add(imgArray.getString(k));
                    }
                    if(questionType.equals("1")) {
                        // for multiple choice
                        JSONArray questionOptions = question.getJSONArray("options");
                        ArrayList<String> options = new ArrayList<>();
                        for (int k = 0; k < questionOptions.length(); k++) {
                            options.add(questionOptions.getString(k));
                        }
                        quizGet = new Quiz(
                                questionNumber, questionTitle,
                                questionType, imgURLS,
                                questionImageQuiz, options, questionAnswer
                        );
                        quizListTypeOne.add(quizGet);
                    } else if (questionType.equals("2")) {
                        // for underscore
                        quizGet = new Quiz(
                                questionNumber, questionTitle,
                                questionType, imgURLS,
                                questionImageQuiz, questionAnswer
                        );
                        quizListTypeTwo.add(quizGet);
                    } else if (questionType.equals("3")) {
                        // for fill
                        quizGet = new Quiz(
                                questionNumber, questionTitle,
                                questionType, imgURLS,
                                questionImageQuiz, questionAnswer
                        );
                        quizListTypeThree.add(quizGet);
                    }
//                    quizListAll.add(quizGet);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

    }

    @Override
    protected String doInBackground(String... strings) {

        HttpHandler httpHandler = new HttpHandler();
        globals.setJsonDataQuiz(
                httpHandler.makeServiceCall(
                        "http://websusi.000webhostapp.com/collocare/json/json_data_quiz.json"));
        String result = "";
        if(globals.getJsonDataQuiz() != null) {
            try {
                JSONObject object = new JSONObject(globals.getJsonDataQuiz());
//                globals.setNumberOfQuizTypeOne(object.getInt("NUMBER_OF_QUESTIONS_TYPE1"));
//                globals.setNumberOfQuizTypeTwo(object.getInt("NUMBER_OF_QUESTIONS_TYPE2"));
//                globals.setNumberOfQuizTypeThree(object.getInt("NUMBER_OF_QUESTIONS_TYPE3"));

                JSONObject types = object.getJSONObject("types");
                ArrayList<String> quizTypes = new ArrayList<>();
                quizTypes.add("multiple_choice");
                quizTypes.add("underscore");
                quizTypes.add("fill_in_the_blank");
                fillQuiz(quizTypes, types);
//                globals.setQuizzes(quizListAll);
                globals.setQuizTypeOne(quizListTypeOne);
                globals.setQuizTypeTwo(quizListTypeTwo);
                globals.setQuizTypeThree(quizListTypeThree);
                result = "success";
            } catch (JSONException e) {
                e.printStackTrace();
                result = "fail";
            }
        } else {
            result = "fail";
        }
        return result;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
    }
}
