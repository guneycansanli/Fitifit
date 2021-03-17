package com.example.fitifit.CaloriCounter;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

import com.example.fitifit.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class GetStart extends AppCompatActivity {

    private EditText tartgetWeight_et;
    private EditText currentWeight_et;
    private TextView calorieTarget;
    private TextView calorieCurrent;
    private Button calculateDayCalories;
    private Button getStarted;
    static double parameter;
    int currentWeight;
    int targetWeight;
    int currentCalories;
    int targetCalories;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_start);

        tartgetWeight_et = findViewById(R.id.targetWeight);
        currentWeight_et = findViewById(R.id.currentWeight);
        calorieTarget = findViewById(R.id.calorieTarget);
        calorieCurrent = findViewById(R.id.calorieCurrent);
        calculateDayCalories = findViewById(R.id.calculateDayCal);

    }

    //man : kg * 1 * 24
    //woman : kg * 0.9 * 24

    public void getStarted(View view) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref_basicInfo = database.getReference("basicInfo");
        Intent intent = new Intent(this, EatActivity.class);
        GetStartModel getStart = new GetStartModel(currentWeight, targetWeight, currentCalories, targetCalories);
        ref_basicInfo.setValue(getStart);
        startActivity(intent);
    }

    public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();
        Button button = findViewById(R.id.calculateDayCal);


        // Check which radio button was clicked
        switch (view.getId()) {
            case R.id.radio_other:
                if (checked)
                    parameter = 0.95;
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        currentWeight = Integer.valueOf(String.valueOf(currentWeight_et.getText()));
                        currentCalories = (int) (currentWeight * parameter * 24);
                        calorieCurrent.setText(String.valueOf((int) currentCalories));

                        targetWeight = Integer.valueOf(String.valueOf(tartgetWeight_et.getText()));
                        targetCalories = (int) (targetWeight * parameter * 24);
                        calorieTarget.setText(String.valueOf((targetCalories)));
                    }
                });
                break;

            case R.id.radio_female:
                if (checked)
                    parameter = 0.9;
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        currentWeight = Integer.valueOf(String.valueOf(currentWeight_et.getText()));
                        currentCalories = (int) (currentWeight * parameter * 24);
                        calorieCurrent.setText(String.valueOf((int) currentCalories));

                        targetWeight = Integer.valueOf(String.valueOf(tartgetWeight_et.getText()));
                        targetCalories = (int) (targetWeight * parameter * 24);
                        calorieTarget.setText(String.valueOf((targetCalories)));
                    }
                });
                break;
            case R.id.radio_male:
                if (checked)
                    parameter = 1.0;
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        currentWeight = Integer.valueOf(String.valueOf(currentWeight_et.getText()));
                        currentCalories = (int) (currentWeight * parameter * 24);
                        calorieCurrent.setText(String.valueOf((int) currentCalories));

                        targetWeight = Integer.valueOf(String.valueOf(tartgetWeight_et.getText()));
                        targetCalories = (int) (targetWeight * parameter * 24);
                        calorieTarget.setText(String.valueOf((targetCalories)));

                    }
                });
                break;

        }
    }


}