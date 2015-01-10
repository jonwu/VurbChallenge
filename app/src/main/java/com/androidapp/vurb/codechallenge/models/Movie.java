package com.androidapp.vurb.codechallenge.models;

/**
 * Created by jonwu on 1/7/15.
 */
public class Movie extends Card {
    private String movieExtraImageURL;

    public Movie(String title, String thumbnail) {
        super(title, thumbnail);
    }

    public String getMovieExtraImageURL() {
        return movieExtraImageURL;
    }

    public void setMovieExtraImageURL(String movieExtraImageURL) {
        this.movieExtraImageURL = movieExtraImageURL;
    }
}
