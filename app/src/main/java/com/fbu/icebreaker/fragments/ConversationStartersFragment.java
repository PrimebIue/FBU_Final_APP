package com.fbu.icebreaker.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.fbu.icebreaker.R;
import com.fbu.icebreaker.adapters.ConversationPagerAdapter;
import com.fbu.icebreaker.subclasses.ConversationStarter;
import com.fbu.icebreaker.subclasses.Hobby;
import com.fbu.icebreaker.subclasses.PairingsByTag;
import com.fbu.icebreaker.util.HobbyMethods;
import com.google.android.material.tabs.TabLayout;
import com.parse.ParseException;
import com.parse.ParseQuery;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 */
public class ConversationStartersFragment extends Fragment {

    private ViewPager viewPager;
    private TabLayout tbConvoStarterTabLayout;

    private List<Hobby> qrHobbies;
    private List<Hobby> userHobbies;

    private HobbyMethods hobbyMethods;
    private List<PairingsByTag> pairingsByTag;

    private List<String> tagsUsed;
    private List<ConversationStarter> conversationStarterList;

    private ArrayList<Fragment> fragments;

    public ConversationStartersFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_conversation_starters, container, false);
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewPager = view.findViewById(R.id.vpConvoStarters);
        tbConvoStarterTabLayout = view.findViewById(R.id.tbConvoStarterTabLayout);

        hobbyMethods = new HobbyMethods();
        userHobbies = new ArrayList<>();
        qrHobbies = new ArrayList<>();
        pairingsByTag = new ArrayList<>();
        tagsUsed = new ArrayList<>();
        conversationStarterList = new ArrayList<>();
        fragments = new ArrayList<>();

        assert getArguments() != null;
        userHobbies = getArguments().getParcelableArrayList("userHobbies");
        qrHobbies = getArguments().getParcelableArrayList("qrHobbies");

        pairingsByTag.addAll(hobbyMethods.getSimilarHobbiesByTag(userHobbies, qrHobbies));

        try {
            tagsUsed.clear();
            for (PairingsByTag pairing : pairingsByTag) {
                tagsUsed.add(pairing.getPairedTag());
            }
            queryConversationStarters();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    private void queryConversationStarters() throws ParseException {
        ParseQuery<ConversationStarter> query = ParseQuery.getQuery(ConversationStarter.class);
        query.include("convoTags");
        query.whereContainedIn("convoTags", tagsUsed);
        conversationStarterList.addAll(query.find());

        for (ConversationStarter convo : conversationStarterList) {
            ConvoStarterItemFragment fragment = ConvoStarterItemFragment.getInstance(convo);
            fragments.add(fragment);
        }
        ConversationPagerAdapter pagerAdapter = new ConversationPagerAdapter(requireActivity().getSupportFragmentManager(), fragments);
        viewPager.setAdapter(pagerAdapter);
        tbConvoStarterTabLayout.setupWithViewPager(viewPager, true);
        pagerAdapter.notifyDataSetChanged();
    }
}