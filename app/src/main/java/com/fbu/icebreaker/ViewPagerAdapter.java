package com.fbu.icebreaker;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.fbu.icebreaker.fragments.PairedProfileFragment;

import org.jetbrains.annotations.NotNull;

public class ViewPagerAdapter extends FragmentPagerAdapter {

    private static final String USERID_KEY = "userId";

    private String userId;

    public ViewPagerAdapter(@NonNull @NotNull FragmentManager fm, String userId) {
        super(fm);
        this.userId = userId;
    }

    @NonNull
    @NotNull
    @Override
    public Fragment getItem(int position) {
        PairedProfileFragment pairedProfileFragment = new PairedProfileFragment();
        position = position + 1;
        Bundle bundle = new Bundle();
        bundle.putString("message", "Fragment: " + position);
        bundle.putString(USERID_KEY, userId);
        pairedProfileFragment.setArguments(bundle);

        return pairedProfileFragment;
    }

    @Override
    public int getCount() {
        return 3;
    }
}
