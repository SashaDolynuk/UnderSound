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

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class SearchActivity extends AppCompatActivity {
    TextView genreText;
    TextView artistText;
    TextView trackText;

    // bigger scope for our strings
    String genre;
    String artist;
    String track;


    // set popularity parameters
    String minPop = "5";
    String maxPop = "50";

    // pass token into this activity as a string
    // this is a temporary token
    String token = "BQBqrTEoFeCyGcrcFeJHTkfwDBNsNU1sUigcddmBpb59raCStFkJ4ODT6NeRBLVnsWRI2mpNZI5KSVTWZQkV9ltI0vxh0UoGZxopD9QMT3s5sl2PIOAyuV9Pkwvh-k2YfFpYyCKJu9SMNAm-6g";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        //Sets the strings according to the values "pushed" from Main.java
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
        String searchTrackURL = "https://api.spotify.com/v1/search?q=" + formatTrack + "&type=track";
        // StringRequest or JsonObjectRequest
        JsonObjectRequest getTrackRequest = new JsonObjectRequest(Request.Method.GET, searchTrackURL, (JSONObject) null,
                new Response.Listener<JSONObject>()
                {
                    @Override
                    public void onResponse(JSONObject response) {
                        // response
                        Log.d("Response", response.toString());
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
        String searchArtistURL = "https://api.spotify.com/v1/search?q=" + formatArtist + "&type=artist";
        // StringRequest or JsonObjectRequest
        JsonObjectRequest getArtistRequest = new JsonObjectRequest(Request.Method.GET, searchArtistURL, (JSONObject) null,
                new Response.Listener<JSONObject>()
                {
                    @Override
                    public void onResponse(JSONObject response) {
                        // response
                        Log.d("Response", response.toString());
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

        // parse through getTrackRequest and getArtistRequest for ids
        // these are temporary, change values to whatever is in the get request
        String trackID = "0c6xIDDpzE81m2q797ordA";
        String artistID = "4NHQUGzhtTLFvgF5SZesLK";

        // get 1 rec based on genre string (from user), generated seed artist and seed track, and popularity (set by us)
        String recURL = "https://api.spotify.com/v1/recommendations?limit=1&seed_artists=" + artistID + "&seed_genres=" + genre + "&seed_tracks=" + trackID + "&min_popularity=" + minPop + "&max_popularity=" + maxPop;
        JsonObjectRequest getRecRequest = new JsonObjectRequest(Request.Method.GET, recURL, (JSONObject) null,
                new Response.Listener<JSONObject>()
                {
                    @Override
                    public void onResponse(JSONObject response) {
                        // response
                        Log.d("Response", response.toString());
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