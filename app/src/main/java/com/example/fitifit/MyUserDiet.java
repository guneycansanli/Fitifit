package com.example.fitifit;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.fitifit.Adapter.DietAdapter;
import com.example.fitifit.Model.Diet;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MyUserDiet extends AppCompatActivity {

    private DatabaseReference reference;
    private RelativeLayout giveDiet;
    private RecyclerView myDietBreakfast, myDietSnacks, myDietLunch, myDietSnackNight, myDietDinner;
    private ArrayList<Diet> listBreakFast, listSnacks, listLunch, listSnackNight, listDinner;
    private DietAdapter dietAdapter;
    private FirebaseAuth firebaseUser;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_user_diet);

        myDietBreakfast = findViewById(R.id.myDietBreakfast);
        myDietBreakfast.setLayoutManager(new LinearLayoutManager(this));

        myDietSnacks = findViewById(R.id.myDietSnacks);
        myDietSnacks.setLayoutManager(new LinearLayoutManager(this));

        myDietLunch = findViewById(R.id.myDietLunch);
        myDietLunch.setLayoutManager(new LinearLayoutManager(this));

        myDietSnackNight = findViewById(R.id.myDietSnackNight);
        myDietSnackNight.setLayoutManager(new LinearLayoutManager(this));

        myDietDinner = findViewById(R.id.myDietDinner);
        myDietDinner.setLayoutManager(new LinearLayoutManager(this));


        firebaseUser = FirebaseAuth.getInstance();
        String myUid = firebaseUser.getCurrentUser().getUid();

        getBreakFast(myUid);
        getSnacks(myUid);
        getLunch(myUid);
        getSnacksNight(myUid);
        getDinner(myUid);

    }

    private void getBreakFast(String myUid){
        listBreakFast = new ArrayList<Diet>();
        reference = FirebaseDatabase.getInstance().getReference().child("Diet").child(myUid).child("Breakfast");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                listBreakFast.clear();
                for(DataSnapshot dataSnapshot1: snapshot.getChildren()){
                    Diet p = dataSnapshot1.getValue(Diet.class);
                    listBreakFast.add(p);
                }
                dietAdapter = new DietAdapter(MyUserDiet.this, listBreakFast);
                myDietBreakfast.setAdapter(dietAdapter);
                dietAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getApplicationContext(), "Veri Yok", Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void getSnacks(String myUid){
        listSnacks = new ArrayList<Diet>();
        reference = FirebaseDatabase.getInstance().getReference().child("Diet").child(myUid).child("Snacks");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                listSnacks.clear();
                for(DataSnapshot dataSnapshot1: snapshot.getChildren()){
                    Diet p = dataSnapshot1.getValue(Diet.class);
                    listSnacks.add(p);
                }
                dietAdapter = new DietAdapter(MyUserDiet.this, listSnacks);
                myDietSnacks.setAdapter(dietAdapter);
                dietAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getApplicationContext(), "Veri Yok", Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void getLunch(String myUid){
        listLunch = new ArrayList<Diet>();
        reference = FirebaseDatabase.getInstance().getReference().child("Diet").child(myUid).child("Lunch");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                listLunch.clear();
                for(DataSnapshot dataSnapshot1: snapshot.getChildren()){
                    Diet p = dataSnapshot1.getValue(Diet.class);
                    listLunch.add(p);
                }
                dietAdapter = new DietAdapter(MyUserDiet.this, listLunch);
                myDietLunch.setAdapter(dietAdapter);
                dietAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getApplicationContext(), "Veri Yok", Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void getSnacksNight(String myUid){
        listSnackNight = new ArrayList<Diet>();
        reference = FirebaseDatabase.getInstance().getReference().child("Diet").child(myUid).child("SnacksNight");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                listSnackNight.clear();
                for(DataSnapshot dataSnapshot1: snapshot.getChildren()){
                    Diet p = dataSnapshot1.getValue(Diet.class);
                    listSnackNight.add(p);
                }
                dietAdapter = new DietAdapter(MyUserDiet.this, listSnackNight);
                myDietSnackNight.setAdapter(dietAdapter);
                dietAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getApplicationContext(), "Veri Yok", Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void getDinner(String myUid){
        listDinner = new ArrayList<Diet>();
        reference = FirebaseDatabase.getInstance().getReference().child("Diet").child(myUid).child("Dinner");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                listDinner.clear();
                for(DataSnapshot dataSnapshot1: snapshot.getChildren()){
                    Diet p = dataSnapshot1.getValue(Diet.class);
                    listDinner.add(p);
                }
                dietAdapter = new DietAdapter(MyUserDiet.this, listDinner);
                myDietDinner.setAdapter(dietAdapter);
                dietAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getApplicationContext(), "Veri Yok", Toast.LENGTH_SHORT).show();
            }
        });
    }
}