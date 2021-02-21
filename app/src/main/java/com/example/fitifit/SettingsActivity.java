package com.example.fitifit;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.webkit.MimeTypeMap;
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
    private Uri imageUri;
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

        reference=FirebaseAuth.getInstance();
        storageReference = FirebaseStorage.getInstance().getReference("profile_images");
        String user_id=reference.getCurrentUser().getUid();
        mDatabase=FirebaseDatabase.getInstance().getReference().child("Users").child(user_id);
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
           String  user_name=snapshot.child("name").getValue().toString();
           String user_image=snapshot.child("image").getValue().toString();

           if(user_image.equals("default")){
               settingsImg.setImageResource(R.mipmap.ic_launcher);
           }else {
               Glide.with(getBaseContext()).load(user_image).into(settingsImg);
           }
             settingsName.setText(user_name);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        settingsImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openImage ();

            }
        });

    }
    private void openImage(){
        Intent intent= new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, IMAGE_REQUEST);
    }

    private String getFileExtension(Uri uri){
        ContentResolver contentResolver = getBaseContext().getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));
    }

    private void uploadImage(){


        if (imageUri !=null){
            final StorageReference fileReference = storageReference.child(System.currentTimeMillis()
                    +"."+getFileExtension(imageUri));

            uploadTask = fileReference.putFile(imageUri);
            uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                @Override
                public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                    if(!task.isSuccessful()) {
                        throw task.getException();
                    }

                    return fileReference.getDownloadUrl();
                }

            }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                @Override
                public void onComplete(@NonNull Task<Uri> task) {
                    if(task.isSuccessful()) {
                        Uri downloadUri = task.getResult();
                        String mUri=downloadUri.toString();

                        HashMap<String, Object> map = new HashMap<>();
                        map.put("image", mUri);
                        mDatabase.updateChildren(map);


                    }else {
                        Toast.makeText(getBaseContext(),"Hata" , Toast.LENGTH_SHORT).show();

                    }
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(getBaseContext(), e.getMessage(),Toast.LENGTH_SHORT).show();

                }
            });
        }else {
            Toast.makeText(getBaseContext(),"Fotograf Secilmedi",Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == IMAGE_REQUEST && resultCode == RESULT_OK
        && data != null && data.getData() != null) {
            imageUri = data.getData();

            if(uploadTask != null && uploadTask.isInProgress()) {
                Toast.makeText(getBaseContext(), "Yukleme islemde", Toast.LENGTH_SHORT).show();

            }else {
                uploadImage();
            }
        }
    }
}