package com.example.fitifit;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.Toast;
import android.widget.Toolbar;

import com.example.fitifit.Model.ProfileModel;
import com.example.fitifit.adapter.ProfileAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ProfilesActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ProfileAdapter adapter;
    private List<ProfileModel> list;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profiles);

        init();
        recyclerView.setLayoutManager(new LinearLayoutManager(this,
                LinearLayoutManager.VERTICAL,false));

        list = new ArrayList<>();
        adapter = new ProfileAdapter(list);

        recyclerView.setAdapter(adapter);

        loadData();

    }

    private void loadData(){
        reference.child("Users").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot : snapshot.getChildren()){

                    ProfileModel model  =dataSnapshot.getValue(ProfileModel.class);
                    list.add(model);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(ProfilesActivity.this,""+error.getMessage(),Toast.LENGTH_SHORT).show();

            }
        });
    }


    private void init(){
      /*  Toolbar toolbar=(Toolbar) findViewById(R.id.toolbar);
        setActionBar(toolbar);
        if(getActionBar() != null)
            getSupportActionBar().setTitle("User s Profile..."); */

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);

        reference = FirebaseDatabase.getInstance().getReference();
    }
}