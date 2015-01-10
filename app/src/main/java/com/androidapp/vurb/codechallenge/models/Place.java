package com.androidapp.vurb.codechallenge.models;

/**
 * Created by jonwu on 1/7/15.
 */
public class Place extends Card {
    private String placeCategory;

    public Place(String title, String thumbnail) {
        super(title, thumbnail);
    }

    public String getPlaceCategory() {
        return placeCategory;
    }

    public void setPlaceCategory(String placeCategory) {
        this.placeCategory = placeCategory;
    }
}
