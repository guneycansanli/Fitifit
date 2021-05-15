package com.example.fitifit;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.fitifit.HelperClasses.FeaturedAdapter;
import com.example.fitifit.HelperClasses.FeaturedHelperClass;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MyUserWorkoutActivity extends AppCompatActivity {

    private RecyclerView featuredRecycler;
    private Intent intent;
    private DatabaseReference reference;
    private FeaturedAdapter adapter;
    private ImageView goHome;
    private ArrayList<FeaturedHelperClass> featuredWorkouts;
    private FirebaseAuth firebaseUser;
    private RelativeLayout cardSportWeb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_my_user_workout);

        goHome = findViewById(R.id.goHome);
        cardSportWeb = findViewById(R.id.sportWeb);

        //Hooks
        featuredRecycler = findViewById(R.id.featured_recycler);


        goHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MyUserWorkoutActivity.this, MainActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);  //coming back engellendi
                startActivity(i);
                finish();
            }
        });

        cardSportWeb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MyUserWorkoutActivity.this, SportWebActivity.class);
                startActivity(i);
                ((Activity) MyUserWorkoutActivity.this).overridePendingTransition(0, 0);
            }
        });

        firebaseUser = FirebaseAuth.getInstance();
        String myUid = firebaseUser.getCurrentUser().getUid();

        getWorkout(myUid);

    }

    private void getWorkout(String myUid) {

        featuredRecycler.setHasFixedSize(true);
        featuredRecycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        featuredWorkouts = new ArrayList<FeaturedHelperClass>();

        reference = FirebaseDatabase.getInstance().getReference().child("Workout").child(myUid);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                featuredWorkouts.clear();
                for (DataSnapshot dataSnapshot1 : snapshot.getChildren()) {
                    FeaturedHelperClass model = dataSnapshot1.getValue(FeaturedHelperClass.class);
                    featuredWorkouts.add(model);
                }
                adapter = new FeaturedAdapter(MyUserWorkoutActivity.this, featuredWorkouts);
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