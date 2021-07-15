package com.example.icebreaker.Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.icebreaker.HobbiesAdapter;
import com.example.icebreaker.R;
import com.example.icebreaker.Subclasses.Hobby;
import com.parse.ParseQuery;

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
        
        // Initialize the array
        allHobbies = new ArrayList<>();
        adapter = new HobbiesAdapter(getContext(), allHobbies);
        
        // Set the adapter
        rvHobbies.setAdapter(adapter);
        
        // Create layout manager
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        rvHobbies.setLayoutManager(linearLayoutManager);
        
        queryHobbies();
    }

    private void queryHobbies() {
        // Specify data to query
        ParseQuery<Hobby> query =  ParseQuery.getQuery(Hobby.class);
        // TODO--Create proper relation on Back4App to query correctly
    }
}
