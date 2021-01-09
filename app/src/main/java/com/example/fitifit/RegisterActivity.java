package com.example.fitifit;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class RegisterActivity extends AppCompatActivity {

    private EditText registerName;
    private EditText registerEmail;
    private EditText registerPassword;
    private TextView registerToLogin;
    private Button registerButton;
    private ProgressDialog registerProgress;
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        registerButton = (Button) findViewById(R.id.register_dietician_button);
        registerEmail = (EditText) findViewById(R.id.register_dietician_email);
        registerName = (EditText) findViewById(R.id.register_dietician_name);
        registerPassword = (EditText) findViewById(R.id.register_dietician_password);
        registerToLogin = (TextView) findViewById(R.id.register_to_login);
        registerProgress = new ProgressDialog(this);
        mAuth = FirebaseAuth.getInstance();


        registerToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent loginIntent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(loginIntent);
            }
        });

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = registerName.getText().toString();
                String password = registerPassword.getText().toString();
                String email = registerEmail.getText().toString();

                if (!TextUtils.isEmpty(name) || !TextUtils.isEmpty(password) || !TextUtils.isEmpty(email)) {
                    registerProgress.setTitle("Kaydediliyor");
                    registerProgress.setMessage("Hesabınızı oluşturuyoruz, lütfen bekleyiniz");
                    registerProgress.setCanceledOnTouchOutside(false);
                    registerProgress.show();
                    register_user(name, password, email);
                }
                else {
                    Toast.makeText(getApplicationContext(), "Boş Alan Bırakılamaz ", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void register_user(String name, String password, String email) {
        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    String user_id=mAuth.getCurrentUser().getUid();
                    mDatabase= FirebaseDatabase.getInstance().getReference().child("Users").child(user_id);
                    HashMap<String,String> userMap=new HashMap<>();
                    userMap.put("name",name);
                    userMap.put("email",email);
                    userMap.put("uid",user_id);
                    userMap.put("image","default");
                    mDatabase.setValue(userMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {

                            if(task.isSuccessful()){
                                registerProgress.dismiss();
                                Intent mainIntent = new Intent(RegisterActivity.this, MainActivity.class);
                                startActivity(mainIntent);
                            }
                        }
                    });

                } else {
                    registerProgress.dismiss();
                    Toast.makeText(getApplicationContext(), "Hata " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}