package com.susiapp.collocare.Async;

import android.os.AsyncTask;

import com.susiapp.collocare.Models.Chapter;
import com.susiapp.collocare.Models.Globals;
import com.susiapp.collocare.Models.HttpHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class GetStory extends AsyncTask<String, Integer, String> {

    private ArrayList<Chapter> chapterList = new ArrayList<>();
    private Globals globals = Globals.getInstance();

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        chapterList.clear();
    }

    @Override
    protected String doInBackground(String... strings) {
        String result = "";
        HttpHandler httpHandler = new HttpHandler();
        globals.setJsonDataStory(
                httpHandler.makeServiceCall(
                        "http://websusi.000webhostapp.com/collocare/json/json_data_story.json"));
        if(globals.getJsonDataStory() != null) {
            try {
                JSONObject object = new JSONObject(globals.getJsonDataStory());
                globals.setNumberOfChapters(object.getInt("NUMBER_OF_CHAPTERS"));
                JSONArray chapters = object.getJSONArray("chapters");
                for (int i = 0; i < chapters.length(); i++) {
                    JSONObject chapter = chapters.getJSONObject(i);
                    String chapterNumber = chapter.getString("chapter");
                    String chapterTitle = chapter.getString("title");
                    JSONArray imgArray = chapter.getJSONArray("images");
                    ArrayList<String> imgURLS = new ArrayList<>();
                    for (int j = 0; j < imgArray.length(); j++) {
                        imgURLS.add(imgArray.getString(j));
                    }
                    Chapter chapterGet = new Chapter(chapterNumber, chapterTitle, imgURLS);
                    chapterList.add(chapterGet);
                }
                globals.setChapters(chapterList);
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
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
    }

    @Override
    protected void onPostExecute(String list) {
        super.onPostExecute(list);
    }



}
