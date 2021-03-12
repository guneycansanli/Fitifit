package com.example.fitifit;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.fitifit.Adapter.ToDoAdapter;
import com.example.fitifit.Model.ToDo;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MyUserToDo extends AppCompatActivity {

    private Button btnAddNew;

    private DatabaseReference reference;
    private RecyclerView ourdoes;
    private ArrayList<ToDo> list;
    private ToDoAdapter doesAdapter;
    private FirebaseAuth firebaseUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_user_to_do);

        ourdoes = findViewById(R.id.ourdoes);
        ourdoes.setLayoutManager(new LinearLayoutManager(this));
        list = new ArrayList<ToDo>();
        btnAddNew = findViewById(R.id.btnAddNew);
        btnAddNew.setVisibility(View.GONE);  //Users can t give To-Do theirselves

        firebaseUser = FirebaseAuth.getInstance();
        String myUid = firebaseUser.getCurrentUser().getUid();

        reference = FirebaseDatabase.getInstance().getReference().child("ToDo").child(myUid);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                list.clear();
                for(DataSnapshot dataSnapshot1: snapshot.getChildren()){
                    ToDo p = dataSnapshot1.getValue(ToDo.class);
                    list.add(p);
                }
                doesAdapter = new ToDoAdapter(MyUserToDo.this, list);
                ourdoes.setAdapter(doesAdapter);
                doesAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getApplicationContext(), "Veri Yok", Toast.LENGTH_SHORT).show();
            }
        });


    }


}