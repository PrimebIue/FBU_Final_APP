package com.fbu.icebreaker;

import androidx.appcompat.app.AppCompatActivity;

import com.facebook.AccessToken;
import com.facebook.Profile;
import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.facebook.ParseFacebookUtils;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.renderscript.ScriptGroup;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.facebook.GraphRequest;
import com.parse.ParseUser;

import org.json.JSONException;

import java.util.Arrays;
import java.util.Collection;

import android.view.View;

public class LoginActivity extends AppCompatActivity {

    private static final String TAG = "LoginActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        if (ParseUser.getCurrentUser() != null) {
            Intent i = new Intent(this, MainActivity.class);
            startActivity(i);
            finish();
        }

        final Button btnLoginFB = findViewById(R.id.btnLoginFB);
        final EditText etUsername = findViewById(R.id.etUsername);
        final EditText etPassword = findViewById(R.id.etPassword);
        final Button btnLogin = findViewById(R.id.btnLogin);
        final Button btnRegister = findViewById(R.id.btnRegister);

        // Login with Facebook
        btnLoginFB.setOnClickListener(v -> {
            final ProgressDialog dialog = new ProgressDialog(this);
            dialog.setTitle(getString(R.string.wait_moment));
            dialog.setMessage(getString(R.string.login_in));
            dialog.show();
            Collection<String> permissions = Arrays.asList("public_profile", "email");
            ParseFacebookUtils.logInWithReadPermissionsInBackground(this, permissions, (user, err) -> {
                dialog.dismiss();
                if (err != null) {
                    Log.e(TAG, "done: ", err);
                    Toast.makeText(this, err.getMessage(), Toast.LENGTH_LONG).show();
                } else if (user == null) {
                    Toast.makeText(this, R.string.cancel_fb_login, Toast.LENGTH_LONG).show();
                    Log.d(TAG, "Uh oh. The user cancelled the Facebook login.");
                } else if (user.isNew()) {
                    Toast.makeText(this, R.string.sign_login_facebook, Toast.LENGTH_LONG).show();
                    Log.d(TAG, "User signed up and logged in through Facebook!");
                    getUserDetailFromFB();
                } else {
                    Toast.makeText(this, R.string.login_facebook, Toast.LENGTH_LONG).show();
                    Log.d(TAG, "User logged in through Facebook!");
                    showAlert(getString(R.string.oh_you), getString(R.string.welcome_back));
                }
            });
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "onClick login button");
                String email = etUsername.getText().toString();
                String password = etPassword.getText().toString();
                loginUser(email, password);
            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "onClick Register button");
                goSignUpActivity();
            }
        });
    }

    private void loginUser(String username, String password) {
        Log.i(TAG, "Attempting to login user with email: " + username);
        ParseUser.logInInBackground(username, password, new LogInCallback() {
            @Override
            public void done(ParseUser user, ParseException e) {
                if (e != null) {
                    Log.e(TAG, "Issue with login", e);
                    Toast.makeText(LoginActivity.this, R.string.login_issue, Toast.LENGTH_SHORT).show();
                    return;
                }
                goMainActivity();
                Toast.makeText(LoginActivity.this, R.string.success, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void goMainActivity() {
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
        finish();
    }

    private void goSignUpActivity() {
        Intent i = new Intent(this, SignUpActivity.class);
        startActivity(i);
    }

    private void getUserDetailFromFB() {
        ParseUser user = ParseUser.getCurrentUser();

        GraphRequest request = GraphRequest.newMeRequest(AccessToken.getCurrentAccessToken(), (object, response) -> {

            try {
                if (object.has("name"))
                    user.setUsername(object.getString("name"));
                if (object.has("email"))
                    user.setEmail(object.getString("email"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            user.saveInBackground(e -> {
                if (e == null) {
                    showAlert(getString(R.string.first_login), getString(R.string.welcome));
                } else
                    showAlert(getString(R.string.error), e.getMessage());
            });
        });

        Bundle parameters = new Bundle();
        parameters.putString("fields", "name,email");
        request.setParameters(parameters);
        request.executeAsync();
    }

    private void showAlert(String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton(R.string.ok, (dialog, which) -> {
                    dialog.cancel();
                    Intent intent = new Intent(this, MainActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                });
        AlertDialog ok = builder.create();
        ok.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        ParseFacebookUtils.onActivityResult(requestCode, resultCode, data);
    }
}
