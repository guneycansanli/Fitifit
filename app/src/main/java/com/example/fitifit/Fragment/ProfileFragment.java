package com.example.fitifit.Fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.fitifit.ChatMainActivity;
import com.example.fitifit.DieticianChatMainActivity;
import com.example.fitifit.MainActivity;
import com.example.fitifit.Model.UserProfileModel;
import com.example.fitifit.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class ProfileFragment extends Fragment {

    private Button btn_chat,btn_chat_for_dietician;
    
    private String currentUid;
    private DatabaseReference myRef,reference;
    private List<UserProfileModel> mUsers;


    public ProfileFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=  inflater.inflate(R.layout.fragment_profile, container, false);


        btn_chat =view.findViewById(R.id.chatWithDietician);
        btn_chat_for_dietician=view.findViewById(R.id.chatWithUser);
        currentUid = FirebaseAuth.getInstance().getCurrentUser().getUid();

        mUsers = new ArrayList<>();
        check();




        btn_chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), ChatMainActivity.class);
                startActivity(i);
                ((Activity) getActivity()).overridePendingTransition(0, 0);

            }
        });

        btn_chat_for_dietician.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), DieticianChatMainActivity.class);
                startActivity(i);
                ((Activity) getActivity()).overridePendingTransition(0, 0);

            }
        });




        return view;
    }

    private void check(){
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users");
        DatabaseReference referenceDietician = FirebaseDatabase.getInstance().getReference("Dieticians");   //This Function for which accound we sign in (Dietician Or User)

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                mUsers.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    UserProfileModel user = dataSnapshot.getValue(UserProfileModel.class);

                    assert user != null;
                    assert firebaseUser != null;
                    if (user.getUid().equals(firebaseUser.getUid())) {
                       btn_chat_for_dietician.setVisibility(View.GONE);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        referenceDietician.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                mUsers.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    UserProfileModel user = dataSnapshot.getValue(UserProfileModel.class);

                    assert user != null;
                    assert firebaseUser != null;
                    if (user.getUid().equals(firebaseUser.getUid())) {
                        btn_chat.setVisibility(View.GONE);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

}