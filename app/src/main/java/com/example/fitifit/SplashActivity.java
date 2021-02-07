package com.example.fitifit;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);


        Thread animation = new Thread(){
            @Override
            public void run(){
                ImageView image = findViewById(R.id.splash);
                Animation animation1 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade_intro_autor);
                image.startAnimation(animation1);
            }
        };
        animation.start();


        Thread redirect = new Thread(){
            @Override
            public void run(){
                try {
                    sleep(2500);
                    Intent i = new Intent(getApplicationContext(),MainActivity.class);
                    startActivity(i);
                    finish();
                    super.run();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        redirect.start();


    }
}