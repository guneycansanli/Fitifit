package com.example.fitifit;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.fitifit.Model.UserProfileModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MyUserToDoEdit extends AppCompatActivity {

    private EditText titleDoes, descDoes, dateDoes;
    private Button btnDelete;
    private DatabaseReference reference;
    private String task;
    private FirebaseAuth firebaseAuth;
    private List<UserProfileModel> mUsers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_user_to_do_edit);

        titleDoes = findViewById(R.id.titledoes);
        descDoes = findViewById(R.id.descdoes);
        dateDoes = findViewById(R.id.datedoes);
        titleDoes.setClickable(false);
        descDoes.setClickable(false);

        mUsers = new ArrayList<>();
        check(); //Only users can delete their To-Do

        btnDelete = findViewById(R.id.btnDelete);

        //get value from page

        titleDoes.setText(getIntent().getStringExtra("titledoes"));
        descDoes.setText(getIntent().getStringExtra("descdoes"));
        dateDoes.setText(getIntent().getStringExtra("datedoes"));
        task = getIntent().getStringExtra("task");     //normalde tasklara id vermeden yapiliyordu ama sade kullanici göruyordu silebilmesi icin

        firebaseAuth = FirebaseAuth.getInstance();
        String myUid = firebaseAuth.getCurrentUser().getUid();

        reference = FirebaseDatabase.getInstance().getReference().child("ToDo").child(myUid).child(task);

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reference.removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Intent a = new Intent(MyUserToDoEdit.this, MyUserToDo.class);
                            startActivity(a);
                        } else {
                            Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });


    }

    private void check() {
        FirebaseUser firebaseU = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference referenceDietician = FirebaseDatabase.getInstance().getReference("Dieticians");   //This Function for which accound we sign in (Dietician Or User)


        referenceDietician.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                mUsers.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    UserProfileModel user = dataSnapshot.getValue(UserProfileModel.class);

                    assert user != null;
                    assert firebaseU != null;
                    if (user.getUid().equals(firebaseU.getUid())) {
                        btnDelete.setVisibility(View.GONE);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }


}