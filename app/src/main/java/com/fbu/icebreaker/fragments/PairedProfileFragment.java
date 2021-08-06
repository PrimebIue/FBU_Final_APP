package com.fbu.icebreaker.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.fbu.icebreaker.HobbyDetailsActivity;
import com.fbu.icebreaker.R;
import com.fbu.icebreaker.adapters.HobbiesAdapter;
import com.fbu.icebreaker.subclasses.Hobby;
import com.fbu.icebreaker.util.HobbyMethods;
import com.parse.ParseUser;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 */
public class PairedProfileFragment extends Fragment {

    private final static String TAG = "PairedProfileFragment";

    private HobbyMethods hobbyMethods;

    private ImageView ivProfilePicture;
    private TextView tvUsername;

    private TextView tvHobbiesNumber;
    private TextView tvBio;

    private ParseUser user;

    private List<Hobby> userHobbies;
    private List<Hobby> qrHobbies;
    private List<Hobby> allHobbies;

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
        rvPairedHobbies = view.findViewById(R.id.rvPairedHobbies);
        tvUsername = view.findViewById(R.id.tvUsername);
        tvBio = view.findViewById(R.id.tvBio);
        tvHobbiesNumber = view.findViewById(R.id.tvHobbiesNumber);

        allHobbies = new ArrayList<>();
        userHobbies = new ArrayList<>();
        qrHobbies = new ArrayList<>();

        hobbyMethods = new HobbyMethods();

        assert getArguments() != null;
        userHobbies = getArguments().getParcelableArrayList("userHobbies");
        qrHobbies = getArguments().getParcelableArrayList("qrHobbies");

        allHobbies.addAll(hobbyMethods.getEqualHobbies(qrHobbies, userHobbies));

        HobbiesAdapter.OnClickListener clickListener = new HobbiesAdapter.OnClickListener() {
            @Override
            public void onHobbyClicked(int position) {
                Intent i = new Intent(getContext(), HobbyDetailsActivity.class);
                i.putExtra("hobby", allHobbies.get(position));
                startActivity(i);
            }
        };

        adapter = new HobbiesAdapter(getContext(), allHobbies, clickListener);
        rvPairedHobbies.setAdapter(adapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        rvPairedHobbies.setLayoutManager(linearLayoutManager);

        tvHobbiesNumber.setText(String.valueOf(qrHobbies.size()));

        assert getArguments() != null;
        user = getArguments().getParcelable("user");

        tvUsername.setText(user.getUsername());
        tvBio.setText(user.getString("bio"));

        Glide.with(this)
                .load(Objects.requireNonNull(user.getParseFile("profilePicture")).getUrl())
                .into(ivProfilePicture);


        adapter.notifyDataSetChanged();
    }

    @Override
    public boolean onContextItemSelected(@NonNull @NotNull MenuItem item) {

        if (item.getItemId() == 121) {
            Toast.makeText(getContext(), R.string.removing_hobby_error, Toast.LENGTH_SHORT).show();
        }
        return super.onContextItemSelected(item);
    }
}