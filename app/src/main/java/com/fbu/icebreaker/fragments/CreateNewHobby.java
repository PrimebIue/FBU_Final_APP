package com.fbu.icebreaker.fragments;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import com.fbu.icebreaker.R;
import com.fbu.icebreaker.subclasses.Hobby;
import com.fbu.icebreaker.util.GoogleCustomSearch;
import com.parse.ParseFile;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

import me.kaede.tagview.OnTagClickListener;
import me.kaede.tagview.Tag;
import me.kaede.tagview.TagView;

/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */
public class CreateNewHobby extends DialogFragment {

    private static final String TAG = "CreateNewHobby";
    private static final String[] HOBBY_TAGS = new String[]{"Sport", "Endurance", "Art", "Esport", "Caring", "Instrument", "Music", "Extreme", "Handcraft", "Technology", "Building", "Outdoors", "Technical Skill"};

    private final ArrayList<String> newHobbyTags = new ArrayList<String>();

    private GoogleCustomSearch googleCustomSearch;

    private EditText etHobbyName;

    private Button btnAddHobby;
    private Button btnCancelHobbyAdd;

    private TagView tvSelectedTags;
    private TagView tvTagsToSelect;
    private TagView tvTagsToSelect2;
    private TagView tvTagsToSelect3;
    private TagView tvTagsToSelect4;

    public CreateNewHobby() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_create_new_hobby, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Objects.requireNonNull(getDialog()).getWindow().setLayout(getResources().getDisplayMetrics().widthPixels, getResources().getDisplayMetrics().heightPixels);

        etHobbyName = view.findViewById(R.id.etHobbyName);
        btnCancelHobbyAdd = view.findViewById(R.id.btnCancelHobbyAdd);
        btnAddHobby = view.findViewById(R.id.btnAddHobby);
        tvTagsToSelect = view.findViewById(R.id.tvTagsToSelect);
        tvTagsToSelect2 = view.findViewById(R.id.tvTagsToSelect2);
        tvTagsToSelect3 = view.findViewById(R.id.tvTagsToSelect3);
        tvTagsToSelect4 = view.findViewById(R.id.tvTagsToSelect4);
        tvSelectedTags = view.findViewById(R.id.tvSelectedTags);

        Dialog dialog = getDialog();

        googleCustomSearch = new GoogleCustomSearch();

        // Had to use multiple TagView because it was impossible to fit the tags without modifying the library
        for (int i = 0; i < HOBBY_TAGS.length; i++) {
            Tag tag = new Tag(HOBBY_TAGS[i]);
            tag.tagTextSize = 12;
            if (i <= 4)
                tvTagsToSelect.addTag(tag);
            else if (i <= 8)
                tvTagsToSelect2.addTag(tag);
            else if (i <= 11)
                tvTagsToSelect3.addTag(tag);
            else
                tvTagsToSelect4.addTag(tag);
        }

        tvTagsToSelect.setOnTagClickListener(new OnTagClickListener() {
            @Override
            public void onTagClick(int i, Tag tag) {
                onAddTagClick(tag);
            }
        });

        tvTagsToSelect2.setOnTagClickListener(new OnTagClickListener() {
            @Override
            public void onTagClick(int i, Tag tag) {
                onAddTagClick(tag);
            }
        });

        tvTagsToSelect3.setOnTagClickListener(new OnTagClickListener() {
            @Override
            public void onTagClick(int i, Tag tag) {
                onAddTagClick(tag);
            }
        });

        tvTagsToSelect4.setOnTagClickListener(new OnTagClickListener() {
            @Override
            public void onTagClick(int i, Tag tag) {
                onAddTagClick(tag);
            }
        });

        tvSelectedTags.setOnTagClickListener(new OnTagClickListener() {
            @Override
            public void onTagClick(int i, Tag tag) {
                tvSelectedTags.remove(i);
                newHobbyTags.remove(i);
            }
        });

        btnCancelHobbyAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Objects.requireNonNull(getDialog()).dismiss();
            }
        });

        btnAddHobby.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Get first image from google search
                googleCustomSearch.fetchHobbyResources(etHobbyName.getText().toString(), newHobbyTags, getContext(), dialog);
            }
        });
    }

    void onAddTagClick(Tag tag) {
        if (newHobbyTags.size() < 4) {
            String tagName = tag.text;
            newHobbyTags.add(tagName);
            tvSelectedTags.addTag(tag);
        } else {
            Toast.makeText(getContext(), "Sorry, you can only add 4 tags per hobby", Toast.LENGTH_SHORT).show();
        }
    }
}