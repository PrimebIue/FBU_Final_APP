package com.fbu.icebreaker;

import androidx.appcompat.app.AppCompatActivity;

import android.media.Image;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import org.w3c.dom.Text;

public class UserPairingActivity extends AppCompatActivity {

    private ImageView tvProfilePicture;
    private TextView tvUsername;
    private TextView tvHobbiesNumber;
    private TextView tvBio;
    private ParseUser user;

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
}