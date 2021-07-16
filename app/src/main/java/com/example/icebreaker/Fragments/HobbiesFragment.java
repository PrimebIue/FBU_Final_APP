package com.example.icebreaker.Fragments;

import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.icebreaker.HobbiesAdapter;
import com.example.icebreaker.R;
import com.example.icebreaker.Subclasses.Hobby;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.parse.FindCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class HobbiesFragment extends Fragment {
    
    public static final String TAG = "HobbiesFragment";
    
    private HobbiesAdapter adapter;
    private List<Hobby> allHobbies;

    public HobbiesFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_hobbies, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        
        final RecyclerView rvHobbies = view.findViewById(R.id.rvHobbies);
        final FloatingActionButton btnAddHobby = view.findViewById(R.id.btnAddHobby);
        
        // Initialize the array
        allHobbies = new ArrayList<>();
        adapter = new HobbiesAdapter(getContext(), allHobbies);
        
        // Set the adapter
        rvHobbies.setAdapter(adapter);
        
        // Create layout manager
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        rvHobbies.setLayoutManager(linearLayoutManager);

        // Click listener for adding hobbies
        btnAddHobby.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addHobbyDialog();
            }
        });
        queryHobbies();
    }

    private void addHobbyDialog() {
        FragmentManager fragmentManager = getFragmentManager();
        AddHobbyFragment addHobbyFragment = new AddHobbyFragment();
        addHobbyFragment.show(fragmentManager, "addHobbyFragment");

        fragmentManager.executePendingTransactions();
        addHobbyFragment.getDialog().setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                queryHobbiesUpdate();
            }
        });
    }

    private void queryHobbies() {

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
                allHobbies.addAll(hobbies);
                adapter.notifyDataSetChanged();
            }
        });
    }

    private void queryHobbiesUpdate() {
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

                allHobbies.clear();
                adapter.notifyDataSetChanged();

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
