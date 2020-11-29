package com.example.undersound;

import android.app.Activity;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends Activity implements OnClickListener {

    private Button classical;
    private Button pop;
    private Button rock;
    private Button rap;
    private Button country;
    private Button jazz;

    private String genre;

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


    }
}