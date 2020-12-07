package com.example.undersound;

import android.app.Activity;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.content.Intent;

public class MainActivity extends Activity implements OnClickListener {

    private Button startbutton;
    private Button genrebutton;
    private EditText genretext;
    private EditText artisttext;
    private EditText tracktext;
    //private Spinner genretext;

    public static final String TAG_GENRE = "genre";
    public static final String TAG_ARTIST = "artist";
    public static final String TAG_TRACK = "track";
    //private String genre;
    private static final String TAG_DEBUG = MainActivity.class.getName();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        genretext = (EditText) findViewById(R.id.editGenre);
        artisttext = (EditText) findViewById(R.id.editArtist);
        tracktext = (EditText) findViewById(R.id.editTrack);
        startbutton = (Button) findViewById(R.id.button);

        genrebutton = (Button) findViewById(R.id.genrebutton);
        genrebutton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                genreactivity();
            }
        });

        //sets
        startbutton.setOnClickListener(this);
    }

    private void genreactivity() {
        Intent genreactivity = new Intent(this, GenreActivity.class);
        startActivity(genreactivity);
    }

    @Override
    public void onClick(View v) {
        String genre = genretext.getText().toString();
        String artist = artisttext.getText().toString();
        String track = tracktext.getText().toString();

        if (genre.length() == 0 || artist.length() == 0 || track.length() == 0) {
            Toast.makeText(MainActivity.this, "Please enter a valid artist and track!", Toast.LENGTH_LONG).show();
            return;
        }

//      Intent intent= new Intent(MainActivity.this,SearchActivity.class);
//      intent.putExtra("data",String.valueOf(genretext.getSelectedItem())); //doesn't this happen later

        //includes global variable genre
        launchResultActivity(genre, artist, track);
        startActivity(new Intent(MainActivity.this, GenreActivity.class));
    }

    private void launchResultActivity(String genre, String artist, String track)
    {
        /*The intent class represents an action is used to "load" activities into a variable so they can be passed in and launched from
         * the startActivity method. Basic intents take two arguments, the current class(.java) and the class(.java) that the app will move to
         *  The line below initializes an Intent named resultActivity and passes in (Main.this,Result.class) much like the this-> pointer in C++,
         *  the this keyword in java is used by classes to reference themselves*/
        Intent resultActivity = new Intent(MainActivity.this, SearchActivity.class);

        /*Since this method is private, if we want the Result Activity/class to access it's members (the strings TAG_TIP and TAG_GRAND_TOTAL),
         *we can "push" members from the Main Activity/class to Result, much like how a friend function can "pull" private members from objects
         */
        resultActivity.putExtra(TAG_GENRE, genre);
        resultActivity.putExtra(TAG_ARTIST, artist);
        resultActivity.putExtra(TAG_TRACK, track);
        Log.d(TAG_DEBUG, "Genre: " + genre);
        Log.d(TAG_DEBUG, "Artist: " + artist);
        Log.d(TAG_DEBUG, "Track: " + track);
        //Launches the new activity
        startActivity(resultActivity);
    }
    /*
    private void ShowAlbum(){
        String imageUri = "https://i.imgur.com/tGbaZCY.jpg";
        ImageView albumcover = (ImageView) findViewById(R.id.ivBasicImage);
        Picasso.with(context).load(imageUri).into(albumcover);
    }
    */
}
