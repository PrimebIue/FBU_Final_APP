package com.fbu.icebreaker.fragments;

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

import com.fbu.icebreaker.R;
import com.fbu.icebreaker.subclasses.Hobby;
import com.parse.ParseException;
import com.parse.SaveCallback;

/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */
public class CreateNewHobby extends DialogFragment {

    private static final String TAG = "CreateNewHobby";

    private EditText etHobbyName;
    private Button btnCancelHobbyAdd;
    private Button btnAddHobby;

    public CreateNewHobby() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_create_new_hobby, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        getDialog().getWindow().setLayout(1100, 500);

        etHobbyName = view.findViewById(R.id.etHobbyName);
        btnCancelHobbyAdd = view.findViewById(R.id.btnCancelHobbyAdd);
        btnAddHobby = view.findViewById(R.id.btnAddHobby);

        btnCancelHobbyAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDialog().dismiss();
            }
        });

        btnAddHobby.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveNewHobby();
            }
        });
    }

    private void saveNewHobby() {

        Hobby hobby = new Hobby();

        hobby.setName(etHobbyName.getText().toString());

        hobby.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if (e != null) {
                    Log.e(TAG, "Error while saving hobby.", e);
                    Toast.makeText(getContext(), R.string.saving_error, Toast.LENGTH_SHORT).show();
                }
                Log.i(TAG, "Hobby save successful");
            }
        });

        getDialog().dismiss();
    }
}