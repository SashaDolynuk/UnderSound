package com.example.undersound;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;


public class SearchActivity extends AppCompatActivity {
    TextView genreText;
    TextView artistText;
    TextView trackText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        genreText = (TextView) findViewById(R.id.editGenre);
        artistText = (TextView) findViewById(R.id.editArtist);
        trackText = (TextView) findViewById(R.id.editTrack);
        initializeTextViews();
    }

    //comment
    private void initializeTextViews() {
        //Sets doubles according to the values "pushed" from Main.java
        String genre = getIntent().getExtras().getString(MainActivity.TAG_GENRE);
        String artist = getIntent().getExtras().getString(MainActivity.TAG_ARTIST);
        String track = getIntent().getExtras().getString(MainActivity.TAG_TRACK);
        //Sets the strings accordingly
        String currentGenre = genreText.getText().toString();
        String currentArtist = artistText.getText().toString();
        String currentTrack = trackText.getText().toString();
        //Sets the texts to display the values
        genreText.setText(genre); //use the string from the other class
        artistText.setText(artist);
        trackText.setText(track);
    }

    // get request for search for item based on track string that user enters
}