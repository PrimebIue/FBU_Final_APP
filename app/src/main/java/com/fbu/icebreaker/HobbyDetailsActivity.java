package com.fbu.icebreaker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.fbu.icebreaker.subclasses.Hobby;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.util.List;

public class HobbyDetailsActivity extends AppCompatActivity {

    private static final String TAG = "HobbyDetailsActivity";

    private Hobby hobby;

    private ImageView ivHobbyImage;

    private TextView tvHobbyName;
    private TextView tvHobbyDescription;

    private Button btnAddHobby;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hobby_details);

        ivHobbyImage = findViewById(R.id.ivHobbyImage);
        tvHobbyName = findViewById(R.id.tvHobbyName);
        tvHobbyDescription = findViewById(R.id.tvHobbyDescription);
        btnAddHobby = findViewById(R.id.btnAddHobby);

        hobby = getIntent().getParcelableExtra("hobby");

        Glide.with(this)
                .load(hobby.getImage())
                .into(ivHobbyImage);

        tvHobbyName.setText(hobby.getName());

        tvHobbyDescription.setText(hobby.getDescription());

        btnAddHobby.setOnClickListener(v -> {
            hobby.getRelation("usersWithHobby").add(ParseUser.getCurrentUser());
            hobby.saveInBackground(e -> {
                if (e != null) {
                    Log.e(TAG, "Error while saving hobby.", e);
                    Toast.makeText(HobbyDetailsActivity.this, R.string.saving_error, Toast.LENGTH_SHORT).show();
                }
                Log.i(TAG, "Hobby save successful");
            });
        });
    }
}