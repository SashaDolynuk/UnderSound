package com.example.undersound;

import android.app.Activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.content.Intent;

public class MainActivity extends Activity {

    private Button startbutton;
    private Button genrebutton;
    private EditText genretext;
    private EditText artisttext;
    private EditText tracktext;

    public static final String TAG_GENRE = "genre";
    public static final String TAG_ARTIST = "artist";
    public static final String TAG_TRACK = "track";
    private static final String TAG_DEBUG = MainActivity.class.getName();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        genretext = (EditText) findViewById(R.id.editGenre);
        artisttext = (EditText) findViewById(R.id.editArtist);
        tracktext = (EditText) findViewById(R.id.editTrack);

        // startbutton (launches SearchActivity.java) creation and functionality
        startbutton = (Button) findViewById(R.id.button);
        startbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String genre = genretext.getText().toString();
                String artist = artisttext.getText().toString();
                String track = tracktext.getText().toString();

                if (genre.length() == 0 || artist.length() == 0 || track.length() == 0) {
                    Toast.makeText(MainActivity.this, "Please enter a valid genre, artist and track!", Toast.LENGTH_LONG).show();
                    return;
                }
                searchactivity(genre, artist, track);
            }
        });

        // genrebutton (launches GenreActivity.java) creation and functionality
        genrebutton = (Button) findViewById(R.id.genrebutton);
        genrebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                genreactivity();
            }
        });
    }

    private void searchactivity(String genre, String artist, String track) {
        Intent resultActivity = new Intent(MainActivity.this, SearchActivity.class);
        resultActivity.putExtra(TAG_GENRE, genre);
        resultActivity.putExtra(TAG_ARTIST, artist);
        resultActivity.putExtra(TAG_TRACK, track);
        Log.d(TAG_DEBUG, "Genre: " + genre);
        Log.d(TAG_DEBUG, "Artist: " + artist);
        Log.d(TAG_DEBUG, "Track: " + track);
        //Launches SearchActivity
        startActivity(resultActivity);
    }

    private void genreactivity() {
        Intent genreactivity = new Intent(this, GenreActivity.class);
        //Launches GenreActivity
        startActivity(genreactivity);
    }

}
