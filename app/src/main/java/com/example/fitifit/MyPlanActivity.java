package com.example.fitifit;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MyPlanActivity extends AppCompatActivity {

    private EditText edtPacketName, edtPacketDesc, edtPacketPrize;
    private Button savePacket;
    private DatabaseReference reference;
    private FirebaseAuth firebaseUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_plan);

        edtPacketName = findViewById(R.id.editTextTextPacketName);
        edtPacketDesc = findViewById(R.id.editTextTextMultiLine);
        edtPacketPrize = findViewById(R.id.editTextNumberDecimal2);
        savePacket = findViewById(R.id.buttonPacket);


        firebaseUser = FirebaseAuth.getInstance();
        String doctorUid = firebaseUser.getCurrentUser().getUid();




        savePacket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reference = FirebaseDatabase.getInstance().getReference().child("DieticiansPacket").child(doctorUid);
                reference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        snapshot.getRef().child("packetName").setValue(edtPacketName.getText().toString());
                        snapshot.getRef().child("packetDesc").setValue(edtPacketDesc.getText().toString());
                        snapshot.getRef().child("packetPrize").setValue(edtPacketPrize.getText().toString());
                        snapshot.getRef().child("user_id").setValue(doctorUid);
                       // getDatas(doctorUid);
                        Toast.makeText(getApplicationContext(), "Paketiniz Guncellendi ", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(getApplicationContext(), "Basarisiz islem", Toast.LENGTH_SHORT).show();
                    }
                });


            }
        });

    }

    private void getDatas(String doctorUid){
        reference = FirebaseDatabase.getInstance().getReference().child("DieticiansPacket").child(doctorUid);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String Name = snapshot.child("packetName").getValue().toString();
                String Desc = snapshot.child("packetDesc").getValue().toString();
                String Prize = snapshot.child("packetPrize").getValue().toString();
                if(Name.equals(null)){
                    edtPacketName.setText("Paket Adi");
                }else {
                    edtPacketName.setText(Name);
                }
                if(Name.equals(null)){
                    edtPacketDesc.setText("Paket icerigi");
                }else {
                    edtPacketDesc.setText(Desc);
                }
                if(Name.equals(null)){
                    edtPacketPrize.setText("Fiyat");
                }else {
                    edtPacketPrize.setText(Prize);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }
}