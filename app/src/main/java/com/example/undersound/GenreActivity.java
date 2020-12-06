package com.example.undersound;

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

public class GenreActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    //private Button genrebutton;
    private Spinner genretext;
    protected String genre;
    public static final String TAG_GENRE = "genre";

    public void onItemSelected(AdapterView<?> parent, View view,
                               int pos, long id) {
        // An item was selected. You can retrieve the selected item using
        // parent.getItemAtPosition(pos)
        String tempgenre = parent.getItemAtPosition(pos).toString();
        Log.d("genre: ", tempgenre);
        // Showing selected spinner item
        Toast.makeText(parent.getContext(), "Selected: " + tempgenre, Toast.LENGTH_LONG).show();
        genre = tempgenre;
    }

    public void onNothingSelected(AdapterView<?> parent) {
        // Another interface callback
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_genre);

        genretext = (Spinner) findViewById(R.id.editGenre); //genre assigned via spinner
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.genre_array, android.R.layout.simple_spinner_item);
        //specifies the layout
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        genretext.setAdapter(adapter);

        launchResultActivity(genre);
        //sets
        //genrebutton.setOnClickListener(this);
    }

    private void launchResultActivity(String genre)
    {
        /*The intent class represents an action is used to "load" activities into a variable so they can be passed in and launched from
         * the startActivity method. Basic intents take two arguments, the current class(.java) and the class(.java) that the app will move to
         *  The line below initializes an Intent named resultActivity and passes in (Main.this,Result.class) much like the this-> pointer in C++,
         *  the this keyword in java is used by classes to reference themselves*/
        Intent resultActivity = new Intent(GenreActivity.this, MainActivity.class);

        /*Since this method is private, if we want the Result Activity/class to access it's members (the strings TAG_TIP and TAG_GRAND_TOTAL),
         *we can "push" members from the Main Acivity/class to Result, much like how a friend function can "pull" private members from objects
         */

        resultActivity.putExtra(TAG_GENRE, genre);
        Log.d("Genre: ", genre);
        //Launches the new activity
        startActivity(resultActivity);
    }
}