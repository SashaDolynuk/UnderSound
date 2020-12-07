package com.example.undersound;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
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

import android.annotation.SuppressLint;
import android.content.Intent;
import com.spotify.android.appremote.api.ConnectionParams;
import com.spotify.android.appremote.api.Connector;
import com.spotify.android.appremote.api.SpotifyAppRemote;
import com.spotify.sdk.android.auth.AuthorizationClient;
import com.spotify.sdk.android.auth.AuthorizationRequest;
import com.spotify.sdk.android.auth.AuthorizationResponse;

import android.widget.ImageView;

import com.squareup.picasso.Picasso;


public class SearchActivity extends AppCompatActivity {
    private Button pausebutton;//Pause/Play button
    private static final int REQUEST_CODE = 1337;
    public static SpotifyClass spotifyClass;

    //Holds the physical genre text, artist text, and the recommendations
    TextView genreText;
    TextView artistText;
    TextView trackText;
    TextView artistRec;
    TextView trackRec;

    // carry over from user input in main, initialized in onCreate
    String genre;
    String artist;
    String track;
    String formatGenre = "";

    // store info retrieved from get requests
    String trackID = "";
    String artistID = "";
    String recTrack = ""; // name of recommended track
    String recArtist = ""; // name of artist of recommended track
    String recTrackID = ""; // id of recommended track for playback
    String albumCoverURL = ""; // album cover image of recommended track for display

    // set popularity parameters
    String minPop = "0";
    String maxPop = "5";

    // Spotify authentication vars
    String token;
    private static final String CLIENT_ID = "2f184ad41615437489cfd03177eade83";
    private static final String REDIRECT_URI = "com.example.undersound://callback/";
    private SpotifyAppRemote mSpotifyAppRemote;

    //Suppressing Lint for signing report
    @SuppressLint("all")
    @Override
    protected void onStart() {
        spotifyClass = new SpotifyClass();
        super.onStart();

        LogIn();
    }

    // authorization request
    @SuppressLint("all")
    public void LogIn() {
        AuthorizationRequest.Builder builder = new AuthorizationRequest.Builder(spotifyClass.CLIENT_ID, AuthorizationResponse.Type.TOKEN, spotifyClass.REDIRECT_URI);
        builder.setScopes(new String[]{"streaming"});
        AuthorizationRequest request = builder.build();
        AuthorizationClient.openLoginActivity(this, REQUEST_CODE, request);

        ConnectionParams connP = new ConnectionParams.Builder(spotifyClass.CLIENT_ID).setRedirectUri(spotifyClass.REDIRECT_URI).showAuthView(true).build();

        SpotifyAppRemote.connect(this, connP, new Connector.ConnectionListener() {
            public void onConnected(SpotifyAppRemote spotifyAppRemote) {
                spotifyClass.mSpotifyAppRemote = spotifyAppRemote;
                Log.d("Testing!!!", "Connected");

                connected();
            }

            public void onFailure(Throwable throwable) {
                Log.e("Testing!!!", throwable.getMessage(), throwable);
                //Something went wrong when attempting to connect! Handle errors here
            }
        });
    }

