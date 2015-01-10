package com.androidapp.vurb.codechallenge.activities;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.androidapp.vurb.codechallenge.R;
import com.androidapp.vurb.codechallenge.adapters.CardListAdapter;
import com.androidapp.vurb.codechallenge.listeners.InfiniteScrollListener;
import com.androidapp.vurb.codechallenge.models.Card;
import com.androidapp.vurb.codechallenge.models.Movie;
import com.androidapp.vurb.codechallenge.models.Music;
import com.androidapp.vurb.codechallenge.models.Place;
import com.androidapp.vurb.codechallenge.requests.AsyncParent;
import com.androidapp.vurb.codechallenge.requests.RequestCards;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class CardsActivity extends ActionBarActivity implements AsyncParent {

    private ArrayList<Card> mCards = new ArrayList<Card>();
    private ListView lvCards;
    private TextView tvLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cards);

        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(this).build();
        ImageLoader.getInstance().init(config);


        lvCards = (ListView) findViewById(R.id.lvCards);
        tvLocation = (TextView) findViewById(R.id.tvLocation);
        //Get Extras
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();

        if(extras != null){
            Double latitude = intent.getDoubleExtra(SplashActivity.LAT_ID, 0.0);
            Double longitude = intent.getDoubleExtra(SplashActivity.LONG_ID, 0.0);
            String cards = intent.getStringExtra(SplashActivity.CARDS_ID);
            processCards(cards);
            setLocationView(latitude, longitude);
        }
        CardListAdapter adapter = new CardListAdapter( this, mCards);
        lvCards.setAdapter(adapter);
        lvCards.setOnScrollListener(new InfiniteScrollListener() {
            @Override
            public void loadMore(int page, int totalItemsCount) {
                requestCards();
            }
        });



    }

    /**
     * Retrieve cards if network is available.
     */
    private void requestCards(){
        ConnectivityManager connMgr = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            RequestCards requestCards = new RequestCards();
            requestCards.delegate = this;
            requestCards.execute();
        }else{
            Toast.makeText(this, "Network is unavailable in your device", Toast.LENGTH_LONG).show();
        }
    }

    /**
     * Set text to current location
     * @param latitude
     * @param longitude
     */
    private void setLocationView(double latitude, double longitude){
        String location = latitude + ", "+ longitude;
        tvLocation.setText(location);

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * Converts String Formatted Card to JSON Object
     * @param result String detail of card
     */
    private void processCards(String result){
        try {
            JSONObject jObject = new JSONObject(result);
            JSONArray jArrayCards = jObject.getJSONArray("cards");
            for (int i = 0; i < jArrayCards.length(); i++) {
                JSONObject jObjectCard = jArrayCards.getJSONObject(i);

                Card card = initType(jObjectCard);
                if (card != null){
                    mCards.add(card);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    /**
     * Store JSONObject to their respective types
     * @param jObjectCard JSON detail of a card
     * @return A Card
     * @throws JSONException
     */
    private Card initType(JSONObject jObjectCard) throws JSONException {
        Card card = null;

        String type = jObjectCard.getString("type");
        String title = jObjectCard.getString("title");
        String imageURL = jObjectCard.getString("imageURL");

        switch (type) {
            case "place":
                Place place = new Place(title, imageURL);
                place.setPlaceCategory(jObjectCard.getString("placeCategory"));
                card = place;
                break;
            case "movie":
                Movie movie = new Movie(title, imageURL);
                movie.setMovieExtraImageURL(jObjectCard.getString("movieExtraImageURL"));
                card = movie;
                break;
            case "music":
                Music music = new Music(title, imageURL);
                music.setMusicVideoURL(jObjectCard.getString("musicVideoURL"));
                card = music;
                break;
            default:
                break;
        }
        return card;
    }

    /**
     * Go to Home Screen when back button is pressed
     */
    public void onBackPressed() {
        Intent startMain = new Intent(Intent.ACTION_MAIN);
        startMain.addCategory(Intent.CATEGORY_HOME);
        startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(startMain);

    }

    @Override
    public void onPostRequestCards(String result) {
        processCards(result);
        ((CardListAdapter) lvCards.getAdapter()).notifyDataSetChanged();
    }
}
