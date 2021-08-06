package com.fbu.icebreaker.fragments;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.fbu.icebreaker.R;
import com.fbu.icebreaker.UserPairingActivity;
import com.fbu.icebreaker.adapters.UserSearchAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 */
public class SearchUserFragment extends DialogFragment {

    private static final String TAG = "SearchUserFragment";
    private static final String SCANNED_USER_ID_ID = "userid";

    private UserSearchAdapter adapter;

    private List<ParseUser> users;

    private RecyclerView rvUserSearch;
    private EditText etUsernameSearch;
    private FloatingActionButton btnSearchUser;

    public SearchUserFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_search_user, container, false);
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Objects.requireNonNull(getDialog()).getWindow().setLayout(getResources().getDisplayMetrics().widthPixels - 30, getResources().getDisplayMetrics().heightPixels - 30);

        rvUserSearch = view.findViewById(R.id.rvUserSearch);
        etUsernameSearch = view.findViewById(R.id.etUsernameSearch);
        btnSearchUser = view.findViewById(R.id.btnSearchUser);

        UserSearchAdapter.OnClickListener onClickListener = position -> {
            Intent i = new Intent(getActivity(), UserPairingActivity.class);
            i.putExtra(SCANNED_USER_ID_ID, users.get(position).getObjectId());
            startActivity(i);
            Objects.requireNonNull(getDialog()).dismiss();
        };

        users = new ArrayList<>();
        adapter = new UserSearchAdapter(getContext(), users, onClickListener);

        rvUserSearch.setAdapter(adapter);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        rvUserSearch.setLayoutManager(linearLayoutManager);

        rvUserSearch.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));

        btnSearchUser.setOnClickListener(v -> UserSearchQuery(etUsernameSearch.getText().toString()));
    }

    private void UserSearchQuery(String searchData) {
        ParseQuery<ParseUser> query = ParseUser.getQuery();
        query.whereMatches("username", "("+searchData+")", "i");
        query.findInBackground((objects, e) -> {
            if (e != null) {
                Log.e(TAG, "Issue with searching users.", e);
            }
            users.clear();
            users.addAll(objects);
            Log.i(TAG, "Users: " + objects.toString());
            adapter.notifyDataSetChanged();
        });
    }
}