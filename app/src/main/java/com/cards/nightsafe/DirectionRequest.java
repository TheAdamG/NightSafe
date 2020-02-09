package com.cards.nightsafe;
import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;
import java.lang.*;

public class DirectionRequest {
    private JSONObject jObject;
    private static final String DEFAULT_URL = "https://maps.googleapis.com/maps/api/directions/json?";
    private String url = "&transit_routing_preference=fewer_transfers";
    private static final String KEY = "&key=AIzaSyCcoALVP0D-QDqaPu46Cx1-fMB5qy_CD0c";
    private final String origin;
    private final String destination;

    public DirectionRequest(
            int originLat, int originLong, int destLat, int destLong, String departure, boolean drive)
            throws IOException {
        this(originLat + "," + originLong, destLat + "," + destLong, departure, drive);
    }

    public DirectionRequest(String origin, String destination, boolean drive) throws IOException {
        this(origin, destination, "now", drive);
    }

    public DirectionRequest(String origin, String destination, String departure, boolean drive)
            throws IOException {
        this.origin = DirectionRequest.parseLocation(origin);
        this.destination = DirectionRequest.parseLocation(destination);
        url +=
                "&origin="
                        + this.origin
                        + "&destination="
                        + this.destination
                        + "&departure_time="
                        + departure
                        + KEY;
        if (drive) {
            url += "&mode=driving";
        } else {
            url += "&mode=transit";
        }
        getGoogleJSON();
    }

    /* Will NEED this for launching google maps. Append the result of this to the URL query for launching google maps
    with the route. It specifies all of the parameters.
     */
    public String getURL() {
        return url;
    }

    /* Returns the list containing longitudes and latitudes for the origin and destination. You need these to launch UBER.
     */
    public double[] getUberPickup() throws IOException {
        ArrayList<Double> output = DirectionRequest.toLongLat(origin);
        output.addAll(DirectionRequest.toLongLat(destination));
        double[] doubleArray = new double[output.size()];
        for (int i = 0; i < output.size(); i++) {
            doubleArray[i] = Double.valueOf(output.get(i));
        }
        return doubleArray;
    }

    private static ArrayList<Double> toLongLat(String location) throws IOException {
        ArrayList<Double> output = new ArrayList<>();
        try {
        JSONObject longLat =
                DirectionRequest.getJSON(
                        "https://maps.googleapis.com/maps/api/geocode/json?address="
                                + location
                                + DirectionRequest.KEY)
                        .getJSONArray("results")
                        .getJSONObject(0)
                        .getJSONObject("geometry")
                        .getJSONObject("location");

        output.add(longLat.getDouble("lat"));
        output.add(longLat.getDouble("lng"));
         } catch (JSONException e) {
            Log.v("Json Exeception", e.getMessage());
        }
        return output;
    }

    /* This allows you to provide two coordinates, the long and lat for each. It returns the distance in metres between
    them. Useful for friend tracking!
     */
    public static double distanceBetweenLatLong(double lat1, double lon1, double lat2, double lon2) {
        lat1 = Math.toRadians(lat1);
        lon1 = Math.toRadians(lon1);
        lat2 = Math.toRadians(lat2);
        lon2 = Math.toRadians(lon2);
        int earthRadius = 6371010; // Metres
        return earthRadius
                * Math.acos(
                Math.sin(lat1) * Math.sin(lat2)
                        + Math.cos(lat1) * Math.cos(lat2) * Math.cos(lon1 - lon2));
    }

    private static String parseLocation(String s) {
        String[] arr = s.split(" ");
        StringBuilder parsed = new StringBuilder();
        for (String word : arr) {
            parsed.append(word);
            parsed.append("+");
        }
        return parsed.toString();
    }

    /* Provides the length of a journey via google maps (walking or transit). Will need to show this where user decides.
     *
     * I can possibly upgrade this later so that the time if you drive is used as a time estimate for selecting an UBER. */
    public String getDuration() {
        String s = "";
        try {
            s =  jObject.getJSONObject("duration").getString("text");
        } catch (JSONException e) {
            Log.v("Json Exeception", e.getMessage());
        }
        return s;
    }

    /* Provides a string which lists the forms of travel involved in using google maps to get back. So they know if they
    choice entails walking, subways, both etc.
     */
    public String getMode() {
        Set<String> descriptions = new HashSet<>();
        try {

            JSONObject current;
            JSONArray ja = jObject.getJSONArray("steps");

            for (int i = 0; i < ja.length(); i++) {
                current = ja.getJSONObject(i);
                descriptions.add(current.getString("html_instructions").split(" ")[0]);
            }
        } catch (JSONException e) {
            Log.v("Json Exeception", e.getMessage());
        }
        return TextUtils.join(", ", descriptions);
    }

    private static JSONObject getJSON(String address) throws IOException {
        Log.v("address", address);
        URL obj = new URL(address);
        HttpURLConnection connection = (HttpURLConnection) obj.openConnection();
        BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String inputLine;
        StringBuilder response = new StringBuilder();
        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();
        try {
        return new JSONObject(response.toString());
        } catch (JSONException e) {
            return null;
        }
    }

    private void getGoogleJSON() throws IOException {
        try {
        jObject =
                DirectionRequest.getJSON(DEFAULT_URL + url)
                        .getJSONArray("routes")
                        .getJSONObject(0)
                        .getJSONArray("legs")
                        .getJSONObject(0); }
        catch (JSONException e) {
            Log.v("Json Exeception", e.getMessage());
        }
    }
}
