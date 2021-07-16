package com.example.icebreaker.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.icebreaker.R;
import com.example.icebreaker.subclasses.Hobby;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SaveCallback;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddHobbyFragment extends DialogFragment {

    public static final String TAG = "AddHobbyFragment";

    private EditText etHobbyName;
    private EditText etEmoji;

    public AddHobbyFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_hobby, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // Get field from view
        etHobbyName = (EditText) view.findViewById(R.id.etHobbyName);
        etEmoji = (EditText) view.findViewById(R.id.etEmoji);
        final Button btnAdd = view.findViewById(R.id.btnAdd);

        /*ParseObject hobby = new Hobby();*/
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create Parse hobby object
                Hobby hobby = new Hobby();

                String hobbyName = etHobbyName.getText().toString();
                String emoji = etEmoji.getText().toString();
                hobby.setEmoji(emoji);
                hobby.setName(hobbyName);
                hobby.getRelation("usersWithHobby").add(ParseUser.getCurrentUser());
                hobby.saveInBackground(new SaveCallback() {
                    @Override
                    public void done(ParseException e) {
                        if (e != null) {
                            Log.e(TAG, "Error while saving hobby.", e);
                            Toast.makeText(getContext(), "Error while saving!", Toast.LENGTH_SHORT).show();
                        }
                        Log.i(TAG, "Hobby save successful");
                        etHobbyName.setText("");
                        etEmoji.setText("");
                        getDialog().dismiss();
                    }
                });
            }
        });
    }
}