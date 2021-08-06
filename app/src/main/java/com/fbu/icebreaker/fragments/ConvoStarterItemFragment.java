package com.fbu.icebreaker.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.fbu.icebreaker.R;
import com.fbu.icebreaker.subclasses.ConversationStarter;

import org.jetbrains.annotations.NotNull;
import org.w3c.dom.Text;

public class ConvoStarterItemFragment extends Fragment {

    private TextView tvConvoString;

    private ConversationStarter conversationStarter;

    public static ConvoStarterItemFragment getInstance(ConversationStarter conversationStarter) {
        ConvoStarterItemFragment fragment = new ConvoStarterItemFragment();

        Bundle bundle = new Bundle();
        bundle.putParcelable("conversationStarter", conversationStarter);
        fragment.setArguments(bundle);
        Log.i("Test", "here");
        return fragment;
    }

    @Override
    public void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        conversationStarter = getArguments().getParcelable("conversationStarter");
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_convo_starter_item, container, false);
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        tvConvoString = view.findViewById(R.id.tvConvoString);
        init();
    }

    private void init(){

        tvConvoString.setText(conversationStarter.getConvoString());

    }
}
