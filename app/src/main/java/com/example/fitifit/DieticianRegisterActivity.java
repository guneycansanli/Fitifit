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

public class DieticianRegisterActivity extends AppCompatActivity {

    private EditText dieticianName,diticianEmail,dieticianPassword;
    private Button btnDieticianRegister;
    private ProgressDialog registerProgress;
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    private TextView dieticianToLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dietician_register);


        init();
        clickListener();
        goBackToLogin();

    }

    private void goBackToLogin() {
        dieticianToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DieticianRegisterActivity.this,LoginActivity.class);
                startActivity(intent);
            }
        });
    }

    private void clickListener() {
        btnDieticianRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nameDi = dieticianName.getText().toString();
                String emailDi = diticianEmail.getText().toString();
                String passwordDi = dieticianPassword.getText().toString();
                if(!TextUtils.isEmpty(nameDi) || !TextUtils.isEmpty(emailDi) || !TextUtils.isEmpty(passwordDi)){
                    registerProgress.setTitle("Kaydediliyor");
                    registerProgress.setMessage("Hesabınızı Oluşturuyoruz Lütfen Bekleyin ");
                    registerProgress.setCanceledOnTouchOutside(false);
                    registerProgress.show();
                    dieticianRegister(nameDi, emailDi , passwordDi);

                }
            }
        });
    }

    private void init() {
        dieticianName = (EditText) findViewById(R.id.register_dietician_name);
        diticianEmail = (EditText) findViewById(R.id.register_dietician_email);
        dieticianPassword = (EditText) findViewById(R.id.register_dietician_password);
        dieticianToLogin = (TextView) findViewById(R.id.register_to_login);
        btnDieticianRegister = (Button) findViewById(R.id.register_dietician_button);
        registerProgress = new ProgressDialog(this);
        mAuth = FirebaseAuth.getInstance();
    }

    private void dieticianRegister(String name, String email, String password){
        mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    String user_id=mAuth.getCurrentUser().getUid();
                    mDatabase = FirebaseDatabase.getInstance().getReference().child("Dieticians").child(user_id);
                    HashMap<String,String> userMap = new HashMap<>();
                    userMap.put("name",name);
                    userMap.put("email",email);
                    userMap.put("uid",user_id);
                    userMap.put("image","default");
                    mDatabase.setValue(userMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                registerProgress.dismiss();
                                Intent mainIntent = new Intent(DieticianRegisterActivity.this, MainActivity.class);
                                startActivity(mainIntent);
                            }
                        }
                    });
                }else {
                    registerProgress.dismiss();
                    Toast.makeText(getApplicationContext(), "Hata " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}