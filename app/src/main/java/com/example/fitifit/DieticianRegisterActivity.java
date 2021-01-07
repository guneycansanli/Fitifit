package com.example.fitifit;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

public class DieticianRegisterActivity extends AppCompatActivity {

    private EditText dieticianName,diticianEmail,dieticianPassword;
    private Button btnDieticianRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dietician_register);
    }
}