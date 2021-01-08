package com.example.fitifit;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class fitifitAdmin extends AppCompatActivity {

    private EditText emailAdmin,passwordAdmin;
    private TextView forgotPasswordAdmin;
    private Button loginAdmin;
    private FirebaseAuth auth;
    private DatabaseReference reference;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fitifit_admin);

        init();
        reference = FirebaseDatabase.getInstance().getReference().child("admin");

        loginAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String emailA = emailAdmin.getText().toString();
                String passwordA = passwordAdmin.getText().toString();

                if(TextUtils.isEmpty(emailA)) {
                    Toast.makeText(fitifitAdmin.this,"Input valid email",Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(passwordA)) {
                    Toast.makeText(fitifitAdmin.this,"Input valid password",Toast.LENGTH_SHORT).show();
                    return;
                }
                loginValidations(emailA,passwordA);


            }
        });
    }
    private void init(){
        emailAdmin = (EditText)findViewById(R.id.emailAdmin);
        passwordAdmin = (EditText) findViewById(R.id.passwordAdmin);
        forgotPasswordAdmin = (TextView)findViewById(R.id.forgotPasswordAdmin);
        loginAdmin= (Button)findViewById(R.id.loginBtnAdmin);

        auth = FirebaseAuth.getInstance();
    }

    private void loginValidations(final String email,final String password){
        //Here Checking for admin
        Query query = reference.orderByChild("admin").equalTo(true);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists() && snapshot.hasChild("email")){
                    String emailDB = snapshot.child("email").getValue(String.class);

                    if(emailDB.equalsIgnoreCase(email)){
                        loginUser(email,password);
                    }else {
                        Toast.makeText(fitifitAdmin.this,"Please input admin email",Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(fitifitAdmin.this,"Please input admin email",Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(fitifitAdmin.this,"Error"+error.getMessage(),
                        Toast.LENGTH_SHORT).show();
            }
        });

    }
    private void loginUser(String emailA,String passwordA){

        auth.signInWithEmailAndPassword(emailA,passwordA).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if (task.isSuccessful()){
                    startActivity(new Intent(fitifitAdmin.this,AdminMainActivity.class));
                }
                else {
                    Toast.makeText(fitifitAdmin.this,"Error"+task.getException().getMessage(),
                            Toast.LENGTH_SHORT).show();
                }


            }
        });

    }
}