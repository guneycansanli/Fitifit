package com.example.fitifit;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

public class SelectAccound extends AppCompatActivity {

        private EditText selectText;
        private Button btnDiet,btnUser;
        private ImageView logo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_accound);

        btnDiet=(Button) findViewById(R.id.selectDietician);
        btnUser=(Button) findViewById(R.id.selectUser);

        btnUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent registerUserIntent = new Intent(SelectAccound.this,RegisterActivity.class);
                startActivity(registerUserIntent);
            }
        });

        btnDiet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent registerDieticianIntent = new Intent(SelectAccound.this,DieticianRegisterActivity.class);
                startActivity(registerDieticianIntent);
            }
        });

    }
}