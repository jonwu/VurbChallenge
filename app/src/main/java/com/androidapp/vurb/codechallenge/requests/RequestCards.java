package com.androidapp.vurb.codechallenge.requests;

import android.os.AsyncTask;

import com.androidapp.vurb.codechallenge.activities.SplashActivity;

import org.apache.http.HttpResponse;

import java.io.IOException;

/**
 * Created by jonwu on 1/7/15.
 * Request for card information
 */
public class RequestCards extends AsyncTask<String, String, String> {

    String kBaseEndpoint = "https://gist.githubusercontent.com/helloandrewpark/0a407d7c681b833d6b49/raw/5f3936dd524d32ed03953f616e19740bba920bcd/gistfile1.js";
    public AsyncParent delegate = null;
    HttpRequestController httpRequestController = new HttpRequestController();
    SplashActivity splashActivity;
    public RequestCards(){}
    public RequestCards(SplashActivity activity){
        splashActivity = activity;
    }
    @Override
    protected String doInBackground(String... uri) {
        String result = null;
        HttpResponse response = httpRequestController.get(kBaseEndpoint);
        try {
            result = httpRequestController.convertStreamToString(response.getEntity().getContent());
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Wait for location value before executing onPostExecute. (Both tasks are in parallel)
        while(splashActivity != null && splashActivity.getCurrentLocation() == null){
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        return result;
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        if (result != null){
            delegate.onPostRequestCards(result);
        }

    }
}
