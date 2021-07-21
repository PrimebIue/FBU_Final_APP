package com.fbu.icebreaker;

import androidx.appcompat.app.AppCompatActivity;

import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.fbu.icebreaker.subclasses.Hobby;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import org.w3c.dom.Text;

import java.util.List;

public class UserPairingActivity extends AppCompatActivity {

    private static final String TAG = "UserPairingActivity";

    private ImageView tvProfilePicture;
    private TextView tvUsername;
    private TextView tvHobbiesNumber;
    private TextView tvBio;
    private ParseUser user;
    private List<Hobby> currUserHobbies;
    private List<Hobby> qrHobbies;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_pairing);

        tvUsername = findViewById(R.id.tvUsername);

        final String userId = getIntent().getStringExtra("userid");

        ParseQuery<ParseUser> userQuery = ParseUser.getQuery();
        userQuery.whereEqualTo("objectId", userId);
        try {
            user = userQuery.find().get(0);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        tvUsername.setText(user.getUsername());
    }

    private void queryCurrUserHobbies() {

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

                for (Hobby hobby : hobbies) {
                    Log.i(TAG, "Hobby: " + hobby.getName());
                }
                Log.i(TAG, "Gets here" + hobbies);
                currUserHobbies = hobbies;
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

                for (Hobby hobby : hobbies) {
                    Log.i(TAG, "Hobby: " + hobby.getName());
                }
                Log.i(TAG, "Gets here" + hobbies);
                qrHobbies = hobbies;
            }
        });
    }
}