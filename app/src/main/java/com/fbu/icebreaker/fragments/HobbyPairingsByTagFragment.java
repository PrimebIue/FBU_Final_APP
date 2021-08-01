package com.fbu.icebreaker.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ListAdapter;

import com.fbu.icebreaker.R;
import com.fbu.icebreaker.adapters.GridHobbyPairingAdapter;
import com.fbu.icebreaker.subclasses.PairingsByTag;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class HobbyPairingsByTagFragment extends Fragment {

    private GridView gvHobbyPairings;
    private GridHobbyPairingAdapter adapter;

    List<PairingsByTag> pairingsByTag;

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

        gvHobbyPairings = (GridView) view.findViewById(R.id.gvHobbyPairings);

        pairingsByTag = new ArrayList<>();
        adapter = new GridHobbyPairingAdapter(getContext(), pairingsByTag);
        gvHobbyPairings.setAdapter(adapter);



    }
}