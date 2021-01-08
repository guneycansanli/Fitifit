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

public class LoginActivity extends AppCompatActivity {

    private EditText loginEmail;
    private EditText loginPassword;
    private Button loginButton;
    private TextView loginToRegister,admin;
    private ProgressDialog loginProgress;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        admin = (TextView) findViewById(R.id.admin);
        loginPassword = (EditText) findViewById(R.id.login_password);
        loginEmail = (EditText) findViewById(R.id.login_email);
        loginButton = (Button) findViewById(R.id.login_button);
        loginToRegister = (TextView) findViewById(R.id.login_need_account);
        loginProgress = new ProgressDialog(this);
        mAuth = FirebaseAuth.getInstance();

        loginToRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent selectRegisterIntent = new Intent(LoginActivity.this, SelectAccound.class);
                startActivity(selectRegisterIntent);
            }
        });
        admin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent adminIntent = new Intent(LoginActivity.this, fitifitAdmin.class);
                startActivity(adminIntent);
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = loginEmail.getText().toString();
                String password = loginPassword.getText().toString();
                if (!TextUtils.isEmpty(email) || !TextUtils.isEmpty(password)) {
                    loginProgress.setMessage("Hesabınıza Giriş Yapılıyor Lütfen Bekleyiniz...");
                    loginProgress.setCanceledOnTouchOutside(false);
                    loginProgress.show();
                    loginUser(email, password);
                }
                else {
                    loginProgress.dismiss();
                    Toast.makeText(getApplicationContext(), "Hatalı Giriş", Toast.LENGTH_SHORT).show();
                }
            }

        });

    }

    private void loginUser(String email, String password) {
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    loginProgress.dismiss();
                    Toast.makeText(getApplicationContext(), "Giriş Başarılı", Toast.LENGTH_SHORT).show();
                    Intent mainIntent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(mainIntent);

                } else {
                    loginProgress.dismiss();
                    Toast.makeText(getApplicationContext(), "Giriş Yapılamadı" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

}