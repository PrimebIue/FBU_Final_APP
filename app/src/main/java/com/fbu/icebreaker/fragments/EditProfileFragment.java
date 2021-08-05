package com.fbu.icebreaker.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.bumptech.glide.Glide;
import com.fbu.icebreaker.R;
import com.parse.ParseUser;

import java.util.Objects;

public class EditProfileFragment extends DialogFragment {

    private static final String TAG = "DialogFragment";

    private Button btnProfilePhoto;
    private Button btnSubmit;

    private EditText etDescription;
    private EditText etUsername;

    private ImageView ivProfilePicture;

    public EditProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_edit_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ivProfilePicture = view.findViewById(R.id.ivProfilePicture);
        etUsername = view.findViewById(R.id.etUsernameSearch);
        etDescription = view.findViewById(R.id.etDescription);
        btnProfilePhoto = view.findViewById(R.id.btnProfilePhoto);
        btnSubmit = view.findViewById(R.id.btnSubmit);

        ParseUser user = ParseUser.getCurrentUser();

        Glide.with(this)
                .load(Objects.requireNonNull(user.getParseFile("profilePicture")).getUrl())
                .into(ivProfilePicture);

        etUsername.setText(user.getUsername());
        etDescription.setText(user.getString("bio"));

        btnSubmit.setOnClickListener(v -> submit());
    }

    private void submit() {
        String description = etDescription.getText().toString();
        String username = etUsername.getText().toString();
        if (description.isEmpty()) {
            Toast.makeText(getContext(), R.string.bio_empty_requirement, Toast.LENGTH_LONG).show();
            return;
        }
        if (username.isEmpty()) {
            Toast.makeText(getContext(), R.string.username_empty_requirement, Toast.LENGTH_LONG).show();
            return;
        }
        if (username.length() < 5) {
            Toast.makeText(getContext(), R.string.username_min_length, Toast.LENGTH_LONG).show();
            return;
        }
        Log.i(TAG, "on btnSubmit click");
        saveProfileChanges(description, username);
    }

    private void saveProfileChanges(String description, String username) {
        Log.i(TAG, "Saving changes...");
        ParseUser user = ParseUser.getCurrentUser();

        user.setUsername(username);
        user.put("bio", description);

        user.saveInBackground(e -> {
            if (e != null) {
                Log.e(TAG, "Error while saving", e);
                Toast.makeText(getContext(), R.string.saving_error, Toast.LENGTH_SHORT).show();
            }
            Log.i(TAG, getString(R.string.user_save_successful));
            Objects.requireNonNull(getDialog()).dismiss();
        });
    }
}