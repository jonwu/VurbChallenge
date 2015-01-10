package com.androidapp.vurb.codechallenge.models;

/**
 * Created by jonwu on 1/7/15.
 */
public class Card {
    private String title;
    private String thumbnail;

    public Card(String title, String thumbnail) {
        this.title = title;
        this.thumbnail = thumbnail;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }
}
