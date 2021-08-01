package com.fbu.icebreaker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.fbu.icebreaker.adapters.ViewPagerAdapter;

public class UserPairingActivity extends AppCompatActivity {

    private static final String TAG = "UserPairingActivity";

    private Toolbar toolbar;
    private ViewPager viewPager;
    private ViewPagerAdapter viewPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_pairing);

        toolbar = findViewById(R.id.tbTab);
        viewPager = findViewById(R.id.pager);

        final String userId = getIntent().getStringExtra("userid");

        setSupportActionBar(toolbar);
        viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(), userId, 3);
        viewPager.setAdapter(viewPagerAdapter);

    }
}