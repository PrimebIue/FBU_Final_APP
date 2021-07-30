package com.fbu.icebreaker.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.fbu.icebreaker.QRScanActivity;
import com.fbu.icebreaker.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.parse.ParseUser;

public class IcebreakFragment extends Fragment {

    private ImageView ivQRCode;
    private FloatingActionButton btnCamera;

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

        final String qrCodeUrl = String.format("https://api.qrserver.com/v1/create-qr-code/?size=500x500&data=%s", ParseUser.getCurrentUser().getObjectId());

        Glide.with(getContext())
                .load(qrCodeUrl)
                .into(ivQRCode);

        btnCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), QRScanActivity.class);
                startActivity(i);
            }
        });
    }
}