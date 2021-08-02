package com.fbu.icebreaker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.annotation.SuppressLint;
import android.os.Bundle;

import com.fbu.icebreaker.fragments.HobbiesFragment;
import com.fbu.icebreaker.fragments.IcebreakFragment;
import com.fbu.icebreaker.fragments.ProfileFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    @SuppressLint("NonConstantResourceId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final BottomNavigationView bottomNavigationView;

        final FragmentManager fragmentManager = getSupportFragmentManager();

        bottomNavigationView = findViewById(R.id.bottomNavigation);

        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            Fragment fragment = null;
            switch (item.getItemId()) {
                case R.id.action_hobbies:
                    fragment = new HobbiesFragment();
                    break;
                case R.id.action_icebreak:
                    fragment = new IcebreakFragment();
                    break;
                case R.id.action_profile:
                    fragment = new ProfileFragment();
                    break;
                default:
                    break;
            }
            assert fragment != null;
            fragmentManager.beginTransaction().replace(R.id.flContainer, fragment).commit();
            return true;
        });

        // Default selection
        bottomNavigationView.setSelectedItemId(R.id.action_hobbies);
    }
}