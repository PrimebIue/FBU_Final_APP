package com.fbu.icebreaker.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.fbu.icebreaker.HobbiesAdapter;
import com.fbu.icebreaker.R;
import com.fbu.icebreaker.subclasses.Hobby;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class PairedProfileFragment extends Fragment {

    private final static String TAG = "PairedProfileFragment";

    private ImageView ivProfilePicture;
    private TextView tvUsername;
    private TextView tvHobbiesNumber;
    private TextView tvBio;
    private ParseUser user;
    private List<Hobby> allHobbies;
    private List<Hobby> qrHobbies;

    private HobbiesAdapter adapter;
    private RecyclerView rvPairedHobbies;

    public PairedProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_paired_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ivProfilePicture = view.findViewById(R.id.ivProfilePicture);
        tvUsername = view.findViewById(R.id.tvUsername);
        tvBio = view.findViewById(R.id.tvBio);
        tvHobbiesNumber = view.findViewById(R.id.tvHobbiesNumber);
        allHobbies = new ArrayList<>();
        qrHobbies = new ArrayList<>();

        rvPairedHobbies = view.findViewById(R.id.rvPairedHobbies);
        adapter = new HobbiesAdapter(getContext(), allHobbies, null);
        rvPairedHobbies.setAdapter(adapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        rvPairedHobbies.setLayoutManager(linearLayoutManager);
        
        assert getArguments() != null;
        String userId = getArguments().getString("userId");

        ParseQuery<ParseUser> userQuery = ParseUser.getQuery();
        userQuery.whereEqualTo("objectId", userId);
        try {
            user = userQuery.find().get(0);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        tvUsername.setText(user.getUsername());
        tvBio.setText(user.getString("bio"));

        Glide.with(this)
                .load(user.getParseFile("profilePicture").getUrl())
                .into(ivProfilePicture);

        Log.i(TAG, "NEW VERSION");
        queryQRHobbies();
    }

    private void queryCurrUserHobbies() {
        // Specify data to query
        ParseQuery<Hobby> query =  ParseQuery.getQuery(Hobby.class);
        query.include("usersWithHobby");
        query.whereEqualTo("usersWithHobby", user);
        query.findInBackground(new FindCallback<Hobby>() {
            @Override
            public void done(List<Hobby> hobbies, ParseException e) {
                // Check for errors
                if (e != null) {
                    Log.e(TAG, "Issue with getting hobbies.", e);
                }

                for (Hobby hobby : qrHobbies) {
                    Log.i(TAG, "qrHobbies" + hobby.getName());
                }
                for (Hobby hobby : hobbies) {
                    Log.i(TAG, "allHobbies" + hobby.getName());
                    if (!qrHobbies.contains(hobby))
                        allHobbies.add(hobby);
                }

                adapter.notifyDataSetChanged();
            }
        });
    }

    private void queryQRHobbies() {

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

                qrHobbies.addAll(hobbies);

                tvHobbiesNumber.setText(String.valueOf(qrHobbies.size()));
                queryCurrUserHobbies();
            }
        });
    }
}