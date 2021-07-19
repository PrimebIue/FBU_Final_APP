package com.fbu.icebreaker;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

public class SignUpActivity extends AppCompatActivity {

    private static final String TAG = "SignUpActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        final EditText etUsername = findViewById(R.id.etUsername);
        final EditText etEmail = findViewById(R.id.etEmail);
        final EditText etPassword = findViewById(R.id.etPassword);
        final Button btnSignUp = findViewById(R.id.btnSignUp);

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create Parse user
                ParseUser user = new ParseUser();

                // Get strings from edit text
                String username = etUsername.getText().toString();
                String password = etPassword.getText().toString();
                String email = etEmail.getText().toString();
                // Set core properties
                user.setUsername(username);
                user.setPassword(password);
                user.setEmail(email);
                // invoke signup
                user.signUpInBackground(new SignUpCallback() {
                    @Override
                    public void done(ParseException e) {
                        if (e != null) {
                            Log.e(TAG, "Issue with SignUp", e);
                            Toast.makeText(SignUpActivity.this, R.string.sign_up_issue, Toast.LENGTH_SHORT).show();
                            return;
                        }
                        Toast.makeText(SignUpActivity.this, R.string.successful_sign_up, Toast.LENGTH_SHORT).show();
                        finish();
                    }
                });
            }
        });
    }
}