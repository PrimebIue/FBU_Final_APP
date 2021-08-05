package com.fbu.icebreaker.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.bumptech.glide.Glide;
import com.fbu.icebreaker.QRScanActivity;
import com.fbu.icebreaker.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.ortiz.touchview.TouchImageView;
import com.parse.ParseUser;

import java.util.Objects;

public class IcebreakFragment extends Fragment {

    private FloatingActionButton btnCamera;
    private FloatingActionButton btnSearchUser;

    private TouchImageView ivQRCode;

    public IcebreakFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_icebreak, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ivQRCode = view.findViewById(R.id.ivQRCode);

        btnCamera = view.findViewById(R.id.btnCamera);
        btnSearchUser = view.findViewById(R.id.btnSearchUser);

        final String qrCodeUrl = String.format("https://api.qrserver.com/v1/create-qr-code/?size=500x500&data=%s", ParseUser.getCurrentUser().getObjectId());

        Glide.with(requireContext())
                .load(qrCodeUrl)
                .into(ivQRCode);

        ivQRCode.setMinZoom(0.1f);
        ivQRCode.setZoom(0.3f);

        btnCamera.setOnClickListener(v -> {
            Intent i = new Intent(getActivity(), QRScanActivity.class);
            startActivity(i);
        });

        btnSearchUser.setOnClickListener(v -> {
            addSearchUserDialog();
        });
    }

    private void addSearchUserDialog() {
        FragmentManager fragmentManager = getFragmentManager();
        SearchUserFragment searchUserFragment = new SearchUserFragment();
        assert fragmentManager != null;
        searchUserFragment.show(fragmentManager, "searchHobbyFragment");
    }
}