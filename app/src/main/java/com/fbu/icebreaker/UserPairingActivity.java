package com.fbu.icebreaker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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

    private ImageView ivProfilePicture;
    private TextView tvUsername;
    private TextView tvHobbiesNumber;
    private TextView tvBio;
    private ParseUser user;
    private List<Hobby> allHobbies;
    private List<Hobby> qrHobbies;

    private HobbiesAdapter adapter;
    private RecyclerView rvPairedHobbies;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_pairing);

        ivProfilePicture = findViewById(R.id.ivProfilePicture);
        tvUsername = findViewById(R.id.tvUsername);
        tvBio = findViewById(R.id.tvBio);
        tvHobbiesNumber = findViewById(R.id.tvHobbiesNumber);
        allHobbies = new ArrayList<>();
        qrHobbies = new ArrayList<>();

        rvPairedHobbies = findViewById(R.id.rvPairedHobbies);
        adapter = new HobbiesAdapter(this, allHobbies);
        rvPairedHobbies.setAdapter(adapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        rvPairedHobbies.setLayoutManager(linearLayoutManager);

        final String userId = getIntent().getStringExtra("userid");

        ParseQuery<ParseUser> userQuery = ParseUser.getQuery();
        userQuery.whereEqualTo("objectId", userId);
        try {
            user = userQuery.find().get(0);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        tvUsername.setText(user.getUsername());
        tvBio.setText(user.getString("bio"));

        Glide.with(this)
                .load(user.getParseFile("profilePicture"))
                .into(ivProfilePicture);

        Log.i(TAG, "NEW VERSION");
        queryQRHobbies();
    }

    private void queryCurrUserHobbies() {

        // Specify data to query
        ParseQuery<Hobby> query =  ParseQuery.getQuery(Hobby.class);
        query.include("usersWithHobby");
        query.whereEqualTo("usersWithHobby", user);
        query.findInBackground(new FindCallback<Hobby>() {
            @Override
            public void done(List<Hobby> hobbies, ParseException e) {
                // Check for errors
                if (e != null) {
                    Log.e(TAG, "Issue with getting hobbies.", e);
                }

                for (Hobby hobby : qrHobbies) {
                    Log.i(TAG, "qrHobbies" + hobby.getName());
                }
                for (Hobby hobby : hobbies) {
                    Log.i(TAG, "allHobbies" + hobby.getName());
                    if (!qrHobbies.contains(hobby))
                        allHobbies.add(hobby);
                }
                
                adapter.notifyDataSetChanged();
            }
        });
    }

    private void queryQRHobbies() {

        // Specify data to query
        ParseQuery<Hobby> query =  ParseQuery.getQuery(Hobby.class);
        query.include("usersWithHobby");
        query.whereEqualTo("usersWithHobby", ParseUser.getCurrentUser());
        query.findInBackground(new FindCallback<Hobby>() {
            @Override
            public void done(List<Hobby> hobbies, ParseException e) {
                // Check for errors
                if (e != null) {
                    Log.e(TAG, "Issue with getting hobbies.", e);
                }

                qrHobbies.addAll(hobbies);

                tvHobbiesNumber.setText(String.valueOf(qrHobbies.size()));
                queryCurrUserHobbies();
            }
        });
    }
}