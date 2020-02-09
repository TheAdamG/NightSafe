package com.cards.nightsafe;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.uber.sdk.android.rides.RideParameters;
import com.uber.sdk.android.rides.RideRequestButton;
import com.uber.sdk.android.rides.RideRequestButtonCallback;
import com.uber.sdk.rides.client.ServerTokenSession;
import com.uber.sdk.rides.client.SessionConfiguration;
import com.uber.sdk.rides.client.error.ApiError;


import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.IOException;

public class goHome extends AppCompatActivity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_go_home);

        new goHome.Download(goHome.this).execute();
    }


    public void launchMap(View v) {
        String mapUrl = (String) v.getTag();
        Intent intent = new Intent(android.content.Intent.ACTION_VIEW, Uri.parse(mapUrl));
        startActivity(intent);

    }
    private void generateButton(double[] ordinates) {

        SessionConfiguration config = new SessionConfiguration.Builder()
                // mandatory
                .setClientId("YC-5t-0uyRwffiRi5SsL6pIhWOfvS2JJ")
                .setServerToken("8djPJuSqXnudSVLbvEEUR2Ks8-9SKVGCWKnjmKVp")
                // required for enhanced button features
                .build();
        ServerTokenSession session = new ServerTokenSession(config);
        RideRequestButton requestButton = findViewById(R.id.uberID);

        RideParameters rideParams = new RideParameters.Builder()
                .setPickupLocation(ordinates[0], ordinates[1], "Event", "")
                .setDropoffLocation(ordinates[2], ordinates[3], "Home", "")
                .build();
        RideRequestButtonCallback callback = new RideRequestButtonCallback() {

            @Override
            public void onRideInformationLoaded() {

            }

            @Override
            public void onError(ApiError apiError) {

            }

            @Override
            public void onError(Throwable throwable) {

            }
        };
        requestButton.setRideParameters(rideParams);
        requestButton.setSession(session);
        requestButton.setCallback(callback);
        requestButton.loadRideInformation();
    }




    private class Download extends AsyncTask<Void, Void, String> {

        Context context;
        private GroupDestination destinations;
        private String transportButtonText;
        private String transportDurationText;
        private String uberDurationText;
        private String mapUrl = "https://www.google.com/maps/dir/?api=1&travelmode=transit&";
        private double[] uberCoords;

        public Download(Context context) {
            this.context = context;
        }

        protected void onPreExecute() {

        }

        protected String doInBackground(Void... params) {
            destinations = Queries.GroupDestinationQuery(10);
            String destination = destinations.getDestination();
            String origin = destinations.getEventLocation();
            Log.v("Destination", destination);
            Log.v("Origin", origin);
            DirectionRequest directionRequest;
            DirectionRequest uberRequest;
            try {
                directionRequest = new DirectionRequest(origin, destination, false);
                Log.v("directionRequest", directionRequest.getMode());

                mapUrl += directionRequest.getURL();
                transportButtonText = directionRequest.getMode();
                transportDurationText = directionRequest.getDuration();

                uberRequest = new DirectionRequest(origin, destination, true);
                uberDurationText = uberRequest.getDuration();
                uberCoords = uberRequest.getUberPickup();

            } catch (IOException e) {
                Log.v("IOException", e.getMessage());
            }
            return "Complete";
        }

        protected void onPostExecute(String result) {
            if (result.equals("Complete")) {
                Button transportButton = findViewById(R.id.transport_button);
                TextView transitDuration = findViewById(R.id.transit_duration);
                TextView uberDuration = findViewById(R.id.uber_duration);
                transportButton.setText(transportButtonText);
                transportButton.setTag(mapUrl);
                transitDuration.setText(transportDurationText);
                uberDuration.setText(uberDurationText);

                generateButton(uberCoords);
            }
        }

    }




}
