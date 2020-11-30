package com.example.undersound;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class SearchActivity extends AppCompatActivity {
    TextView genreText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        genreText = (TextView)findViewById(R.id.genre);
        initializeTextViews();
    }
//comment
    private void initializeTextViews()
    {
        //Sets doubles according to the values "pushed" from Main.java
        String genre = getIntent().getExtras().getString(MainActivity.TAG_GENRE);

        //Sets the strings accordingly
        String currentGenre = genreText.getText().toString();

        //Sets the texts to display the values
        genreText.setText(currentGenre);
    }
}