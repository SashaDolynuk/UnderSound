package com.example.undersound;

import android.app.Activity;
import androidx.appcompat.app.AppCompatActivity;
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
import android.widget.Toast;
import android.content.Intent;
import android.widget.AdapterView.OnItemSelectedListener;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.AdapterView;
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
import android.widget.Toast;
import android.content.Intent;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    //private Button genrebutton;
    private Spinner genretext;
    public String genre;
    private Button NextAct;
    Intent intent;
    public static final String TAG_GENRE = "genre";
    private static final String TAG_DEBUG = MainActivity.class.getName();

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        genretext = (Spinner) findViewById(R.id.editGenre); //genre assigned via spinner
        NextAct = (Button) findViewById(R.id.NextButton);

        //creates an array adapter using the string array and default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.genre_array, android.R.layout.simple_spinner_item);
        //specifies the layout
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        genretext.setAdapter(adapter);
        //sets
        genretext.setOnItemSelectedListener(new OnItemSelectedListener();
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view,
                               int pos, long id) {
        // An item was selected. You can retrieve the selected item using
        // parent.getItemAtPosition(pos)
        genre = (String) genretext.getSelectedItem();
        Log.d("genre: ", genre);
        // Showing selected spinner item
        Toast.makeText(parent.getContext(), "Selected: " + genre, Toast.LENGTH_LONG).show();
    }
    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        // Another interface callback
    }

    NextAct.setOnClickListener(new View.OnClickListener()

    /*onClick is what is called when the buttons are pressed and they take in Views as arguments
     * as buttons are children of the view class, buttons can polymorphically be passed in. The button
     * that called the onClick is automatically fed in*/

    private void launchResultActivity(String genre)
    {
        /*The intent class represents an action is used to "load" activities into a variable so they can be passed in and launched from
         * the startActivity method. Basic intents take two arguments, the current class(.java) and the class(.java) that the app will move to
         *  The line below initializes an Intent named resultActivity and passes in (Main.this,Result.class) much like the this-> pointer in C++,
         *  the this keyword in java is used by classes to reference themselves*/
        Intent resultActivity = new Intent(MainActivity.this, TrackArtistActivity.class);

        /*Since this method is private, if we want the Result Activity/class to access it's members (the strings TAG_TIP and TAG_GRAND_TOTAL),
         *we can "push" members from the Main Acivity/class to Result, much like how a friend function can "pull" private members from objects
         */

        resultActivity.putExtra(TAG_GENRE, genre);
        Log.d(TAG_DEBUG, "Genre: " + genre);
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
