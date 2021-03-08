package com.example.fitifit;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fitifit.Adapter.ToDoAdapter;
import com.example.fitifit.Model.ToDo;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class UserToDo extends AppCompatActivity {

    Button btnAddNew;

    DatabaseReference reference;
    RecyclerView ourdoes;
    ArrayList<ToDo> list;
    ToDoAdapter doesAdapter;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_to_do);


        ourdoes = findViewById(R.id.ourdoes);
        ourdoes.setLayoutManager(new LinearLayoutManager(this));
        list = new ArrayList<ToDo>();
        btnAddNew = findViewById(R.id.btnAddNew);

        intent = getIntent();
        String userid = intent.getStringExtra("userid");


        btnAddNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent a = new Intent(UserToDo.this, NewTaskActivity.class);
                a.putExtra("userid",userid);
                startActivity(a);
            }
        });

        //get data From firebase

        reference = FirebaseDatabase.getInstance().getReference().child("ToDo").child(userid);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                list.clear();
                for(DataSnapshot dataSnapshot1: snapshot.getChildren()){
                    ToDo p = dataSnapshot1.getValue(ToDo.class);
                    list.add(p);
                }
                doesAdapter = new ToDoAdapter(UserToDo.this, list);
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