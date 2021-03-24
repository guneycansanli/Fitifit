package com.example.fitifit;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import com.example.fitifit.Fragment.HomeFragment;
import com.example.fitifit.Fragment.NotificaitonDieticianFragment;
import com.example.fitifit.Fragment.NotificitionFragment;
import com.example.fitifit.Fragment.ProfileFragment;
import com.example.fitifit.Fragment.SearchFragment;
import com.example.fitifit.Model.UserProfileModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {


    private FirebaseAuth mAuth;
    private Toolbar mToolbar;
    BottomNavigationView bottomNavigationView;
    Fragment selectedFragment = null;
    private List<UserProfileModel> mUsers;


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        super.onOptionsItemSelected(item);
        if (item.getItemId() == R.id.action_signout) {
            mAuth.signOut();
            Toast.makeText(getApplicationContext(), "Oturum Kapatıldı", Toast.LENGTH_SHORT).show();
            Intent loginIntent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(loginIntent);
        }
        else if(item.getItemId() == R.id.actions_settings){
           CheckForSettings();
        }
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mUsers = new ArrayList<>();
        bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(navigationItemSelectedListener);
        mAuth = FirebaseAuth.getInstance();
        mToolbar = (Toolbar) findViewById(R.id.mainToolbar);
        setSupportActionBar(mToolbar);
       // check();

        if (mAuth.getCurrentUser() == null) {
            Intent loginIntent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(loginIntent);
            Toast.makeText(getApplicationContext(), "Lütfen Giriş Yapınız", Toast.LENGTH_SHORT).show();
        }
        else {
            getSupportFragmentManager().beginTransaction().replace(R.id.frame_Layout,new ProfileFragment()).commit();
        }



    }
    private BottomNavigationView.OnNavigationItemSelectedListener navigationItemSelectedListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    switch (item.getItemId())
                    {
                        case R.id.nav_home:
                            selectedFragment = new HomeFragment();

                            break;

                        case R.id.nav_search:
                            selectedFragment = new SearchFragment();

                            break;

                        case R.id.nav_add:
                            selectedFragment = null;
                            startActivity(new Intent(MainActivity.this, PostActivity.class));

                            break;

                        case R.id.nav_favourite:
                            check();
                            break;

                        case R.id.nav_person:
                            SharedPreferences.Editor editor = getSharedPreferences("PREFS", MODE_PRIVATE).edit();
                            editor.putString("profileid", FirebaseAuth.getInstance().getCurrentUser().getUid());
                            editor.apply();
                            selectedFragment = new ProfileFragment();

                            break;
                    }
                    if(selectedFragment != null)
                    {

                        getSupportFragmentManager().beginTransaction().replace(R.id.frame_Layout,selectedFragment).commit();

                    }
                    return true;
                }
            };

    private void check(){
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users");
        DatabaseReference referenceDietician = FirebaseDatabase.getInstance().getReference("Dieticians");   //This Function for which accound we sign in (Dietician Or User)

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                mUsers.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    UserProfileModel user = dataSnapshot.getValue(UserProfileModel.class);

                    assert user != null;
                    assert firebaseUser != null;
                    if (user.getUid().equals(firebaseUser.getUid())) {
                        selectedFragment= new NotificitionFragment();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        referenceDietician.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                mUsers.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    UserProfileModel user = dataSnapshot.getValue(UserProfileModel.class);

                    assert user != null;
                    assert firebaseUser != null;
                    if (user.getUid().equals(firebaseUser.getUid())) {
                      selectedFragment=new NotificaitonDieticianFragment();
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void CheckForSettings(){
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users");
        DatabaseReference referenceDietician = FirebaseDatabase.getInstance().getReference("Dieticians");   //This Function for which accound we sign in (Dietician Or User)

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                mUsers.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    UserProfileModel user = dataSnapshot.getValue(UserProfileModel.class);

                    assert user != null;
                    assert firebaseUser != null;
                    if (user.getUid().equals(firebaseUser.getUid())) {
                        Intent settingsIntent= new Intent(MainActivity.this,SettingsActivity.class);
                        startActivity(settingsIntent);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        referenceDietician.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                mUsers.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    UserProfileModel user = dataSnapshot.getValue(UserProfileModel.class);

                    assert user != null;
                    assert firebaseUser != null;
                    if (user.getUid().equals(firebaseUser.getUid())) {
                        Intent settingsIntent= new Intent(MainActivity.this,SettingsDieticianActivity.class);
                        startActivity(settingsIntent);
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

}
