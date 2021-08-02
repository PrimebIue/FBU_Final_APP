package com.fbu.icebreaker;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.ParseUser;

public class SignUpActivity extends AppCompatActivity {

    private static final String TAG = "SignUpActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        final Button btnSignUp = findViewById(R.id.btnSignUp);
        final EditText etEmail = findViewById(R.id.etEmail);
        final EditText etPassword = findViewById(R.id.etPassword);
        final EditText etUsername = findViewById(R.id.etUsername);

        btnSignUp.setOnClickListener(v -> {
            // Create Parse user
            ParseUser user = new ParseUser();

            // Get strings from edit text
            String email = etEmail.getText().toString();
            String password = etPassword.getText().toString();
            String username = etUsername.getText().toString();
            // Set core properties
            user.setEmail(email);
            user.setPassword(password);
            user.setUsername(username);
            // invoke signup
            user.signUpInBackground(e -> {
                if (e != null) {
                    Log.e(TAG, "Issue with SignUp", e);
                    Toast.makeText(SignUpActivity.this, R.string.sign_up_issue, Toast.LENGTH_SHORT).show();
                    return;
                }
                Toast.makeText(SignUpActivity.this, R.string.successful_sign_up, Toast.LENGTH_SHORT).show();
                finish();
            });
        });
    }
}