    public void connected(){


    }

    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);

        // Check if result comes from the correct activity
        if (requestCode == REQUEST_CODE) {
            AuthorizationResponse response = AuthorizationClient.getResponse(resultCode, intent);

            switch (response.getType()) {
                // Response was successful and contains auth token
                case TOKEN:
                    // Handle successful response
                    Log.d("TOKEN!!", response.getAccessToken());
                    token = response.getAccessToken();
                    // Sets the strings according to the values "pushed" from Main.java
                    genre = getIntent().getExtras().getString(MainActivity.TAG_GENRE);
                    artist = getIntent().getExtras().getString(MainActivity.TAG_ARTIST);
                    track = getIntent().getExtras().getString(MainActivity.TAG_TRACK);

                    // can only access ids and set/get text from the activity_search.xml file. there is only one textview in there, editGenre2
                    genreText = (TextView) findViewById(R.id.UserEntryGenre);
                    artistText = (TextView) findViewById(R.id.userEntryArtist);
                    trackText = (TextView) findViewById(R.id.userEntryTrack);
                    artistRec = (TextView) findViewById(R.id.recArtist);
                    trackRec = (TextView) findViewById(R.id.recTrack);

                    // format strings correctly for use in url
                    formatGenre = formatStr(genre);
                    String formatTrack = formatStr(track);
                    String formatArtist = formatStr(artist);

                    // search for an item (track) using volley get request, returns json object, parse for track id
                    RequestQueue queue = Volley.newRequestQueue(this);
                    String searchTrackURL = "https://api.spotify.com/v1/search?q=" + formatTrack + "&type=track&limit=1";
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
                        @Override // parameters for header for get request
                        public Map<String, String> getHeaders() throws AuthFailureError {
                            Map<String, String>  params = new HashMap<String, String>();
                            params.put("Accept", "application/json");
                            params.put("Content-Type", "application/json");
                            params.put("Authorization", "Bearer " + token);

                            return params;
                        }
                    };
                    queue.add(getTrackRequest);

                    // search for an item (artist) using volley get request, returns json object, parse for artist id
                    String searchArtistURL = "https://api.spotify.com/v1/search?q=" + formatArtist + "&type=artist&limit=1";
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

                                        // this is in the on response of another request so it only completes after it has all necessary info
                                        // get 1 rec based on genre string (from user), generated seed artist and seed track, and popularity (set by us)
                                        String recURL = "https://api.spotify.com/v1/recommendations?limit=1&seed_artists=" + artistID + "&seed_genres=" + formatGenre + "&seed_tracks=" + trackID + "&min_popularity=" + minPop + "&max_popularity=" + maxPop;
                                        JsonObjectRequest getRecRequest = new JsonObjectRequest(Request.Method.GET, recURL, (JSONObject) null,
                                                new Response.Listener<JSONObject>()
                                                {
                                                    @Override
                                                    public void onResponse(JSONObject response) {
                                                        try {
                                                            // get track name, artist name, track id (for playing), and image url (for image) from json
                                                            Log.d("JSON Response", response.toString());
                                                            JSONArray tracksArr = response.getJSONArray("tracks");
                                                            JSONObject obj = tracksArr.getJSONObject(0);
                                                            JSONArray artistsArr = obj.getJSONArray("artists");
                                                            JSONObject obj2 = artistsArr.getJSONObject(0);
                                                            JSONObject albumObj = obj.getJSONObject("album");
                                                            JSONArray imagesArr = albumObj.getJSONArray("images");
                                                            JSONObject obj3 = imagesArr.getJSONObject(0);
                                                            recArtist = obj2.getString("name");
                                                            recTrack = obj.getString("name");
                                                            recTrackID = obj.getString("id");
                                                            albumCoverURL = obj3.getString("url");

                                                            // display info of recommended stuff once all info has been retrieved
                                                            initializeTextViews();
                                                            // Display album cover via Picasso
                                                            ImageView album_artwork = (ImageView) findViewById(R.id.AlbumCover);
                                                            Picasso.get().load(albumCoverURL).into(album_artwork);

                                                            spotifyClass.playSong(recTrackID);

                                                            // create pause/play button and functionality
                                                            pausebutton = (Button) findViewById(R.id.pausebutton);
                                                            pausebutton.setOnClickListener(new View.OnClickListener() {
                                                                public void onClick(View view) {
                                                                    spotifyClass.mSpotifyAppRemote.getPlayerApi().getPlayerState().setResultCallback(playerState -> {
                                                                        if (playerState.isPaused) {
                                                                            spotifyClass.mSpotifyAppRemote.getPlayerApi().resume();
                                                                        } else {
                                                                            spotifyClass.mSpotifyAppRemote.getPlayerApi().pause();
                                                                        }
                                                                    });
                                                                }
                                                            });

                                                        }
                                                        catch (JSONException e) {
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
                    break;

                // Auth flow returned an error
                case ERROR:
                    // Handle error response
                    Log.d("no token!", "no token!");
                    break;

                // Most likely auth flow was cancelled
                default:
                    // Handle other cases
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
    }

    @Override
    protected void onStop() {
        super.onStop();
        SpotifyAppRemote.disconnect(mSpotifyAppRemote);
    }

    private void initializeTextViews() {
        //Sets the texts to display the values
        genreText.setText(genre);
        artistText.setText(artist);
        trackText.setText(track);
        artistRec.setText(recArtist);
        trackRec.setText(recTrack);
    }

    // format string correctly for use in get requests
    private String formatStr(String toFormat) {
        String formattedStr = "";
        String temp = "";
        for (int i = 0; i < toFormat.length(); i++) {
            if (toFormat.charAt(i) != ' ') {
                temp += toFormat.charAt(i);
            } else {
                formattedStr = formattedStr + temp + "%20";
                temp = "";
            }
        } formattedStr += temp;
        return formattedStr;
    }
}