package com.example.fitifit;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.fitifit.Model.ProfileModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;

public class EditActivity extends AppCompatActivity {

    DatabaseReference reference;
    private EditText spinsEt;
    private TextView emailTv, nameTv;
    private String uid;
    private Button updateBtn;
    private CircleImageView profileImage;
    public static final String ALPHABET_REGEX = "a-zA-z";
    private Dialog dialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        init();
        clickListener();
        loadData();
    }

    private void clickListener() {
        updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateData();
            }
        });

    }

    private void init() {
        nameTv = (TextView) findViewById(R.id.nameTv);
        spinsEt = (EditText) findViewById(R.id.spinsEt);
        emailTv = (TextView) findViewById(R.id.emailTv);
        updateBtn = (Button) findViewById(R.id.updateBtn);
        profileImage = (CircleImageView) findViewById(R.id.profileImage);

        uid = getIntent().getStringExtra("uid");

        reference = FirebaseDatabase.getInstance().getReference().child("Users").child(uid);
        dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_loading);
        dialog.setCancelable(false);
        if (dialog.getWindow() != null)
            dialog.getWindow().setBackgroundDrawable(getResources().getDrawable(R.drawable.dialog_bg));

    }

    private void loadData() {
        dialog.show();
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                ProfileModel model = snapshot.getValue(ProfileModel.class);
                nameTv.setText(model.getName());
                spinsEt.setText(String.valueOf(model.getSpins()));
                emailTv.setText(model.getEmail());

                Glide.with(getApplicationContext())
                        .load(model.getImage())
                        .placeholder(R.drawable.profile)
                        .into(profileImage);
                dialog.dismiss();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                dialog.dismiss();
            }
        });
    }

    private void updateData() {

        String spins = spinsEt.getText().toString().trim();

        if (spins.matches(ALPHABET_REGEX) || TextUtils.isEmpty(spins)) {                           //check spins value contain any alphabet
            Toast.makeText(this, "Input Valid Spins", Toast.LENGTH_SHORT).show();
            return;
        }

        int spinsAmount = Integer.parseInt(spins);
        HashMap<String, Object> map = new HashMap<>();
        map.put("spins", spinsAmount);

        dialog.show();

        reference.updateChildren(map).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(EditActivity.this, "Updated", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(EditActivity.this, "Error" + task.getException().getMessage(),
                            Toast.LENGTH_SHORT).show();
                }
                dialog.dismiss();
            }
        });


    }
}