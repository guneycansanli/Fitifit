package com.example.fitifit;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.File;
import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;

public class SettingsActivity extends AppCompatActivity {

    private Toolbar mToolbar;
    private Button settingsBtn;
    private EditText settingsName;
    private CircleImageView settingsImg;
    private FirebaseAuth reference;
    private DatabaseReference mDatabase;
    private Boolean isCheck=false;
    private Uri imageUri=null;
    private ProgressDialog settingsProgress;
    public static final int IMAGE_REQUEST = 1;
    private StorageReference storageReference;
    private StorageTask uploadTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);


        mToolbar = (Toolbar) findViewById(R.id.mainToolbar);
        setSupportActionBar(mToolbar);

        settingsBtn=(Button) findViewById(R.id.settings_btn);
        settingsName=(EditText) findViewById(R.id.settings_name);
        settingsImg=(CircleImageView) findViewById(R.id.settings_image);
        settingsProgress = new ProgressDialog(this);


        reference=FirebaseAuth.getInstance();
        storageReference = FirebaseStorage.getInstance().getReference();
        String user_id=reference.getCurrentUser().getUid();
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

        settingsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userName=settingsName.getText().toString();

                    settingsProgress.setTitle("Güncelleniyor");
                    settingsProgress.setMessage("Bilgilerinizi Güncelliyoruz Lütfen Bekleyin");
                    settingsProgress.show();
                    if(!TextUtils.isEmpty(userName) && imageUri != null)
                    {
                            final StorageReference fileReference = storageReference.child("profile_images").child(user_id+".jpg");
                            uploadTask=fileReference.getFile(imageUri);
                            uploadTask.continueWith(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                                @Override
                                public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                                    if(!task.isSuccessful()){
                                        throw  task.getException();
                                    }
                                    return  fileReference.getDownloadUrl();
                                }
                            }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                                @Override
                                public void onComplete(@NonNull Task<Uri> task) {
                                    if (task.isSuccessful()){
                                        Uri downloadUri = task.getResult();
                                        String mUri =downloadUri.toString();

                                        mDatabase = FirebaseDatabase.getInstance().getReference("Users").child(user_id);
                                        HashMap<String, Object> map = new HashMap<>();
                                        map.put("image", mUri);
                                        mDatabase.updateChildren(map);
                                        settingsProgress.dismiss();
                                    } else {
                                        Toast.makeText(getBaseContext(), "Failed",Toast.LENGTH_SHORT).show();
                                        settingsProgress.dismiss();
                                    }
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(getBaseContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                                    settingsProgress.dismiss();
                                }
                            });

                    }
            }
        });


    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE ) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                imageUri = result.getUri();
                settingsImg.setImageURI(imageUri);
                isCheck=true;
                Toast.makeText(
                        this, "Cropping successful, Sample: " + result.getSampleSize(), Toast.LENGTH_LONG).show();
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Toast.makeText(this, "Cropping failed: " + result.getError(), Toast.LENGTH_LONG).show();
            }
        }
    }


}