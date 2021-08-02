package com.fbu.icebreaker.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fbu.icebreaker.R;
import com.fbu.icebreaker.adapters.HobbyPairingAdapter;
import com.fbu.icebreaker.hobbyPairingLogic.HobbyMethods;
import com.fbu.icebreaker.subclasses.Hobby;
import com.fbu.icebreaker.subclasses.PairingsByTag;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.List;
import java.util.Set;

/**
 * A simple {@link Fragment} subclass.
 */
public class HobbyPairingsByTagFragment extends Fragment {

    private HobbyPairingAdapter adapter;

    private HobbyMethods hobbyMethods;

    private List<Hobby> qrHobbies;
    private List<Hobby> userHobbies;

    private List<PairingsByTag> pairingsByTag;

    private RecyclerView rvHobbyPairings;

    private Set<Hobby> setAlreadyPairedHobbies;

    public HobbyPairingsByTagFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_hobby_pairings_by_tag, container, false);
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        rvHobbyPairings = view.findViewById(R.id.rvHobbyPairings);
        rvHobbyPairings.setHasFixedSize(true);
        final GridLayoutManager layout = new GridLayoutManager(getContext(), 2);

        hobbyMethods = new HobbyMethods();
        userHobbies = new ArrayList<>();
        qrHobbies = new ArrayList<>();
        pairingsByTag = new ArrayList<>();

        setAlreadyPairedHobbies = new HashSet<>();

        adapter = new HobbyPairingAdapter(getContext(), pairingsByTag);
        rvHobbyPairings.setAdapter(adapter);

        rvHobbyPairings.setLayoutManager(layout);

        assert getArguments() != null;
        userHobbies = getArguments().getParcelableArrayList("userHobbies");
        qrHobbies = getArguments().getParcelableArrayList("qrHobbies");

        pairingsByTag.addAll(hobbyMethods.getSimilarHobbiesByTag(userHobbies, qrHobbies));

        adapter.notifyDataSetChanged();
    }
}