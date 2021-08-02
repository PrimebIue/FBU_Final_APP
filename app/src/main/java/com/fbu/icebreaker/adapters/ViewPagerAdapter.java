package com.fbu.icebreaker.adapters;

import android.os.Bundle;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.fbu.icebreaker.fragments.ConversationStartersFragment;
import com.fbu.icebreaker.fragments.HobbyPairingsByTagFragment;
import com.fbu.icebreaker.fragments.PairedProfileFragment;
import com.fbu.icebreaker.subclasses.Hobby;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class ViewPagerAdapter extends FragmentPagerAdapter {

    private static final String TAG = "ViewPagerAdapter";

    private static final String USERID_KEY = "userId";
    private static final String USER_HOBBIES_KEY = "userHobbies";
    private static final String QR_HOBBIES_KEY = "qrHobbies";
    private static final String USER_KEY = "user";

    private final int numOfTabs;

    private final String userId;

    private List<Hobby> qrHobbies;
    private List<Hobby> userHobbies;

    private ParseUser user;

    public ViewPagerAdapter(@NonNull @NotNull FragmentManager fm, String userId, int numOfTabs) {
        super(fm);
        this.userId = userId;
        this.numOfTabs = numOfTabs;
    }

    @NonNull
    @NotNull
    @Override
    public Fragment getItem(int position) {
        PairedProfileFragment pairedProfileFragment = new PairedProfileFragment();
        HobbyPairingsByTagFragment hobbyPairingsByTagFragment = new HobbyPairingsByTagFragment();
        ConversationStartersFragment conversationStartersFragment = new ConversationStartersFragment();

        userHobbies = new ArrayList<>();
        qrHobbies = new ArrayList<>();

        ParseQuery<ParseUser> userQuery = ParseUser.getQuery();
        userQuery.whereEqualTo("objectId", userId);
        try {
            user = userQuery.find().get(0);
            queryCurrUserHobbies();
            queryQRHobbies();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Bundle bundle = new Bundle();
        bundle.putString(USERID_KEY, userId);
        bundle.putParcelableArrayList(USER_HOBBIES_KEY, (ArrayList<? extends Parcelable>) userHobbies);
        bundle.putParcelableArrayList(QR_HOBBIES_KEY, (ArrayList<? extends Parcelable>) qrHobbies);
        bundle.putParcelable(USER_KEY, user);

        switch (position) {
            case 0:
                pairedProfileFragment.setArguments(bundle);
                return pairedProfileFragment;
            case 1:
                hobbyPairingsByTagFragment.setArguments(bundle);
                return hobbyPairingsByTagFragment;
            case 2:
                conversationStartersFragment.setArguments(bundle);
                return conversationStartersFragment;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return numOfTabs;
    }

    private void queryCurrUserHobbies() throws ParseException {
        // Specify data to query
        ParseQuery<Hobby> query =  ParseQuery.getQuery(Hobby.class);
        query.include("usersWithHobby");
        query.whereEqualTo("usersWithHobby", ParseUser.getCurrentUser());
        userHobbies.addAll(query.find());
    }

    private void queryQRHobbies() throws ParseException {
        // Specify data to query
        ParseQuery<Hobby> query =  ParseQuery.getQuery(Hobby.class);
        query.include("usersWithHobby");
        query.whereEqualTo("usersWithHobby", user);
        qrHobbies.addAll(query.find());
    }
}
