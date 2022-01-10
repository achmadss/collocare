package com.susiapp.collocare.Models;

import java.util.ArrayList;

public class Chapter {

    private String chapterNumber;
    private String chapterTitle;
    private ArrayList<String> imageURLS;

    public Chapter(String chapterNumber, String chapterTitle, ArrayList<String> imageURLS) {
        setChapterNumber(chapterNumber);
        setChapterTitle(chapterTitle);
        setImageURLS(imageURLS);
//        this.chapterNumber = chapterNumber;
//        this.chapterTitle = chapterTitle;
//        this.imageURLS = imageURLS;
    }


    public void setChapterNumber(String chapterNumber) {
        this.chapterNumber = chapterNumber;
    }

    public void setChapterTitle(String chapterTitle) {
        this.chapterTitle = chapterTitle;
    }

    public void setImageURLS(ArrayList<String> imageURLS) {
        this.imageURLS = imageURLS;
    }

    public String getChapterNumber() {
        return chapterNumber;
    }

    public String getChapterTitle() {
        return chapterTitle;
    }

    public ArrayList<String> getImageURLS() {
        return imageURLS;
    }
}
