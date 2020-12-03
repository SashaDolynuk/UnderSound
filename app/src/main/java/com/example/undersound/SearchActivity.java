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

import java.io.IOException;
import java.io.Reader;
import java.util.HashMap;
import java.util.Map;


public class SearchActivity extends AppCompatActivity {
    TextView genreText;
    TextView artistText;
    TextView trackText;

    //Sets the strings according to the values "pushed" from Main.java
    String genre = getIntent().getExtras().getString(MainActivity.TAG_GENRE);
    String artist = getIntent().getExtras().getString(MainActivity.TAG_ARTIST);
    String track = getIntent().getExtras().getString(MainActivity.TAG_TRACK);

    // pass token into this activity as a string
    // this is a temporary token
    String token = "BQCAruZoG1lSp9JyQe7X1UOzawUN6N9lGe11aVLVKflELK2P5khUpYm9HziKsP4rhEOEcqa-i47cw6GqjJMrz2qWHBurFP8OpUw-vxJDtAI6SN9OS6tqZ98oxpnh8QJOzg31G5YjUFl7lDLQZQ";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        // can only access ids and set/get text from the activity_search.xml file. there is only one textview in there, editGenre2
        genreText = (TextView) findViewById(R.id.UserEntryText);

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
        String searchURL = "https://api.spotify.com/v1/search?q=" + formatTrack + "&type=track";
        // StringRequest or JsonObjectRequest
        JsonObjectRequest getTrackRequest = new JsonObjectRequest(Request.Method.GET, searchURL, (JSONObject) null,
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
        searchURL = "https://api.spotify.com/v1/search?q=" + formatArtist + "&type=artist";
        // StringRequest or JsonObjectRequest
        JsonObjectRequest getArtistRequest = new JsonObjectRequest(Request.Method.GET, searchURL, (JSONObject) null,
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
    }


    private void initializeTextViews() {
        //Sets the texts to display the values
        genreText.setText(genre); // whatever you put in here will pop up in the edited text box, rn it is just what the user input for genre
//        artistText.setText(artist);
//        trackText.setText(track);
    }

    // get request for search for item based on track string that user enters
}

// show The Image in a ImageView
