package com.example.fitifit;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Random;

public class NewTaskActivity extends AppCompatActivity {

    private EditText titledoes, descdoes, datedoes;
    private Intent b;
    private Button btnSaveTask, btnCancel;
    private DatabaseReference reference;
    private Integer todoNumber = new Random().nextInt();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_task);


        titledoes = findViewById(R.id.titledoes);
        descdoes = findViewById(R.id.descdoes);
        datedoes = findViewById(R.id.datedoes);
        btnSaveTask = findViewById(R.id.btnSaveTask);
        btnCancel = findViewById(R.id.btnCancel);

        b = getIntent();
        String userid = b.getStringExtra("userid");

        btnSaveTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reference = FirebaseDatabase.getInstance().getReference().child("ToDo").child(userid).child("task" + todoNumber);
                reference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        snapshot.getRef().child("titledoes").setValue(titledoes.getText().toString());
                        snapshot.getRef().child("descdoes").setValue(descdoes.getText().toString());
                        snapshot.getRef().child("datedoes").setValue(datedoes.getText().toString());

                        Intent a = new Intent(NewTaskActivity.this, MainActivity.class);
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