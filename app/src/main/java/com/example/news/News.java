package com.example.news;

public class News {

    private String mHeadLine;
    private String mDiscription;
    private String mAuthor;
    private String mUrl;

    public News(String headline, String author, String discription, String url) {
        mHeadLine = headline;
        mAuthor = author;
        mDiscription = discription;
        mUrl = url;
    }

    public String getmHeadLine() {
        return mHeadLine;
    }

    public String getmAuthor() {
        return mAuthor;
    }

    public String getmDiscription() {
        return mDiscription;
    }

    public String getmUrl() {
        return mUrl;
    }
}
