package com.example.fitifit.Fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.fitifit.Adapter.UserAdapter;
import com.example.fitifit.Adapter.UserChatAdapter;
import com.example.fitifit.Model.Chat;
import com.example.fitifit.Model.UserProfileModel;
import com.example.fitifit.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.auth.User;

import java.util.ArrayList;
import java.util.List;


public class ChatsFragment extends Fragment {

    private RecyclerView recyclerView;
    private UserChatAdapter userAdapter;
    private List<UserProfileModel> mUsers;

    FirebaseUser fuser;
    DatabaseReference reference;
    private List<String> usersList;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_chats,container,false);

        recyclerView = view.findViewById(R.id.recycler_view_chats);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        fuser = FirebaseAuth.getInstance().getCurrentUser();
        usersList = new ArrayList<>();

        reference = FirebaseDatabase.getInstance().getReference("Chats");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                    usersList.clear();
                    for(DataSnapshot dataSnapshot : snapshot.getChildren()){

                        Chat chat=dataSnapshot.getValue(Chat.class);
                        if(chat.getSender().equals(fuser.getUid())){
                            usersList.add(chat.getReceiver());
                        }
                        if (chat.getReceiver().equals(fuser.getUid())){
                            usersList.add(chat.getSender());
                        }
                    }
                    readChats();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

        return view;

    }

    private void readChats(){
        mUsers = new ArrayList<>();

        reference = FirebaseDatabase.getInstance().getReference("Dieticians");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                mUsers.clear();

                // Display 1 user from chats
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    UserProfileModel user = dataSnapshot.getValue(UserProfileModel.class);

                    for(String id : usersList){
                        if (user.getUid().equals(id)){
                            if (mUsers.size() != 0){
                                for (UserProfileModel userl : mUsers){
                                    if (!user.getUid().equals(userl.getUid())){
                                        mUsers.add(user);
                                    }
                                }
                            } else {
                                mUsers.add(user);
                            }
                        }
                    }
                }
                userAdapter = new UserChatAdapter(getContext(),mUsers);
                recyclerView.setAdapter(userAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

}