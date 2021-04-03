package com.example.fitifit;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import com.example.fitifit.Adapter.UserDietAdapter;
import com.example.fitifit.Adapter.UserToDoAdapter;
import com.example.fitifit.Model.UserProfileModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class GiveDietSelectUserActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private UserDietAdapter UserDietAdapter;
    private List<UserProfileModel> mUsers;
    EditText search_bar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_give_diet_select_user);

        recyclerView = findViewById(R.id.recycler_view_search);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getBaseContext()));
        search_bar = findViewById(R.id.edt_search_bar);

        mUsers = new ArrayList<>();
        UserDietAdapter = new UserDietAdapter(getBaseContext(),mUsers);
        recyclerView.setAdapter(UserDietAdapter);

        ReadUsers();

        search_bar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                UserSearch(s.toString().toLowerCase());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }

    private void UserSearch(String s)
    {
        Query sorgu = FirebaseDatabase.getInstance().getReference("Users").orderByChild("name")
                .startAt(s)
                .endAt(s+"\uf8ff");
        sorgu.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                mUsers.clear();
                for(DataSnapshot snapshot1 : snapshot.getChildren())
                {
                    UserProfileModel user = snapshot1.getValue(UserProfileModel.class);
                    mUsers.add(user);
                }
                UserDietAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void ReadUsers() {

        DatabaseReference usersPath = FirebaseDatabase.getInstance().getReference("Users");

        usersPath.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(search_bar.getText().toString().equals(""))
                {
                    mUsers.clear();
                    for (DataSnapshot dataSnapshot : snapshot.getChildren() )
                    {
                        UserProfileModel user = dataSnapshot.getValue(UserProfileModel.class);
                        mUsers.add(user);
                    }
                    UserDietAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


}