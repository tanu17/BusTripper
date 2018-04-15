package com.bustripper.bustripper.UserInterface;

import android.text.TextUtils;
import android.util.Log;

import com.bustripper.bustripper.Entity.BusService;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import java.util.Calendar;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.TimeZone;


/**
 * Created by wayne on 25/3/2018.
 */

public class QueryUtils {

    /** Tag for the log messages */
    private static final String LOG_TAG = QueryUtils.class.getSimpleName();

    /**
     * Create a private constructor because no one should ever create a {@link QueryUtils} object.
     * This class is only meant to hold static variables and methods, which can be accessed
     * directly from the class name QueryUtils (and an object instance of QueryUtils is not needed).
     */
    private QueryUtils() {
    }

    /**
     * Query the USGS dataset and return a list of {@link com.bustripper.bustripper.Entity.BusService} objects.
     */
    public static List<BusService> fetchBusServiceData(String requestUrl, String requestUrlByBusStop, String userServiceNo) {
        // Create URL object
        URL url = createUrl(requestUrl);

        // Perform HTTP request to the URL and receive a JSON response back
        String jsonResponse = null;
        try {
            jsonResponse = makeHttpRequest(url);
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem making the HTTP request.", e);
        }

        // Extract relevant fields from the JSON response and create a list of {@link Earthquake}s
        List<BusService> busServices = extractBusServiceFromJson(jsonResponse, requestUrlByBusStop, userServiceNo);

        // Return the list of {@link BusService}s
        return busServices;
    }

    /**
     * Returns new URL object from the given string URL.
     */
    private static URL createUrl(String stringUrl) {
        URL url = null;
        try {
            url = new URL(stringUrl);
        } catch (MalformedURLException e) {
            Log.e(LOG_TAG, "Problem building the URL ", e);
        }
        return url;
    }

