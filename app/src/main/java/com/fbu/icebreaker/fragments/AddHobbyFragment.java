package com.fbu.icebreaker.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.fbu.icebreaker.MultiSelectionAdapter;
import com.fbu.icebreaker.R;
import com.fbu.icebreaker.subclasses.Hobby;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.util.ArrayList;
import java.util.List;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddHobbyFragment extends DialogFragment {

    private static final String TAG = "AddHobbyFragment";

    private RecyclerView rvHobbySelector;
    private List<Hobby> allHobbies;
    private MultiSelectionAdapter adapter;
    private FloatingActionButton btnAdd;

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
        rvHobbySelector = view.findViewById(R.id.rvHobbySelector);
        btnAdd = view.findViewById(R.id.btnAdd);


        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        rvHobbySelector.setLayoutManager(layoutManager);

        allHobbies = new ArrayList<>();
        adapter = new MultiSelectionAdapter(getContext(), allHobbies);
        rvHobbySelector.setAdapter(adapter);

        queryHobbies();

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (adapter.getSelected().size() > 0) {

                    // TODO -- Only update hobby relation not create new hobby
                    for (int i = 0; i < adapter.getSelected().size(); i++) {

                        adapter.getSelected().get(i).getRelation("usersWithHobby").add(ParseUser.getCurrentUser());
                        adapter.getSelected().get(i).saveInBackground(new SaveCallback() {
                            @Override
                            public void done(ParseException e) {
                                if (e != null) {
                                    Log.e(TAG, "Error while saving hobby.", e);
                                    Toast.makeText(getContext(), R.string.saving_error, Toast.LENGTH_SHORT).show();
                                }
                                Log.i(TAG, "Hobby save successful");
                            }
                        });
                    }
                }
                getDialog().dismiss();
            }
        });
    }

    private void queryHobbies() {

        // TODO -- This currently gets all hobbies, it eventually has to only include hobbies the user doesnt already have
        // Specify data to query
        ParseQuery<Hobby> query =  ParseQuery.getQuery(Hobby.class);
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
                allHobbies.addAll(hobbies);
                adapter.notifyDataSetChanged();
            }
        });
    }
}