package com.example.fitifit.CaloriCounter;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.fitifit.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class InputEat extends AppCompatActivity {

    EditText foodName;
    EditText caloriesPerGram;

    private Intent intent;
    private DatabaseReference ref_eat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_eat);

        foodName = findViewById(R.id.foodName);
        caloriesPerGram = findViewById(R.id.caloriesPerGram);

    }

    public void goToHistory(View view) {
        intent = new Intent(this, HistoryController.class);
        startActivity(intent);
    }

    public void goToOverview(View view) {
        intent = new Intent(this, OverviewActivity.class);
        startActivity(intent);
    }

    public void goToRecord(View view) {
        intent = new Intent(this, EatActivity.class);
        startActivity(intent);
    }

    public void addToList(View view) {
        String newFoodName = String.valueOf(foodName.getText());
        String newFoodCalories = String.valueOf(caloriesPerGram.getText());

//            write to DB
        if(newFoodName != "" && newFoodCalories != ""){
            String newExercise = String.valueOf(newFoodName);
            String newCaloriesLost = String.valueOf(newFoodCalories);

//            write to DB
            FirebaseDatabase database = FirebaseDatabase.getInstance();
            ref_eat = database.getReference("DB_CaloriesInfo");
            String id = ref_eat.push().getKey();
            Eat eat = new Eat(newExercise,newCaloriesLost);
            ref_eat.child("eat").child(id).setValue(eat);
            Toast.makeText(this,"Added to list",Toast.LENGTH_LONG).show();

        }else {
            Toast.makeText(this,"Please input exercise and calories lost",Toast.LENGTH_LONG).show();
        }
    }
}