    /**
     * Make an HTTP request to the given URL and return a String as the response.
     */
    private static String makeHttpRequest(URL url) throws IOException {
        String jsonResponse = "";

        // If the URL is null, then return early.
        if (url == null) {
            return jsonResponse;
        }

        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(10000 /* milliseconds */);
            urlConnection.setConnectTimeout(15000 /* milliseconds */);
            urlConnection.setRequestMethod("GET");
            urlConnection.setRequestProperty("AccountKey","2g5Yw8tvSN+HFJ/J0VSwaA== ");
            urlConnection.connect();

            // If the request was successful (response code 200),
            // then read the input stream and parse the response.
            if (urlConnection.getResponseCode() == 200) {
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            } else {
                Log.e(LOG_TAG, "Error response code: " + urlConnection.getResponseCode());
            }
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem retrieving the bus service JSON results.", e);
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {
                // Closing the input stream could throw an IOException, which is why
                // the makeHttpRequest(URL url) method signature specifies than an IOException
                // could be thrown.
                inputStream.close();
            }
        }
        return jsonResponse;
    }

    /**
     * Convert the {@link InputStream} into a String which contains the
     * whole JSON response from the server.
     */
    private static String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null) {
                output.append(line);
                line = reader.readLine();
            }
        }
        return output.toString();
    }

    /**
     * Return a list of {@link BusService} objects that has been built up from
     * parsing the given JSON response.
     */
    private static List<BusService> extractBusServiceFromJson(String busServiceJSON, String requestUrlByBusStop, String userServiceNo) {
        // If the JSON string is empty or null, then return early.
        if (TextUtils.isEmpty(busServiceJSON)) {
            return null;
        }

        // Create an empty ArrayList that we can start adding earthquakes to
        List<BusService> busServices = new ArrayList<>();

        // Try to parse the JSON response string. If there's a problem with the way the JSON
        // is formatted, a JSONException exception object will be thrown.
        // Catch the exception so the app doesn't crash, and print the error message to the logs.
        try {

            // Create a JSONObject from the JSON response string
            JSONObject baseJsonResponse = new JSONObject(busServiceJSON);

            // Extract the JSONArray associated with the key called "features",
            // which represents a list of features (or earthquakes).
            JSONArray busServiceArray = baseJsonResponse.getJSONArray("value");

            boolean found = false;

            // For each earthquake in the earthquakeArray, create an {@link Earthquake} object
            for (int i = 0; i < busServiceArray.length(); i++) {

                // Get a single earthquake at position i within the list of earthquakes
                JSONObject currentBusService = busServiceArray.getJSONObject(i);

                if (currentBusService.getString("ServiceNo").equals(userServiceNo) && currentBusService.getInt("Direction")==1) {
                    found = true;
                    // Extract the value for the key called "BusStopCode"
                    String busStopCode = currentBusService.getString("BusStopCode");

                    ArrayList<String> info = extractBusServiceByBusStopFromJson(requestUrlByBusStop, busStopCode, userServiceNo);
                    String busStopName = info.get(0);

                    ArrayList<String> arrivalList = new ArrayList<>();//TODO format to integer later on
                    for (int k = 1; k < 4; k++) {
                        arrivalList.add(info.get(k));
                    }


                    // Create a new {@link Earthquake} object with the magnitude, location, time,
                    // and url from the JSON response.
                    BusService busService = new BusService(busStopName, busStopCode, arrivalList);

                    // Add the new {@link Earthquake} to the list of earthquakes.
                    busServices.add(busService);
                }

                if(found == true && currentBusService.getInt("Direction")==2){
                    break;
                }
            }

        } catch (JSONException e) {
            // If an error is thrown when executing any of the above statements in the "try" block,
            // catch the exception here, so the app doesn't crash. Print a log message
            // with the message from the exception.
            Log.e("QueryUtils", "Problem parsing the bus service JSON results", e);
        }

        // Return the list of earthquakes
        return busServices;
    }

    private static ArrayList<String> extractBusServiceByBusStopFromJson(String requestUrl, String busStopCode, String userServiceNo) {

        URL url = createUrl(requestUrl+"?BusStopCode="+busStopCode+"&ServiceNo="+userServiceNo);

        // Perform HTTP request to the URL and receive a JSON response back
        String jsonResponse = null;
        try {
            jsonResponse = makeHttpRequest(url);
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem making the HTTP request.", e);
        }

        // If the JSON string is empty or null, then return early.
        if (TextUtils.isEmpty(jsonResponse)) {
            return null;
        }

        ArrayList<String> info = new ArrayList<>();

        try {
            URL url2 = createUrl("http://datamall2.mytransport.sg/ltaodataservice/BusStops");

            // Perform HTTP request to the URL and receive a JSON response back
            String jsonResponse2 = null;
            try {
                jsonResponse2 = makeHttpRequest(url2);
            } catch (IOException e) {
                Log.e(LOG_TAG, "Problem making the HTTP request.", e);
            }

            // If the JSON string is empty or null, then return early.
            if (TextUtils.isEmpty(jsonResponse2)) {
                return null;
            }

            JSONObject busStopJsonResponse = new JSONObject(jsonResponse2);
            JSONArray busStopArray = busStopJsonResponse.getJSONArray("value");
            for (int d=0; d<busStopArray.length(); d++){
                JSONObject currentBusStop = busStopArray.getJSONObject(d);
                if (currentBusStop.getString("BusStopCode").equals("00481")) {//TODO replace 00481 busStopCode
                    info.add(currentBusStop.getString("Description"));
                    break;
                }
            }

            // Create a JSONObject from the JSON response string
            JSONObject baseJsonResponse = new JSONObject(jsonResponse);

            // Extract the JSONArray associated with the key called "features",
            // which represents a list of features (or earthquakes).
            JSONArray busServiceArray = baseJsonResponse.getJSONArray("Services");

            // For each earthquake in the earthquakeArray, create an {@link Earthquake} object
            for (int i = 0; i < busServiceArray.length(); i++) {

                JSONObject currentBusService = busServiceArray.getJSONObject(i);

                JSONObject nextBus = currentBusService.getJSONObject("NextBus");
                try{
                    info.add(timeCalculator(nextBus.getString("EstimatedArrival")));
                }
                catch (Exception e){
                    Log.e(LOG_TAG, "Problem with time manipulation.", e);
                }

                JSONObject nextBus2 = currentBusService.getJSONObject("NextBus2");
                try{
                    info.add(timeCalculator(nextBus2.getString("EstimatedArrival")));
                }
                catch (Exception e){
                    Log.e(LOG_TAG, "Problem with time manipulation.", e);
                }

                JSONObject nextBus3 = currentBusService.getJSONObject("NextBus3");
                try{
                    info.add(timeCalculator(nextBus3.getString("EstimatedArrival")));
                }
                catch (Exception e){
                    Log.e(LOG_TAG, "Problem with time manipulation.", e);
                }
            }

        } catch (JSONException e) {
            // If an error is thrown when executing any of the above statements in the "try" block,
            // catch the exception here, so the app doesn't crash. Print a log message
            // with the message from the exception.
            Log.e("QueryUtils", "Problem parsing the bus stop JSON results", e);
        }

        return info;
    }

    private static String timeCalculator(String unformattedArrivalTime) throws Exception{
        if (unformattedArrivalTime.isEmpty()){
            return null;
        }

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ssZ", Locale.ENGLISH);
        sdf.setTimeZone(TimeZone.getTimeZone("Asia/Singapore"));
        Calendar formattedTime = Calendar.getInstance(TimeZone.getTimeZone("Asia/Singapore"));
        formattedTime.setTime(sdf.parse(unformattedArrivalTime));
        Calendar currentTime = Calendar.getInstance(TimeZone.getTimeZone("Asia/Singapore"));

        long difference = (formattedTime.getTimeInMillis() - currentTime.getTimeInMillis())/60000;
        return Long.toString(difference);
    }

}
