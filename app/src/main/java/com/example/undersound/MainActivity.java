package com.example.undersound;

import android.app.Activity;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;
import android.content.Intent;

public class MainActivity extends Activity implements OnClickListener {

    private Button classical;
    private Button pop;
    private Button rock;
    private Button rap;
    private Button country;
    private Button jazz;

    public static final String TAG_GENRE = "genre";
    private static final String TAG_DEBUG = MainActivity.class.getName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        classical = (Button) findViewById(R.id.classical);
        pop = (Button) findViewById(R.id.pop);
        rock = (Button) findViewById(R.id.rock);
        rap = (Button) findViewById(R.id.rap);
        jazz = (Button) findViewById(R.id.jazz);
        country = (Button) findViewById(R.id.country);

        classical.setOnClickListener(this);
        pop.setOnClickListener(this);
        rock.setOnClickListener(this);
        rap.setOnClickListener(this);
        jazz.setOnClickListener(this);
        country.setOnClickListener(this);
    }

    @Override
    /*onClick is what is called when the buttons are pressed and they take in Views as arguments
     * as buttons are children of the view class, buttons can polymorphically be passed in. The button
     * that called the onClick is automatically fed in*/
    public void onClick(View v) {
        String genre = "";
        //The switch statements grab the id values of the button pressed and calculates the tip accordingly
        switch (v.getId()) {

            case R.id.jazz: {
                genre = "jazz";
                break;
            }
            case R.id.classical: {
                genre = "classical";
                break;
            }
            case R.id.pop: {
                genre = "pop";
                break;
            }
            case R.id.country: {
                genre = "country";
                break;
            }
            case R.id.rock: {
                genre = "rock";
                break;
            }
            case R.id.rap: {
                genre = "rap";
                break;
            }
            default: {
                break;
            }
        }

        launchResultActivity(genre);
    }

    private void launchResultActivity(String genre)
    {
        /*The intent class represents an action is used to "load" activities into a variable so they can be passed in and launched from
         * the startActivity method. Basic intents take two arguments, the current class(.java) and the class(.java) that the app will move to
         *  The line below initializes an Intent named resultActivity and passes in (Main.this,Result.class) much like the this-> pointer in C++,
         *  the this keyword in java is used by classes to reference themselves*/
        Intent resultActivity = new Intent(MainActivity.this, SearchActivity.class);

        /*Since this method is private, if we want the Result Activity/class to access it's members (the strings TAG_TIP and TAG_GRAND_TOTAL),
         *we can "push" members from the Main Acivity/class to Result, much like how a friend function can "pull" private members from objects
         */

        resultActivity.putExtra(TAG_GENRE, genre);
        Log.d(TAG_DEBUG, "Genre: " + genre);
        //Launches the new activity
        startActivity(resultActivity);
    }
}