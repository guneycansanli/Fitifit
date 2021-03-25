package com.example.fitifit;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class WaterTrackerActivity extends AppCompatActivity {

    Button createButton;
    Button bottleButton;
    ProgressBar progressBar;
    ProgressBar vertical_progressbar;
    TextView waterLeftTextView;
    SharedPreferences sharedPreferences;
    int btnClickCount=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_water_tracker);

        sharedPreferences = getApplication().getSharedPreferences("com.example.fitifit", Context.MODE_PRIVATE);
        waterLeftTextView = findViewById(R.id.wodaLeftTextView);
        createButton = findViewById(R.id.generujButton);
        bottleButton = findViewById(R.id.szklankaButton);
        progressBar = findViewById(R.id.progressBar);
        vertical_progressbar = findViewById(R.id.vertical_progressbar);


        waterLeftTextView.setVisibility(View.INVISIBLE);
        vertical_progressbar.setVisibility(View.INVISIBLE);
        bottleButton.setVisibility(View.INVISIBLE);
        progressBar.setVisibility(View.INVISIBLE);
        progressBar.setProgress(0);
        vertical_progressbar.setProgress(2500);

        // Button for filling the progressbar

        bottleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (progressBar.getProgress()>=2250)
                {
                    Toast.makeText(getApplicationContext(),"Günlük yeterli su içtiniz!",Toast.LENGTH_SHORT).show();
                }
                progressBar.setProgress(progressBar.getProgress() + 250);
                vertical_progressbar.setProgress(vertical_progressbar.getProgress()-250);
                btnClickCount+=1;
                waterLeft(bottleButton);
            }

        });


    }

    // Button to generate water / stripes / etc

    public void generateWater(View view){
        createButton.setVisibility(View.INVISIBLE);
        progressBar.setVisibility(View.VISIBLE);
        bottleButton.setVisibility(View.VISIBLE);
        vertical_progressbar.setVisibility(View.VISIBLE);
        waterLeftTextView.setVisibility(View.VISIBLE);

    }

    public void waterLeft(View view){
        int numberOfGlasses=10-btnClickCount;
        if (numberOfGlasses>=0) {
            waterLeftTextView.setText("Kalan: " + numberOfGlasses + " içilecek bardak!");
        }else waterLeftTextView.setText("İçmeniz gereken bardak sayısı 0 !");
    }

    //MENU


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_water,menu);
        return super.onCreateOptionsMenu(menu);
    }

    //Menu Operation

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);
        if(item.getItemId() == R.id.newDay){
            Intent intent = new Intent(getApplicationContext(),NewDayWater.class);
            startActivity(intent);
            return true;
        }
        return false;
    }
}