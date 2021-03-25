package com.example.fitifit;

import android.os.Bundle;
import android.view.View;

public class NewDayWater extends WaterTrackerActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        generateWater(bottleButton);
    }

}
