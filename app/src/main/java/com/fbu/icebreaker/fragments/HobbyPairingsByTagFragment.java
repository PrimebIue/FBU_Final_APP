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

        getEqualHobbies();

        Hashtable<String, List<Hobby>> userHobbiesPerTag = new Hashtable<>();

        for (Hobby hobby : userHobbies) {
            if (!setAlreadyPairedHobbies.contains(hobby)) {
                for(String tag : hobby.getTags()) {
                    List<Hobby> hobbyListTag;

                    if (userHobbiesPerTag.get(tag) == null) {
                        hobbyListTag = new ArrayList<>();
                    } else {
                        hobbyListTag = userHobbiesPerTag.get(tag);
                    }
                    hobbyListTag.add(hobby);
                    userHobbiesPerTag.put(tag, hobbyListTag);
                }
            }
        }

        Hashtable<String, List<Hobby>> qrHobbiesPerTag = new Hashtable<>();

        for (Hobby hobby : qrHobbies) {
            if (!setAlreadyPairedHobbies.contains(hobby)) {
                for (String tag : hobby.getTags()) {
                    List<Hobby> qrHobbyListTag;

                    if (userHobbiesPerTag.get(tag) != null) {
                        if (qrHobbiesPerTag.get(tag) == null) {
                            qrHobbyListTag = new ArrayList<>();
                        } else {
                            qrHobbyListTag = qrHobbiesPerTag.get(tag);
                        }
                        qrHobbyListTag.add(hobby);
                        qrHobbiesPerTag.put(tag, qrHobbyListTag);
                    }
                }
            }
        }

        for (String tag : qrHobbiesPerTag.keySet()) {

            PairingsByTag currPairing = new PairingsByTag();

            qrHobbiesPerTag.get(tag).addAll(userHobbiesPerTag.get(tag));

            currPairing.setPairedTag(tag);
            currPairing.setPairedHobbies(qrHobbiesPerTag.get(tag));
            pairingsByTag.add(currPairing);
        }

        adapter.notifyDataSetChanged();
    }

    private void getEqualHobbies() {
        // Use set so that .contains is an O(1) operation and keep a time complexity of O(n)
        // Considering the creation of the set O(2n)
        Set<String> setQr = new HashSet<>();

        for (Hobby hobby : qrHobbies) {
            setQr.add(hobby.getObjectId());
        }

        for (Hobby hobby : userHobbies) {
            if (setQr.contains(hobby.getObjectId()))
                setAlreadyPairedHobbies.add(hobby);
        }
    }
}