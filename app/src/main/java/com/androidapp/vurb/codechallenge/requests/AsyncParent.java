package com.androidapp.vurb.codechallenge.requests;

/**
 * Created by jonwu on 1/7/15.
 * This interface is used to direct users back to main thread after an Async task has finished.
 */
public interface AsyncParent {
    void onPostRequestCards(String result);
}
