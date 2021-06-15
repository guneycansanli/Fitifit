package com.example.fitifit;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.fitifit.Adapter.BuyPacketAdapter;
import com.example.fitifit.Adapter.ToDoAdapter;
import com.example.fitifit.Model.PacketModel;
import com.example.fitifit.Model.ToDo;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class BuyPersonalActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private BuyPacketAdapter buyPacketAdapter;
    private List<PacketModel> mPacket;
    private DatabaseReference reference;
    private FirebaseAuth firebaseUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy_personal);

        recyclerView = findViewById(R.id.buyPacket);
      //  recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getBaseContext()));


        mPacket = new ArrayList<PacketModel>();

        firebaseUser = FirebaseAuth.getInstance();
        String myUid = firebaseUser.getCurrentUser().getUid();

        reference = FirebaseDatabase.getInstance().getReference().child("DieticiansPacket");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                mPacket.clear();
                for(DataSnapshot dataSnapshot1: snapshot.getChildren()){
                    PacketModel p = dataSnapshot1.getValue(PacketModel.class);
                    mPacket.add(p);
                }
                buyPacketAdapter = new BuyPacketAdapter(BuyPersonalActivity.this, mPacket);
                recyclerView.setAdapter(buyPacketAdapter);
                buyPacketAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}