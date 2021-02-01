package com.example.fitifit;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import com.example.fitifit.Fragment.HomeFragment;
import com.example.fitifit.Fragment.NotificitionFragment;
import com.example.fitifit.Fragment.ProfileFragment;
import com.example.fitifit.Fragment.SearchFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private Toolbar mToolbar;
    BottomNavigationView bottomNavigationView;
    Fragment selectedFragment = null;


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
           Intent settingsIntent= new Intent(MainActivity.this,SettingsActivity.class);
           startActivity(settingsIntent);
        }
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(navigationItemSelectedListener);
        mAuth = FirebaseAuth.getInstance();
        mToolbar = (Toolbar) findViewById(R.id.mainToolbar);
        setSupportActionBar(mToolbar);

        getSupportFragmentManager().beginTransaction().replace(R.id.frame_Layout,new NotificitionFragment()).commit();

        if (mAuth.getCurrentUser() == null) {
            Intent loginIntent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(loginIntent);
            Toast.makeText(getApplicationContext(), "Lütfen Giriş Yapınız", Toast.LENGTH_SHORT).show();
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
                            selectedFragment = new NotificitionFragment();

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

}