package com.example.fitifit;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.fitifit.Adapter.DietAdapter;
import com.example.fitifit.Fragment.NotificaitonDieticianFragment;
import com.example.fitifit.Fragment.ProfileFragment;
import com.example.fitifit.HelperClasses.FeaturedAdapter;
import com.example.fitifit.HelperClasses.FeaturedHelperClass;
import com.example.fitifit.Model.Diet;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class UserWorkoutActivity extends AppCompatActivity {


    private RecyclerView featuredRecycler;
    private Intent intent;
    private DatabaseReference reference;
    private FeaturedAdapter adapter;
    private ImageView giveWorkout, goHome;
    private ArrayList<FeaturedHelperClass> featuredWorkouts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_workout);

        intent = getIntent();
        String userid = intent.getStringExtra("userid");

        giveWorkout = findViewById(R.id.giveWorkout);
        goHome = findViewById(R.id.goHome);

        //Hooks
        featuredRecycler = findViewById(R.id.featured_recycler);


        goHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(UserWorkoutActivity.this, MainActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);  //coming back engellendi
                startActivity(i);
                finish();
            }
        });

        giveWorkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent a = new Intent(UserWorkoutActivity.this, GiveWorkoutActivity.class);
                a.putExtra("userid",userid);
                startActivity(a);
            }
        });

        getWorkout(userid);

    }

    private void getWorkout(String userid) {

        featuredRecycler.setHasFixedSize(true);
        featuredRecycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        featuredWorkouts = new ArrayList<FeaturedHelperClass>();

        reference = FirebaseDatabase.getInstance().getReference().child("Workout").child(userid);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                featuredWorkouts.clear();
                for(DataSnapshot dataSnapshot1: snapshot.getChildren()){
                    FeaturedHelperClass model = dataSnapshot1.getValue(FeaturedHelperClass.class);
                    featuredWorkouts.add(model);
                }
                adapter = new FeaturedAdapter(UserWorkoutActivity.this,featuredWorkouts);
                featuredRecycler.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getApplicationContext(), "Veri Yok", Toast.LENGTH_SHORT).show();
            }
        });





        GradientDrawable gradient1 = new GradientDrawable(GradientDrawable.Orientation.LEFT_RIGHT, new int[]{0xffeff400, 0xffaff600});


    }
}