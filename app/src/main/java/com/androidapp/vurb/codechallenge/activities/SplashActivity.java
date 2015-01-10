package com.androidapp.vurb.codechallenge.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.androidapp.vurb.codechallenge.R;
import com.androidapp.vurb.codechallenge.requests.AsyncParent;
import com.androidapp.vurb.codechallenge.requests.RequestCards;


public class SplashActivity extends Activity implements AsyncParent {

    private final int SPLASH_DISPLAY_LENGTH = 2000;
    private LocationManager mLocationManager;
    private Location mCurrentLocation;



    public static final String LAT_ID = "lat_id";
    public static final String LONG_ID = "long_id";
    public static final String CARDS_ID = "cards_id";

    public Location getCurrentLocation() {
        return mCurrentLocation;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        // Default splash delay of 3 seconds

        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {
                setLocation();
                requestCards();
            }
        }, SPLASH_DISPLAY_LENGTH);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_splash, menu);
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
     * Retrieve cards if network is available.
     */
    private void requestCards(){
        ConnectivityManager connMgr = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            RequestCards requestCards = new RequestCards(this);
            requestCards.delegate = this;
            requestCards.execute();
        }else{
            Toast.makeText(this, "Network is unavailable in your device", Toast.LENGTH_LONG).show();
        }
    }

    /**
     * Start CardsActivity after successfully retrieving cards and location.
     * @param result String result of get request
     */
    @Override
    public void onPostRequestCards(String result) {
        Intent intent = new Intent(this, CardsActivity.class);
        intent.putExtra(LAT_ID, mCurrentLocation.getLatitude());
        intent.putExtra(LONG_ID, mCurrentLocation.getLongitude());
        intent.putExtra(CARDS_ID, result);
        startActivity(intent);
    }

    /**
     * Start a new task to find current location.
     */
    private void setLocation(){
        mLocationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        if (mLocationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            LocationListener listener = new LocationListener() {
                @Override
                public void onLocationChanged(Location location) {
                    if (location != null) {
                        mCurrentLocation = location;
                        mLocationManager.removeUpdates(this);
                    }
                }

                @Override
                public void onStatusChanged(String provider, int status, Bundle extras) {

                }

                @Override
                public void onProviderEnabled(String provider) {

                }

                @Override
                public void onProviderDisabled(String provider) {

                }
            };
            mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, listener);
        }else{
            Toast.makeText(this, "GPS is Disabled in your device", Toast.LENGTH_LONG).show();
        }
    }

}
