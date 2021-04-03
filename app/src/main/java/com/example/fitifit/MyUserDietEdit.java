package com.example.fitifit;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.fitifit.Fragment.NotificaitonDieticianFragment;
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

public class MyUserDietEdit extends AppCompatActivity {

    private EditText foodEdit, commentEdit ;
    private Button deleteFood;
    private DatabaseReference reference;
    private String food_id;
    private FirebaseAuth firebaseAuth;
    private List<UserProfileModel> mUsers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_user_diet_edit);

        foodEdit = findViewById(R.id.foodEdit);
        commentEdit = findViewById(R.id.commentEdit);
        deleteFood = findViewById(R.id.deleteFood);

        mUsers = new ArrayList<>();
        check(); //Only users can delete User's Diet

        foodEdit.setText(getIntent().getStringExtra("food"));
        commentEdit.setText(getIntent().getStringExtra("comment"));
        food_id = getIntent().getStringExtra("food_id");
        String userId = getIntent().getStringExtra("user_id");
        String Time = getIntent().getStringExtra("time");

        firebaseAuth = FirebaseAuth.getInstance();
        String myUid = firebaseAuth.getCurrentUser().getUid();

        reference = FirebaseDatabase.getInstance().getReference().child("Diet").child(userId).child(Time).child(food_id);

        deleteFood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reference.removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Intent a = new Intent(MyUserDietEdit.this, MainActivity.class);
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
        DatabaseReference referenceUsers = FirebaseDatabase.getInstance().getReference("Users");   //This Function for which accound we sign in (Dietician Or User)


        referenceUsers.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                mUsers.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    UserProfileModel user = dataSnapshot.getValue(UserProfileModel.class);

                    assert user != null;
                    assert firebaseU != null;
                    if (user.getUid().equals(firebaseU.getUid())) {
                        deleteFood.setVisibility(View.GONE);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}