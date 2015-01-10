package com.androidapp.vurb.codechallenge.models;

/**
 * Created by jonwu on 1/7/15.
 */
public class Music extends Card {
    private String musicVideoURL;

    public Music(String title, String thumbnail) {
        super(title, thumbnail);
    }

    public String getMusicVideoURL() {
        return musicVideoURL;
    }

    public void setMusicVideoURL(String musicVideoURL) {
        this.musicVideoURL = musicVideoURL;
    }
}
