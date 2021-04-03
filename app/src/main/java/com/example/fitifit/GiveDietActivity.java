package com.example.fitifit;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Random;

public class GiveDietActivity extends AppCompatActivity {

    private EditText addFood, addComment;
    private Button btnSaveBreakfast, btnSaveSnacks, btnSavelunch, btnSaveSnacksNight, btnSaveDinner;
    private Intent b;
    private DatabaseReference reference;
    private Integer food_id = new Random().nextInt();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_give_diet);

        addFood = findViewById(R.id.addFood);
        addComment= findViewById(R.id.addComment);
        btnSaveBreakfast= findViewById(R.id.btnSaveBreakfast);
        btnSaveSnacks= findViewById(R.id.btnSaveSnacks);
        btnSavelunch= findViewById(R.id.btnSavelunch);
        btnSaveSnacksNight= findViewById(R.id.btnSaveSnacksNight);
        btnSaveDinner= findViewById(R.id.btnSaveDinner);


        b = getIntent();
        String userid = b.getStringExtra("userid");


        btnSaveBreakfast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reference = FirebaseDatabase.getInstance().getReference().child("Diet").child(userid).child("Breakfast").child(food_id.toString());
                reference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        snapshot.getRef().child("food").setValue(addFood.getText().toString());
                        snapshot.getRef().child("comment").setValue(addComment.getText().toString());
                        snapshot.getRef().child("food_id").setValue(food_id.toString());
                        snapshot.getRef().child("time").setValue("Breakfast");
                        snapshot.getRef().child("user_id").setValue(userid);

                        Intent a = new Intent(GiveDietActivity.this, MainActivity.class);
                        startActivity(a);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });
        btnSaveSnacks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reference = FirebaseDatabase.getInstance().getReference().child("Diet").child(userid).child("Snacks").child(food_id.toString());
                reference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        snapshot.getRef().child("food").setValue(addFood.getText().toString());
                        snapshot.getRef().child("comment").setValue(addComment.getText().toString());
                        snapshot.getRef().child("food_id").setValue(food_id.toString());
                        snapshot.getRef().child("time").setValue("Snacks");
                        snapshot.getRef().child("user_id").setValue(userid);

                        Intent a = new Intent(GiveDietActivity.this, MainActivity.class);
                        startActivity(a);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });
        btnSavelunch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reference = FirebaseDatabase.getInstance().getReference().child("Diet").child(userid).child("Lunch").child(food_id.toString());
                reference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        snapshot.getRef().child("food").setValue(addFood.getText().toString());
                        snapshot.getRef().child("comment").setValue(addComment.getText().toString());
                        snapshot.getRef().child("food_id").setValue(food_id.toString());
                        snapshot.getRef().child("time").setValue("Lunch");
                        snapshot.getRef().child("user_id").setValue(userid);


                        Intent a = new Intent(GiveDietActivity.this, MainActivity.class);
                        startActivity(a);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });
        btnSaveSnacksNight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reference = FirebaseDatabase.getInstance().getReference().child("Diet").child(userid).child("SnacksNight").child(food_id.toString());
                reference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        snapshot.getRef().child("food").setValue(addFood.getText().toString());
                        snapshot.getRef().child("comment").setValue(addComment.getText().toString());
                        snapshot.getRef().child("food_id").setValue(food_id.toString());
                        snapshot.getRef().child("time").setValue("SnacksNight");
                        snapshot.getRef().child("user_id").setValue(userid);

                        Intent a = new Intent(GiveDietActivity.this, MainActivity.class);
                        startActivity(a);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });
        btnSaveDinner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reference = FirebaseDatabase.getInstance().getReference().child("Diet").child(userid).child("Dinner").child(food_id.toString());
                reference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        snapshot.getRef().child("food").setValue(addFood.getText().toString());
                        snapshot.getRef().child("comment").setValue(addComment.getText().toString());
                        snapshot.getRef().child("food_id").setValue(food_id.toString());
                        snapshot.getRef().child("time").setValue("Dinner");
                        snapshot.getRef().child("user_id").setValue(userid);

                        Intent a = new Intent(GiveDietActivity.this, MainActivity.class);
                        startActivity(a);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });
    }
}