package com.example.fitifit;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

public class UserWorkoutActivity extends AppCompatActivity {



    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_workout);



        intent = getIntent();
        String userid = intent.getStringExtra("userid");

    }
}