package com.example.icebreaker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.icebreaker.Fragments.HobbiesFragment;
import com.example.icebreaker.Fragments.IcebreakFragment;
import com.example.icebreaker.Fragments.ProfileFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final FragmentManager fragmentManager = getSupportFragmentManager();

        final BottomNavigationView bottomNavigationView;

        bottomNavigationView = findViewById(R.id.bottomNavigation);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment fragment = null;
                switch (item.getItemId()) {
                    case R.id.action_hobbies:
                        Toast.makeText(MainActivity.this, "Hobbies!", Toast.LENGTH_SHORT).show();
                        fragment = new HobbiesFragment();
                        break;
                    case R.id.action_icebreak:
                        Toast.makeText(MainActivity.this, "IceBreak!", Toast.LENGTH_SHORT).show();
                        fragment = new IcebreakFragment();
                        break;
                    case R.id.action_profile:
                        Toast.makeText(MainActivity.this, "Profile!", Toast.LENGTH_SHORT).show();
                        fragment = new ProfileFragment();
                        break;
                    default:
                        break;
                }
                fragmentManager.beginTransaction().replace(R.id.flContainer, fragment).commit();
                return true;
            }
        });

        // Default selection
        bottomNavigationView.setSelectedItemId(R.id.action_hobbies);
    }
}