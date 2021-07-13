package com.example.icebreaker;

import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.parse.facebook.ParseFacebookUtils;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.telecom.Call;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;
import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.parse.ParseUser;
import com.parse.facebook.ParseFacebookUtils;
import org.json.JSONException;
import java.util.Arrays;
import java.util.Collection;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.Arrays;
import java.util.Collection;

public class LoginActivity extends AppCompatActivity {

    public static final String TAG = "LoginActivity";

    private CallbackManager callbackManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        callbackManager = CallbackManager.Factory.create();

        final Button btnLoginFB = findViewById(R.id.btnLoginFB);

        // Login
        btnLoginFB.setOnClickListener(v -> {
            final ProgressDialog dialog = new ProgressDialog(this);
            dialog.setTitle("Please, wait a moment.");
            dialog.setMessage("Logging in...");
            dialog.show();
            Collection<String> permissions = Arrays.asList("public_profile", "email");
            ParseFacebookUtils.logInWithReadPermissionsInBackground(this, permissions, (user, err) -> {
                dialog.dismiss();
                if (err != null) {
                    Log.e("FacebookLoginExample", "done: ", err);
                    Toast.makeText(this, err.getMessage(), Toast.LENGTH_LONG).show();
                } else if (user == null) {
                    Toast.makeText(this, "The user cancelled the Facebook login.", Toast.LENGTH_LONG).show();
                    Log.d("FacebookLoginExample", "Uh oh. The user cancelled the Facebook login.");
                } else if (user.isNew()) {
                    Toast.makeText(this, "User signed up and logged in through Facebook.", Toast.LENGTH_LONG).show();
                    Log.d("FacebookLoginExample", "User signed up and logged in through Facebook!");
                    getUserDetailFromFB();
                } else {
                    Toast.makeText(this, "User logged in through Facebook.", Toast.LENGTH_LONG).show();
                    Log.d("FacebookLoginExample", "User logged in through Facebook!");
                    showAlert("Oh, you!", "Welcome back!");
                }
            });
        });
    }


    private void getUserDetailFromFB() {
        GraphRequest request = GraphRequest.newMeRequest(AccessToken.getCurrentAccessToken(), (object, response) -> {
            ParseUser user = ParseUser.getCurrentUser();
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
                    showAlert("First Time Login!", "Welcome!");
                } else
                    showAlert("Error", e.getMessage());
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
                .setPositiveButton("OK", (dialog, which) -> {
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
        ParseFacebookUtils.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }
}
