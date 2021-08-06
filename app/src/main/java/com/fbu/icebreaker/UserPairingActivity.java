package com.fbu.icebreaker;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.ViewPager;

import com.fbu.icebreaker.adapters.ViewPagerAdapter;

import nl.dionsegijn.konfetti.KonfettiView;

import nl.dionsegijn.konfetti.models.Shape;
import nl.dionsegijn.konfetti.models.Size;

public class UserPairingActivity extends AppCompatActivity {

    private Toolbar toolbar;

    private ViewPagerAdapter viewPagerAdapter;

    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_pairing);

        final KonfettiView konfettiView = findViewById(R.id.viewKonfetti);

        konfettiView.build()
                .addColors(Color.CYAN, Color.YELLOW, Color.WHITE)
                .setDirection(0.0, 359.0)
                .setSpeed(1f, 5f)
                .setFadeOutEnabled(true)
                .setTimeToLive(2000L)
                .addShapes(Shape.Square.INSTANCE, Shape.Circle.INSTANCE)
                .addSizes(new Size(12, 5f))
                .setPosition(-50f, konfettiView.getWidth() + 2000f, -50f, -50f)
                .streamFor(300, 2500L);

        toolbar = findViewById(R.id.tbTab);
        viewPager = findViewById(R.id.pager);

        final String userId = getIntent().getStringExtra("userid");

        setSupportActionBar(toolbar);
        viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(), userId, 3);
        viewPager.setAdapter(viewPagerAdapter);
    }
}