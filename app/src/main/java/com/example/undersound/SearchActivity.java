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

    // pass token into this activity as a string
    // this is a temporary token
    String token = "BQAVQg497bjIi-a7P6AUFLkxADwPDfy34WYE0dQCX5XZKt9k7FoItvFEKdONsW6ixInP-a8Qted9QXoO-i8wxXXkZNXb4twE3gEBnQtUVEhDERFwpQ-sPyQdNtR56oeR8dvS9EdEGKamirl6Rw";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        // can only access ids and set/get text from the activity_search.xml file. there is only one textview in there, editGenre2
        genreText = (TextView) findViewById(R.id.editGenre2);

        initializeTextViews();


        // search for an item (track) using volley get request
        // returns json object, parse for track id
        String track = "watermelon%20sugar"; // figure out how to initialize with "%20" as spaces
        RequestQueue queue = Volley.newRequestQueue(this);
        String searchURL = "https://api.spotify.com/v1/search?q=" + track + "&type=track";
        // StringRequest or JsonObjectRequest
        JsonObjectRequest getRequest = new JsonObjectRequest(Request.Method.GET, searchURL, (JSONObject) null,
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
                        // TODO Auto-generated method stub
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
        queue.add(getRequest);
    }

    //comment
    private void initializeTextViews() {
        //Sets doubles according to the values "pushed" from Main.java
        String genre = getIntent().getExtras().getString(MainActivity.TAG_GENRE);
        String artist = getIntent().getExtras().getString(MainActivity.TAG_ARTIST);
        String track = getIntent().getExtras().getString(MainActivity.TAG_TRACK);
        //Sets the strings accordingly

        //commented the following out

//        String currentGenre = genreText.getText().toString();
//        String currentArtist = artistText.getText().toString();
//        String currentTrack = trackText.getText().toString();
        //Sets the texts to display the values
        genreText.setText(genre); //use the string from the other class
//        artistText.setText(artist);
//        trackText.setText(track);
    }

    // get request for search for item based on track string that user enters
}

// show The Image in a ImageView
