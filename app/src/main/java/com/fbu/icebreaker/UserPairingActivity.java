package com.fbu.icebreaker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.fbu.icebreaker.subclasses.Hobby;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

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
        viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(), userId);
        viewPager.setAdapter(viewPagerAdapter);

    }
}