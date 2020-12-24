package com.example.fitifit;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import de.hdodenhof.circleimageview.CircleImageView;

public class SettingsActivity extends AppCompatActivity {

    private Toolbar mToolbar;
    private Button settingsBtn;
    private EditText settingsName;
    private CircleImageView settingsImg;
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    private Uri imageUri=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);


        mToolbar = (Toolbar) findViewById(R.id.mainToolbar);
        setSupportActionBar(mToolbar);

        settingsBtn=(Button) findViewById(R.id.settings_btn);
        settingsName=(EditText) findViewById(R.id.settings_name);
        settingsImg=(CircleImageView) findViewById(R.id.settings_image);
        mAuth=FirebaseAuth.getInstance();
        String user_id=mAuth.getCurrentUser().getUid();
        mDatabase=FirebaseDatabase.getInstance().getReference().child("Users").child(user_id);
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
           String  user_name=snapshot.child("name").getValue().toString();
           String user_image=snapshot.child("image").getValue().toString();
           imageUri=Uri.parse(user_image);

                RequestOptions requestOptions = new RequestOptions();
                requestOptions.placeholder(R.drawable.app_account_image);
                Glide.with(SettingsActivity.this).setDefaultRequestOptions(requestOptions).load(imageUri).into(settingsImg);

           settingsName.setText(user_name);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        settingsImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityCompat.requestPermissions(SettingsActivity.this,new String []{
                    Manifest.permission.READ_EXTERNAL_STORAGE},1);
                CropImage.activity()
                        .setGuidelines(CropImageView.Guidelines.ON)
                        .start(SettingsActivity.this);

            }
        });



    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                imageUri = result.getUri();
                settingsImg.setImageURI(imageUri);
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }
        }
    }
}