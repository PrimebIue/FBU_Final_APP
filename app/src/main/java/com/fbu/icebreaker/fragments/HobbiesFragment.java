package com.fbu.icebreaker.fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.fbu.icebreaker.R;
import com.fbu.icebreaker.adapters.HobbiesAdapter;
import com.fbu.icebreaker.subclasses.Hobby;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 */
public class HobbiesFragment extends Fragment {

    private static final String TAG = "HobbiesFragment";

    private HobbiesAdapter adapter;

    private List<Hobby> allHobbies;

    private SwipeRefreshLayout swipeContainer;

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

        HobbiesAdapter.OnClickListener onClickListener = position -> {
            Log.i(TAG, "Click at post position: " + position);

            DialogInterface.OnClickListener dialogClickListener = (dialog, which) -> {
                switch (which) {
                    case DialogInterface.BUTTON_POSITIVE:

                        allHobbies.get(position).getRelation("usersWithHobby").remove(ParseUser.getCurrentUser());
                        allHobbies.get(position).saveInBackground(e -> {
                            if (e != null) {
                                Log.e(TAG, "Error while removing hobby", e);
                                Toast.makeText(getContext(), getString(R.string.removing_hobby_error), Toast.LENGTH_SHORT).show();
                            }
                            Log.i(TAG, "Hobby removal successful");
                        });

                        queryHobbiesUpdate();
                        break;
                    case DialogInterface.BUTTON_NEGATIVE:
                        // do nothing
                        break;
                }
            };

            AlertDialog.Builder ab = new AlertDialog.Builder(getContext());
            ab.setMessage(getString(R.string.remove_hobby_question))
                    .setPositiveButton(R.string.remove, dialogClickListener)
                    .setNegativeButton(R.string.Cancel, dialogClickListener)
                    .show();
        };

        // Initialize the array
        allHobbies = new ArrayList<>();
        adapter = new HobbiesAdapter(getContext(), allHobbies, onClickListener);

        // Lookup the swipe container view
        swipeContainer = (SwipeRefreshLayout) view.findViewById(R.id.swipeContainer);

        // Setup refresh listener which triggers new data loading
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                queryHobbiesUpdate();
            }
        });

        // Refreshing colors
        swipeContainer.setColorSchemeResources(
                android.R.color.holo_purple,
                android.R.color.holo_red_light,
                android.R.color.holo_orange_light);

        // Set the adapter
        rvHobbies.setAdapter(adapter);

        // Create layout manager
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        rvHobbies.setLayoutManager(linearLayoutManager);

        // Click listener for adding hobbies
        btnAddHobby.setOnClickListener(v -> addHobbyDialog());
        queryHobbies();
    }

    private void addHobbyDialog() {
        FragmentManager fragmentManager = getFragmentManager();
        AddHobbyFragment addHobbyFragment = new AddHobbyFragment();
        assert fragmentManager != null;
        addHobbyFragment.show(fragmentManager, "addHobbyFragment");

        fragmentManager.executePendingTransactions();
        Objects.requireNonNull(addHobbyFragment.getDialog()).setOnDismissListener(dialog -> queryHobbiesUpdate());
    }

    private void queryHobbies() {
        // Specify data to query
        ParseQuery<Hobby> query = ParseQuery.getQuery(Hobby.class);
        query.include("usersWithHobby");
        query.whereEqualTo("usersWithHobby", ParseUser.getCurrentUser());
        query.findInBackground((hobbies, e) -> {
            // Check for errors
            if (e != null) {
                Log.e(TAG, "Issue with getting hobbies.", e);
            }

            for (Hobby hobby : hobbies) {
                Log.i(TAG, "Hobby: " + hobby.getName());
            }
            allHobbies.addAll(hobbies);
            adapter.notifyDataSetChanged();
        });
    }

    private void queryHobbiesUpdate() {
        // Specify data to query
        ParseQuery<Hobby> query = ParseQuery.getQuery(Hobby.class);
        query.include("usersWithHobby");
        query.whereEqualTo("usersWithHobby", ParseUser.getCurrentUser());
        query.findInBackground((hobbies, e) -> {
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

            swipeContainer.setRefreshing(false);
            adapter.notifyDataSetChanged();
        });
    }
}
