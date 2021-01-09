package com.example.fitifit;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class AdminMainActivity extends AppCompatActivity {

    private CardView userProfileCard,withdrawCard,amazonCard,deleteUsers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_main);

        init();
        clickListener();

    }

    private void init(){
       // androidx.appcompat.widget.Toolbar toolbar= (Toolbar) findViewById(R.id.toolbar);
        //setActionBar(toolbar);
        //if(getActionBar() != null)
          //  getSupportActionBar().setTitle("Admin...");

        userProfileCard =  findViewById(R.id.profilesCard);
        withdrawCard =  findViewById(R.id.withdrawCard);
        amazonCard =  findViewById(R.id.amazonCard);
        deleteUsers = findViewById(R.id.deleteUser);
    }

    private void clickListener(){
        userProfileCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AdminMainActivity.this,ProfilesActivity.class));
            }
        });
    }
}
