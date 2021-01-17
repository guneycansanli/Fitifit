package com.example.fitifit;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.theartofdev.edmodo.cropper.CropImage;

import java.util.HashMap;

public class PostActivity extends AppCompatActivity {

    Uri imageUri;
    String myUri="";
    ImageView image_Close, image_added;
    StorageTask loadTask;
    StorageReference imageLoadPath;
    TextView  txt_post;
    EditText  edt_Post_About;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);

        image_Close = findViewById(R.id.close_Post);
        image_added = findViewById(R.id.added_Image_Post);
        txt_post = findViewById(R.id.txt_Post);
        edt_Post_About = findViewById(R.id.edt_Post_About);

        imageLoadPath = FirebaseStorage.getInstance().getReference("Posts");

        image_Close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(PostActivity.this,MainActivity.class));
                finish();       //geri tuşuna basarsa dönmemesi için
            }
        });

        txt_post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                postImage();
            }
        });
        CropImage.activity()
                .setAspectRatio(1,1)
                .start(PostActivity.this);
    }

    private String takeFileExtension(Uri uri)
    {
        ContentResolver contentResolver = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();

        return mime.getExtensionFromMimeType(contentResolver.getType(uri));
    }

    private void postImage()
    {
        ProgressDialog progressDialog=new ProgressDialog(this);
        progressDialog.setMessage("Gonderiliyor..");
        progressDialog.show();

        if (imageUri != null)
        {
            StorageReference filePath = imageLoadPath.child(System.currentTimeMillis()
                    +"."+takeFileExtension(imageUri));

            loadTask = filePath.putFile(imageUri);
            loadTask.continueWithTask(new Continuation() {
                @Override
                public Object then(@NonNull Task task) throws Exception {
                    if(!task.isSuccessful())
                    {
                        throw  task.getException();
                    }


                    return filePath.getDownloadUrl();
                }
            }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                @Override
                public void onComplete(@NonNull Task<Uri> task) {
                    if(task.isSuccessful())
                    {
                        Uri downloadUri = task.getResult();
                        myUri = downloadUri.toString();

                        DatabaseReference dataPath = FirebaseDatabase.getInstance().getReference("Posts");

                        String postId = dataPath.push().getKey();
                        HashMap<String,Object> hashMap= new HashMap<>();

                        hashMap.put("postId",postId);
                        hashMap.put("postImage",myUri);
                        hashMap.put("postAbout",edt_Post_About.getText().toString());
                        hashMap.put("postFrom", FirebaseAuth.getInstance().getCurrentUser().getUid());

                        dataPath.child(postId).setValue(hashMap);
                        progressDialog.dismiss();
                        startActivity(new Intent(PostActivity.this,MainActivity.class));
                        finish();

                    }
                    else
                    {
                        Toast.makeText(PostActivity.this, "Gönderme Başarısız", Toast.LENGTH_SHORT).show();

                    }
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(PostActivity.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }
        else
        {
            Toast.makeText(this, "Seçilen Fotograf Yok", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE && requestCode == RESULT_OK)
        {
           CropImage.ActivityResult result = CropImage.getActivityResult(data);
           imageUri = result.getUri();

           image_added.setImageURI(imageUri);
        }
        else 
        {
            Toast.makeText(this, "Fotoğraf Seçilemedi", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(PostActivity.this,MainActivity.class));
            finish();
        }
    }


}