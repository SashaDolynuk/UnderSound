package com.example.undersound;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class SearchActivity extends AppCompatActivity {
    //Holds the physical genre text, artist text
    TextView genreText;
    TextView artistText;
    TextView trackText;

    // carry over from user input in main, initialized in onCreate
    String genre;
    String artist;
    String track;

    // store info retrieved from get requests
    String trackID = "";
    String artistID = "";
    String recTrack = ""; // name of recommended track
    String recArtist = ""; // name of artist of recommended track
    String recTrackID = ""; // id of recommended track for playback
    String albumCoverURl = ""; // album cover image of recommended track for display

    // set popularity parameters
    String minPop = "5";
    String maxPop = "50";

    // pass token into this activity as a string
    // this is a temporary token
    String token = "BQA9BuWu-Eya1SIyXdMKwWKw6WHaL_9XtuPdALfMihAUnDDXYGFRE3oQQvWU-VtsjitTusufdn1oWr_kQxldZ5z5SewGp9JFIAi1bTWxrOGVV3GZ8TOEytlgo0-_789kTTqX3BKCU4iGGqeWnQ";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        // Sets the strings according to the values "pushed" from Main.java
        genre = getIntent().getExtras().getString(MainActivity.TAG_GENRE);
        artist = getIntent().getExtras().getString(MainActivity.TAG_ARTIST);
        track = getIntent().getExtras().getString(MainActivity.TAG_TRACK);

        // can only access ids and set/get text from the activity_search.xml file. there is only one textview in there, editGenre2
        genreText = (TextView) findViewById(R.id.UserEntryGenre);
        artistText = (TextView) findViewById(R.id.userEntryArtist);
        trackText = (TextView) findViewById(R.id.userEntryTrack);
        initializeTextViews();

        // format track string correctly
        String formatTrack = "";
        int trackStrLen = track.length();
        String temp = "";
        for (int i = 0; i < trackStrLen; i++) {
            if (track.charAt(i) != ' ') {
                temp += track.charAt(i);
            } else {
                formatTrack = formatTrack + temp + "%20";
                temp = "";
            }
        } formatTrack += temp;

        // format artist string correctly
        String formatArtist = "";
        int artistStrLen = artist.length();
        temp = "";
        for (int i = 0; i < artistStrLen; i++) {
            if (artist.charAt(i) != ' ') {
                temp += artist.charAt(i);
            } else {
                formatArtist = formatArtist + temp + "%20";
                temp = "";
            }
        } formatArtist += temp;

        // search for an item (track) using volley get request, returns json object, parse for track id
        RequestQueue queue = Volley.newRequestQueue(this);
        String searchTrackURL = "https://api.spotify.com/v1/search?q=" + formatTrack + "&type=track&limit=1";
        // StringRequest or JsonObjectRequest
        JsonObjectRequest getTrackRequest = new JsonObjectRequest(Request.Method.GET, searchTrackURL, (JSONObject) null,
                new Response.Listener<JSONObject>()
                {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONObject obj = response.getJSONObject("tracks");
                            JSONArray arr = obj.getJSONArray("items");
                            JSONObject obj2 = arr.getJSONObject(0);
                            // Retrieves the string labeled "id" from external_urls within 0 within items within tracks
                            trackID = obj2.getString("id");
                            Log.d("Response", trackID);
                        }
                        // Try and catch are included to handle any errors due to JSON
                        catch (JSONException e) {
                            // If an error occurs, this prints the error to the log
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("ERROR","error => "+error.toString());
                    }
                }
        ) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String>  params = new HashMap<String, String>();
                params.put("Accept", "application/json");
                params.put("Content-Type", "application/json");
                params.put("Authorization", "Bearer " + token);

                return params;
            }
        };
        queue.add(getTrackRequest);

        // search for an item (artist) using volley get request, returns json object, parse for track id
        String searchArtistURL = "https://api.spotify.com/v1/search?q=" + formatArtist + "&type=artist&limit=1";
        // StringRequest or JsonObjectRequest
        JsonObjectRequest getArtistRequest = new JsonObjectRequest(Request.Method.GET, searchArtistURL, (JSONObject) null,
                new Response.Listener<JSONObject>()
                {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONObject obj = response.getJSONObject("artists");
                            JSONArray arr = obj.getJSONArray("items");
                            JSONObject obj2 = arr.getJSONObject(0);
                            // Retrieves the string labeled "id" from folder 0 within folder items within folder artists
                            artistID = obj2.getString("id");
                            Log.d("Response", artistID);
                        }
                        // Try and catch are included to handle any errors due to JSON
                        catch (JSONException e) {
                            // If an error occurs, this prints the error to the log
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("ERROR","error => "+error.toString());
                    }
                }
        ) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String>  params = new HashMap<String, String>();
                params.put("Accept", "application/json");
                params.put("Content-Type", "application/json");
                params.put("Authorization", "Bearer " + token);

                return params;
            }
        };
        queue.add(getArtistRequest);

        // get 1 rec based on genre string (from user), generated seed artist and seed track, and popularity (set by us)
        String recURL = "https://api.spotify.com/v1/recommendations?limit=1&seed_artists=" + artistID + "&seed_genres=" + genre + "&seed_tracks=" + trackID + "&min_popularity=" + minPop + "&max_popularity=" + maxPop;
        JsonObjectRequest getRecRequest = new JsonObjectRequest(Request.Method.GET, recURL, (JSONObject) null,
                new Response.Listener<JSONObject>()
                {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray arr = response.getJSONArray("tracks");
                            JSONObject obj = arr.getJSONObject(0);
                            JSONArray arr2 = obj.getJSONArray("artists");
                            JSONObject obj3 = arr2.getJSONObject(0);
                            recArtist = obj3.getString("artists");
                            recTrack = obj.getString("name");
                            recTrackID = obj.getString("id");

                            // get other info needed from jsonobject

                            Log.d("Response", recTrack);
                        }
                        // Try and catch are included to handle any errors due to JSON
                        catch (JSONException e) {
                            // If an error occurs, this prints the error to the log
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("ERROR","error => "+error.toString());
                    }
                }
        ) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String>  params = new HashMap<String, String>();
                params.put("Accept", "application/json");
                params.put("Content-Type", "application/json");
                params.put("Authorization", "Bearer " + token);

                return params;
            }
        };
        queue.add(getRecRequest);
    }


    private void initializeTextViews() {
        //Sets the texts to display the values
        genreText.setText(genre); // whatever you put in here will pop up in the edited text box, rn it is just what the user input for genre
        artistText.setText(artist);
        trackText.setText(track);
    }
}

// show The Image in a ImageView