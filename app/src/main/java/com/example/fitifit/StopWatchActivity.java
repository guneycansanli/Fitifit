package com.example.fitifit;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.ImageView;

public class StopWatchActivity extends AppCompatActivity {

    private Button btnstart, btnstop;
    private ImageView icanchor;
    private Animation roundingalone;
    private Chronometer timeHere;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stop_watch);

        btnstart = findViewById(R.id.btnstart);
        icanchor = findViewById(R.id.icanchor);
        btnstop = findViewById(R.id.btnstop);
        timeHere = findViewById(R.id.timeHere);

        //create optional anim
        btnstop.setAlpha(0);

        //load anim
        roundingalone = AnimationUtils.loadAnimation(this, R.anim.roundingalone);


        btnstart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //passing anim
                icanchor.startAnimation(roundingalone);
                btnstop.animate().alpha(1).translationY(-80).setDuration(300).start();
                btnstart.animate().alpha(0).setDuration(300).start();
                btnstart.setVisibility(View.GONE);
                btnstop.setVisibility(View.VISIBLE);

                //start time
                timeHere.setBase(SystemClock.elapsedRealtime());
                timeHere.start();
            }
        });

        btnstop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                icanchor.clearAnimation();
                btnstart.animate().alpha(1).translationY(-80).setDuration(300).start();
                btnstop.animate().alpha(0).setDuration(300).start();

                btnstop.setVisibility(View.GONE);
                btnstart.setVisibility(View.VISIBLE);

                timeHere.stop();
                timeHere.setBase(SystemClock.elapsedRealtime());
            }
        });
    }
}