package com.fbu.icebreaker.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.fbu.icebreaker.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ConversationStartersFragment extends Fragment {

    public ConversationStartersFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_conversation_starters, container, false);
    }
}