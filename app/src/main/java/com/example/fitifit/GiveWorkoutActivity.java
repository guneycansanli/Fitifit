package com.example.fitifit;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;
import java.util.Random;

public class GiveWorkoutActivity extends AppCompatActivity {

    private EditText descWorkout, starWorkout, titleWorkout;
    private Button addWorkout;
    private ImageView imageWorkout;
    private Intent b;
    private DatabaseReference reference;
    public static final int IMAGE_REQUEST = 1;
    private Integer workout_id = new Random().nextInt();
    private StorageReference storageReference;
    private StorageTask uploadTask;
    private Uri imageUri;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_give_workout);

        imageWorkout = findViewById(R.id.image_workout);
        descWorkout = findViewById(R.id.desc_workout);
        titleWorkout = findViewById(R.id.title_workout);
        starWorkout = findViewById(R.id.star_workout);
        addWorkout = findViewById(R.id.add_workout_btn);

        b = getIntent();
        String userid = b.getStringExtra("userid");

        storageReference = FirebaseStorage.getInstance().getReference("workout_photos");
        reference= FirebaseDatabase.getInstance().getReference().child("Workout").child(userid).child(workout_id.toString());

        imageWorkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });


        addWorkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        snapshot.getRef().child("title").setValue(titleWorkout.getText().toString());
                      //  snapshot.getRef().child("star").setValue(starWorkout.getText().toString());
                        snapshot.getRef().child("desc").setValue(descWorkout.getText().toString());
                        snapshot.getRef().child("workout_id").setValue(Integer.toString(workout_id));

                        Intent i = new Intent(GiveWorkoutActivity.this, MainActivity.class);
                        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);  //coming back engellendi
                        startActivity(i);
                        finish();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });


    }


}